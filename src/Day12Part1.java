import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12Part1 {
    static char[][] grid;

    record FloodResult(int perimeter, int area) {
    }

    record Pos(int r, int c) {
        boolean inBounds() {
            return r >= 0 && c >= 0 && r < grid.length && c < grid[0].length;
        }
    }

    static FloodResult floodFill(Pos pos, Set<Pos> visited) {
        if (visited.contains(pos)) {
            return new FloodResult(0, 0);
        }
        visited.add(pos);
        int[][] deltas = {
                {-1, 0},
                {1, 0},
                {0, 1},
                {0, -1},
        };
        int perimeter = 0;
        int area = 1;
        for (int[] delta : deltas) {
            Pos newPos = new Pos(pos.r + delta[0], pos.c + delta[1]);
            if (newPos.inBounds()) {
                if (grid[newPos.r][newPos.c] == grid[pos.r][pos.c]) {
                    FloodResult next = floodFill(newPos, visited);
                    area += next.area;
                    perimeter += next.perimeter;
                } else {
                    perimeter++;
                }
            } else {
                perimeter++;
            }
        }
        return new FloodResult(perimeter, area);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/12"));
        List<char[]> gridList = new ArrayList<>();
        while (s.hasNextLine()) {
            gridList.add(s.nextLine().toCharArray());
        }
        grid = gridList.toArray(char[][]::new);
        Set<Pos> visitedAll = new HashSet<>();
        long price = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                Pos pos = new Pos(r, c);
                if (!visitedAll.contains(pos)) {
                    Set<Pos> visited = new HashSet<>();
                    FloodResult result = floodFill(pos, visited);
                    price += (long) result.area * result.perimeter;
                    visitedAll.addAll(visited);
                }
            }
        }
        System.out.println(price);
    }
}
