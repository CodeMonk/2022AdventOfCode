
import java.lang.System;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class RucksackPriorities {

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
            if (second.indexOf(ch) >= 0 and third.indexOf(ch) >= 0) {
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

        Stream<String> lines = Files.lines(Paths.get(args[0]));
        lines.forEach(
                line -> {
                    if (!line.isBlank()) {
                        // Split line in half and find list of common items in both bags
                        int mid = line.length() / 2;
                        String first = line.substring(0, mid);
                        String second = line.substring(mid);

                        // Sum up score of rucksack's common items
                        int score = computeScore(first, second);
                        totalScore.set(totalScore.get() + score);
                    }
                }
        );

        System.out.println("Total Score: " + totalScore.get());
    }
}
