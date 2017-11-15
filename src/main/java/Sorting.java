import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * Class implementing sorting algorithms.
 *
 * @see <a href="https://cs125.cs.illinois.edu/lab/11/">Lab 11 Description</a>
 */

public class Sorting {

    /** Increment to sweep the sort. */
    private static final int SORT_INCREMENT = 1;

    /** Total number of values to try. */
    private static final int TOTAL_SORT_VALUES = 100;

    /** Total data size. */
    private static final int TOTAL_INTEGER_VALUES = 10000;

    /**
     * Bubble sort.
     *
     * @param array unsorted input array
     * @return the sorted array, or null on failure
     */
    static int[] bubbleSort(final int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 1; j < array.length - i; j++) {
                if (array[j - 1] > array[j]) {
                    swap(array, j - 1, j);
                }
            }
        }
        return array;
    }
    /**
     * helper function that swaps two ints in a array given the array and the indexes.
     * @param array - the array which contains the swapping targets
     * @param a - one of the target to be swapped
     * @param b - the second of the target to be swapped
     */
    static void swap(final int[] array, final int a, final int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    /**
     * Selection sort.
     *
     * @param array unsorted input array
     * @return the sorted array, or null on failure
     */
    static int[] selectionSort(final int[] array) {
        if (array.length <= 0) {
            return null;
        }
        return selectionHelper(array, 0, array.length - 1);
    }
    /**
     * Helper for selection sort.
     * @param array the unsorted array
     * @param low the lower bound
     * @param hi the higher bound
     * @return sorted array
     */
    static int[] selectionHelper(final int[] array, final int low, final int hi) {
        if (low == hi) {
            return array;
        }
        int min = findMin(array, low, hi);
        swap(array, low, min);
        return selectionHelper(array, low + 1, hi);
    }

    /**
     * helper function to find the index of min between indicated indexes.
     * @param arr - the entire array
     * @param low - the lowest bound for which to search, inclusive
     * @param hi - the highest bound for which to search, inclusive
     * @return the minimum between hi and low
     */
    static int findMin(final int[] arr, final int low, final int hi) {
        int min = low;
        for (int i = low + 1; i <= hi; i++) {
            if (arr[i] < arr[min]) {
                min = i;
            }
        }
        return min;
    }


    /**
     * Merge sort.
     *
     * @param array array that needs to be sorted
     * @return the sorted array, or null on failure
     */
    static int[] mergeSort(final int[] array) {
        if (array.length <= 0) {
            return null;
        } else if (array.length == 1) {
            return array;
        }
        int mid = array.length / 2;
        int[] left = new int[mid];
        int[] right = new int[array.length - mid];
        for (int i = 0; i < left.length; i++) {
            left[i] = array[i];
        }
        for (int j = 0; j < right.length; j++) {
            right[j] = array[mid + j];
        }

        return merge(mergeSort(left), mergeSort(right));
    }

    /**
     * Merge helper function that merges two sorted arrays into a single sorted array.
     * <p>
     * Implement an in place merge algorithm that repeatedly picks the smaller of two numbers from
     * "right" and "left" subarray and copies it to the "arr" array to produce a bigger sorted array
     *
     * @param arr1 array contains sorted subarrays, should contain the resulting merged sorted array
     * @param arr2 start position of sorted left subarray
     * @return the sorted array, or null on failure
     */
    static int[] merge(final int[] arr1, final int[] arr2) {
        int i = 0;
        int j = 0;
        int[] output = new int[arr1.length + arr2.length];
        for (int k = 0; k < (arr1.length + arr2.length); k++) {
            if (i < arr1.length && (j >= arr2.length || arr1[i] <= arr2[j])) {
                output[k] = arr1[i];
                i++;
            } else {
                output[k] = arr2[j];
                j++;
            }
        }
        return output;
    }

    /**
     * Main method for testing.
     * <p>
     * This method reads numbers from input file of type specified by user, runs different sorting
     * algorithms on different sizes of the input and plots the time taken by each.
     *
     * @param unused unused input arguments
     * @throws FileNotFoundException thrown if the file is not found
     * @throws URISyntaxException thrown if the file is not found
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public static void main(final String[] unused)
            throws FileNotFoundException, URISyntaxException {

        /*
         * Prompt for the input file.
         */
        Scanner userInput = new Scanner(System.in);
        String dataFilename = "";
        while (true) {
            System.out.println("Enter the type of data to sort "
                    + "(1 for sorted, 2 for almost sorted, 3 for reverse sorted): ");
            int datatype = userInput.nextInt();
            switch (datatype) {
                case 1 :
                    dataFilename = "sorted.txt";
                    break;
                case 2 :
                    dataFilename = "almostsorted.txt";
                    break;
                case 3 :
                    dataFilename = "reverse.txt";
                    break;
                default :
                    System.out.println("Please enter 1, 2, or 3");
                    break;
            }
            if (!dataFilename.equals("")) {
                break;
            }
        }

        /*
         * Load the input file.
         */
        String numbersFilePath = //
                Sorting.class.getClassLoader().getResource(dataFilename).getFile();
        numbersFilePath = new URI(numbersFilePath).getPath();
        File numbersFile = new File(numbersFilePath);
        Scanner numbersScanner = new Scanner(numbersFile, "UTF-8");
        int[] allnumbers = new int[TOTAL_INTEGER_VALUES];
        for (int i = 0; i < TOTAL_INTEGER_VALUES; i++) {
            allnumbers[i] = numbersScanner.nextInt();
        }
        numbersScanner.close();

        /*
         * Prompt for the algorithm to use.
         */
        int whichAlgorithm;
        while (true) {
            System.out.println("Enter the sorting algorithm that you want to use"
                    + " (1 for bubble sort, 2 for insertion sort, 3 for merge sort): ");
            whichAlgorithm = userInput.nextInt();
            if (whichAlgorithm > 0 && whichAlgorithm < 4) {
                break;
            }
        }

        int[] timeValues = new int[TOTAL_SORT_VALUES];
        boolean succeeded = true;
        for (int i = 1; i <= TOTAL_SORT_VALUES; i++) {
            /*
             * Sweep in increments of SORT_INCREMENT. Copy the array first. Clone doesn't work here
             * because we only want a certain number of values.
             */
            int[] unsortedArray = new int[i * SORT_INCREMENT];
            for (int j = 0; j < (i * SORT_INCREMENT); j++) {
                unsortedArray[j] = allnumbers[j];
            }

            /*
             * Sort the array using the algorithm requested. Measure and record the time taken.
             */
            int[] sortedArray;
            long startTime = System.currentTimeMillis();
            switch (whichAlgorithm) {
                case 1 :
                    sortedArray = bubbleSort(unsortedArray);
                    break;
                case 2 :
                    sortedArray = selectionSort(unsortedArray);
                    break;
                default :
                    sortedArray = mergeSort(unsortedArray);
                    break;
            }
            if (sortedArray == null) {
                succeeded = false;
                break;
            }
            long endTime = System.currentTimeMillis();
            timeValues[i - 1] = (int) (endTime - startTime);
        }
        userInput.close();

        if (!succeeded) {
            System.out.println("Sorting failed");
            return;
        }

        /*
         * Plot the results if the sorts succeeded.
         */
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new GraphPlotter(timeValues));
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setVisible(true);
    }
}
