package day2;

import utils.InputReaderToLines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class part1 {
    public static void main(String[] args) throws IOException {
        InputReaderToLines i = new InputReaderToLines("src/day2/input.txt");
//        InputReaderToLines i = new InputReaderToLines("src/day2/inputShort.txt");

        List<String> lines = i.getLines();
        int total = lines.stream().mapToInt((l) -> {
            System.out.print("Safe?: " + l);
            boolean safe = isSafe(Arrays.asList(l.split(" ")), false);

            System.out.println(" " + safe);
            return safe ? 1 : 0;
        }).sum();

        System.out.println("total: " + total);
    }

    public static boolean isSafe(List<String> levels, boolean levelRemoved) {
        int increaseOrDecrease = 0;


        for (int i = 0; i < levels.size() - 1; i++) {

            int x = Integer.parseInt(levels.get(i));
            int y = Integer.parseInt(levels.get(i + 1));

            int difference = y - x;
            int newIncreaseOrDecrease = difference == 0 ? 0 : difference / Math.abs(difference);

            if (increaseOrDecrease == 0) {
                increaseOrDecrease = newIncreaseOrDecrease;
            }

            if (
                    increaseOrDecrease != newIncreaseOrDecrease
                            || Math.abs(difference) > 3
                            || difference == 0
            ) {
                if (levelRemoved) {
                    return false;
                } else {
                    return checkAgainByRemovingOneElement(levels);
                }
            }


        }

        return true;
    }

    public static boolean checkAgainByRemovingOneElement(List<String> levels) {
        for (int i=0; i<levels.size(); i++) {
            List<String> newList = new ArrayList<>(levels.stream().toList());
            newList.remove(i);

            if (isSafe(newList, true)) {
                return true;
            }
        }
        return false;
    }
}
