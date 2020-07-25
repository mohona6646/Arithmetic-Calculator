
public class arrayStack<E> implements Stack<E> {

	//Declaring variables
	//Setting the capacity to 100
	private int capacity=100;
	private int top;
	private E[] items;
	
	//Constructor method that sets the size to capacity with top=-1
	public arrayStack() {
		items = (E[]) new Object[capacity];
		top =-1;
	}
	
	//Parameterized constructor that takes in an integer with top = -1
	public arrayStack(int size) {
		items = (E[]) new Object[size];
		capacity = size;
		top = -1;
	}
	
	//Returns the size of the array
	public int size() {
		return top+1;
	}
	
	//Checks if the arrayStack is empty (if top=-1)
	public boolean isEmpty() {
		return top == -1;
	}

	//Pushes a value to the stack
	 public void push(E e) throws IllegalStateException
     {
		 //throws the error if the array is empty
         if (size() == items.length) throw new IllegalStateException("Stack is full!");
         items[++top] = e;
     }

	 //Pops the value of the stack
	 public E pop()
     {
         if (isEmpty()) return null;
         E temp = items[top];
         items[top] = null;
         top--;
         return temp;
     }
	 
	 //Returns the top value of the stack
	 public E top()
     {
         if (isEmpty()) return null;
         return items[top];
     }
	 
	 
	 
}
