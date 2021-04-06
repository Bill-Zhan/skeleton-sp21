package deque;

import org.junit.Test;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    @Test
    public void maxTestInt() {
        int N = 10;
        int insertNum, signNum;

        /* integer deque */
        Comparator<Integer> nic = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        };
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(nic);
        for (int i = 0; i < N; i++) {
            insertNum = StdRandom.uniform(0, 1_000);
            signNum = StdRandom.uniform(0, 2);
            if (signNum == 0) { insertNum = insertNum * -1; }
            mad1.addLast(insertNum);
        }
        mad1.printDeque();
        System.out.println(mad1.max());

    }

    @Test
    public void maxTestVehicle() {
        /* vehicle deque */
        Comparator<Vehicle> nvc = Vehicle.getMakeComparator();
        MaxArrayDeque<Vehicle> mad2 = new MaxArrayDeque<>(nvc);

        Vehicle v1, v2, v3;
        v1 = new Vehicle("Dodge", "Charger", 2020, 150);
        v2 = new Vehicle("Mazda", "R8", 2018, 140);
        v3 = new Vehicle("Benz", "G", 2008, 120);

        mad2.addLast(v1); mad2.addLast(v2); mad2.addLast(v3);

        /* 1. default make comparison */
        Vehicle maxMakeCar = mad2.max();
        System.out.println("Max make car: " + maxMakeCar.getMake());

        /* 2. year comparison */
        Comparator<Vehicle> nvyc = Vehicle.getYearComparator();
        Vehicle maxYearCar = mad2.max(nvyc);
        System.out.println("Max year car: " + maxYearCar.getMake());
     }
}