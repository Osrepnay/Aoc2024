import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16Part1 {
    static char[][] grid;

    record Pos(int r, int c) {
        boolean inBounds() {
            return r >= 0 && c >= 0 && r < grid.length && c < grid[0].length;
        }
    }

    record SearchState(Pos pos, int lastDirIdx) {
    }

    static int search(Pos start, Pos goal) {
        int[][] deltas = {
                {-1, 0},
                {0, 1},
                {1, 0},
                {0, -1}
        };
        Map<SearchState, Integer> distances = new HashMap<>();
        PriorityQueue<SearchState> queue = new PriorityQueue<>(Comparator.comparing(distances::get));
        Set<SearchState> visited = new HashSet<>();
        SearchState startSS = new SearchState(start, 1);
        distances.put(startSS, 0);
        queue.add(startSS);
        visited.add(startSS);
        while (!queue.isEmpty()) {
            SearchState state = queue.poll();
            visited.add(state);
            if (state.pos.equals(goal)) {
                return distances.get(state);
            }
            int[] turned = {
                    Math.floorMod(state.lastDirIdx - 1, deltas.length),
                    state.lastDirIdx,
                    Math.floorMod(state.lastDirIdx + 1, deltas.length)
            };
            for (int deltaIdx : turned) {
                int[] delta = deltas[deltaIdx];
                Pos newPos = new Pos(state.pos.r + delta[0], state.pos.c + delta[1]);
                SearchState newSS = new SearchState(newPos, deltaIdx);
                if (newPos.inBounds() && grid[newPos.r][newPos.c] == '.' && !visited.contains(newSS)) {
                    int dist = distances.get(state) + 1;
                    if (state.lastDirIdx != deltaIdx) {
                        dist += 1000;
                    }
                    distances.merge(newSS, dist, Math::min);
                    queue.add(new SearchState(newPos, deltaIdx));
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/16"));
        List<char[]> gridList = new ArrayList<>();
        Pos goal = null;
        Pos start = null;
        while (s.hasNextLine()) {
            char[] line = s.nextLine().toCharArray();
            for (int c = 0; c < line.length; c++) {
                if (line[c] == 'E') {
                    goal = new Pos(gridList.size(), c);
                    line[c] = '.';
                } else if (line[c] == 'S') {
                    start = new Pos(gridList.size(), c);
                    line[c] = '.';
                }
            }
            gridList.add(line);
        }
        assert goal != null;
        assert start != null;
        grid = gridList.toArray(char[][]::new);
        System.out.println(search(start, goal));
    }
}
