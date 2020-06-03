package atc;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

//The HeapSort class contains all the methods for a Heap Priority Queue.
public class HeapSort {
	
	//A HeapSort object requires 2 input variables: 
	private ArrayList<Airplane> flights; //An ArrayList of Airplanes,
	private int heapSize; //And the size of the heap.
	
	//Constructor for HeapSort, which takes and initializes the input parameters.
	public HeapSort(ArrayList<Airplane> flights, int heapSize) {
		this.setFlights(flights);
		this.setHeapSize(heapSize);
	}

	//For the input parameter index, this method returns the location of where
	//the index's parent lies.
	public int parent(int index) {
		return (index - 1)/2;
	}
	
	//For the input parameter index, this method returns the location of where
	//the index's left child lies.
	public int left(int index) {
		return 2*index + 1;
	}
	
	//For the input parameter index, this method returns the location of where
	//the index's right child lies.
	public int right(int index) {
		return 2*(index+1);
	}
	
	/* This method takes the input parameters in order to rearrange the input
	 * ArrayList flights such that the Airplane located at the input index
	 * would satisfy the max-heap property.
	 * In other words, this method ensures the max-heap property is met for the
	 * given input "node" (Airplane at the index specified).
	 * (The Max-Heap Property states that the Airplane's key at the given node must be
	 * less than or equal to the key of that Airplane's parent node.
	 * Each Airplane's "key" is specified by the value of its ATC variable.) */ 
	public void maxHeapify(ArrayList<Airplane> flights, int index) {
		int left = this.left(index);
		int right = this.right(index);
		int largest;
		
		if(left <= (this.getHeapSize()) && 
				flights.get(left).getATC() > flights.get(index).getATC())
			largest = left;
		else
			largest = index;
		
		if(right <= (this.getHeapSize()) && 
				flights.get(right).getATC() > flights.get(largest).getATC())
			largest = right;
		
		if (largest != index) {
			Airplane temp1 = flights.get(index);
			flights.set(index, flights.get(largest));
			flights.set(largest, temp1);
			this.maxHeapify(flights, largest);
		}
	}
	
	//Calls maxHeapify method for all PARENT (root) nodes in the input
	//ArrayList<Airplane> flights. By the end of this method, all Airplanes in flights 
	//should now satisfy the max-heap property.
	//At the start of this method, set the heapSize to the number of nodes in flights.
	public void buildMaxHeap(ArrayList<Airplane> flights) {
		this.setHeapSize(flights.size() - 1);
		for (int i = (int)(.5*(flights.size() - 1)); i >= 0; i--)
			this.maxHeapify(flights, i);
	}
	
	//Sorts the input ArrayList<Airplane> called flights by calling buildMaxHeap.
	//flights now satisfies the max-heap property, and so can easily be reversed
	//in order to sort flights, based on their key, from low to high.
	public void heapSort(ArrayList<Airplane> flights) {
		this.buildMaxHeap(flights);
		for (int i = flights.size() - 1; i >= 1; i--) {
			Airplane temp1 = flights.get(0);
			flights.set(0, flights.get(i));
			flights.set(i, temp1);
			setHeapSize(getHeapSize() - 1);
			maxHeapify(flights, 0);
		}
	}
	
	//Returns the node with the highest key, i.e. the root node.
	//Assumes that the ArrayList passed in already satisfies the max-heap property.
	public Airplane heapMaximum(ArrayList<Airplane> flights) {
		return flights.get(0);
	}
	
	//Extracts (removes) and returns the Airplane with the highest key (i.e. the root
	//node). Sets the new root node as the last node in the ArrayList, and calls 
	//maxHeapify on the new root node. It then sets heapSize and removes the last 
	//node from the input ArrayList. Assumes the input satisfies the max-heap property.
	public Airplane heapExtractMax(ArrayList<Airplane> flights) {
		if (this.getHeapSize() < 0)
			throw new IndexOutOfBoundsException("heap underflow");
		Airplane max = heapMaximum(getFlights());
		flights.set(0, flights.get(this.getHeapSize()));
		setHeapSize(this.getHeapSize() - 1);
		flights.remove(flights.size() - 1);
		maxHeapify(this.getFlights(), 0);
		return max;
	}
	
	//This method can "force change" the key of an Airplane at the specified index to
	//the new specified key. Rearranges the ArrayList such that the Airplane at the
	//specified index will satisfy the max-heap property at its new location.
	public void heapIncreaseKey(ArrayList<Airplane> flights, int index, double key){
		if (key < flights.get(index).getATC()) {
			sop("new key is smaller than current key");
			return;
		}
		flights.get(index).setATC(key);
		while (index > 0 && 
				(flights.get(parent(index)).getATC() < flights.get(index).getATC())) {
			Airplane temp1 = flights.get(this.parent(index));
			flights.set(this.parent(index), flights.get(index));
			flights.set(index, temp1);
			index = parent(index);
		}
	}
	
