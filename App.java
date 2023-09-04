import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        long t0;
        long t1;
        int arraySize = 10;
        Random rnd = new Random();
        int key = rnd.nextInt(120) + 1;
        int[] array = sorted(arraySize);

        System.out.println("Current Key :" + key);
        System.out.println("Current Size :" + arraySize);

        t0 = System.nanoTime();
        binary_search_sorted(array, key);
        t1 = System.nanoTime();
        System.out.println(" Binary search array Size :" + arraySize + "| Time In ns: " + (t1 - t0));

        // benchmarkSortedUnsortedArraySize();
    }

    public static void benchmarkSortedUnsortedArraySize() {

        int arraySize = 10;
        Random rnd = new Random();
        int key = rnd.nextInt(120) + 1;

        System.out.println("Current Key :" + key);

        System.out.println("Current Size :" + arraySize);
        int[] array = sorted(arraySize);

        long t0 = System.nanoTime();
        search_unsorted(array, key);
        long t1 = System.nanoTime();
        System.out.println(" Search unsorted array Size :" + arraySize + "| Time In ns: " + (t1 - t0));

        t0 = System.nanoTime();
        search_sorted(array, key);
        t1 = System.nanoTime();
        System.out.println(" Search sorted array Size :" + arraySize + "| Time In ns: " + (t1 - t0));

        t0 = System.nanoTime();
        binary_search_sorted(array, key);
        t1 = System.nanoTime();
        System.out.println(" Binary search array Size :" + arraySize + "| Time In ns: " + (t1 - t0));

    }

    public static void benchmarkFindDuplicates() {
    }

    private static int[] sorted(int n) {
        Random rnd = new Random();
        int[] array = new int[n];
        int nxt = 0;
        for (int i = 0; i < n; i++) {
            nxt += rnd.nextInt(10) + 1;
            array[i] = nxt;
        }
        return array;
    }

    public static boolean search_unsorted(int[] array, int key) {
        for (int index = 0; index < array.length; index++) {
            if (array[index] == key) {
                return true;
            }
        }
        return false;
    }

    public static boolean search_sorted(int[] array, int key) {
        for (int index = 0; index < array.length; index++) {
            if (array[index] == key) {
                return true;
            }
            if (array[index] > key) {
                index = array.length;
            }
        }
        return false;
    }

    public static boolean binary_search_sorted(int[] array, int key) {
        int first = 0;
        int last = array.length - 1;
        boolean foundDuplicate=false;
        while (foundDuplicate==false) {
            // jump to the middle
            int index = (first + last) / 2;
            System.out.println("Current Index: " + index);
            System.out.println("Current Value: " + array[index]);
            System.out.println("Current Key: " + array[index]);

            if (array[index] == key) {
                foundDuplicate=true;
            } else if (array[index] < key && index < last) {
                // The index position holds something that is less than
                // what we're looking for, what is the first possible page?
                first = index;
                continue;
            } else if (array[index] > key && index > first) {
                // The index position holds something that is larger than
                // what we're looking for, what is the last possible page?
                last = index;
                continue;
            }
            // Why do we land here? What should we do?
            if ((array[index] > key && index == last) || (array[index] < key && index == first)) {
                return false;
            }
        }
        return true;
    }

    public static boolean binary_find_duplicate_sorted(int[] firstArray, int[] secondArray) {

        int currentElement = 0;
        for (int i = 0; i < firstArray.length; i++) {
            if (currentElement < firstArray[i]) {
                currentElement = firstArray[i];
                if (binary_search_sorted(secondArray, currentElement)) {
                    return true;
                }
            }
        }
        // Above checks once for each int in firstArray if there exists a duplicate in
        // secondArray, using binary search

        return false;
    }

    public static boolean semiLinear_find_duplicate_sorted(int[] firstArray, int[] secondArray) {
        int firstIndex = 0;
        int secondIndex = 0;
        while ((firstIndex < firstArray.length) && (secondIndex < secondArray.length)) {
            if (firstArray[firstIndex] <= secondArray[secondIndex]) {
                firstIndex++;
                if (firstArray[firstIndex] == secondArray[secondIndex]) {
                    return true;
                }
            }
            if (firstArray[firstIndex] > secondArray[secondIndex]) {
                secondIndex++;
            }

        }

        return false;
    }

    public static int[] linear_find_duplicate_unsorted(int[] firstArray, int[] secondArray) {

        int[] duplicates = new int[firstArray.length];
        int duplicateIndex = 0;
        boolean alreadyExists = false;

        for (int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray.length; j++) {
                if (firstArray[i] == secondArray[j]) {
                    alreadyExists = false;
                    for (int k = 0; k < duplicateIndex; k++) {
                        if (duplicates[k] == firstArray[i]) {
                            alreadyExists = true;
                        }

                    }
                    if (alreadyExists == false) {
                        duplicates[duplicateIndex] = firstArray[i];
                        duplicateIndex++;
                        j = secondArray.length;// Kolla om detta är okej, aka ska det vara flera duplicates eller en per
                                               // nummer
                    }
                }
            }
        }

        return duplicates;

    }// Fråga om denna ska vara return int antalet lika element, eller return bool
     // för om det finns, lite otydlig formulering i uppgiften

}
