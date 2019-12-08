import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {
    /**
     * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
     * Throws a ExpressionParseException if the specified string cannot be parsed.
     *
     * @param str                the string to parse into an expression tree
     * @param withJavaFXControls you can just ignore this variable for R1
     * @return the Expression object representing the parsed expression tree
     */
    public Expression parse(String str, boolean withJavaFXControls) throws ExpressionParseException {
        // Remove spaces -- this simplifies the parsing logic
        str = str.replaceAll(" ", "");
        Expression expression = parseExpression(str);
        if (expression == null) {
            // If we couldn't parse the string, then raise an error
            throw new ExpressionParseException("Cannot parse expression: " + str);
        }

        // Flatten the expression before returning
        expression.flatten();
        return expression;
    }

    /**
     * Attempts to parse the specified string into an Expression
     * Grammar:
     * - E → P | L | A | M
     * - P → (E)
     * - L → [a-z] | [0-9]+ | -[0-9]+
     * - A → E+E
     * - M → E*E
     *
     * @param str the string to attempt to parse
     * @return null if could not parse str, the associated Expression otherwise
     */
    protected Expression parseExpression(String str) {
        // See if it is a literal expression
        Expression exp = parseL(str);
        if (exp != null) return exp;
        // See if it is a parenthetical expression
        exp = parseP(str);
        if (exp != null) return exp;
        // See if it is an additive expression (THIS MUST BE BEFORE parseM TO KEEP ORDER OF OPERATIONS CORRECT!!!)
        exp = parseA(str);
        if (exp != null) return exp;
        // See if it is a multiplicative expression (if it isn't, this will return null)
        return parseM(str);
    }

    /**
     * Implement of P grammar
     *
     * @param exp the string to parse
     * @return Expression if exp is parsable or null if it is not
     */
    private Expression parseP(String exp) {
        // Check to see if exp is bounded by parens
        if (Pattern.compile("\\(.*\\)").matcher(exp).matches()) {
            // Check to see if what is inside the parens is an expression or not
            final Expression insides = parseExpression(exp.substring(1, exp.length() - 1));
            if (insides != null) return new ParentheticalExpression(insides);
        }
        return null; // Either not bounded by parens or insides of parens not an expression
    }

    /**
     * Implement of L grammar
     *
     * @param exp the string to parse
     * @return Expression if exp is parsable or null if it is not
     */
    private Expression parseL(String exp) {
        // Return a literal expression if exp matches the necessary regex, null otherwise
        return (Pattern.compile("[a-z]|(-?\\d+)").matcher(exp).matches()) ? new LiteralExpression(exp) : null;
    }

    /**
     * Parse a generic math operation (like addition or multiplication)
     * This method could be easily used with subtraction and division too
     *
     * @param exp       the string to parse
     * @param operation the operation to parse with (+ or *, but could also work with - and /)
     * @return a list of children expressions or null if it is not parsable
     */
    private List<Expression> parseGenericOperation(String exp, char operation) {
        // Find index of the operation (if it is in the string)
        for (int index = exp.indexOf(operation); index != -1; index = exp.indexOf(operation, index + 1)) {
            // Parse both side of the equation
            final Expression exp1 = parseExpression(exp.substring(0, index)),
                    exp2 = parseExpression(exp.substring(index + 1));
            // If both sides of the operation are valid expressions, return them as a list
            if (exp1 != null && exp2 != null) return Arrays.asList(exp1, exp2);
            // Else (one or more sides was not a valid expression), try again until no more operations in exp
        }
        // Return null if we weren't able to parse exp
        return null;
    }

    /**
     * Implement of A grammar
     *
     * @param exp the string to parse
     * @return Expression if exp is parsable or null if it is not
     */
    private Expression parseA(String exp) {
        // Parse using parseGenericOperation
        List<Expression> children = parseGenericOperation(exp, '+');
        // If parseGenericOperation didn't find anything return null else make a new node
        if (children == null) return null;
        return new AdditiveExpression(children);
    }

    /**
     * Implement of M grammar
     *
     * @param exp the string to parse
     * @return Expression if exp is parsable or null if it is not
     */
    private Expression parseM(String exp) {
        // Parse using parseGenericOperation
        List<Expression> children = parseGenericOperation(exp, '*');
        // If parseGenericOperation didn't find anything return null else make a new node
        if (children == null) return null;
        return new MultiplicativeExpression(children);
    }
}
