package atc;

import java.util.ArrayList;
import java.util.Random;

//This class is used to define the object Airplane, and specifies the methods
//an object of type Airplane can call. Airplane is an object which will be used
//by all classes in this package.
public class Airplane {
	
	//These are the 4 variables every airplane will have.
	private String name; //This is the airplane's flight number
	private double distance; //This is direct distance to the runway (in meters)
	private double elevation; //This is the flights elevation (in meters)
	private double atc; //ATC is the approach code for each airplane
	
	//Constructing an object of type Airplane with the input parameters.
	//ATC is a variable calculated with the equation:
	//ATC = 15000 - (distance + elevation)/2
	public Airplane(String name, double distance, double elevation) {
		this.setName(name);
		this.setDistance(distance);
		this.setElevation(elevation);
		this.setATC(15000 - .5*(distance + elevation));
	}
	
	//Constructing an airplane with the input argument of an object type Airplane.
	public Airplane(Airplane plane) {
		this.setName(plane.getName());
		this.setDistance(plane.getDistance());
		this.setElevation(plane.getElevation());
		this.setATC(15000 - .5*(plane.getDistance() + plane.getElevation()));
	}
	
	//No-argument constructor for airplane
	public Airplane() {
		
	}

	//As all the variables for an Airplane are private, the below methods are
	//setters and getter methods for each private variable.
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public double getATC() {
		return atc;
	}

	public void setATC(double atc) {
		this.atc = atc;
	}

	//These 2 objects are used in the generate method, and are created outside the
	//method in order to save memory.
	Random random = new Random();
	String[] alpha = {"A", "B", "C", "D", "E",
						"F", "G", "H", "I", "J",
						"K", "L", "M", "N", "O",
						"P", "Q", "R", "S", "T",
						"U", "V", "W", "X", "Y", "Z"};
	
	//Generate method is used to create a randomly generated Airplane.
	//This means that name, distance, and elevation will all be created using
	//a random number generator.
	public Airplane generate() {
		name = new String(
				alpha[random.nextInt(26)] +
				alpha[random.nextInt(26)] +
				Integer.toString(random.nextInt(9999) + 1));
		distance = random.nextDouble()*17000 + 3000;
		elevation = random.nextDouble()*2000 + 1000;
		return new Airplane(name, distance, elevation);
	}

	//Calls the generate method 30 times and adds each randomly generated 
	//airplane to an ArrayList<Airplane> called incomingFlights.
	public ArrayList<Airplane> flights(int toGenerate) {
		ArrayList<Airplane> incomingFlights = new ArrayList<Airplane>();
		for (int counter = 0; counter < toGenerate; counter ++)
			incomingFlights.add(counter, this.generate());
		return incomingFlights;
	}
	
	//The toString method displays the information of each Airplane in a
	//clean and legible manner.
	@Override
	public String toString() {
		return "(" + this.getName() + ", D:" + String.format("%.0f", this.getDistance()) + 
				", H:" + String.format("%.0f", this.getElevation()) + 
				") - AC: " + String.format("%.0f", this.getATC());
	}
	
	//sop method is used as a shorthand instead of typing 
	//System.out.println() every time a print statement is used.
	public static void sop(Object x) {
		System.out.println(x);
	}
}
