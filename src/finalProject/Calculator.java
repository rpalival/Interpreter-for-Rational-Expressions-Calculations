package finalProject;

import java.util.HashMap;
import java.util.Scanner;

/*
 * In this program, the expression method is the top-level procedure that corresponds to the start symbol of the grammar. 
 * It calls the term method to evaluate the first term in the expression, and 
 * then uses while loops to evaluate any additional terms that are separated by + or -. 
 * Within each term, the while loops for multiplication and division call the factor method to evaluate the first factor, and 
 * then use while loops to evaluate any additional factors that are separated by * or /.
 * The factor method is another recursive procedure that is called by the term method to evaluate factors in the expression. 
 * It can handle factors that are numbers, variables, or sub-expressions enclosed in parentheses.
 * */

public class Calculator {
	// Define a private static HashMap called variables to store variables and their values
    private static HashMap<String, Double> variables = new HashMap<String, Double>();

    public static void main(String[] args) {
    	//new Scanner object to read user input from the console
        Scanner scanner = new Scanner(System.in);
        
        // Start an infinite loop to keep the program running until the user types "exit" 
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();
            if (line.equals("exit")) {
                break;
            }
            try {
            	evaluate(line);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            catch (StackOverflowError e) {
                System.out.println("Error: Expression is too complex");
            }
            //The above catch blocks are to catch any errors which occurs if it's not able to evaluate the expression.
        }
        scanner.close();
    }
    
    // Return true if the expression contains any characters that are not digits, letters, keyword='define' 
    // or valid operators(+, -, ".", =, *, /, ^)
    public static boolean hasInvalidCharacters(String expression) {
        return expression.matches(".*[^\\d\\w+\\-.^*/()\\s=define].*");
    }


	public static void evaluate(String line) throws Exception {
		// If the line starts with "define", treat it as a variable definition
		if (line.startsWith("define")) {
			define(line.substring(6));
		} else {
		// Otherwise, evaluate the line as a mathematical expression and print the result
			double result = expression(line);
			System.out.println(result);
		}
	}

	public static void define(String line) throws Exception {
		// Split the line into two parts: the variable name and the expression to assign to it
	    String[] parts = line.split("=", 2);
	    if (parts.length != 2) {
	        throw new Exception("Invalid define statement");
	    }
	    
	    String identifier = parts[0].trim();
	    // If the identifier is not a valid variable name, throw an exception
	    // checking that it should start with an alphabet followed by any number of apha-numeric characters.
	    if (!identifier.matches("[a-zA-Z][a-zA-Z0-9]*")) {
	        throw new Exception("Invalid identifier: " + identifier);
	    }
	    
	    String expression = parts[1].trim();
	    double value = expression(expression);
	    variables.put(identifier, value);
	}


	//Evaluates an expression by calling term which in-turn calls factor to deal with all kinds of expressions.
    public static double expression(String line) throws Exception { 
	        line = line.trim();
	        
	        // Evaluate the first term in the expression
	        ResultWithRemainingLine resultWithRemainingLine = term(line);
	        double result = resultWithRemainingLine.result;
	        line = resultWithRemainingLine.remainingLine;
	        
	        // Evaluate any additional terms in the expression that are separated by + or -
	        while (line.length() > 0) {
	            char operator = line.charAt(0);
	            if (operator != '+' && operator != '-') {
	                break;
	            }
	            line = line.substring(1).trim();
	            resultWithRemainingLine = term(line);
	            double term = resultWithRemainingLine.result;
	            line = resultWithRemainingLine.remainingLine;
	
	            if (operator == '+') {
	                result += term;
	            } else {
	                result -= term;
	            }
	        }	        
	        // If the expression is a single character and it matches a variable name, return the variable's value
	        if (line.length() == 0 && variables.containsKey(Character.toString((char) ((int) result)))) {
	            return variables.get(Character.toString((char) ((int) result)));
	        }
	        // Otherwise, return the final result of the expression
	        if(line.length() == 0) {
	        	return result;
	        }
	        else {
	            throw new Exception("Invalid Expression: ");
	        }
	    }

    // Get the value of the variable with the given name
    public static double getVariableValue(String variableName) throws Exception {
    	// If the variable exists in the variables map, return its value
        if (variables.containsKey(variableName)) {
            return variables.get(variableName);
        } else {
        	// Otherwise, throw an exception indicating that the variable is undefined
            throw new Exception("Undefined variable: " + variableName);
        }
    }

