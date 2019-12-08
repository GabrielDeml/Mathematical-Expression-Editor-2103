import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Code to test Project 5; you should definitely add more tests!
 */
public class ExpressionParserPartialTester {
    private ExpressionParser _parser;

    /**
     * Instantiates the actors and movies graphs
     */
    @Before
    public void setUp() {
        _parser = new SimpleExpressionParser();
    }

    /**
     * Just verifies that the SimpleExpressionParser could be instantiated without crashing.
     */
    @Test
    public void finishedLoading() {
        assertTrue(true);
        // Yay! We didn't crash
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpression1() throws ExpressionParseException {
        final String expressionStr = "a+b";
        final String parseTreeStr = "+\n\ta\n\tb\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpression2() throws ExpressionParseException {
        final String expressionStr = "13*x";
        final String parseTreeStr = "*\n\t13\n\tx\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpression3() throws ExpressionParseException {
        final String expressionStr = "-4*(z+5*x)";
        final String parseTreeStr = "*\n\t-4\n\t()\n\t\t+\n\t\t\tz\n\t\t\t*\n\t\t\t\t5\n\t\t\t\tx\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpression4() throws ExpressionParseException {
        final String expressionStr = "(x*x+x)";
        final String parseTreeStr = "()\n\t+\n\t\t*\n\t\t\tx\n\t\t\tx\n\t\tx\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpression5() throws ExpressionParseException {
        final String expressionStr = "1*2+-34";
        final String parseTreeStr = "+\n\t*\n\t\t1\n\t\t2\n\t-34\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpression6() throws ExpressionParseException {
        final String expressionStr = "1+2*3";
        final String parseTreeStr = "+\n\t1\n\t*\n\t\t2\n\t\t3\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpressionAndFlatten1() throws ExpressionParseException {
        final String expressionStr = "1+2+3";
        final String parseTreeStr = "+\n\t1\n\t2\n\t3\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test
    public void testExpressionAndFlatten2() throws ExpressionParseException {
        final String expressionStr = "(x+(x)+(x+x)+x)";
        final String parseTreeStr = "()\n\t+\n\t\tx\n\t\t()\n\t\t\tx\n\t\t()\n\t\t\t+\n\t\t\t\tx\n\t\t\t\tx\n\t\tx\n";
        assertEquals(parseTreeStr, _parser.parse(expressionStr, false).convertToString(0));
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test(expected = ExpressionParseException.class)
    public void testException1() throws ExpressionParseException {
        final String expressionStr = "1+2+";
        _parser.parse(expressionStr, false);
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test(expected = ExpressionParseException.class)
    public void testException2() throws ExpressionParseException {
        final String expressionStr = "((()))";
        _parser.parse(expressionStr, false);
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test(expected = ExpressionParseException.class)
    public void testException3() throws ExpressionParseException {
        final String expressionStr = "()()";
        _parser.parse(expressionStr, false);
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test(expected = ExpressionParseException.class)
    public void testException4() throws ExpressionParseException {
        final String expressionStr = "(X*X)";
        _parser.parse(expressionStr, false);
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test(expected = ExpressionParseException.class)
    public void testException5() throws ExpressionParseException {
        final String expressionStr = "(x*6x)";
        _parser.parse(expressionStr, false);
    }

    /**
     * Verifies that a specific expression is parsed into the correct parse tree.
     */
    @Test(expected = ExpressionParseException.class)
    public void testException6() throws ExpressionParseException {
        final String expressionStr = "(x*xx)";
        _parser.parse(expressionStr, false);
    }
}
