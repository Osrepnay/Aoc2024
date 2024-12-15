import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day15Part1 {
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

    static boolean move(Pos pos, char direction) {
        Pos newPos = pos.shift(direction);
        if (!newPos.inBounds()) {
            return false;
        }
        switch (grid[newPos.r][newPos.c]) {
            case '#':
                return false;
            case 'O':
                if (!move(newPos, direction)) {
                    return false;
                }
            case '.':
                grid[newPos.r][newPos.c] = grid[pos.r][pos.c];
                grid[pos.r][pos.c] = '.';
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + grid[newPos.r][newPos.c]);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/15"));
        List<char[]> gridList = new ArrayList<>();
        String line;
        int row = 0;
        Pos startPos = null;
        while (!(line = s.nextLine()).isEmpty()) {
            char[] lineArr = line.toCharArray();
            for (int c = 0; c < lineArr.length; c++) {
                if (lineArr[c] == '@') {
                    startPos = new Pos(row, c);
                }
            }
            gridList.add(lineArr);
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
            if (move(pos, dir)) {
                pos = pos.shift(dir);
            }
        }

        int gps = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 'O') {
                    gps += r * 100 + c;
                }
            }
        }
        System.out.println(gps);
    }
}
