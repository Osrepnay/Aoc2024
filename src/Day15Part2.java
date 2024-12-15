import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day15Part2 {
    static char[][] grid;

    record Pos(int r, int c) {
        boolean inBounds() {
            return r >= 0 && c >= 0 && r < grid.length && c < grid[0].length;
        }

        Pos shift(char direction) {
            return switch (direction) {
                case '^' -> new Pos(r - 1, c);
                case '>' -> new Pos(r, c + 1);
                case 'v' -> new Pos(r + 1, c);
                case '<' -> new Pos(r, c - 1);
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            };
        }
    }

    static List<Pos> expand(Pos pos, char direction) {
        if (direction == '<' || direction == '>') {
            return List.of(pos);
        }
        return switch (grid[pos.r][pos.c]) {
            case '[' -> List.of(pos, new Pos(pos.r, pos.c + 1));
            case ']' -> List.of(new Pos(pos.r, pos.c - 1), pos);
            default -> List.of(pos);
        };
    }

    static boolean canMove(List<Pos> pos, char direction) {
        List<Pos> newPos = pos.stream().map(p -> p.shift(direction)).toList();
        if (!newPos.stream().allMatch(Pos::inBounds)) {
            return false;
        }
        for (Pos subPos : newPos) {
            switch (grid[subPos.r][subPos.c]) {
                case '#':
                    return false;
                case '[':
                case ']':
                    if (!canMove(expand(subPos, direction), direction)) {
                        return false;
                    }
                case '.':
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + grid[subPos.r][subPos.c]);
            }
        }
        return true;
    }

    static boolean move(List<Pos> pos, char direction) {
        if (!canMove(pos, direction)) {
            return false;
        }
        List<Pos> newPos = pos.stream().map(p -> p.shift(direction)).toList();
        Set<List<Pos>> movers = new HashSet<>();
        for (Pos subPos : newPos) {
            switch (grid[subPos.r][subPos.c]) {
                case '[':
                case ']':
                    movers.add(expand(subPos, direction));
                    break;
                case '.':
                    break;
            }
        }
        for (List<Pos> mover : movers) {
            move(mover, direction);
        }
        for (int i = 0; i < pos.size(); i++) {
            grid[newPos.get(i).r][newPos.get(i).c] = grid[pos.get(i).r][pos.get(i).c];
            grid[pos.get(i).r][pos.get(i).c] = '.';
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/15"));
        List<char[]> gridList = new ArrayList<>();
        String line;
        int row = 0;
        Pos startPos = null;
        while (!(line = s.nextLine()).isEmpty()) {
            char[] lineArr = line.toCharArray();
            char[] widenedLine = new char[lineArr.length * 2];
            for (int c = 0; c < lineArr.length; c++) {
                switch (lineArr[c]) {
                    case '.':
                    case '#':
                        widenedLine[c * 2] = lineArr[c];
                        widenedLine[c * 2 + 1] = lineArr[c];
                        break;
                    case 'O':
                        widenedLine[c * 2] = '[';
                        widenedLine[c * 2 + 1] = ']';
                        break;
                    case '@':
                        widenedLine[c * 2] = '@';
                        widenedLine[c * 2 + 1] = '.';
                        startPos = new Pos(row, c * 2);
                        break;
                }
            }
            gridList.add(widenedLine);
            row++;
        }
        grid = gridList.toArray(char[][]::new);

        StringBuilder dirs = new StringBuilder();
        while (s.hasNextLine()) {
            dirs.append(s.nextLine());
        }
        char[] directions = dirs.toString().toCharArray();
        Pos pos = startPos;
        for (char dir : directions) {
            if (move(List.of(pos), dir)) {
                pos = pos.shift(dir);
            }
        }

        int gps = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '[') {
                    gps += r * 100 + c;
                }
            }
        }
        System.out.println(gps);
    }
}
