import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4Part1 {
    static List<char[]> grid;

    static boolean inBounds(int r, int c) {
        return r >= 0 && c >= 0 && r < grid.size() && c < grid.getFirst().length;
    }

    static int xmasSquare(int r, int c) {
        int[][] deltas = {
                {1, 0},
                {0, 1},
                {1, 1},
                {-1, 0},
                {0, -1},
                {-1, -1},
                {1, -1},
                {-1, 1},
        };
        int count = 0;
        for (int[] d : deltas) {
            String find = "XMAS";
            int nr = r;
            int nc = c;
            while (!find.isEmpty() && inBounds(nr, nc) && grid.get(nr)[nc] == find.charAt(0)) {
                find = find.substring(1);
                nr += d[0];
                nc += d[1];
            }
            if (find.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/4"));
        grid = new ArrayList<>();
        while (s.hasNextLine()) {
            grid.add(s.nextLine().toCharArray());
        }
        int count = 0;
        for (int r = 0; r < grid.size(); r++) {
            for (int c = 0; c < grid.get(r).length; c++) {
                if (grid.get(r)[c] == 'X') {
                    count += xmasSquare(r, c);
                }
            }
        }
        System.out.println(count);
    }
}