	//Inserts the input Airplane into the input ArrayList<Airplane> called flights.
	//This method assumes that all nodes in flights satisfy the max-heap property.
	//After adding the airplane to the end of flights, call heapIncreaseKey to move
	//the Airplane to its appropriate location in the max-heap.
	public void maxHeapInsert(ArrayList<Airplane> flights, Airplane toInsert) {
		setHeapSize(getHeapSize() + 1);
		flights.add(toInsert);
		double key = toInsert.getATC();
		flights.get(getHeapSize()).setATC(Integer.MIN_VALUE);
		heapIncreaseKey(flights, getHeapSize(), key);
	}
	
	//This method displays the options the user can select from
	public void terminalOptions() {
		sop("Please select and input an integer between the options below: ");
		sop("1. In case of an emergency, ensures the "
				+ "inputted flight will land first. (uses Heap-Increase-Key())");
		sop("2. Will allow user to add a flight to the queue. (uses Max-Heap-Insert)");
		sop("3. Will allow user to view the first airplane in queue to land. (uses Heap-Extract-Max)");
		sop("4. Will print the flights in the current queue. (uses heapSort)");
		sop("5. Quit terminal. ");
	}
	
	//This method is used to both direct the user to choose one of the options and
	//responds appropriately to the option by calling the correct methods.
	//The print statements embedded within terminalOptions and within this method allow for
	//a seamless and easy-to-understand interface for the user.
	public void terminal() throws NumberFormatException, IOException {
		this.terminalOptions();
		InputStreamReader reader = null;
		BufferedReader br = null;
		
		reader = new InputStreamReader(System.in);
		br = new BufferedReader(reader);
		
		int input = Integer.parseInt(br.readLine());
		
		while (input < 1 || input > 6) {
			sop("Please select a valid choice.");
			input = Integer.parseInt(br.readLine());
		}
		
		while (input != 5) {
			if (input == 1) {
				sop("For the emergency landing flight, please input the "
						+ "FLIGHT you'd like to change");
				sop("i.e. 'MW0146' is a valid format for flight numbers.");
				String emergency = br.readLine();
				boolean found = false;
				for (int i = 0; i < this.getFlights().size(); i++) {
					if(this.getFlights().get(i).getName().compareTo(emergency) == 0) {
						sop(this.getFlights().get(i).toString() + " has been cleared"
								+ " for emergency landing");
						sop("Please input the Approach Code to change " 
								+ emergency + " to: ");
						double key = Double.parseDouble(br.readLine());
						this.heapIncreaseKey(this.getFlights(), i, key);
						found = true;
					}	
				}
				if (found == false)
					sop(emergency + " not found in the current flight queue.\n");
			}
			
			if (input == 2) {
				sop("Please input the 2 letter flight code you would like to add, "
						+ "followed by up to 4 digits (between 0-9)");
				String line = br.readLine();
				if (line.length() > 6)
					sop("Invalid Length for Flight Number");
				else {
					Random random = new Random();
					double distance = random.nextDouble()*17000 + 3000;
					double elevation = random.nextDouble()*2000 + 1000;
					Airplane userFlight = new Airplane(line, distance, elevation);
					this.maxHeapInsert(this.getFlights(), userFlight);
				}
			}
			
			if (input == 3)
				sop(this.heapExtractMax(getFlights()));
			
			if (input == 4) {
				sop("Current Flight Queue (largest AC to smallest AC): ");
				this.flightQueue();
			}
			
			sop("");
			this.terminalOptions();
			input = Integer.parseInt(br.readLine());
		}
		
		br.close();
	}
	
	//A helper method called by terminal to print out all the airplanes in
	//the heapSort's flights ArrayList using toString for each Airplane.
	public void flightQueue() {
		this.heapSort(getFlights());
		int counter = 1;
		for (int i = (this.getFlights().size() - 1); i >= 0; i--) {
			sop(counter + ". " + this.getFlights().get(i).toString());
			counter++;
		}
		buildMaxHeap(getFlights());
	}

	//The heapSort variables are private variables, and so the below 4 methods are
	//simple setters and getters for each private variable.
	public ArrayList<Airplane> getFlights() {
		return flights;
	}

	public void setFlights(ArrayList<Airplane> flights) {
		this.flights = flights;
	}

	public int getHeapSize() {
		return heapSize;
	}

	public void setHeapSize(int heapSize) {
		this.heapSize = heapSize;
	}

	//A helper method used to make printing information easier and faster.
	public static void sop(Object x) {
		System.out.println(x);
	}
}
