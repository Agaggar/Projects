package sortingAlgorithms;

import java.util.Random;

public class MainRunner {
	
	//This method is a helper method used as a shorthand to print statements.
	public static void sopln(Object x) {
		System.out.println(x);
	}
	
	//This method is a helper method used as a shorthand to print statements.
	public static void sop(Object x) {
		System.out.print(x);
	}
	
	//Main method which will demonstrate each of the three algorithms.
	public static void main(String[] args) {
		sopln("Sorting using Counting Sort: ");
		int[] numbers = new int[] {7, 7, 2, 
				8, 4, 2, 3, 5, 4, 8, 3, 6, 4, 6, 2};
		int[] B = new int[numbers.length];
		sopln("Original values in Array A: ");
		for (int p = 0; p < numbers.length; p++)
			sop(numbers[p] + ", ");
		sopln("\n");
		CountingSort cs = new CountingSort();
		cs.countingSort(numbers, B, 8);	
		sopln("\nArray A is now sorted and stored in Array B");
		sopln("Final Result for Array B: ");
		for (int p = 0; p < B.length; p++)
			sop(B[p] + ", ");
		
		sopln("\n\n\nSorting randomly generated hex numbers using Radix Sort: ");
		Random random = new Random();
		String[] toSort = new String[30];
		//Generates 30 random Hex numbers. The inner for loop adds each randomly
		//generated digit to the previous digit to make a 5-digit hex number.
		for (int i = 0; i < 30; i++) {
			String Hex = new String();
			for (int j = 0; j < 5; j++) {
				int digit = random.nextInt(16);
				while (j == 4 && digit == 0) {
					digit = random.nextInt(16);	
				}
				Hex = Integer.toHexString(digit) + Hex;
			}
			toSort[i] = Hex;
		}
		RadixSort radix = new RadixSort();
		sopln("Before Sorting, the Array looks like: ");
		for (int j = 0; j < toSort.length; j++) {
			sop((j+1) + ". " + toSort[j] + ", ");
			if ((j+1)%10==0)
				sopln("");
		}
		radix.radixSort(toSort, 5);
		
		sopln("\n\nSorting given Array from #1 using BucketSort");
		float[] arrayFrom1 = new float[] {7, 7, 2, 
				8, 4, 2, 3, 5, 4, 8, 3, 6, 4, 6, 2};
		BucketSort bucketsort = new BucketSort();
		bucketsort.bucketSort(arrayFrom1);
		sop("\nNow Sorted Array: ");
		for (int i = 0; i < arrayFrom1.length; i++)
			sop((int) arrayFrom1[i] + ", ");
	}

}
