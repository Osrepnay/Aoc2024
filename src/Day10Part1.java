import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10Part1 {
    static int[][] grid;

    record Pos(int r, int c) {
        boolean inBounds() {
            return r >= 0 && c >= 0 && r < grid.length && c < grid[0].length;
        }
    }

    static int numPeaks(Pos start) {
        int[][] deltas = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
        };
        Queue<Pos> boundary = new ArrayDeque<>();
        Set<Pos> peaks = new HashSet<>();
        boundary.add(start);
        while (!boundary.isEmpty()) {
            Pos pos = boundary.poll();
            int initialHeight = grid[pos.r][pos.c];
            if (initialHeight == 9) {
                peaks.add(pos);
                continue;
            }
            for (int[] delta : deltas) {
                Pos newPos = new Pos(pos.r + delta[0], pos.c + delta[1]);
                if (newPos.inBounds() && grid[newPos.r][newPos.c] == initialHeight + 1 && !peaks.contains(newPos)) {
                    boundary.add(newPos);
                }
            }
        }
        return peaks.size();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/10"));
        List<int[]> linesList = new ArrayList<>();
        while (s.hasNextLine()) {
            linesList.add(s.nextLine().chars().map(c -> c - '0').toArray());
        }
        grid = linesList.toArray(int[][]::new);
        int total = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == 0) {
                    total += numPeaks(new Pos(r, c));
                }
            }
        }
        System.out.println(total);
    }
}
