package facebook;

import java.util.Random;

public class FacebookSimulator {
	//Runner class, only class with a main method so that users can interact with it.
	
	//A helper method used to make printing information easier and faster.
  	public static void sop(Object x) {
  		System.out.print(x);
  	}
  	
  	//A helper method used to make printing information easier and faster.
  	public static void sopln(Object x) {
  		System.out.println(x);
  	}
  	
  	//Adds the 50 given names to the HashTable T
  	public static void hardcodeAdd(HashTable T) {
  		T.addPerson(new String("Liam"));
  		T.addPerson(new String("Noah"));
  		T.addPerson(new String("William"));
  		T.addPerson(new String("James"));
  		T.addPerson(new String("Logan"));
  		T.addPerson(new String("Benjamin"));
  		T.addPerson(new String("Mason"));
  		T.addPerson(new String("Elijah"));
  		T.addPerson(new String("Oliver"));
  		T.addPerson(new String("Jacob"));
  		T.addPerson(new String("Lucas"));
  		T.addPerson(new String("Michael"));
  		T.addPerson(new String("Alexander"));
  		T.addPerson(new String("Ethan"));
  		T.addPerson(new String("Daniel"));
  		T.addPerson(new String("Matthew"));
  		T.addPerson(new String("Aiden"));
  		T.addPerson(new String("Henry"));
  		T.addPerson(new String("Joseph"));
  		T.addPerson(new String("Jackson"));
  		T.addPerson(new String("Samuel"));
  		T.addPerson(new String("Sebastian"));
  		T.addPerson(new String("David"));
  		T.addPerson(new String("Carter"));
  		T.addPerson(new String("Wyatt"));
  		
  		T.addPerson(new String("Emma"));
  		T.addPerson(new String("Olivia"));
  		T.addPerson(new String("Ava"));
  		T.addPerson(new String("Isabella"));
  		T.addPerson(new String("Sophia"));
  		T.addPerson(new String("Mia"));
  		T.addPerson(new String("Charlotte"));
  		T.addPerson(new String("Amelia"));
  		T.addPerson(new String("Evelyn"));
  		T.addPerson(new String("Abigail"));
  		T.addPerson(new String("Harper"));
  		T.addPerson(new String("Emily"));
  		T.addPerson(new String("Elizabeth"));
  		T.addPerson(new String("Avery"));
  		T.addPerson(new String("Sofia"));
  		T.addPerson(new String("Ella"));
  		T.addPerson(new String("Madison"));
  		T.addPerson(new String("Scarlett"));
  		T.addPerson(new String("Victoria"));
  		T.addPerson(new String("Aria"));
  		T.addPerson(new String("Grace"));
  		T.addPerson(new String("Chloe"));
  		T.addPerson(new String("Camilia"));
  		T.addPerson(new String("Penelope"));
  		T.addPerson(new String("Riley"));
  	}
  	
  	/*
  	 * The main method is where the HashTable object is created and where the necessary methods 
  	 * are called from. First, main creates an empty HashTable T, which calls hardcodeAdd and 
  	 * stores the 50 names in their correct locations within T. It then iterates through each 
  	 * PersonLinkedList, and adds a random number of friends to each Person using HashTable’s 
  	 * chainedHashInsert method. The friends being added are also randomly chosen using the random 
  	 * number generator. Main then "boots up" the terminal, and allows users to interact with the 
  	 * simulator, by calling the terminal method in the HashTable class. This method terminates 
  	 * whenever the terminal method terminates, based on user interaction with the Facebook Simulator.
  	 */
  	public static void main(String[] args) throws Exception {
  		sopln("Welcome to Facebook's Portal for Friends!");
  		HashTable T = new HashTable();
  		hardcodeAdd(T);
  		
  		Random random = new Random();
  		for (int i = 0; i < 11; i++) {
  			PersonLinkedList p = T.getList().get(i);
  			for (int j = 0; j < p.length(); j++) {
  				for (int friendsToGenerate = 0; friendsToGenerate < 4; friendsToGenerate++) {
  					PersonLinkedList r = T.getList().get(random.nextInt(11));
  					Node<Person> friend1 = p.getNode(j);
  					Node<Person> friend2 = r.getNode(random.nextInt(r.length()));
  					T.chainedHashInsert(T, friend1.getData().getName(), friend2.getData().getName());
  				}
  			}
  		}
  		T.terminal();
  		
  	}

}
