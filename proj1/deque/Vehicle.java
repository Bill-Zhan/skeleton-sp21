package deque;

import java.util.Comparator;

public class Vehicle implements Comparable<Vehicle>{
    private String make;
    private String model;
    private Integer year;
    private int maxSpeed;

    public Vehicle (String make, String model, int year, int maxSpeed) {
       this.make = make;
       this.model = model;
       this.year = year;
       this.maxSpeed = maxSpeed;
    }

    public int compareTo(Vehicle otherVehicle) {
        return this.year - otherVehicle.year;
    }

    private static class MakeComparator implements Comparator<Vehicle> {
        public int compare(Vehicle a, Vehicle b) {
            return a.make.compareTo(b.make);
        }
    }

    private static class YearComparator implements Comparator<Vehicle> {
        public int compare(Vehicle a, Vehicle b) {
            return a.year.compareTo(b.year);
        }
    }

    public static Comparator<Vehicle> getMakeComparator () {
        return new MakeComparator();
    }

    public static Comparator<Vehicle> getYearComparator () {
        return new YearComparator();
    }

    public String getMake () {
        return this.make;
    }
}
