import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ArrayElementOcc extends RecursiveAction {
    public long occurrences;
    int element;
    int arr[], lo, hi;

    public ArrayElementOcc(int[] arr, int lo, int hi, int element) {
        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
        this.occurrences = 0;
        this.element = element;
    }

    public long findSeq() {
        for (int i = lo; i <= hi; ++i) {
            if (arr[i] == element){
                occurrences += 1;
            }
        }
        return occurrences;
    }

    @Override
    protected void compute() {
        if (hi - lo > 10_000_000) {
            int mid = (lo + hi) / 2;
            ArrayElementOcc left = new ArrayElementOcc(arr, lo, mid, 1);
            ArrayElementOcc right = new ArrayElementOcc(arr, mid + 1, hi, 1);
            left.fork();
            right.compute();
            left.join();
            occurrences = left.occurrences + right.occurrences;
        } else {
            for (int i = lo; i <= hi; ++i) {
                if (arr[i] == element){
                    occurrences += 1;
                }
            }
        }
    }

    public void computeStream() {
        occurrences = Arrays.stream(arr).asLongStream().parallel().filter(e -> e == element).count();
//        all the intermediate and final operation run in parallel
//         Introduce to Map Reduce pattern credit to java streams
    }

    public void computeStreamSeq() {
        occurrences = Arrays.stream(arr).asLongStream().filter(e -> e == element).count();
//        all the intermediate and final operation run in parallel
//         Introduce to Map Reduce pattern credit to java streams
    }
}
