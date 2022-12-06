
import java.lang.System;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class RucksackPrioritiesAgain {

    public static int charScore(char ch) {
        if (ch >= 'a' && ch <= 'z')
            return ((int)ch - (int)'a') + 1;

        if (ch >= 'A' && ch <= 'Z')
            return ((int)ch - (int)'A') + 1 + 26;

        return 0;
    }
    public static int computeScore(String first, String second) {
        int score = 0;

        // Return the score for all items in first that are also in second
        for (String ch: first.split("")) {
            if (second.indexOf(ch) >= 0) {
                score += charScore(ch.toCharArray()[0]);
                break;
            }
        }

        return score;
    }

    public static char findCommon(String first, String second, String third) {
        for (String ch : first.split("")) {
            if (second.indexOf(ch) >= 0 && third.indexOf(ch) >= 0) {
                return ch.toCharArray()[0];
            }
        }
        return (char)0;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Must pass in filename as first argument");
            System.exit(-1);
        }

        AtomicInteger totalScore = new AtomicInteger(0);
        AtomicInteger lineNumber = new AtomicInteger(0);

        String[] previousLines = new String[3];
        Stream<String> lines = Files.lines(Paths.get(args[0]));
        lines.forEach(
                line -> {
                    if (!line.isBlank()) {

                        if (lineNumber.get() > 0 && (lineNumber.get() % 3) == 0) {
                            // Find our total
                            char common = findCommon(previousLines[0], previousLines[1], previousLines[2]);
                            //  System.out.println("Char: " + common + " " + charScore(common));
                            // System.out.println(previousLines[0]);
                            // System.out.println(previousLines[1]);
                            // System.out.println(previousLines[2]);
                            totalScore.set(totalScore.get() + charScore(common));
                        }
                        previousLines[lineNumber.get() % 3] = line;
                        lineNumber.addAndGet(1);
                    }
                }
        );

        char common = findCommon(previousLines[0], previousLines[1], previousLines[2]);
        System.out.println("Char: " + common + " " + charScore(common));
        totalScore.set(totalScore.get() + charScore(common));

        System.out.println("Total Score: " + totalScore.get());
    }
}
