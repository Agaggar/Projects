package facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HashTable {
	
	//HashTable class with appropriate methods to meet all functional requirements.
	 
    private ArrayList<PersonLinkedList> list = new ArrayList<PersonLinkedList>(11);
 
    //Constructor: Initializes each of the 11 slots of the HashTable as a new 
    //PersonLinkedList.
    public HashTable() {
        for(int i = 0; i < 11; i++) {
            list.add(new PersonLinkedList());
        }
    }
    
    /*
     * ChainedHashInsert modifies the pseudocode to include two String parameters.
     * It first checks to make sure the target String is in the directory; if not, 
     * the method does nothing.
     * It then checks to see if addFriend is in the directory. If it is not, it adds addFriend
     * as a new user.
     * The for loop checks to make sure target and addFriend are not already friends.
     * The next line adds addFriend to target's friend list.
     * Finally, it adds target to addFriend's friend list (as friendships must be mutual).
     */
    public void chainedHashInsert(HashTable h, String addFriend, String target) {
    	int index = this.getHashLocation(target);
    	Node<Person> n = list.get(index).find(target);
    	if (n == null) {
    		sopln("Person: " + target + " was not in directory.");
    		return;
    	}
    	if (list.get(this.getHashLocation(addFriend)).find(addFriend) == null) {
    		this.addPerson(addFriend);
    		sopln("Person: " + addFriend + " was first created in directory before friending "
    				+ "with " + target + ".");
    	}
    	for (int i = 0; i < n.getData().getFriends().length(); i++) {
			Person p = n.getData().getFriends().getNode(i).getData();
    		if (p.getName().equalsIgnoreCase(addFriend) || target.equalsIgnoreCase(addFriend)) {
    			//sopln(target + " and " + addFriend + " are already friends.");
    			return;
    		}
    	}
    	n.getData().getFriends().append(addFriend);
    	list.get(getHashLocation(addFriend)).find(addFriend).getData().getFriends().append(target);
    }
    
    /*
     * ChainedHashSearch follows the pseudocode. It calls the find method in
     * PersonLinkedList class to determine whether the String name is in the directory.
     * If not, return null. If it is, then return the PersonLinkedList friend of String name.
     */
    public PersonLinkedList chainedHashSearch(HashTable h, String name) {
    	int index = this.getHashLocation(name);
    	if (list.get(index).find(name) == null) {
    		sopln("Person: " + name + " was not found.");
    		return null;
    	}
    	return list.get(index).find(name).getData().getFriends();
    }
    
    /*
     * ChainedHashDelete modifies the pseudocode to include two String parameters.
     * It first checks to make sure the target String is in the directory; if not, 
     * the method does nothing because a Person not in the directory obviously cannot be deleted.
     * It then iterates through target's friend list to find String deleteMe.
     * If deleteMe is found, it removes Person deleteMe from the target's friend list. 
     * Finally, it calls getFriends() method to print target's new friend list, if deleteMe was found.
     */
    public void chainedHashDelete(HashTable h, String deleteMe, String target) {
    	int index = this.getHashLocation(target);
    	Node<Person> n = list.get(index).find(target);
    	if (n == null) {
    		sopln("Person: " + target + " was not found and so could not be deleted.");
    		return;
    	}
    	Node<Person> p;
    	boolean found = false;
    	for (int i = 0; i < n.getData().getFriends().length(); i++) {
    		p = n.getData().getFriends().getNode(i);
	    		if (p.getData().getName().equalsIgnoreCase(deleteMe)) {
	    			sopln("Person " + deleteMe + " was deleted from " + target + "'s friend list.");
	    			n.getData().getFriends().delete(p);
	    			found = true;
	    		}
    	}
    	if (found == true) {
    		sopln(target + "'s new Friend List is: ");
        	this.getFriends(target);	
    	}
    	else
    		sopln(target + " is not friends with " + deleteMe);
    }
    
    /*
     * A method not part of functional requirements; this method adds a new Person
     * into the directory of Persons held by the HashTable, if Person name does not
     * already exist.
     */
    public void addPerson(String name) {
    	int index = this.getHashLocation(name);
    	Node<Person> n = list.get(index).find(name);
    	if (n != null) {
    		sopln("Person " + name + " already exists.");
    		return;
    	}
    	else {
        	list.get(index).append(name);
    	}
    }
    
    /*
     * A method not part of functional requirements; this method deletes a given Person
     * from the directory of Persons held by the HashTable, if Person name does not
     * already exist.
     */
    public void deletePerson(String name) {
    	int index = this.getHashLocation(name);
    	Node<Person> n = list.get(index).find(name);
    	if (n == null) {
    		sopln("Person " + name + " does not exist.");
    		return;
    	}
    	list.get(index).delete(n);
    	sopln("Person " + name + " was deleted from the directory");
    	for (int i = 0; i < 11; i++) {
			PersonLinkedList r = list.get(i);
			for (int j = 0; j < r.length(); j++) {
				PersonLinkedList s = r.getNode(j).getData().getFriends();
				for (int k = 0; k < s.length(); k++) {
					if (name.equalsIgnoreCase(s.getNode(k).getData().getName()) == true)
						s.delete(s.getNode(k));
				}
			}
		}
    	sopln("Person " + name + " was deleted from every Person's friend list.");
    }
    
    //getFriends method simply prints all the friends in target's friend list.
    public void getFriends(String target) {
    	PersonLinkedList person = this.chainedHashSearch(this, target);
    	for (int i = 0; i < person.length(); i++)
			sop(person.getNode(i).getData() + ", ");
		sopln("");
    }
    
    //Returns the hashtable slot number where String key would be located.
    public int getHashLocation(String key) {
    	Person findMe = new Person(key);
        return findMe.hashCode();
    }
    
    //This method displays the options the user can select from. Calls method sopln.
    public void terminalOptions() {
		sopln("Please select and input an integer between the options below: ");
		sopln("NOTE: 1 must first be selected in order to perform options 2-6 on the correct Person.");
		sopln("1. Select a person to perform methods to. ");
		sopln("2. Type in the name of a person to add to the selected person's friend list. "
				+ "(uses Chained-Hash-Insert)");
		sopln("3. Type in the name of a person to delete from the selected person's friend list. "
				+ "(uses Chained-Hash-Delete)");
		sopln("4. Searches and prints each friend from the selected person's friend list. " 
				+ "(uses Chained-Hash-Search)");
		sopln("5. Type in the name of the second person to verify if they and the selected person "
				+ "are friends. (Prints yes or no)");
		sopln("6. Sort the selected friend list alphabetically. (Uses BST)");
		sopln("7. Add a NEW Person to directory.");
		sopln("8. Delete an entire Person from directory.");
		sopln("9. Print all Persons along with the LinkedList of their friends.");
		sopln("10. Quit terminal. ");
  	}
  	
    //This method is used to both direct the user to choose one of the options and
    //responds appropriately to the option by calling the correct methods.
    //The print statements embedded within terminalOptions and within this method allow for
  	//a seamless and easy-to-understand interface for the user.
  	public void terminal() throws NumberFormatException, IOException {
  		sopln("Remember to Type 1 to select a Person to use Friend methods for, or some methods will not work.");
  		sopln("Alternatively, Type 9 to print all available Persons and their friends.");
  		sopln("Enjoy!\n");
  		InputStreamReader reader = null;
  		BufferedReader br = null;  		
  		
  		reader = new InputStreamReader(System.in);
  		br = new BufferedReader(reader);
  		
  		String target = null;
  		int tryMe = 0;
  		int input = tryMe;
  		
  		while (input != 10) {
  			sopln("");
  			this.terminalOptions();
  			
  			try {
  	  			tryMe = Integer.parseInt(br.readLine());
  	  			input = tryMe;
  	  		}
  	  		catch (NumberFormatException e) {
  	  			sop("You entered an invalid choice. ");
  	  			input = 0;
  	  		}
  			
  			while (input < 1 || input > 10) {
  	  			sopln("Please select a valid integer choice.");
	  	  		try {
	  	  			tryMe = Integer.parseInt(br.readLine());
	  	  			input = tryMe;
	  	  		}
	  	  		catch (NumberFormatException e) {
	  	  			sop("You entered an invalid choice. ");
	  	  			input = 0;
	  	  		}
  	  		}
  			
  			if (input == 1) {
  				sopln("Please type in the name of the Person you'd like to use Friend methods for.");
  				String useMe = br.readLine();
  				while(list.get(this.getHashLocation(useMe)).find(useMe) == null) {
  					sopln(useMe + " does not exist.");
  					sopln("Please type in the name of a Person that exists in the given directory.");
  					useMe = br.readLine();
  				}
  				int index = this.getHashLocation(useMe);
  				target = list.get(index).find(useMe).getData().getName();
  		    	if (target == null) {
  		    		sopln("Person: " + useMe + " not found in any friend directory.");
  		    	}
  		    	else
  		    		sopln("Person " + target + " has been selected");
  			}
  			
  			if (input == 2) {
  				if (target == null) {
  					sopln("Please select option 1 first.");
  				}
  				else {
  					sopln("Person " + target + " has been selected");
  					sopln("Please type in the name of the friend you'd like to add to " + target + ".");
  					String line = br.readLine();
  	  				this.chainedHashInsert(this, line, target);
  	  				sopln("Person " + target + "'s new friend list is: ");
					this.getFriends(target);
  				}
  			}
  			
  			if (input == 3) {
  				if (target == null)
  					sopln("Please select option 1 first.");
  				else { 
  					sopln("Person " + target + " has been selected");
					sopln("Please type in the name of the friend you'd like to delete from " + target + "'s list.");
					String line = br.readLine();
					this.chainedHashDelete(this, line, target);
  				}
  			}
  			
  			if (input == 4) {
  				if (target == null)
  					sopln("Please select option 1 first.");
  				else {
  					sopln("Person " + target + " has been selected");
  					sopln("Person " + target + "'s friends are: ");
  					this.getFriends(target);
  				}
  			}
  			
  			if (input == 5) {
  				if (target == null)
  					sopln("Please select option 1 first.");
  				else { 
  					sopln("Person " + target + " has been selected");
  					sopln("Please type in the name of the friend to see if " + target + 
							" is friends with them.");
					String friend = br.readLine();
					PersonLinkedList person1 = this.chainedHashSearch(this, target);
					PersonLinkedList person2 = this.chainedHashSearch(this, friend);
					boolean friends = false;
					if (person1 != null && person2 != null) {
						for (int i = 0; i < person1.length(); i++) {
							Person p = person1.getNode(i).getData();
							if (p.getName().equalsIgnoreCase(friend))
								friends = true;
						}
						if (friends == true) {
							friends = false;
							for (int i = 0; i < person2.length(); i++) {
								Person p = person2.getNode(i).getData();
								if (p.getName().equalsIgnoreCase(target))
									friends = true;
							}
						}
					}
					if (friends == true)
						sopln("Yes, " + target + " and " + friend + " are both friends");
					else
						sopln("N0, " + target + " and " + friend + " are not both friends");
  				}
  			}
  			
  			if (input == 6) {
  				if (target == null)
  					sopln("Please select option 1 first.");
  				else { 
  					sopln("Person " + target + " has been selected");
  					int index = this.getHashLocation(target);
  	  				PersonLinkedList p = list.get(index).find(target).getData().getFriends();
  	  				BinarySearchTree bt = new BinarySearchTree();
  					
  	  				for (int i = 0; i < p.length(); i++) 
  	  					bt.treeInsert(bt, p.getNode(i).getData());
  	  				
  	  				sop(target + "'s Friend List sorted alphabetically: ");
  	  				if (bt.getRoot() == null)
  	  					sopln(target + " currently has no friends.");
  					else
  						bt.inorderTreeWalk(bt.getRoot());
  	  				sopln("");
  				}
  			}
  			
  			if (input == 7) {
  				sopln("Please type in the name of the person you'd like to add to the directory. ");
  				String addMe = br.readLine();
  				if (list.get(this.getHashLocation(addMe)).find(addMe) != null)
  					sopln("Person " + addMe + " already exist.");
  				else {
  					this.addPerson(addMe);
  					sopln(addMe + " has been added successfully.");
  				}
  			}
  			
  			if (input == 8) {
  				sopln("Please type in the name of the person you'd like to delete from the directory. ");
  				String deleteMe = br.readLine();
  				if (list.get(this.getHashLocation(deleteMe)).find(deleteMe) == null)
  					sopln("Person " + deleteMe + " does not exist.");
  				else
  					this.deletePerson(deleteMe);
  			}
  			
  			if (input == 9) {
  				for (int i = 0; i < list.size(); i++) {
  					sopln("Hash Slot #" + i + "\n" + list.get(i));
  				}
  			}
  			
  			try {
  				if (this.getList().get(getHashLocation(target)).find(target) == null)
  					target = null;
  			}
  			catch (NullPointerException e) {
  				target = null;
  			}
  		}
  		
  		br.close();
  	}
  	
  	//Getter method to retrieve the HashTable ArrayList.
  	public ArrayList<PersonLinkedList> getList() {
		return list;
	}

	//A helper method used to make printing information easier and faster.
  	public static void sop(Object x) {
  		System.out.print(x);
  	}
  	
  	//A helper method used to make printing information easier and faster.
  	public static void sopln(Object x) {
  		System.out.println(x);
  	}
}