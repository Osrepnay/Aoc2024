import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part1 {
    static final int WIDTH = 101;
    static final int HEIGHT = 103;

    /*
    21
    34
     */
    static int quadrant(int x, int y) {
        int halfWidth = WIDTH / 2;
        int halfHeight = HEIGHT / 2;
        if (WIDTH % 2 == 1 && x == halfWidth
                || HEIGHT % 2 == 1 && y == halfHeight) {
            return -1;
        }
        if (y < halfHeight) {
            if (x < halfWidth) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if (x < halfWidth) {
                return 3;
            } else {
                return 4;
            }
        }
    }

    static int otherModulo(int a, int b) {
        return (a % b + b) % b;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/14"));
        int[] quadrantCounts = new int[4];
        while (s.hasNextLine()) {
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(s.nextLine());
            matcher.find();
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            int dx = Integer.parseInt(matcher.group(3));
            int dy = Integer.parseInt(matcher.group(4));
            int posX = otherModulo(x + dx * 100, WIDTH);
            int posY = otherModulo(y + dy * 100, HEIGHT);
            int quadrant = quadrant(posX, posY);
            if (quadrant != -1) {
                quadrantCounts[quadrant - 1]++;
            }
        }
        System.out.println(Arrays.stream(quadrantCounts).reduce((a, b) -> a * b).getAsInt());
    }
}
