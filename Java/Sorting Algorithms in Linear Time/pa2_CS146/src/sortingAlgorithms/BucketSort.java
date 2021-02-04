package sortingAlgorithms;

import java.util.*;

public class BucketSort {

	/* This method is the critical method which will sort the input Array using 
	 * Bucket Sort. Bucket Sort works best if the input is of type float, as one of
	 * the first steps is to convert the input to a range between [0,1) by dividing by
	 * n, the length of Array A. There is also an array called index which is hardcoded,
	 * and contains the relative index of each element for this exercise.
	 * Next, it creates an ArrayList called B of size 2*n, where each element in B is a 
	 * LinkedList<Float>. The elements in B from [0...n) will be used to store each 
	 * element in A to its correct subinterval location in the "bucket" of B. The 
	 * elements in B from [n...2n) will be used to store the relative index of each
	 * element in A. The relative indices do not have a "subinterval location" in B,
	 * but rather get moved proportionally with their respective element in A, to 
	 * demonstrate that the elements in A maintain their relative position.
	 * After being added to buckets, each LinkedList in B calls insertionSort method
	 * below to sort the elements within it. This step is unnecessary in our case, as
	 * each bucket contains the same number. Then, each LinkedList element in B is 
	 * concatenated to the LinkedList stored at index 0. Using the LinkedList at 0,
	 * the now sorted position is added back to the index A and index respectively,
	 * in a sorted and stable fashion.  
	 */
	public float[] bucketSort(float[] A) {
		int n = A.length;
		int[] index = new int[] {1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 3, 2, 3};
			//index contains the relative order of occurrences for the elements in A
		for (int i = 0; i < n; i++)
			A[i] = A[i]*1/n;
		sopln("Input Array converted to range [0,1)");
		for (int i = 0; i < A.length; i++) {
			sop((i+1) + ". " + A[i] + ", ");
			if ((i+1)%7 == 0 && i!=13)
				sopln("");
		}
		sopln("\n");
		
		ArrayList<LinkedList<Float>> B = new ArrayList<LinkedList<Float>>(2*n);
		for (int i = 0; i < (2*n); i++)
			B.add(new LinkedList<Float>());
		
		for (int i = 0; i < n; i++) {
			B.get((int) (n*A[i])).add(A[i]);
			B.get((int) (n*A[i] + n)).add((float) index[i]);
			sopln("Element #" + (i+1) + " stored into bucket, "
					+ "followed by relative index of elements in bucket: ");
			sop(B.get((int) (n*A[i])) + ", ");
			sopln(B.get((int) (n*A[i] + n)) + "\n");
		}
		
		for (int i = 0; i < n; i++) {
			this.insertionSort(B.get(i));
			this.insertionSort(B.get(n+i));
			sopln("\nSorted Bucket #" + (i+1) + ", followed by relative index");
			sop(B.get(i) + ", ");
			sop(B.get(i + n));
		}
		for (int i = 1; i < 2*n; i++) {
			B.get(0).addAll(B.get(i));
		}
		for (int i = 0; i < n; i++) {
			A[i] = B.get(0).get(i)*n;
			float temp = B.get(0).get(n+i);
			index[i] = (int) temp;
		}
		sop("\n\nRelative Indices: ");
		for (int i = 0; i < n; i++)
			sop((int) index[i] + ", ");
		return A;
	}
	
	//A simple insertionSort method, modified such that its input parameter is
	//of type LinkedList<Float>. It compares numbers in the LinkedList with the 
	//numbers before it, moving them to their correct location.
	public void insertionSort(LinkedList<Float> bucket) { 
        int n = bucket.size();
        for (int i = 1; i < n; ++i) { 
            Float key = bucket.get(i);
            int j = i-1; 
  
            while (j >=0 && bucket.get(j) > key) { 
                bucket.set(j+1, bucket.get(j));
                j--;
            } 
            bucket.set(j+1, key); 
        } 
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
