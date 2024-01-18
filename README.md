# Interpreter-for-Rational-Expressions-Calculations
/*
 * In this program, the expression method is the top-level procedure that corresponds to the start symbol of the grammar. 
 * It calls the term method to evaluate the first term in the expression, and 
 * then uses while loops to evaluate any additional terms that are separated by + or -. 
 * Within each term, the while loops for multiplication and division call the factor method to evaluate the first factor, and 
 * then use while loops to evaluate any additional factors that are separated by * or /.
 * The factor method is another recursive procedure that is called by the term method to evaluate factors in the expression. 
 * It can handle factors that are numbers, variables, or sub-expressions enclosed in parentheses.
 * */
