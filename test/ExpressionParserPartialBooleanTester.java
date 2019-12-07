import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

/**
 * Code to test Project 5; you should definitely add more tests!
 */
public class ExpressionParserPartialBooleanTester {
    private SimpleExpressionParser _parser;

    @Before
    /**
     * Instantiates the actors and movies graphs
     */
    public void setUp () throws IOException {
        _parser = new SimpleExpressionParser();
    }

    @Test
    /**
     * Just verifies that the SimpleExpressionParser could be instantiated without crashing.
     */
    public void simpleBooleanExpression() {
        assertTrue(_parser.parseBoolean("(x+x*x)+(x)*5*4*3+8*(((x)))*3*(x)"));
    }
}
