import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        int arraySize = 10;
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 1000; j++) {
                
        benchmarkSortedUnsortedArraySize(arraySize);
        benchmarkFindDuplicates(arraySize);
            }
        arraySize*=10;
        }
    }

    public static void benchmarkSortedUnsortedArraySize(int arraySize) {
        Random rnd = new Random();
        int key = rnd.nextInt(5*arraySize) + 1;

        System.out.println("Current Key :" + key);

        System.out.println("Current Size :" + arraySize);
        int[] array = sorted(arraySize);

        long t0 = System.nanoTime();
        Boolean found = search_unsorted(array, key);
        long t1 = System.nanoTime();
        System.out.println(" Search unsorted array size :" + arraySize + "| Time In ns: " + (t1 - t0) + "| Found: "+found);

        t0 = System.nanoTime();
        found =search_sorted(array, key);
        t1 = System.nanoTime();
        System.out.println(" Search sorted array size :" + arraySize + "| Time In ns: " + (t1 - t0)+ "| Found: "+found);

        t0 = System.nanoTime();
        found =binary_search_sorted(array, key);
        t1 = System.nanoTime();
        System.out.println(" Binary search array size :" + arraySize + "| Time In ns: " + (t1 - t0)+ "| Found: "+found);
    }

    public static void benchmarkFindDuplicates(int arraySize) {

        Random rnd = new Random();
        int key = rnd.nextInt(5*arraySize) + 1;

        System.out.println("Current Key :" + key);

        System.out.println("Current Size :" + arraySize);
        int[] array = sorted(arraySize);
        int[] array2 = sorted(arraySize);


        long t0 = System.nanoTime();
        Boolean found = binary_find_duplicate_sorted(array, array2);
        long t1 = System.nanoTime();
        System.out.println(" Binary find duplicate array size :" + arraySize + "| Time In ns: " + (t1 - t0) + "| Found: "+found);

        t0 = System.nanoTime();
        found =semiLinear_find_duplicate_sorted(array, array2);
        t1 = System.nanoTime();
        System.out.println(" Semi Linear find duplicate array size :" + arraySize + "| Time In ns: " + (t1 - t0)+ "| Found: "+found);

        t0 = System.nanoTime();
        int numberFound =linear_find_duplicate_unsorted(array, array2);
        t1 = System.nanoTime();
        System.out.println(" Linear find duplicate array size :" + arraySize + "| Time In ns: " + (t1 - t0)+ "| Number found: "+numberFound);

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
        while (true) {
            // jump to the middle
            int index = (first + last) / 2;
            if (array[index] == key) {
                return true;
            }
            if (array[index] < key && index < last) {
                // The index position holds something that is less than
                // what we're looking for, what is the first possible page?
                first = index+1;
                continue;
            }
            if (array[index] > key && index > first) {
                // The index position holds something that is larger than
                // what we're looking for, what is the last possible page?
                last = index;
                continue;
            }
            // Why do we land here? What should we do?
            return false;
        }
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
        while ((firstIndex < firstArray.length-1) && (secondIndex < secondArray.length-1)) {
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

    public static int linear_find_duplicate_unsorted(int[] firstArray, int[] secondArray) {

        int duplicateNumber = 0;

        for (int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray.length; j++) {
                if (firstArray[i] == secondArray[j]) {

                        duplicateNumber++;
                        j = secondArray.length;// Kolla om detta är okej, aka ska det vara flera duplicates eller en per
                                               // nummer
                    }
                }
            }
        

        return duplicateNumber;

    }// Fråga om denna ska vara return int antalet lika element, eller return bool
     // för om det finns, lite otydlig formulering i uppgiften

}
