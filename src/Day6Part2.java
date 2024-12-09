import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6Part2 {
    record Pos(int r, int c) {
    }

    record State(Pos pos, List<Integer> direction) {
    }

    static boolean inBounds(List<char[]> grid, Pos pos) {
        return pos.r >= 0 && pos.c >= 0 && pos.r < grid.size() && pos.c < grid.get(0).length;
    }

    static boolean loops(List<char[]> grid, Pos pos) {
        Set<State> visited = new HashSet<>();
        List<Integer> direction = List.of(-1, 0);
        while (true) {
            Pos newPos = new Pos(pos.r + direction.get(0), pos.c + direction.get(1));
            if (inBounds(grid, newPos)) {
                switch (grid.get(newPos.r)[newPos.c]) {
                    case '#':
                        direction = List.of(direction.get(1), -direction.get(0));
                        break;
                    case '.':
                        State state = new State(pos, direction);
                        if (visited.contains(state)) {
                            return true;
                        } else {
                            visited.add(state);
                        }
                        pos = newPos;
                        break;
                }
            } else {
                break;
            }
        }
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/6"));
        List<char[]> grid = new ArrayList<>();
        Pos start = null;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.indexOf('^') != -1) {
                start = new Pos(grid.size(), line.indexOf('^'));
            }
            grid.add(line.replace('^', '.').toCharArray());
        }
        assert start != null;

        int loopers = 0;
        for (int r = 0; r < grid.size(); r++) {
            for (int c = 0; c < grid.get(r).length; c++) {
                switch (grid.get(r)[c]) {
                    case '#':
                        continue;
                    case '.':
                        grid.get(r)[c] = '#';
                        if (loops(grid, start)) {
                            loopers++;
                        }
                        grid.get(r)[c] = '.';
                }
            }
        }
        System.out.println(loopers);
    }
}
