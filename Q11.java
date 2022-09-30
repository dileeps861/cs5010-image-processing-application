package audible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Q1 {
    public static List<List<Integer>> kClosest(List<List<Integer>> allLocations, int numDeliveries) {
        List<List<Integer>> result = new ArrayList<>();
        List<Point> pointsList = new ArrayList<Point>();
        for (List<Integer> point : allLocations) {
            double distance = Math.sqrt((point.get(0) * point.get(0)) + (point.get(1) * point.get(1)));
            pointsList.add(new Point(point.get(0), point.get(1), distance));
        }
        Collections.sort(pointsList, (p1, p2) -> {
            if (p1.distance == p2.distance) {
                return p1.x - p2.x;
                // if question is looking for nearest point with x value, regardless of whether x value is positive or negative
                // then return Math.abs(p1.x) - Math.abs(p2.x);
            } else
                return Double.compare(p1.distance, p2.distance);
        });
        for (int i = 0; i < numDeliveries; i++) {
            result.add( new ArrayList<>(Arrays.asList(pointsList.get(i).x, pointsList.get(i).y)));
        }
        return result;
    }

    static class Point {
        int x;
        int y;
        double distance;

        Point(int x, int y, double distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        int[][]points = { {1,2} , {3,4} , {1,-1}};
        int k = 2;
        int[][] result = new Q1().kClosest(points, k);
        System.out.println(Arrays.deepToString(result));
    }
}
