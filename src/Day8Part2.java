import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Day8Part2 {
    record Pos(int r, int c) {
        public Pos subtract(Pos pos) {
            return new Pos(r - pos.r, c - pos.c);
        }

        public Pos add(Pos pos) {
            return new Pos(r + pos.r, c + pos.c);
        }
    }

    static int gcd(int a, int b) {
        if (a > b) {
            return gcd(b, a);
        }
        if (b % a == 0) {
            return a;
        }
        return gcd(b % a, a);
    }

    static boolean inBounds(int rows, int cols, Pos pos) {
        return pos.r >= 0 && pos.c >= 0 && pos.r < rows && pos.c < cols;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/8"));
        Map<Character, List<Pos>> antennae = new HashMap<>();
        int rows = 0;
        int cols = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (cols = 0; cols < line.length(); cols++) {
                if (line.charAt(cols) != '.') {
                    antennae.putIfAbsent(line.charAt(cols), new ArrayList<>());
                    antennae.get(line.charAt(cols)).add(new Pos(rows, cols));
                }
            }
            rows++;
        }
        int rowsFinal = rows;
        int colsFinal = cols;
        Set<Pos> antinodes = new HashSet<>();
        for (char antenna : antennae.keySet()) {
            List<Pos> thisAntennae = antennae.get(antenna);
            for (int i = 0; i < thisAntennae.size(); i++) {
                Pos first = thisAntennae.get(i);
                for (int j = i + 1; j < thisAntennae.size(); j++) {
                    Pos second = thisAntennae.get(j);
                    Pos diff = second.subtract(first);
                    int gcd = gcd(Math.abs(diff.r), Math.abs(diff.c));
                    Pos slope = new Pos(diff.r / gcd, diff.c / gcd);
                    Stream.concat(
                            Stream.iterate(
                                    first,
                                    p -> inBounds(rowsFinal, colsFinal, p),
                                    p -> p.subtract(slope)
                            ),
                            Stream.iterate(
                                    first,
                                    p -> inBounds(rowsFinal, colsFinal, p),
                                    p -> p.add(slope)
                            )
                    ).forEach(antinodes::add);
                }
            }
        }
        System.out.println(antinodes.size());
    }
}
