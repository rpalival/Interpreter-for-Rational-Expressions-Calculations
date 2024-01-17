package finalProject;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

//NOTE: Guidelines for INPUT Expression:

/*
 * Please input the expression with a space before and after any operator.
 * For example, use "2 + 2" instead of "2+2" and use "2 + (3 + 5) * 6"
 * This format is used to ensure readability and avoid confusion with any signs.
*/

//NOTE: I have given meaningful names to all the test cases, which accurately describe the test conditions being tested.

public class CalculatorTest {
	//Test Case 1
	@Test
    public void testEvaluateSimpleExpressionAddition() throws Exception {
        String input = "2 + 3";
        double expectedOutput = 5.0;
        double actualOutput = Calculator.expression(input);
        assertEquals(expectedOutput, actualOutput, 0.001);
    }
	
	//Test Case 2
	@Test
    public void testVariables() throws Exception {
        Calculator.define("x = 2");
        double result = Calculator.expression("x + 3");
        assertEquals(5.0, result, 0.001);
    }
	
	//Test Case 3
    @Test
    public void testEvaluateComplexExpression() throws Exception {
        String input = "2 * (3 + 4) - 5 / 2";
        double expectedOutput = 11.5;
        double actualOutput = Calculator.expression(input);
        assertEquals(expectedOutput, actualOutput, 0.001);
    }
    
    //Test Case 4
    @Test(expected = Exception.class)
    public void testEvaluateInvalidIncompleteExpression() throws Exception {
        String input = "2 +";
        Calculator.expression(input);
    }
    
    //Test Case 5
	@Test
	public void testEvaluateLongNumber() throws Exception {
		String input = "1000000000000000 + 3";
		double expectedOutput = 1000000000000003.0;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}
  	
  	//Test Case 6
	@Test
	public void testHugeExponentValue() throws Exception {
		Calculator.define("b = 2");
		double result = Calculator.expression("b ^ 100");
		assertEquals(1.2676506002282294E30, result, 0.001);
	}

	// Test Case 7
	@Test
	public void testEvaluateMutipleVariableAssignment() throws Exception {
		Calculator.define("a = 1");
		Calculator.define("b = 2");
		Calculator.define("c = 3");
		Calculator.define("d = 4");
		Calculator.define("e = 5");
		Calculator.define("f = 6");
		Calculator.define("g = 7");
		Calculator.define("h = 8");
		Calculator.define("i = 9");
		Calculator.define("j = 10");
		double result = Calculator.expression("a * b * c * d * e * f * g * h * i * j");
		assertEquals(3628800.0, result, 0.001);
	}

	// Test Case 8
	@Test(expected = Exception.class)
	public void testEvaluateInvalidBrackets() throws Exception {
		String input = "(5 * 3(";
		Calculator.expression(input);
	}

	// Test Case 9
	@Test
	public void negativeExponent() throws Exception {
		Calculator.define("x = 3");
		double result = Calculator.expression("x ^ -3");
		assertEquals(0.037037037037037035, result, 0.001);
	}

	// Test Case 10
	@Test(expected = Exception.class)
	public void testEvaluateUndefinedVariable() throws Exception {
		String input = "a + 2";
		Calculator.getVariableValue(input);
	}

	// Test Case 11
	@Test
	public void testEvaluateFractionAsExponent() throws Exception {
		String input = "2 ^ (3 / 4)";
		double expectedOutput = 1.6817928305074292;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 12
	@Test
	public void testEvaluateSimpleExpressionSubtraction() throws Exception {
		String input = "2 - 3";
		double expectedOutput = -1.0;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 13
	@Test
	public void testEvaluateSimpleExpressionMultiplication() throws Exception {
		String input = "2 * 3";
		double expectedOutput = 6.0;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 14
	@Test
	public void testEvaluateSimpleExpressionDivision() throws Exception {
		String input = "2 / 3";
		double expectedOutput = 0.6666666666666666;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 15
	@Test
	public void testEvaluateSimpleExpressionExponentiation() throws Exception {
		String input = "2 ^ 3";
		double expectedOutput = 8.0;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 16
	@Test
	public void testEvaluateNegativeNumber() throws Exception {
		String input = "-2 - 3";
		double expectedOutput = -5.0;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 17
	@Test
	public void testEvaluateRationalNumber() throws Exception {
		String input = "2.56 * 3.9878888";
		double expectedOutput = 10.208995328;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 18
	@Test
	public void testEvaluateZeroValueExponent() throws Exception {
		String input = "2.56 ^ 0";
		double expectedOutput = 1.0;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 19
	@Test
	public void testEvaluateExpressionAsExponent() throws Exception {
		String input = "(2 + 5) ^ (4 * 4)";
		double expectedOutput = 3.3232930569601E13;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}

	// Test Case 20
	@Test
	public void testEvaluateRationalExponentiation() throws Exception {
		double a = 2.0;
		double b = 1.5;
		String input = a + " ^ " + b;
		double expectedOutput = Math.pow(Math.E, b * Math.log(a));
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}
	
	// Test Case 21
	@Test(expected = Exception.class)
	public void testEvaluateDivisionByZero() throws Exception {
			String input = "5 / 0";
			Calculator.expression(input);
	}
	
	// Test Case 22
	@Test
	public void testHasInvalidSpecialCharacters() {
	    String expression = "2 * 3 / (4 & 5)";
	    assertTrue(Calculator.hasInvalidCharacters(expression));
	}
	
	// Test Case 23
	@Test
	public void testEvaluateOrderOfPrecedence() throws Exception {
		String input = "1 + 2 - 3 * 4 / 5 ^ 6";
		double expectedOutput = 2.999232;
		double actualOutput = Calculator.expression(input);
		assertEquals(expectedOutput, actualOutput, 0.001);
	}
	
	// Test Case 24
	@Test(expected = Exception.class)
	public void testWrongUpdationOfVariable() throws Exception {
		Calculator.define("x = 2");
		Calculator.expression("x = 3");
	}

	// Test Case 25
	@Test(expected = Exception.class)
	public void testInvalidVariableName() throws Exception {
		Calculator.define("1 = 2");
	}
	
	// Test Case 26
	@Test(expected = Exception.class)
	public void testInvalidExpression() throws Exception {
		Calculator.expression("1 = 2");
	}
	
	//Test Case 27
    @Test(expected = Exception.class)
    public void testEvaluateEmptyString() throws Exception {
        String input = "";
        Calculator.expression(input);
    }
	
    //Test Case 28
    @Test(expected = Exception.class)
    public void testEvaluateInvalidExpression() throws Exception {
        String input = "123 gg";
        Calculator.expression(input);
    }
    
    //Test Case 29
    @Test(expected = Exception.class)
    public void testEvaluateInvalidExpression2() throws Exception {
        String input = "123 gg 45";
        Calculator.expression(input);
    }
    
    //Test Case 30
    @Test(expected = Exception.class)
    public void testEvaluateInvalidExpression3() throws Exception {
        String input = "123.23.333";
        Calculator.expression(input);
    }

}
