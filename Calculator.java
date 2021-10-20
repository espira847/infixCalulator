import java.util.Stack;


public class Calculator {
	// Time complexity : O(n)
	// Space complexity : O(n)
	
	// Driver method
	public static void main(String[] args) {
		
		// equation to be evaluated
		String equation = "10 * ( 2 + 15 ) / 17";
		
		// split in to string arr
		// could be kept as string to save space but arr adds easier access to each element
		String[] data = equation.split(" ");

		System.out.print(equation(data));
		
	}
	
	// static int because there is only one string we need to read with a 
	// static there is no need to keep track of where we are each time we loop
	// from different methods
	static int i = 0;
	
	// priority method that will check if the operation is more or less priority
	// than the one that is getting inputed
	// if less than then operate on the one in the stack
	// if more than or equal equal to then just add it to the stack since we dont know if the next will be higher priority
	static boolean priority(String look, String peek) {
		int lookPriority = 0, peekPriority = 0;
		
		switch (look) {
		case "+": lookPriority = 0;
					break;
		case "-": lookPriority = 0;
					break;
		case "*": lookPriority = 1;
					break;
		case "/": lookPriority = 1;
					break;
		}
		switch (peek) {
		case "+": peekPriority = 0;
					break;
		case "-": peekPriority = 0;
					break;
		case "*": peekPriority = 1;
					break;
		case "/": peekPriority = 1;
					break;
		}
		
		if(lookPriority > peekPriority) {
			return false;
		}
		
		return true;
		
	}
	
	// operate method that will take in the two numbers in the stack and do the computations
	// based on what operand is there
	static int operate(int oper1, int oper2, String operator) {
		int result = 0;
		switch (operator) {
		case "+": result = oper2 + oper1;
					break;
		case "-": result = oper2 - oper1;
					break;
		case "*": result = oper2 * oper1;
					break;
		case "/": result = oper2 / oper1;
					break;
		}
		return result;
	}
	
	// equation method
	// this will be the method that will do the work on the equation
	// will return answer
	static int equation(String[] data) {

		int result = 0;
		
		// two stacks to hold numbers and operators
		Stack<Integer> operand = new Stack<Integer>();
		Stack<String> operator = new Stack<String>();
	
		// for loop through string 
		for(; i < data.length; i++) {
			
			// try catch that will check if int and if not then will know its an operator
			try {
				int num = Integer.parseInt(data[i]);
				operand.push(num);
			}catch(Exception NumberFormatException) {
				
				// check if operator is empty to auto add 
				// also checks if parentheses is first 
				if(operator.empty() && !data[i].equals("(")) {
					operator.push(data[i]);
				}
				// if parentheses then does special method to evaluate what is inside
				else if(data[i].equals("(")) {
					result = parenthesesProtocol(data);
					operand.push(result);
				}
				// checks if the operator will have priority or not
				else if(priority(data[i], operator.peek())) {
					// if the operator in the Stack has priority then do that before the next is added
					result = operate(operand.pop(), operand.pop(), operator.pop());
					operand.push(result);
					operator.push(data[i]);
				}
				else if(!priority(data[i], operator.peek())) {
					// if not then just add to stack
					operator.push(data[i]);
				}
				
			}
			
		}
		// operate rest to finish the computations
		return operateRest(operand, operator);
		
	}
	
	// parentheses special method 
	// basically the same as the equation but will evaluate ( to ) when ( is detected
	// note : that since we have a static in we only need the string arr since we know where we left off
	static int parenthesesProtocol(String[] data) {
		
		int result = 0;
		Stack<Integer> operand = new Stack<Integer>();
		Stack<String> operator = new Stack<String>();
	
		// i++ to not loop at open parentheses
		i++;
		for(; i < data.length; i++) {
			try {
				int num = Integer.parseInt(data[i]);
				operand.push(num);
			}catch(Exception NumberFormatException) {
				if(operator.empty() && !data[i].equals("(")) {
					operator.push(data[i]);
					
				}
				else if(data[i].equals("(")) {
					result = parenthesesProtocol(data);
					operand.push(result);
				}
				// different ) if statement added to know when the computations need to be done
				else if(data[i].equals(")")) {
					return operateRest(operand, operator);
					
				}
				else if(priority(data[i], operator.peek())) {
					result = operate(operand.pop(), operand.pop(), operator.pop());
					operand.push(result);
					operator.push(data[i]);
				}
				else if(!priority(data[i], operator.peek())) {
					operator.push(data[i]);
				}
				
			}
			
		}
		
		// -1 return is if data was not inputed properly 
		return -1;
		}
	
	// operate rest to finish computations on the rest of the string when all data is inputed
	static int operateRest(Stack<Integer> operand, Stack<String> operator) {
		
		int result = 0;
		while(!operand.empty()) {
			try {
				 result = operate(operand.pop(), operand.pop(), operator.pop());
				 operand.push(result);
				
			}catch(Exception e) {
				break;
			}
		}
		return result;
	}
	
}
// Query 2 : Deque
/* With the Deque we could instead of popping before we add to the deque we can add then operate from the other end. 
 in the case of 2 * 3 + 4 we could add the + to the deque then operate on the * and add the result to the front of the deque.
 For the operate rest method we could do it from the beginning of the deque instead of the end of the stack to make it a bit cleaner
 work from right to left instead of left to right.
 */

	
	

	

