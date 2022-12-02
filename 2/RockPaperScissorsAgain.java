
import java.lang.System;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class RockPaperScissorsAgain {

    public static int computeScore(char them, char us) {
        int score = 0;

        // First score the shape
        switch (us) {
            case 'X': // Rock
                score += 1;
                switch (them) {
                    case 'A': // rock is draw
                        score += 3;
                        break;
                    case 'B': // paper beats rock
                        score += 0;
                        break;
                    case 'C': // scisors is beaten
                        score += 6;
                        break;
                }
                break;
            case 'Y': // Paper
                score += 2;
                switch (them) {
                    case 'A': // rock is win
                        score += 6;
                        break;
                    case 'B': // paper is draw
                        score += 3;
                        break;
                    case 'C': // scisors is lose
                        score += 0;
                        break;
                }
                break;
            case 'Z': // Scissors
                score += 3;
                switch (them) {
                    case 'A': // rock is loss
                        score += 0;
                        break;
                    case 'B': // paper is beaten
                        score += 6;
                        break;
                    case 'C': // scisors is draw
                        score += 3;
                }
                break;
            default: // Invalid
                System.out.println("Found invalid us: " + us);
        }

        return score;
    }

    public static char determineOutcome(char them, char result) {
        switch (them) {
            case 'A': // rock
                switch (result) {
                    case 'X': // lose
                        return 'Z';
                    case 'Y': // draw
                        return 'X';
                    case 'Z': // win
                        return 'Y';
                }
                break;
            case 'B': // paper
                switch (result) {
                    case 'X': // lose
                        return 'X';
                    case 'Y': // draw
                        return 'Y';
                    case 'Z': // win
                        return 'Z';
                }
                break;
            case 'C': // Scissors
                switch (result) {
                    case 'X': // lose
                        return 'Y';
                    case 'Y': // draw
                        return 'Z';
                    case 'Z': // win
                        return 'X';
                }
                break;
        }
        return '0';
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
                        char them = line.charAt(0);
                        char result = line.charAt(2);
                        char us = determineOutcome(them, result);
                        int score = computeScore(them, us);
                        System.out.println("Found: " + them + " " + us + " " + result + " " + score);
                        totalScore.set(totalScore.get() + score);
                    }
                }
        );

        System.out.println("Total Score: " + totalScore.get());
    }
}
