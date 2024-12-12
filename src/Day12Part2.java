import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

public class Day12Part2 {
    static char[][] grid;

    record Pos(int r, int c) {
        boolean inBounds() {
            return r >= 0 && c >= 0 && r < grid.length && c < grid[0].length;
        }
    }

    record PPos(int dr, int dc) {
    }

    static int floodFill(Pos pos, Set<PPos> perimeter, Set<Pos> visited) {
        if (visited.contains(pos)) {
            return 0;
        }
        visited.add(pos);
        int[][] deltas = {
                {-1, 0},
                {1, 0},
                {0, 1},
                {0, -1},
        };
        List<Function<Pos, PPos>> transformer = List.of(
                p -> new PPos(p.r * 3 - 1, p.c * 3),
                p -> new PPos(p.r * 3 + 1, p.c * 3),
                p -> new PPos(p.r * 3, p.c * 3 + 1),
                p -> new PPos(p.r * 3, p.c * 3 - 1)
        );
        int area = 1;
        int i = 0;
        for (int[] delta : deltas) {
            Pos newPos = new Pos(pos.r + delta[0], pos.c + delta[1]);
            if (newPos.inBounds()) {
                if (grid[newPos.r][newPos.c] == grid[pos.r][pos.c]) {
                    area += floodFill(newPos, perimeter, visited);
                } else {
                    perimeter.add(transformer.get(i).apply(pos));
                }
            } else {
                perimeter.add(transformer.get(i).apply(pos));
            }
            i++;
        }
        return area;
    }

    static int perimeterWalk(Set<PPos> perimeter) {
        int[][] deltas = {
                {-3, 0},
                {3, 0},
                {0, 3},
                {0, -3},
        };
        int edges = 0;
        // guarantees beginning?
        while (!perimeter.isEmpty()) {
            PPos start = perimeter.stream().min(Comparator.comparing(PPos::dr).thenComparing(PPos::dc)).get();
            // is not set if lone edge, should not affect result
            int[] delta = deltas[0];
            for (int[] d : deltas) {
                PPos newPos = new PPos(start.dr + d[0], start.dc + d[1]);
                if (perimeter.contains(newPos)) {
                    delta = d;
                    break;
                }
            }
            for (PPos newPos = start; perimeter.contains(newPos); newPos = new PPos(newPos.dr + delta[0], newPos.dc + delta[1])) {
                perimeter.remove(newPos);
            }
            edges++;
        }
        return edges;
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
                    Set<PPos> perimeter = new HashSet<>();
                    int result = floodFill(pos, perimeter, visited);
                    visitedAll.addAll(visited);
                    int edges = perimeterWalk(perimeter);
                    price += (long) result * edges;
                }
            }
        }
        System.out.println(price);
    }
}
