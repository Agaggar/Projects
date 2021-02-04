package atc;

import java.util.ArrayList;

//The Simulator class is used as a runner class to test and run all appropriate
//methods, and simulates the Air Traffic Controller Terminal.
public class Simulator {
	
	//A helper method used to make printing information easier and faster.
	public static void sop(Object x) {
		System.out.println(x);
	}
	
	//The main method creates an empty plane in order to create an ArrayList<Airplane>
	//called flights. It then creates a an object heapSort, called sorted, which then
	//turns flights into a max-heap. After creating the necessary objects, main 
	//method "boots up" the terminal, and allows users to interact with the
	//simulator by calling the terminal method.
	public static void main(String[] args) throws Exception {
		Airplane plane = new Airplane();
		ArrayList<Airplane> flights = new ArrayList<Airplane>(plane.flights(40));
		
		HeapSort sorted = new HeapSort(flights, flights.size());
		sorted.buildMaxHeap(sorted.getFlights());
			
		sop("\nTerminal for Air Traffic Control");
		sop("");
		sorted.terminal();
	}
}