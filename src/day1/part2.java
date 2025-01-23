package day1;

import utils.InputReaderToLines;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class part2 {
    public static void main(String[] args) throws IOException {
        InputReaderToLines i = new InputReaderToLines("src/day1/input.txt");

        List<String> lines = i.getLines();

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        lines.forEach((line) -> {
            String[] values = line.split("   ");
            left.add(Integer.parseInt(values[0]));
            right.add(Integer.parseInt(values[1]));
        });

        Map<Integer, Integer> rightMap = new HashMap<>();

        right.forEach((n) -> {
            rightMap.put(n, rightMap.getOrDefault(n, 0) + 1);
        });

        System.out.println(rightMap);

        AtomicInteger score = new AtomicInteger();

        left.forEach((n) -> {
            score.addAndGet(n * rightMap.getOrDefault(n, 0));
        });

        System.out.println(score);
    }
}
