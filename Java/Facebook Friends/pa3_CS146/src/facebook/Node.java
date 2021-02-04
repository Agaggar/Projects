package facebook;

public class Node<T> {
	//Generic Node class, containing only getters, setters, and one constructor
	//Used by Binary Search Tree and PersonLinkedList in order to aggregate 
	//ArrayList<Person>
	
	private T data;
	private Node<T>	prev;
	private Node<T>	next;
	private Node<T>	left;
	private Node<T>	right;
	private Node<T>	parent;
	
	public Node(T data) {
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public Node<T> getNext() {
		return next;
	}
	
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	public Node<T> getPrev() {
		return prev;
	}
	
	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
	
	public Node<T> getLeft() {
		return left;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

}
