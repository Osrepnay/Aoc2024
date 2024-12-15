import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Part2 {
    record Pos(BigInteger x, BigInteger y) {
    }

    record ClawMachine(Pos aDelta, Pos bDelta, Pos prize) {
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/13"));
        BigInteger totalTokens = BigInteger.ZERO;
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
            BigInteger ax = new BigInteger(aMatcher.group(1));
            BigInteger ay = new BigInteger(aMatcher.group(2));
            BigInteger bx = new BigInteger(bMatcher.group(1));
            BigInteger by = new BigInteger(bMatcher.group(2));
            BigInteger gx = new BigInteger(prizeMatcher.group(1)).add(BigInteger.valueOf(10000000000000L));
            BigInteger gy = new BigInteger(prizeMatcher.group(2)).add(BigInteger.valueOf(10000000000000L));
            // ???
            // slopes
            if (bx.multiply(ay).equals(by.multiply(ax))) {
                continue;
            }
            BigInteger qNum = ay.multiply(gx).subtract(ax.multiply(gy));
            BigInteger qDenom = ay.multiply(bx).subtract(ax.multiply(by));
            if (!qNum.remainder(qDenom).equals(BigInteger.ZERO)) {
                continue;
            }
            BigInteger q = qNum.divide(qDenom);
            BigInteger pNum = gx.subtract(q.multiply(bx));
            BigInteger pDenom = ax;
            if (!pNum.remainder(pDenom).equals(BigInteger.ZERO)) {
                continue;
            }
            BigInteger p = pNum.divide(pDenom);
            totalTokens = totalTokens.add(p.multiply(BigInteger.valueOf(3)).add(q));
        }
        System.out.println(totalTokens);
    }
}
