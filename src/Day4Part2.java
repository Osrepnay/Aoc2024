import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4Part2 {
    static List<char[]> grid;

    static boolean inBounds(int r, int c) {
        return r >= 0 && c >= 0 && r < grid.size() && c < grid.getFirst().length;
    }

    static boolean checkPattern(int r, int c, char[][] pattern) {
        for (int nr = r; nr < r + pattern.length; nr++) {
            for (int nc = c; nc < c + pattern[0].length; nc++) {
                char patternChar = pattern[nr - r][nc - c];
                if (patternChar == '.') {
                    continue;
                }
                if (!inBounds(nr, nc) || patternChar != grid.get(nr)[nc]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/4"));
        grid = new ArrayList<>();
        while (s.hasNextLine()) {
            grid.add(s.nextLine().toCharArray());
        }

        char[][][] patterns = new char[][][] {
                {
                        {'M', '.', 'M'},
                        {'.', 'A', '.'},
                        {'S', '.', 'S'},
                },
                {
                        {'M', '.', 'S'},
                        {'.', 'A', '.'},
                        {'M', '.', 'S'},
                },
                {
                        {'S', '.', 'S'},
                        {'.', 'A', '.'},
                        {'M', '.', 'M'},
                },
                {
                        {'S', '.', 'M'},
                        {'.', 'A', '.'},
                        {'S', '.', 'M'},
                },
        };
        int count = 0;
        for (int r = 0; r < grid.size(); r++) {
            for (int c = 0; c < grid.get(r).length; c++) {
                if (grid.get(r)[c] == 'M' || grid.get(r)[c] == 'S') {
                    for (char[][] pattern : patterns) {
                        if (checkPattern(r, c, pattern)) {
                            count++;
                            continue;
                        }
                    }
                }
            }
        }
        System.out.println(count);
    }
}
