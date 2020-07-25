//Interface of the stack
public interface Stack <E> {

	//Functions to define
	int size();
	boolean isEmpty();
	void push(E e);
	E top();
	E pop();
}
