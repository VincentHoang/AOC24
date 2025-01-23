package day1;

import utils.InputReaderToLines;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String input = "3   4\n" +
                "4   3\n" +
                "2   5\n" +
                "1   3\n" +
                "3   9\n" +
                "3   3";

        InputReaderToLines i = new InputReaderToLines("src/day1/input.txt");

        List<String> lines = i.getLines();

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        lines.forEach((line) -> {
            String[] values = line.split("   ");
            left.add(Integer.parseInt(values[0]));
            right.add(Integer.parseInt(values[1]));
        });

        Collections.sort(left);
        Collections.sort(right);

        int totalDistance = 0;

        Iterator<Integer> leftIterator = left.iterator();
        Iterator<Integer> rightIterator = right.iterator();

        System.out.println(left);
        System.out.println(right);
        while(leftIterator.hasNext() && rightIterator.hasNext()) {
            totalDistance += Math.abs(rightIterator.next() - leftIterator.next());
        }

        System.out.println("total distance is: " + totalDistance);
    }
}
//10322790
//2196996