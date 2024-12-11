package Practical_13;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class Interpreter {

    // Stack to store operands during the evaluation of postfix expression
    public Stack<Double> stack;

    // Constructor initializes the stack
    public Interpreter() {
        this.stack = new Stack<>();
    }

    // Checks if a token is an operator
    public boolean checkOperator(String token) {
        // Check if the token is one of the basic arithmetic operators
        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if a token is a numeric operand
    public boolean checkOperand(String token) {
        try {
            // Attempt to convert the token to a Double, if successful, it's a numeric operand
            Double tmpDouble = Double.parseDouble(token);
            return true;
        } catch (NumberFormatException nfe) {
            // If an exception is caught, the token is not a numeric operand
            return false;
        }
    }

    // Processes a postfix expression
    public void processPostfixExpression(String expression) {
        // Tokenizer to split the expression into individual tokens
        StringTokenizer tokenizer = new StringTokenizer(expression, " ");

        while (tokenizer.hasMoreTokens()) {
            // Get the next token
            String token = tokenizer.nextToken();

            // Check if the token is an operand and push it onto the stack
            if (checkOperand(token)) {
                stack.push(Double.parseDouble(token));
            } else if (checkOperator(token)) {
                // If the token is an operator, solve the expression using the operator
                SolveExpression(token);
            } else {
                // If the token is neither operand nor operator, print an error message and exit
                System.out.println("Invalid token: " + token);
                return;
            }

            // Print the current state of the expression and stack
            printExpression(token);
        }
    }

    // Solves an expression using the given operator
    private void SolveExpression(String operator) {
        // Check if there are enough operands on the stack to perform the operation
        if (stack.size() < 2) {
            System.out.println("Invalid expression. Not enough operands for operator " + operator);
            System.exit(1);
        }

        // Pop the top two operands from the stack
        double operand2 = stack.pop();
        double operand1 = stack.pop();

        double result = 0;

        // Perform the operation based on the operator
        switch (operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                // Check for division by zero
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else {
                    System.out.println("Invalid expression. Division by zero.");
                    System.exit(1);
                }
                break;
        }

        // Push the result back onto the stack
        stack.push(result);

        // Print the result and the current state of the expression and stack
        System.out.println("Rule 3: Result(" + result + ") Operand(" + operand1 + ") Operand(" + operand2 + ") Operator(" + operator + ")");
        System.out.println("    Stack: " + stack);
    }

    // Prints the current state of the expression and stack
    public void printExpression(String currentToken) {
        System.out.println("---------------------------");

        // Check if the current token is an operator and print the corresponding rule
        if (checkOperator(currentToken)) {
            System.out.println("Rule 1: Operator(" + currentToken + ") := " + currentToken);
        }

        // Check if the current token is an operand and print the corresponding rule
        if (checkOperand(currentToken)) {
            System.out.println("Rule 2: Operand(" + currentToken + ") := " + currentToken);
        }

        // Print the current state of the stack
        System.out.println("    Stack: " + stack);
        System.out.println("---------------------------");
    }

    // Main method to get input from the user and initiate the postfix expression processing
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a postfix expression: ");
        String postfixExpression = scanner.nextLine();

        // Create an instance of the Interpreter class
        Interpreter interpreter = new Interpreter();

        // Process the entered postfix expression
        interpreter.processPostfixExpression(postfixExpression);

        // Close the scanner
        scanner.close();
    }
}
