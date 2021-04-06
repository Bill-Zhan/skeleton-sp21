package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: working on timeGetLast method
        /* 1. Initialize N AList */
        int[] array = new int[] {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        int M = 10_000;
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        /* 2. Fill in each AList, remember to compute time */
        Stopwatch sw;
        double timeInSeconds;
        SLList<Integer> testList;

        for (int N : array) {
            Ns.addLast(N);
            testList = new SLList<>();
            for (int i = 0; i < N; i++) {
                testList.addLast(i);
            }
            sw = new Stopwatch();
            for (int i = 0; i < M; i++) {
                int ans = testList.getLast();
            }
            timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            opCounts.addLast(M);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
