package sortingAlgorithms;

public class RadixSort {

	/* Radix Sort calls for a stable, linear sorting algorithm for each digit of a given
	 * Array of numbers. This assignment calls for the sorting of hexadecimal numbers,
	 * which may contain letters as well as numbers. Thus, I modified the Counting Sort
	 * algorithm from Exercise #1 in 2 way: 1) input and output arrays are of type 
	 * String, and 2) there is an additional parameter of type int called "digit", which
	 * tells the modified Counting Sort Algorithm which digit of the number it will be 
	 * sorting. The radixSort method below calls this method.
	*/
	public String[] modifiedCountingSort (String[] A, String[] B, int range, int digit) {
		int[] C = new int[range + 1];
		//hexHolder is an additional auxiliary array which is technically unneeded.
		//However, I included it as "hexHolder[j]" is a cleaner and easier way to read
		//this code, versus constantly reading "Integer.parseInt... etc"
		int[] hexHolder = new int[A.length];
		for (int j = 0; j < A.length; j++)
			hexHolder[j] = Integer.parseInt(A[j].substring(digit, digit + 1), 16);
		
		for (int i = 0; i < (range + 1); i++)
			C[i] = 0;
		
		for (int j = 0; j < A.length; j++)
			C[hexHolder[j]] = C[hexHolder[j]] + 1;
		
		for (int i = 1; i < (range + 1); i++)
			C[i] = C[i] + C[i-1];
		
		for (int j = (A.length - 1); j >= 0; j--) {
			B[C[hexHolder[j]] - 1] = A[j];
			C[hexHolder[j]] = C[hexHolder[j]] - 1;
		}
		return B;
	}
	
	/* This method is the central method in this class, and is relatively simple.
	 * Its input parameters are an Array A of type String, which contains the
	 * randomly generated hex numbers, and an int d which tells the method how many
	 * digits long each hex number in A is.
	 * It creates a new empty String[] B for every digit in the hexadecimal number.
	 * It then calls the modifiedCOuntingSort method above for a given digit, starting
	 * from the Least Significant Digit, until the most significant digit is sorted.
	 * It then returns A, which is now sorted.
	 */
	public String[] radixSort(String[] A, int d) {
		for (int i = d-1; i >= 0; i--) {
			String[] B = new String[A.length];
			A = this.modifiedCountingSort(A, B, 15, i);
			sopln("\nAfter Sort #" + (d - i) + ": ");
			for (int j = 0; j < A.length; j++) {
				sop((j+1) + ". " + A[j] + ", ");
				if ((j+1)%10==0)
					sopln("");
			}
		}
		sopln("The hex numbers are now fully sorted.");
		return A;
	}
	
	//This method is a helper method used as a shorthand to print statements.
	public static void sopln(Object x) {
		System.out.println(x);
	}
	
	//This method is a helper method used as a shorthand to print statements.
	public static void sop(Object x) {
		System.out.print(x);
	}
}
