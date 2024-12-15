import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Part1 {
    record Pos(int x, int y) {
    }

    record ClawMachine(Pos aDelta, Pos bDelta, Pos prize) {
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/13"));
        int totalTokens = 0;
        while (s.hasNextLine()) {
            s.next(); // horrible hack
            Pattern buttonPattern = Pattern.compile("^.*\\+(\\d+).*\\+(\\d+).*$");
            String aStr = s.nextLine();
            Matcher aMatcher = buttonPattern.matcher(aStr);
            aMatcher.find();
            String bStr = s.nextLine();
            Matcher bMatcher = buttonPattern.matcher(bStr);
            bMatcher.find();
            Matcher prizeMatcher = Pattern.compile("^.*=(\\d+).*=(\\d+).*$").matcher(s.nextLine());
            prizeMatcher.find();
            ClawMachine clawMachine = new ClawMachine(
                    new Pos(Integer.parseInt(aMatcher.group(1)), Integer.parseInt(aMatcher.group(2))),
                    new Pos(Integer.parseInt(bMatcher.group(1)), Integer.parseInt(bMatcher.group(2))),
                    new Pos(Integer.parseInt(prizeMatcher.group(1)), Integer.parseInt(prizeMatcher.group(2)))
            );
            int minTokens = Integer.MAX_VALUE;
            for (int a = 0; a <= 100; a++) {
                int x = clawMachine.aDelta.x * a;
                int y = clawMachine.aDelta.y * a;
                for (int b = 0; b <= 100 && x <= clawMachine.prize.x && y <= clawMachine.prize.y; b++) {
                    if (x == clawMachine.prize.x && y == clawMachine.prize.y) {
                        int thisTokens = a * 3 + b;
                        if (thisTokens < minTokens) {
                            minTokens = thisTokens;
                        }
                    }
                    x += clawMachine.bDelta.x;
                    y += clawMachine.bDelta.y;
                }
            }
            if (minTokens != Integer.MAX_VALUE) {
                totalTokens += minTokens;
            }
        }
        System.out.println(totalTokens);
    }
}
