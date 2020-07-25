import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FirstCal {

//Implementing two arrayStack
	static arrayStack valStk = new arrayStack<>();
	static arrayStack opStk = new arrayStack<>();
	
	//Declaring double values
	static double x;
	static double y;
	static double result;
	
	//Declaring boolean value
	static boolean resultBoolean;
	static String op;
	
	//Recursive Method that takes a double and finds the factorial of the number
	public static double factorial(double n) {
		if (n <= 1)
            return 1;
      else
            return n * factorial(n - 1);
	} 
	
	//Recursive method that takes two double a and b and find the power of a to the b
	private static double power(double b, double a) {

		//if a equals to 0, return 1
		if(a==0) {
			return 1;

		}else 
			return (int) (b*power(b,a-1));

	}
	
	//Method that finds the right operation and performs the corresponding operation
	public static void doOp() {
		boolean key = false;
		
		//Pops the necessary elements from the stacks: two doubles and one string representing the operation
		x = (double) valStk.pop();
	    y = (double) valStk.pop();
	   op = (String) opStk.pop();
	   
	   //If the operation is equal to + perform the addition
	    if("+".contentEquals(op)) {
		   result= y+x;
		   valStk.push(result);
		  
	   }
	  //If the operation is equal to - perform the subtraction
	   else if("-".contentEquals(op)) {
		   result=y-x;
		   valStk.push(result);
		   
	   }
	  //If the operation is equal to * perform the multiplication
	   else if("*".contentEquals(op)) {
		   result=y*x;
		   valStk.push(result);
		  
	   }
	  //If the operation is equal to / perform the division
	   else if("/".contentEquals(op)) {
		   result=y/x;
		   valStk.push(result);
		
	   }
	  //If the operation is equal to ^ perform the power
	   else if("^".contentEquals(op)) {
		   result=power(y,x);
		   valStk.push(result);
		
	   }
	  //If the operation is equal to ! perform the factorial
	   else if("!".contentEquals(op)) {
		   result= factorial(x);
		   valStk.push(result);
		 
	   }
	  //If the operation is equal to > perform the boolean exp
	   else if(">".contentEquals(op)) {
		   result = (y > x ? 1 : 0);
		   valStk.push(result);
	
	   }
	  //If the operation is equal to < perform the boolean exp
	   else if("<".contentEquals(op)) {
		   result = (y < x ? 1 : 0);
		   valStk.push(result);
		  
	   }
	  //If the operation is equal to >= perform the boolean exp
	   else if(">=".contentEquals(op)) {
		   result = (y >= x ? 1 : 0);
		   valStk.push(result);
		
	   }
	  //If the operation is equal to <= perform the boolean exp
	   else if("<=".contentEquals(op)) { 
		   result = (y <= x ? 1 : 0);
		   valStk.push(result);
		 
	   }
	  //If the operation is equal to == perform the boolean exp
	   else if("==".contentEquals(op)) {
		   result = (y == x ? 1 : 0);
		   valStk.push(result);
		  
	   }
	  //If the operation is equal to != perform the boolean exp
	   else if("!=".contentEquals(op)) {
		   result = (y != x ? 1 : 0);
		   valStk.push(result);
		  
	   }
	   
	   }
	   
	   
	
	//method that does the operation if the precendent of the calling operation is smaller than the top operation
	public static void repeatOps(String op) {
		while(valStk.size()>1 && (precedent(op)<=precedent(opStk.top().toString()))&& !opStk.top().equals("(")) {
			doOp();
		}
	}
	
	//Method that assigns a number to the operation depending on the precendence of each
	public static int precedent(String op) {
		//Switch case for each operation
		switch(op) {
		case "(":
		case ")":
			return 8;
			
		case "^":
			return 6;
		
		case "*":
		case "/":
			return 5;
		
		case "+":
		case "-":
			return 4;
			
		case ">":
		case "<":
		case ">=":
		case "<=":
			return 3;
			
		case "==":
		case "!=":
			return 2;
			
		case "$":
			return 1;
			
		default:
			throw new IllegalArgumentException("The operator is not valid. The operation cannot be performed!");
		}
	}
	
	//Method that takes a string and outputs a double as the answer
	public static double addingAll(String line) {
		//String tokenizer to split the expression depending the spaces
		StringTokenizer split = new StringTokenizer(line);
		String temp= null;
		//While loop that iterates till it has no more tokens
		while(split.hasMoreTokens()) {
			//If the token is an integer
			 try {
				double val = Integer.parseInt(temp=split.nextToken());
				//Push the value in the stack
				valStk.push(val);
			 }
			 //if the token is not an integer and if it's an operation
			 catch(NumberFormatException ne){
				 //if the operation is ")" then perform the operation between parentheses first
				 if(temp.contentEquals(")")) {
					 while(!opStk.isEmpty()&& valStk.size()>1 && !opStk.top().equals("("))
					 doOp();
					 //Push the operation in the stack
					 opStk.pop();
				 }
				 //if the operation is ! perform the operation first
				 else if(temp.contentEquals("!")){
					valStk.push(factorial((double)valStk.pop()));
				 }
				 else {
				 repeatOps(temp);
				 opStk.push(temp);
				 }
			 }
			 
		}
		//If no more tokens, push the value to the top
		repeatOps("$");
		if(!valStk.isEmpty())
		return (double) valStk.pop();
		else
			return 0.0;
	}
	
	
	public static void main(String[] args) {
		//Declaring some variables
		String userChoice = null;
		BufferedReader reader = null;
		PrintWriter pw = null;
		Scanner kb = new Scanner(System.in);
		
		//Prompt the user to enter the name of the file
		System.out.println("Enter the name of the file to read");
		
		//Takes in the String of the user
		userChoice = kb.next();
		String reading;
		String temp= null;
		double result;
		
		
	    //Open files bufferedreader and printwriter in a try catch clause
		try {
		 reader = new BufferedReader(new FileReader(userChoice));
		 pw = new PrintWriter(new FileOutputStream("outputVersion1.txt"));
		 //Reads a line from the input file
		 reading = reader.readLine();
		 //While the reading of the file is not null
		 while(reading!= null) {
			 if(reading.equals("")) {
					reading = reader.readLine();
					pw.append("\n");
					continue;
				}
			 pw.print("The result for " + reading +" is: ");
			 pw.println((result=addingAll(reading))+ " ");
			 reading = reader.readLine();
		 }
		 reader.close();
		 pw.close();
		 kb.close();
		 }
		catch(FileNotFoundException e){
			System.out.println("The file entered does not exist.");
		}
		catch(IOException e1) {
			System.out.println("Problem deteced while reading the file" + userChoice + "\n The program will terminate shortly\n");
		}
		
		
		
		
			
		
		
		
		
	}

	

	
	
	
	
}
