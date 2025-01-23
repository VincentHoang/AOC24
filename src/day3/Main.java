package day3;

import utils.InputReaderToLines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Pair<L, R> {
    private L left;
    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "[" + left + ", " + right + "]";
    }
}

public class Main {

    static AtomicBoolean enabled = new AtomicBoolean(true);
    public static void main(String[] args) throws IOException {
//        InputReaderToLines i = new InputReaderToLines("src/day3/inputShort2.txt");
        InputReaderToLines i = new InputReaderToLines("src/day3/input.txt");

        List<String> lines = i.getLines();

        int result = lines.stream().map((l) -> {
            System.out.println(l + " vs ");

            return sanitize(l).stream()
                    .map((instruction) -> instruction.getLeft() * instruction.getRight())
                    .reduce(0, Integer::sum);
        }).reduce(0, Integer::sum);

        System.out.println("result: " + result);

    }

    public static List<Pair<Integer, Integer>> sanitize(String line) {
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|don't\\(\\)|do\\(\\)");
        Matcher matcher = pattern.matcher(line);
        List<Pair<Integer, Integer>> matches = new ArrayList<>();

        while(matcher.find()) {
            String matchedLine = matcher.group();
            if (matchedLine.equals("don't()")) {
                enabled.set(false);
            } else if (matchedLine.equals("do()")) {
                enabled.set(true);
            } else if (enabled.get()) {
                Pair<Integer, Integer> instruction = new Pair<>(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                matches.add(instruction);
            }
        }

        return matches;
    }
}