    // A helper class that represents a result value and the remaining part of a line of input
    public static class ResultWithRemainingLine {
        public double result;
        public String remainingLine;

        public ResultWithRemainingLine(double result, String remainingLine) {
            this.result = result;
            this.remainingLine = remainingLine;
        }
    }


    // Return the final result of the term and the remaining part of the input line
    public static ResultWithRemainingLine term(String line) throws Exception {
        ResultWithRemainingLine resultWithRemainingLine = factor(line);
        double result = resultWithRemainingLine.result;
        line = resultWithRemainingLine.remainingLine;

        while (line.length() > 0) {
            char operator = line.charAt(0);
            if (operator != '*' && operator != '/') {
                break;
            }
            line = line.substring(1).trim();
            resultWithRemainingLine = factor(line);
            double factor = resultWithRemainingLine.result;
            line = resultWithRemainingLine.remainingLine;

            if (operator == '*') {
                result *= factor;
            }
            else if	(factor == 0){
            	throw new Exception("Division by Zero is not possible as it's Infinity");
            }
            else {
                result /= factor;
            }
        }

        return new ResultWithRemainingLine(result, line);
    }


    // Evaluate the given line of input as a factor in a mathematical expression and return the result
	public static ResultWithRemainingLine factor(String line) throws Exception {
		// If the factor is a sub-expression enclosed in parentheses, evaluating it recursively
		if (line.startsWith("(")) {
			// Finding the matching closing parenthesis for the opening parenthesis at the start of the line
			int openParenCount = 1;
			int closeParenCount = 0;
			int i = 1;
			while (openParenCount != closeParenCount && i < line.length()) {
				if (line.charAt(i) == '(') {
					openParenCount++;
				} else if (line.charAt(i) == ')') {
					closeParenCount++;
				}
				i++;
			}
			// If the parentheses are not balanced, throw an exception
			if (openParenCount != closeParenCount) {
				throw new Exception("Mismatched parentheses");
			}
			// Evaluating the sub-expression inside the parentheses and update the remaining line of input
			String subExpression = line.substring(1, i - 1);
			double result = expression(subExpression);
			line = line.substring(i).trim();
			while (line.length() > 0 && (line.charAt(0) == '^')) {
				line = line.substring(1).trim();
				ResultWithRemainingLine nextFactor = factor(line);
				double exponent = nextFactor.result;
				line = nextFactor.remainingLine;
				result = Math.pow(result, exponent);
			}
			return new ResultWithRemainingLine(result, line);
			//This loop is for evaluating expressions outside parenthesis
		} 
		else {
			int i = 0;
			// If the factor is a number or variable, evaluate it accordingly
			while (i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.'
					|| line.charAt(i) == '-' || line.charAt(i) == '+' || Character.isLetter(line.charAt(i)))) {
				i++;
			}
			if (i == 0) {
				throw new Exception("Invalid Token : " + line);
			}

			String token = line.substring(0, i);
			line = line.substring(i).trim();

			if (token.matches("[-+]?\\d*\\.?\\d+")) {

				if (line.length() > 0 && line.charAt(0) == '=') {
					throw new Exception("Kindly start the variable name with an alphabet and use define keyword");
				}
				if(line.replaceAll("\\s", "").matches("[a-zA-Z]+")) {
					throw new Exception("Invalid Expression");
				}
				else {
					double number = Double.parseDouble(token);
					
					while (line.length() > 0 && (line.charAt(0) == '^')) {
						line = line.substring(1).trim();
						ResultWithRemainingLine nextFactor = factor(line);
						double exponent = nextFactor.result;
						line = nextFactor.remainingLine;
						number = Math.pow(number, exponent);
					}
					
					return new ResultWithRemainingLine(number, line);
				}
				
			} 
			else if (token.matches("[a-zA-Z][a-zA-Z0-9]*")) {
				if (variables.containsKey(token)) {
					if (line.length() > 0 && line.charAt(0) == '=') {
						throw new Exception("Cannot update variable without using define keyword");
					}
					double value = getVariableValue(token);
					
					while (line.length() > 0 && (line.charAt(0) == '^')) {
						line = line.substring(1).trim();
						ResultWithRemainingLine nextFactor = factor(line);
						double exponent = nextFactor.result;
						line = nextFactor.remainingLine;
						value = Math.pow(value, exponent);
					}
					
					return new ResultWithRemainingLine(value, line);
				}
				else {
					throw new Exception("Undefined variable: " + token);
				}
			} 
			else {
				throw new Exception("Invalid token : " + token);
			}
		}
	}
}