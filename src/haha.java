import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class haha{
	private final Stack<String> numb = new Stack<String>();
	private final Stack<String> oper = new Stack<String>();
	private  static ArrayList<String> space = new ArrayList<String>();

	/**
	 * This method returns the result of the expression which is passed by a string 
	 * @param str : Takes a string and checks what it returns
	 * 
	 */
	public double evalExp(String str) {
		
		String nowhitespace = "(" + str.replaceAll("\\s", "") + ")"; /** nowhitespace makes the input that we have from the file and removes the spaces */
		String num = "";
		int count = 0;
		for(int i = 0; i < nowhitespace.length(); i++) {
			if(isNumber((nowhitespace.charAt(i)))) {
				count = 0;
				num += nowhitespace.charAt(i);
				continue;
			}
			if(count == 0) { /** Adds all the string from the nowhitespace as different strings in space ArrayList */
				space.add(num);
				num="";
				count = 1;
			}

			if(!isNumber((nowhitespace.charAt(i)))) { /** Check the second character and adds it to the space ArrayList*/
				if(i < nowhitespace.length()-1 && ((nowhitespace.charAt(i) == '=' && nowhitespace.charAt(i+1) == '=') || (nowhitespace.charAt(i) == '<' && nowhitespace.charAt(i+1) == '=')
						|| (nowhitespace.charAt(i) == '>' && nowhitespace.charAt(i+1) == '=') || (nowhitespace.charAt(i) == '!' && nowhitespace.charAt(i+1) == '='))) {
					String op = "" + nowhitespace.charAt(i) + nowhitespace.charAt(i+1);
					space.add(op);
					i++;
					continue;
				}
				space.add(Character.toString((nowhitespace.charAt(i))));
				continue;
			}
				
			}


		
		

		for(int i = 0; i < space.size(); i++) {

			if(space.get(i).equals(")")) 
			{ /** Check if the string is the closing bracket*/
				while (!oper.isEmpty() && !oper.lastElement().equals("("))
					doOp(); /**if we find a parentheses we should solve for the value immediately and then add that resolved value to the stack */
				oper.pop(); /**after stopping with the last value in the operator stack being ( we can pop it since we no longer need it as a reference */

			}

			else if("!".equals(space.get(i))) {
				numb.pop();
				numb.push(String.valueOf(factorial(Integer.valueOf(space.get(i-1)))));
				

			}

			else if ("+".equals(space.get(i)) || "-".equals(space.get(i)) || "^".equals(space.get(i)) 
					|| "*".equals(space.get(i)) || "/".equals(space.get(i)) || ">".equals(space.get(i)) 
					|| ">=".equals(space.get(i)) || "<".equals(space.get(i)) || "<=".equals(space.get(i)) 
					|| "==".equals(space.get(i)) || "!=".equals(space.get(i))|| ")".equals(space.get(i)) || "(".equals(space.get(i))) {
				oper.push(space.get(i)); /**Search for proper operators and push them to the stack */

			}
			else
				numb.push(space.get(i));
		}


		
		while (!oper.isEmpty())
			doOp(); /**Handle operations until we run out of operators */
		return Double.parseDouble(numb.pop()); /**Return the final value in the operand stack */
	}

	/**
	 * Method which does the operation from the string
	 * @param a: a double variable which is needed for the calculation
	 * @param b: a double variable which is needed for the calculation
	 * @param operator : a string which returns the operator which we should do
	 */

	private void doOp() {
		double a;
		double b;
		String operator = "";
		String bString = "";
		String aString = "";

		operator = oper.pop();
		bString = numb.pop();
		aString = numb.pop();


		a = Double.parseDouble(aString);
		b = Double.parseDouble(bString);

		//Parse the values accordingly

		double result = 0;

		switch (operator) { //search for the appropriate oeprand and do the math
		case "+":
			result = a + b;
			break;
		case "-":
			result = a - b;
			break;
		case "^":
			result = power(a,b);
			break;
		case "*":
			result = a*b;
			break;
		case "/":
			result = a/b;
			break;
		case ">":
			result = (a > b ? 1 : 0);
			break;
		case ">=":
			result = (a >= b ? 1 : 0);
			break;
		case "<":
			result = (a < b ? 1 : 0);
			break;
		case "<=":
			result = (a <= b ? 1 : 0);
			break;
		case "==":
			result = (a == b ? 1 : 0);
			break;
		case "!=":
			result = (a != b ? 1 : 0);
			break;


		}
		numb.push(Double.toString(result)); /**Pushes result to operand stack */
	}

	/**
	 * Method which returns the factorial value 
	 * @param c : an int value to do the factorial value
	 * @return factorial : returns the factorial of the int i passed
	 */
	private static int factorial( int c) { //handle factorial calculations recursively

		// Make sure that the input argument is positive

		if (c < 0) throw
		new IllegalArgumentException("i must be >= 0");

		// Use simple look to compute factorial....

		int factorial = 1;
		for(int i = 2; i <= c; i++)
			factorial *= i;

		return factorial;
	}
	/**
	 * Method which returns the exponential operator 
	 * @param b : base of the function
	 * @param a : exponent of the function 
	 * @return power: returns the power of two given numbers
	 */

	private int power(double b, double a) {

		if(a==0) {
			return 1;

		}else 
			return (int) (b*power(b,a-1));

		/**
		 * Main method of the function
		 */

	}
	public static boolean isNumber(char c) {
		if(c >= '0' && c <= '9')
			return true;
		return false;
	}

	public static void main (String args[]) {
		String fileName = "test.txt";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			BufferedWriter writer = new BufferedWriter(new FileWriter("OutputV1.txt"));
			line = bufferedReader.readLine();
			
			

			
			haha calculator = new haha();
			while(line != null) {
				if(line.equals("")) {
					line = bufferedReader.readLine();
					writer.append("\n");
					continue;
				}

				double result = calculator.evalExp(line);
				writer.append(line + " = " + result + "\n");
				line = bufferedReader.readLine();
				

			}
			writer.flush();
			writer.close();
			// Always close files.
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" +
							fileName + "'");
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '"
							+ fileName + "'");

		}



	}

}