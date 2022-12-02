
import java.lang.System;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class CountCalories {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Must pass in filename as first argument");
            System.exit(-1);
        }

        Stream<String> lines = Files.lines(Paths.get(args[0]));

        List<Integer> elfTotals=new ArrayList<Integer>();
        AtomicInteger currentTotal = new AtomicInteger(0);
        
        lines.forEach(
                line -> {
                    if (line.isBlank()) {
                        // Store our total
                        elfTotals.add(currentTotal.get());
                        currentTotal.set(0);
                    } else {
                        int num = Integer.parseInt(line);
                        currentTotal.set(currentTotal.get() + num);
                        System.out.println("Found: " + num);
                    }
                }
        );

        // now print the sorted list which should include the highest ones last.
        Collections.sort(elfTotals);
        for (Integer i : elfTotals) {
            System.out.println("Found " + i);
        }
    }
}
