package facebook;

public class Person {
	//Person class. Each name in the directory is of type Person, just aggregated within a Node.
	
	private String name;
	private PersonLinkedList friends;
	
	public Person (String name) {
		this.setName(name);
		this.setFriends(new PersonLinkedList());
	}
	
	//Method used to add a String to Person's friend list
	public void addFriend(String s) {
		for (int i = 0; i < getFriends().length(); i++) {
			Person p = this.getFriends().getNode(i).getData();
    		if (p.getName().equalsIgnoreCase(s))
    			return;
    	}
		this.getFriends().append(new Node<Person>(new Person(s)));
	}
	
	//Returns the weighted sum of its characters as a double. Used by hashCode() method 
	public double asciiKey(String s) {
		double sum = 0;
		double ascii = 0;
		for (int i = 0; i < s.length(); i++) {
			ascii = (int) s.charAt(i);
			ascii = (ascii * Math.pow(128, (s.length() - 1 - i)));
			sum = sum + ascii;
		}
		return sum;
	}
	
	//Returns the int value of the Person's hashCode, as specified by the division method.
	//Uses Person's name to calculate its hashcode.
    public int hashCode() {
    	int result = 0;
    	result = (int) (this.asciiKey(this.getName()) - Math.floor(this.asciiKey(this.getName())/11)*11);
    	return result;
    }
    
    //Converts the given letter of this Person's name to Ascii. Used by BST class.
	public int toAscii(int letter) {
		int ascii = (int) this.getName().charAt(letter);
		return ascii;
	}

    //Setters and Getter methods as appropriate.
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public PersonLinkedList getFriends() {
		return friends;
	}

	public void setFriends(PersonLinkedList friends) {
		this.friends = friends;
	}

	//toString method returns the Person's nameinstead of Person's object hashcode.
	@Override
	public String toString() {
		return this.getName();
	}
}
