import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6Part1 {
    record Pos(int r, int c) {
    }

    static List<char[]> grid;

    static boolean inBounds(Pos pos) {
        return pos.r >= 0 && pos.c >= 0 && pos.r < grid.size() && pos.c < grid.get(0).length;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/6"));
        grid = new ArrayList<>();
        Pos start = null;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.indexOf('^') != -1) {
                start = new Pos(grid.size(), line.indexOf('^'));
            }
            grid.add(line.replace('^', 'X').toCharArray());
        }
        assert start != null;
        Pos current = start;
        int[] direction = {-1, 0};
        int visited = 1;
        while (true) {
            Pos newPos = new Pos(current.r + direction[0], current.c + direction[1]);
            if (inBounds(newPos)) {
                switch (grid.get(newPos.r)[newPos.c]) {
                    case '#':
                        direction = new int[]{direction[1], -direction[0]};
                        break;
                    case '.':
                        visited++;
                        grid.get(newPos.r)[newPos.c] = 'X';
                    case 'X':
                        current = newPos;
                        break;
                }
            } else {
                break;
            }
        }
        System.out.println(visited);
    }
}
