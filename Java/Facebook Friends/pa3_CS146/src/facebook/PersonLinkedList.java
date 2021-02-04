package facebook;

public class PersonLinkedList {
	//Custom LinkedList class, where each Link is a Node<ArrayList<Person>>.
	
	private Node<Person> head;	// Empty if head and
	private Node<Person> tail;	// tail are null
	
	//No-args constructor
	public PersonLinkedList() {
		
	}
	
	//Constructor to create a new PersonLinkedList. Calls the overloaded, 
	//appropriate append method. 
	public PersonLinkedList(String s) {
		append(s);
	}
	
	//This append method takes in a String, creates a new Node<ArrayList<Person>>, and 
	//calls the next append method.
	public void append(String s) {
		append(new Node<Person>(new Person(s)));
	}
	
	// This method takes in a Node<ArrayList<Person>>, and Appends n to the tail of this list.
	public void append(Node<Person> n) {
		
		// Corner case: empty list.
		//The Corner Case means there are no people in that index of the hash table.
		if (tail == null) {
			n.setPrev(null);
			n.setNext(null);
			head = n;
			tail = n;
		}
		
		else {
			tail.setNext(n);
			n.setPrev(tail);
			tail = n;
			n.setNext(null);
		}
	}
	
	//Returns an int equal to the length of this PersonLinkedList.
	public int length() {
		int length = 0;
		Node<Person> placeHead = head;
		while (placeHead != null) {
			length++;
			placeHead = placeHead.getNext();
		}
		return length;
	}
	
	//Returns the Node<ArrayList<Person>> located at the specified location in 
	//this PersonLinkedList
	public Node<Person> getNode(int location) {
		if ((this.length() - 1) < location || head == null) {
			sop("Node you are trying to find is out of bounds");
		}
		Node<Person> placeHead = head;
		int current = 0;
		while (placeHead != null) {
			if (current == location)
				return placeHead;
			current++;
			placeHead = placeHead.getNext();
		}
		return null;
	}
	
	// If this PersonLinkedList contains a Node who's name is the target, returns that
	// Node. If the target appears multiple times in this list, returns the first occurrence.
	// If the target is not in this list, returns null.
	public Node<Person> find(String target) {
		Node<Person> placeHead = head;
		while (placeHead != null) {
			if (this.match(placeHead, target) == true) {
				return placeHead;
			}
			placeHead = placeHead.getNext();
		}
		return null;
	}
	
	// Helper method, returns true if the node startNode's Person matches the target string.
	public boolean match(Node<Person> startNode, String target) {
		if (startNode.getData().getName().equalsIgnoreCase(target)) {
			return true;
		}
		else
			return false;
	}
	
	//Delete method used to delete a Node<ArrayList<Person>> from this PersonLinkedList.
	public void delete(Node<Person> deleteMe) {
		if (this.find(deleteMe.getData().getName()) == null) {
			sop("Person " + deleteMe + " is not your friend.");
			return;
		}
		
		Node<Person> beforeFirst = deleteMe.getPrev();
		Node<Person> afterLast = deleteMe.getNext();
		
		//Check all 4 corner cases.
		if (beforeFirst != null && afterLast != null) {
			beforeFirst.setNext(afterLast);
			afterLast.setPrev(beforeFirst);	
		}
		if (beforeFirst == null && afterLast != null) {
			afterLast.setPrev(beforeFirst);
			head = afterLast;
		}
		if (beforeFirst != null && afterLast == null) {
			beforeFirst.setNext(afterLast);
			tail = beforeFirst;
		}
		if (beforeFirst == null && afterLast == null) {
			head = null;
			tail = null;
		}
	}
	
	//Setters and getters as appropriate.
	public Node<Person> getHead() {
		return head;
	}

	public Node<Person> getTail() {
		return tail;
	}

	//toString Method returns the names of every Node in the list, 
	//along with each Node's friend list.
	@Override
	public String toString() {
		String s = "";
		Node<Person> placeHead = this.getHead();
		while (placeHead != null) {
			s += placeHead.getData().getName() + ": ";
			PersonLinkedList p = placeHead.getData().getFriends();
	    	for (int i = 0; i < p.length(); i++) {
	    		s += p.getNode(i).getData().getName() + ", ";
	    	}
			s += "\n";
			placeHead = placeHead.getNext();
		}
		return s;
	}
	
	//A helper method used to make printing information easier and faster.
	public static void sop(Object x) {
		System.out.println(x);
	}
}