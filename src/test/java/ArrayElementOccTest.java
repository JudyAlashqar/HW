import junit.framework.TestCase;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ArrayElementOccTest extends TestCase {
    int size = 1000_000_000;
    int bound = 100_000;

    private int[] intRandomStream(int size) {
        int [] intStream = new Random(1).ints(size, 0, bound).toArray();
        return intStream;
    }

    public void testArraySumSeq() {
        int[] arr = intRandomStream(size);
        ArrayElementOcc array = new ArrayElementOcc(arr, 0, arr.length - 1, 1);
        long start = System.currentTimeMillis();
        long occ = array.findSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Time execution for Random Array of size %d is %d ms occurrences of number (1) is %d\n", size, endTimer, occ);
    }

    public void testArraySumPP() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        int[] arr = intRandomStream(size);
        ArrayElementOcc array = new ArrayElementOcc(arr, 0, arr.length - 1, 1);
        long start = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(array);
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Time execution for Random Array of size %d is %d ms occurrences of number (1) is %d\n", size, endTimer, array.occurrences);
    }

    public void testArraySumStream() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","7");
        int[] arr = intRandomStream(size);
        ArrayElementOcc array = new ArrayElementOcc(arr, 0, arr.length - 1, 1);
        long start = System.currentTimeMillis();
        array.computeStream();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Parallel Stream Time execution for Random Array of size %d is %d ms occurrences of number (1) is %d\n", size, endTimer, array.occurrences);
    }

    public void testArraySumStreamSeq() {
        int[] arr = intRandomStream(size);
        ArrayElementOcc array = new ArrayElementOcc(arr, 0, arr.length - 1, 1);
        long start = System.currentTimeMillis();
        array.computeStreamSeq();
        long endTimer = System.currentTimeMillis() - start;
        System.out.printf("Sequential Stream Time execution for Random Array of size %d is %d ms occurrences of number (1) is %d\n", size, endTimer, array.occurrences);
    }

    public void resource() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool().getParallelism());
    }

}
