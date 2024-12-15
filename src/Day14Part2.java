import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part2 {
    static final int WIDTH = 101;
    static final int HEIGHT = 103;

    record Pos(int x, int y) {
    }

    static int otherModulo(int a, int b) {
        return (a % b + b) % b;
    }

    static int floodFill(char[][] grid, Pos start) {
        Set<Pos> visited = new HashSet<>();
        visited.add(start);
        Queue<Pos> queue = new ArrayDeque<>();
        queue.add(start);
        int[][] deltas = new int[][]{
                {-1, -1},
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, 0},
                {1, 0},
                {0, 1},
                {0, -1}
        };
        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            for (int[] delta : deltas) {
                Pos newPos = new Pos(pos.x + delta[0], pos.y + delta[1]);
                if (newPos.x >= 0 && newPos.y >= 0 && newPos.x < grid.length && newPos.y < grid[0].length
                        && !visited.contains(newPos) && grid[newPos.x][newPos.y] != ' ') {
                    visited.add(newPos);
                    queue.add(newPos);
                }
            }
        }
        return visited.size();
    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("inputs/14"));
        List<Pos> positions = new ArrayList<>();
        List<Pos> deltas = new ArrayList<>();
        while (s.hasNextLine()) {
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(s.nextLine());
            matcher.find();
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            int dx = Integer.parseInt(matcher.group(3));
            int dy = Integer.parseInt(matcher.group(4));
            positions.add(new Pos(x, y));
            deltas.add(new Pos(dx, dy));
        }
        char[][] grid = new char[WIDTH][HEIGHT];
        int seconds = 0;
        while (true) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    grid[x][y] = ' ';
                }
            }
            for (int i = 0; i < positions.size(); i++) {
                Pos pos = positions.get(i);
                grid[pos.x][pos.y] = '█';
                Pos delta = deltas.get(i);
                positions.set(i, new Pos(otherModulo(pos.x + delta.x, WIDTH), otherModulo(pos.y + delta.y, HEIGHT)));
            }
            int maxFlood = Integer.MIN_VALUE;
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    if (grid[x][y] != ' ') {
                        maxFlood = Math.max(maxFlood, floodFill(grid, new Pos(x, y)));
                    }
                }
            }
            if (maxFlood > 50) {
                // see the picture!
                /*
                System.out.println("================== SECONDS: " + seconds + " ==================");
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        System.out.print(grid[x][y]);
                        System.out.print(grid[x][y]);
                    }
                    System.out.println();
                }
                 */
                System.out.println(seconds);
                break;
            }
            seconds++;
        }
    }
}
