import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        int arraySize = 100;
        for (int i = 0; i < 3; i++) {

            System.out.println("");

            // testBenchmarkSortedUnsortedArraySize(arraySize);
            // benchmarkSortedUnsortedArraySize(arraySize);

            // testBenchmarkFindDuplicates(arraySize);
            benchmarkFindDuplicates(arraySize);

            System.out.println("");

            arraySize += 50;
        }
    }

    public static int[] keyArray(int arraySize) {
        Random rnd = new Random();

        int[] key = new int[1000];

        for (int j = 0; j < key.length; j++) {
            key[j] = rnd.nextInt(10 * arraySize) + 1;
        }

        return key;

    }

    public static void benchmarkSortedUnsortedArraySize(int arraySize) {

        long[] minimum = new long[3];
        long[] minimumKey = new long[3];
        for (int i = 0; i < minimum.length; i++) {
            minimum[i] = Long.MAX_VALUE;
        }
        long t0;
        long t1;
        int[] array = sorted(arraySize);
        int[] key = keyArray(1000);

        for (int i = 0; i < 100; i++) {

            t0 = System.nanoTime();
            for (int j = 0; j < 1000; j++) {
                search_unsorted(array, key[i]);
            }
            t1 = System.nanoTime();

            if (minimum[0] > (t1 - t0) && (t1 - t0) > 1) {
                minimum[0] = (t1 - t0);
                minimumKey[0] = key[i];
            }

            t0 = System.nanoTime();
            for (int j = 0; j < 1000; j++) {
                search_sorted(array, key[i]);
            }
            t1 = System.nanoTime();

            if (minimum[1] > (t1 - t0)) {
                minimum[1] = (t1 - t0);
                minimumKey[1] = key[i];
            }

            t0 = System.nanoTime();
            for (int j = 0; j < 1000; j++) {
                binary_search_sorted(array, key[i]);
            }
            t1 = System.nanoTime();

            if (minimum[2] > (t1 - t0)) {
                minimum[2] = (t1 - t0);
                minimumKey[2] = key[i];
            }

            array = sorted(arraySize);
            key = keyArray(arraySize);

        }

        System.out.println(" Search unsorted array size :" + arraySize + "| Minimum time in ns: " + minimum[0]
                + "| For Key: " + minimumKey[0]);

        System.out.println(" Search sorted array size :" + arraySize + "| Minimum time in ns: " + minimum[1]
                + "| For Key: " + minimumKey[1]);

        System.out.println(" Binary search array size :" + arraySize + "| Minimum time in ns: " + minimum[2]
                + "| For Key: " + minimumKey[2]);
    }

    public static void benchmarkFindDuplicates(int arraySize) {

        long[] minimum = new long[3];
        for (int i = 0; i < minimum.length; i++) {
            minimum[i] = Long.MAX_VALUE;
        }
        long t0;
        long t1;
        int[] array = sorted(arraySize);
        int[] array2 = sorted(arraySize);
        int[] numberFoundTotal = new int[3];
        int numberFound = 0;

        for (int i = 0; i < 100; i++) {

            t0 = System.nanoTime();
            for (int j = 0; j < 1000; j++) {
                numberFound += binary_find_duplicate_sorted(array, array2);
            }
            t1 = System.nanoTime();

            if (minimum[0] > (t1 - t0) && (t1 - t0) > 1) {
                minimum[0] = (t1 - t0);
            }
            numberFoundTotal[0] += numberFound;
            numberFound = 0;

            t0 = System.nanoTime();
            for (int j = 0; j < 1000; j++) {
                numberFound += semiLinear_find_duplicate_sorted(array, array2);
            }
            t1 = System.nanoTime();

            if (minimum[1] > (t1 - t0)) {
                minimum[1] = (t1 - t0);
            }
            numberFoundTotal[1] += numberFound;
            numberFound = 0;

            t0 = System.nanoTime();
            for (int j = 0; j < 1000; j++) {
                numberFound += linear_find_duplicate_unsorted(array, array2);
            }
            t1 = System.nanoTime();

            if (minimum[2] > (t1 - t0)) {
                minimum[2] = (t1 - t0);
            }
            numberFoundTotal[2] += numberFound;
            numberFound = 0;
            array = sorted(arraySize);
            array2 = sorted(arraySize);
        }

        System.out.println(
                " Binary find duplicate array size :" + arraySize + "| Time In ns: " + minimum[0]
                        + "| Number found total: "
                        + numberFoundTotal[0]);

        System.out.println(" Semi Linear find duplicate array size :" + arraySize + "| Time In ns: " + minimum[1]
                + "| Number found total: " + numberFoundTotal[1]);

        System.out.println(" Linear unsorted find duplicate array size :" + arraySize + "| Time In ns: " + minimum[2]
                + "| Number found total: " + numberFoundTotal[2]);
    }

    public static void testBenchmarkSortedUnsortedArraySize(int arraySize) {
        Random rnd = new Random();
        int key = rnd.nextInt(10 * arraySize) + 1;

        int[] array = sorted(arraySize);

        long t0 = System.nanoTime();
        Boolean found = search_unsorted(array, key);
        long t1 = System.nanoTime();
        System.out.println(
                " Search unsorted array size :" + arraySize + "| Time In ns: " + (t1 - t0) + "| Found: " + found);

        t0 = System.nanoTime();
        found = search_sorted(array, key);
        t1 = System.nanoTime();
        System.out.println(
                " Search sorted array size :" + arraySize + "| Time In ns: " + (t1 - t0) + "| Found: " + found);

        t0 = System.nanoTime();
        found = binary_search_sorted(array, key);
        t1 = System.nanoTime();
        System.out.println(
                " Binary search array size :" + arraySize + "| Time In ns: " + (t1 - t0) + "| Found: " + found);
    }

    public static void testBenchmarkFindDuplicates(int arraySize) {

        int[] array = sorted(arraySize);
        int[] array2 = sorted(arraySize);

        long t0 = System.nanoTime();
        int numberFound = binary_find_duplicate_sorted(array, array2);
        long t1 = System.nanoTime();
        System.out.println(
                " Binary find duplicate array size :" + arraySize + "| Time In ns: " + (t1 - t0) + "| Number found: "
                        + numberFound);

        t0 = System.nanoTime();
        numberFound = semiLinear_find_duplicate_sorted(array, array2);
        t1 = System.nanoTime();
        System.out.println(" Semi Linear find duplicate array size :" + arraySize + "| Time In ns: " + (t1 - t0)
                + "| Number found: " + numberFound);

        t0 = System.nanoTime();
        numberFound = linear_find_duplicate_unsorted(array, array2);
        t1 = System.nanoTime();
        System.out.println(" Linear find duplicate array size :" + arraySize + "| Time In ns: " + (t1 - t0)
                + "| Number found: " + numberFound);

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
                first = index + 1;
                continue;
            }
            if (array[index] > key && index > first) {
                // The index position holds something that is larger than
                // what we're looking for, what is the last possible page?
                last = index - 1;
                continue;
            }
            // Why do we land here? What should we do?
            return false;
        }
    }

    public static int binary_find_duplicate_sorted(int[] firstArray, int[] secondArray) {
        int duplicateNumber = 0;

        int currentElement = 0;
        for (int i = 0; i < firstArray.length; i++) {
            if (currentElement < firstArray[i]) {
                currentElement = firstArray[i];
                if (binary_search_sorted(secondArray, currentElement)) {
                    duplicateNumber++;
                }
            }
        }
        // Above checks once for each int in firstArray if there exists a duplicate in
        // secondArray, using binary search

        return duplicateNumber;
    }

    public static int semiLinear_find_duplicate_sorted(int[] firstArray, int[] secondArray) {
        int firstIndex = -1;
        int firstNext = 0;
        int secondIndex = -1;
        int secondNext = 0;
        int duplicateNumber = 0;

        while ((firstIndex < firstArray.length - 1) && (secondIndex < secondArray.length - 1)) {
            firstNext = firstArray[firstIndex + 1];
            secondNext = secondArray[secondIndex + 1];

            if (firstNext <= secondNext) {
                if (firstNext == secondNext) {
                    duplicateNumber++;
                }
                firstIndex++;
            }
            if (firstNext > secondNext) {
                secondIndex++;
            }

        }

        return duplicateNumber;
    }

    public static int linear_find_duplicate_unsorted(int[] firstArray, int[] secondArray) {

        int duplicateNumber = 0;

        for (int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray.length; j++) {
                if (firstArray[i] == secondArray[j]) {
                    duplicateNumber++;
                }

            }
        }
        return duplicateNumber;
    }

}
