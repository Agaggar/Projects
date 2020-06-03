package sortingAlgorithms;

public class CountingSort {

	/*This method in the Counting Sort algorithm is what will be doing all the
	 *sorting. The method takes in 2 arrays, one as the input and one as the 
	 *output, and also has an int variable called range. The range of the numbers
	 *will determine the size of Array C. The method first creates a hardcoded array
	 *of ints called index, which will be used to show the relative position of each
	 *element in Array A for this assignment. An array C of elements from [0...range]
	 *will be initialized to all zeros. Then, looping through the elements in A, we 
	 *assign each element to its corresponding index in C. We then loop through C, adding
	 *each element in C to the element before it to determine how many input elements 
	 *are less than or equal to index of C. Finally, each element in A will be placed 
	 *into its correct sorted position in the output array B using array C. 
	 *The method returns array B, as a sorted and stable version of array A.
	 *There are various print statements dispersed throughout the method to show
	 *intermediate results for array B and C, as well as the relative index of each
	 *element as its being placed in B to show that the algorithm is stable.
	 */
	public int[] countingSort (int[] A, int[] B, int range) {
		int[] index = new int[] {1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 3, 2, 3};
			//index contains the relative order of occurrences for the elements in A
		int[] C = new int[range + 1];
		for (int i = 0; i < (range + 1); i++)
			C[i] = 0;
		
		sopln("After initializing Arrays C and B: ");
		sop("Array C: ");
		for (int p = 0; p < C.length; p++)
			sop(C[p] + ", ");
		sop("\nArray B: ");
		for (int p = 0; p < B.length; p++)
			sop(B[p] + ", ");
		
		for (int j = 0; j < A.length; j++)
			C[A[j]] = C[A[j]] + 1;
		sopln("\n\nNumber of elements in A equal to a given index in C");
		sop("Array C: ");
		for (int p = 0; p < C.length; p++)
			sop(C[p] + ", ");
		
		for (int i = 1; i < (range + 1); i++)
			C[i] = C[i] + C[i-1];
		
		sopln("\n\nNumber of elements in A less than or equal to a given index in C");
		sop("Array C: ");
		for (int p = 0; p < C.length; p++)
			sop(C[p] + ", ");
		
		sop("\n\nUnsorted values in A: ");
		for (int p = 0; p < A.length; p++)
			sop(A[p] + ", ");
		sop("\nCurrent Indices of A: ");
		for (int p = 0; p < index.length; p++)
			sop(index[p] + ", ");
		
		int[] relative = new int[B.length];
		
		sop("\n\nSorting into B now");
		for (int j = (A.length - 1); j >= 0; j--) {
			B[C[A[j]] - 1] = A[j];
			relative[C[A[j]] - 1] = index[j];
			C[A[j]] = C[A[j]] - 1;

			sopln("\nAfter sort #" + (A.length - j) + ": ");
			sop("Array C: ");
			for (int p = 0; p < C.length; p++)
				sop(C[p] + ", ");
			sop("\nArray B: ");
			for (int p = 0; p < B.length; p++)
				sop(B[p] + ", ");
			sop("\nIndices: ");
			for (int p = 0; p < index.length; p++)
				sop(relative[p] + ", ");
			sopln("");
		}
		return B;
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
