import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        Random rnd = new Random();
        int key = rnd.nextInt(10) + 1;
        int arrayLength=24;

        int [] array = sorted(arrayLength);
        long t0 = System.nanoTime();
        search_unsorted(array,key);
        long t1 = System.nanoTime();
        System.out.println("");

        t0 = System.nanoTime();
        search_sorted(array,key);
        t1 = System.nanoTime();
        System.out.println("");

        t0 = System.nanoTime();
        binary_search(array,key);
        t1 = System.nanoTime();
        System.out.println("");
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

    public static boolean binary_search(int[] array, int key) {
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
                first = index;
                continue;
            }
            if (array[index] > key && index > first) {
                // The index position holds something that is larger than
                // what we're looking for, what is the last possible page?
                last = index;
                continue;
            }
            // Why do we land here? What should we do?
            // If key not found while searching, means either not in array or array not sorted
            return false;
        }
    }
}
