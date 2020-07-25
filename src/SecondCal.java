import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SecondCal {
/*
 * The type of recursion used is tail recursion
 * This type does not have much of an impact of the time complexity nor the memory complexity
 */
	public static double calculate(String equation) {
		//result is the value of the result
		double result = 0;
		//if the equation contains panatheses go to the called method
		if(equation.contains("(")){
			return calculate(paranthese(equation, result));		
		}
		//if the equation contains a factorial and/or not an equal
		if(equation.contains("!")) { 
			int i = equation.indexOf('!');
			if(i < equation.length()-1 && equation.charAt(i+1) == '=') {
				String left = equation.substring(0, equation.indexOf('!'));
				String right = equation.substring(i+2, equation.length());

				if(calculate(left) != calculate(right)) {

					return 1;
				}
				else {

					return 0;
				}
			}
			else {
				return calculate(fact(equation, result));		
			}

		}
		//if the equation contains a power call the method
		if(equation.contains("^")) {
			return calculate(pwr(equation, result));		
		}
		//if the equation contains multiplication AND division call the method
		if(equation.contains("*") && equation.contains("/")) {
			return calculate(priorityMultDiv(equation, result));	
		}
		//if the equation contains multiplication call the method
		if(equation.contains("*")) {
			return calculate(multiplication(equation, result));		
		}
		//if the equation contains division call the method
		if(equation.contains("/")) {
			return calculate(division(equation, result));		
		}
		//if the equation contains + and -, call the method
		if(equation.contains("+") && equation.contains("-")) {
			return calculate(priorityPlusMin(equation, result));		
		}
		//if the method contains + call the method
		if(equation.contains("+")) {
			return calculate(addition(equation,result));
		}
		//if the method contains - call the method
		if(equation.contains("-")) {
			return calculate(subtraction(equation,result));
		}
		//if the method has < and no =
		if(equation.contains("<")&&equation.charAt(equation.indexOf('<')+1)!='=') {
			//make sure everything is okay
			if(equation.indexOf('<')!=0
					&&number(equation.charAt(equation.indexOf('<')-1))
					&&number(equation.charAt(equation.indexOf('<')+1))
					&&equation.indexOf('<')<equation.length()-1) {	
				//take the RHS and LHS values 
				String RHS = equation.substring(equation.indexOf('<')+1,equation.length());
				String LHS = equation.substring(0,equation.indexOf('<'));
				//calculate each side and find if one is bigger than the other and return the answer
				if(calculate(LHS)<calculate(RHS))
					return 1;
				else
					return 0;	
			}
		}
		//if the method has < and =
		if(equation.contains("<")&&equation.charAt(equation.indexOf('<')+1)=='=') {
			//make sure everything is okay 
			if(equation.indexOf('<')!=0
					&&number(equation.charAt(equation.indexOf('<')-1))
					&&number(equation.charAt(equation.indexOf('<')+2))
					&&equation.indexOf('<')<equation.length()-2) {	
				//take the RHS and LHS values
				String RHS = equation.substring(equation.indexOf('<')+2,equation.length());
				String LHS = equation.substring(0,equation.indexOf('<'));
				//calculate each side and find if one is bigger than the other and return the answer
				if(calculate(LHS)<=calculate(RHS))
					return 1;
				else
					return 0;	
			}
		}

		//if the method has > and no =
		if(equation.contains(">")&&equation.charAt(equation.indexOf('>')+1)!='=') {
			//make sure everything is okay
			if(equation.indexOf('>')!=0
					&&number(equation.charAt(equation.indexOf('>')-1))
					&&number(equation.charAt(equation.indexOf('>')+1))
					&&equation.indexOf('>')<equation.length()-1) {	
				//evaluate RHS and LHS
				String RHS = equation.substring(equation.indexOf('>')+1,equation.length());
				String LHS = equation.substring(0,equation.indexOf('>'));
				//calculate each side and find if one is bigger than the other and return the answer
				if(calculate(LHS)>calculate(RHS))
					return 1;
				else
					return 0;	
			}
		}
		//if the method contains > and =
		if(equation.contains(">")&&equation.charAt(equation.indexOf('>')+1)=='=') {
			//make sure everything is okay
			if(equation.indexOf('>')!=0
					&&number(equation.charAt(equation.indexOf('>')-1))
					&&number(equation.charAt(equation.indexOf('>')+2))
					&&equation.indexOf('>')<equation.length()-2) {	
				//evaluate LHS and RHS
				String RHS = equation.substring(equation.indexOf('>')+2,equation.length());
				String LHS = equation.substring(0,equation.indexOf('>'));
				//calculate each side and find if one is bigger than the other and return the answer
				if(calculate(LHS)>=calculate(RHS))
					return 1;
				else
					return 0;	
			}
		}
		//if the method contains ==
		if(equation.contains("==")) {
			//make sure everything is okay
			if(equation.indexOf('=')!=0
					&&number(equation.charAt(equation.indexOf('=')-1))
					&&number(equation.charAt(equation.indexOf('=')+2))
					&&equation.indexOf('=')<equation.length()-2) {
				//evaluate RHS and LHS
				String RHS = equation.substring(equation.indexOf('=')+2,equation.length());
				String LHS = equation.substring(0,equation.indexOf('='));
				//calculate each side and find if  they are equal or not and return the answer
				if(calculate(LHS)==calculate(RHS))
					return 1;
				else
					return 0;			
			}
		}
		//return the equation in double
		return Double.parseDouble(equation);
	}
	
	//evaluate if a character is a number
	public static boolean number(char c) {
		if(c >= '0' && c <= '9'||c=='.')
			return true;
		return false;
	}
	//if the equation has paranthesis, evealuate it
	public static String paranthese(String equation, double result) {
		int i,c;
		String inParantheses = "";
		for( c = 0, i=equation.indexOf('(')+1;i<equation.length();i++) {
			if(equation.charAt(equation.indexOf('('))==equation.charAt(i))
				c++;
			if(equation.charAt(i)== ')') {
				if(c==0) {
					inParantheses = equation.substring(equation.indexOf('(')+1,i);
					break;
				}
				c--;
			}
		}
		result=calculate(inParantheses);
		if(equation.indexOf('(')!=0)
			equation = equation.substring(0,equation.indexOf('('))+Double.toString(result) + equation.substring(i+1);
		else
			equation = Double.toString(result) + equation.substring(i+1);
		return (equation);
	}

	public static String fact(String equation, double result) {
		int i = equation.indexOf('!');
			String op = "";
			int j;
			for(j = i; j > 0 && number(equation.charAt(j-1)) == true; j--) {
				op = equation.charAt(j-1) + op;
			}
			result = factorial(Integer.parseInt(op));
			equation = equation.substring(0, j) + String.valueOf(result) + equation.substring(i+1, equation.length());
			return equation;
		}
	//if the method has a power, evaluate if
	public static String pwr(String equation, double result) {
		int pwrPosition=equation.indexOf('^');
		String base = "";
		String exponant = "";
		int i,j;
		for(i=pwrPosition;i<equation.length()-1 &&number(equation.charAt(i+1));i++) {
			exponant = exponant + equation.charAt(i+1);
		}
		for(j=pwrPosition; j>0 && number(equation.charAt(j-1)); j--) {
			base = equation.charAt(j-1) + base;
		}
		if(j == 1 && equation.charAt(0)== '-')
			result = power(-Double.parseDouble(base), Double.parseDouble(exponant));
		else
			result = power(Double.parseDouble(base), Double.parseDouble(exponant));
		if(equation.charAt(0) == '-')
			equation = Double.toString(result) + equation.substring(i+1, equation.length());
		else
			equation = equation.substring(0,j) + Double.toString(result) + equation.substring(i+1,equation.length());
		return (equation);

	}

	//if the method has both multiplication and division, evaluate it within the priority of the index
	public static String priorityMultDiv(String equation, double result) {
		int index = 0;
		if(equation.indexOf('/')>equation.indexOf('*'))
			index = equation.indexOf('*');
		else
			index = equation.indexOf('/');
		if(number(equation.charAt(index-1))
				&&number(equation.charAt(index+1))
				&& index<equation.length()-1
				&&index!=0) {
			String first = "";
			String second = "";
			int i,j;
			for(j=index;j>0&& number(equation.charAt(j-1));j--)
				first = equation.charAt(j-1) + first;
			for(i=index; i<equation.length()-1&&number(equation.charAt(i+1));i++)
				second = second + equation.charAt(i+1);
			if(equation.charAt(index)=='/')
				result = Double.parseDouble(first) / Double.parseDouble(second);
			else if(j == 1 && equation.charAt(0)== '-'&& equation.charAt(index)=='/')
				result = (-Double.parseDouble(first)/ Double.parseDouble(second));
			else if(j == 1 && equation.charAt(0)== '-'&&equation.charAt(index)=='*')
				result = (-Double.parseDouble(first)* Double.parseDouble(second));
			else
				result = Double.parseDouble(first) * Double.parseDouble(second);
			if(equation.charAt(0) == '-')
				equation = Double.toString(result) + equation.substring(i+1, equation.length());
			else
				equation = equation.substring(0,j)+Double.toString(result) + equation.substring(i+1,equation.length());
			return (equation);
		}
		else
			return "";
	}
	
	//if there is a multiplcation evaluate it
	public static String multiplication(String equation, double result) {
		int multiPosition = equation.indexOf('*');
		if(number(equation.charAt(multiPosition-1))
				&&number(equation.charAt(multiPosition+1))
				&& multiPosition<equation.length()-1
				&&multiPosition!=0) {
			String first = "";
			String second = "";
			int i,j;
			for(j=multiPosition;j>0&& number(equation.charAt(j-1));j--)
				first = equation.charAt(j-1) + first;
			for(i=multiPosition; i<equation.length()-1&&number(equation.charAt(i+1));i++)
				second = second + equation.charAt(i+1);
			if(j == 1 && equation.charAt(0)== '-')
				result = -Double.parseDouble(first) * Double.parseDouble(second);
			else
				result = Double.parseDouble(first) * Double.parseDouble(second);
			if(equation.charAt(0) == '-')
				equation = Double.toString(result) + equation.substring(i+1, equation.length());
			else
				equation = equation.substring(0,j)+Double.toString(result) + equation.substring(i+1,equation.length());
			return equation;
		}
		else
			return "";
	}

	//calculate division 
	public static String division(String equation, double result) {
		int divPosition = equation.indexOf('/');
		if(divPosition!=0
				&&divPosition<equation.length()-1
				&&number(equation.charAt(divPosition-1))
				&&number(equation.charAt(divPosition+1))) {
			String first = "";
			String second = "";
			int i,j;
			for(j=divPosition;j>0&& number(equation.charAt(j-1));j--)
				first = equation.charAt(j-1) + first;
			for(i=divPosition; i<equation.length()-1&&number(equation.charAt(i+1));i++)
				second = second + equation.charAt(i+1);
			if(j == 1 && equation.charAt(0)== '-')
				result = (-Double.parseDouble(first) / Double.parseDouble(second));
			else
				result = Double.parseDouble(first)/Double.parseDouble(second);
			if(equation.charAt(0) == '-')
				equation = Double.toString(result) + equation.substring(i+1, equation.length());
			else
				equation = equation.substring(0,j)+Double.toString(result) + equation.substring(i+1,equation.length());
			return (equation);					
		}
		return "";
	}
	
	//evaluate plus/minus depending on priority of index
	public static String priorityPlusMin(String equation, double result) {
		int index = 0;
		if(equation.indexOf('-')>equation.indexOf('+'))
			index = equation.indexOf('+');
		else
			index = equation.indexOf('-');
		if(number(equation.charAt(index-1))
				&&number(equation.charAt(index+1))
				&& index<equation.length()-1
				&&index!=0) {
			String first = "";
			String second = "";
			int i,j;
			for(j=index;j>0&& number(equation.charAt(j-1));j--)
				first = equation.charAt(j-1) + first;
			for(i=index; i<equation.length()-1&&number(equation.charAt(i+1));i++)
				second = second + equation.charAt(i+1);
			if(equation.charAt(index)=='-')
				result = Double.parseDouble(first) - Double.parseDouble(second);
			else if(j == 1 && equation.charAt(0)== '-'&& equation.charAt(index)=='-')
				result = (-Double.parseDouble(first)- Double.parseDouble(second));
			else if(j == 1 && equation.charAt(0)== '-'&&equation.charAt(index)=='*')
				result = (-Double.parseDouble(first)* Double.parseDouble(second));
			else
				result = Double.parseDouble(first) * Double.parseDouble(second);
			if(equation.charAt(0) == '-')
				equation = Double.toString(result) + equation.substring(i+1, equation.length());
			else
				equation = equation.substring(0,j)+Double.toString(result) + equation.substring(i+1,equation.length());
			return (equation);
		}
		else
			return "";
	}
	
	//evaluate the addition if it is called
	public static String addition(String equation, double result) {
		int plusPosition = equation.indexOf('+');
		if(plusPosition!=0
				&&plusPosition<equation.length()-1
				&&number(equation.charAt(plusPosition-1))
				&&number(equation.charAt(plusPosition+1))) {
			String first = "";
			String second = "";
			int i,j;
			for(j=plusPosition;j>0&& number(equation.charAt(j-1));j--)
				first = equation.charAt(j-1) + first;
			for(i=plusPosition; i<equation.length()-1&&number(equation.charAt(i+1));i++)
				second = second + equation.charAt(i+1);
			if(j == 1 && equation.charAt(0)== '-')
				result = (-Double.parseDouble(first)+ Double.parseDouble(second));
			else
				result = Double.parseDouble(first) + Double.parseDouble(second);
			if(equation.charAt(0) == '-')
				equation = Double.toString(result) + equation.substring(i+1, equation.length());
			else
				equation = equation.substring(0,j)+Double.toString(result) + equation.substring(i+1,equation.length());
			return equation;		
		}
		else 
			return "";
	}

	//evaluate the subtraction if it is called
	public static String subtraction(String equation, double result) {
		int minusPosition = equation.indexOf('-');
		if(minusPosition!=0
				&&minusPosition<equation.length()-1
				&&number(equation.charAt(minusPosition-1))
				&&number(equation.charAt(minusPosition+1))) {
			String first = "";
			String second = "";
			int i,j;
			for(j=minusPosition;j>0&& number(equation.charAt(j-1));j--)
				first = equation.charAt(j-1) + first;
			for(i=minusPosition; i<equation.length()-1&&number(equation.charAt(i+1));i++)
				second = second + equation.charAt(i+1);
			if(j == 1 && equation.charAt(0)== '-')
				result = (-Double.parseDouble(first) - Double.parseDouble(second));
			else
				result = Double.parseDouble(first) - Double.parseDouble(second);

			if(equation.charAt(0) == '-') {
				equation = Double.toString(result) + equation.substring(i+1, equation.length());
			}
			else
				equation = equation.substring(0,j)+Double.toString(result) + equation.substring(i+1,equation.length());
			return equation;		
		}
		else
			return "";
	}
	
	//calculate power the power
	public static double power(double x, double y) {
		if(y == 0)
			return 1;
		else 
			return x * power(x, y - 1);
	}

	//calculate the factorial 
	public static int factorial(int x) {
		if(x == 0)
			return 1;
		else 
			return x * factorial(x-1);
	}
	
	//the main method
	public static void main(String[] args)  throws IOException{
		Scanner sc = null;
		PrintWriter pw = null;

		try {
			//open 2 files
			sc = new Scanner(new FileInputStream("Input.txt"));
			pw = new PrintWriter(new FileOutputStream("Output_V2.txt"));
			//as long the scanner has something next
			while(sc.hasNext()) {
				String equation = sc.nextLine();
				String exp = equation.replaceAll("\\s", "");
				if(exp.equals("")) {
					pw.println();
					continue;

				}
				//write everything into the output file
				pw.print("The result for " + equation + " is: " + calculate(exp) +"\n");

			}
			sc.close();
			pw.close();

		}
		//if the file could not be opened then throw an exceotuin
		catch(FileNotFoundException e) {
			System.out.println("Error! File could not be opened");
			sc.close();
		}
	}
}
