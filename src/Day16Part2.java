import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16Part2 {
    static char[][] grid;

    record Pos(int r, int c) {
        boolean inBounds() {
            return r >= 0 && c >= 0 && r < grid.length && c < grid[0].length;
        }
    }

    record SearchState(Pos pos, int lastDirIdx) {
    }

    static int tally(Map<SearchState, Set<SearchState>> sources, Pos goal) {
        Queue<SearchState> queue = new ArrayDeque<>();
        for (int i = 0; i < 4; i++) {
            if (sources.containsKey(new SearchState(goal, i))) {
                queue.add(new SearchState(goal, i));
            }
        }
        Set<Pos> positions = new HashSet<>();
        while (!queue.isEmpty()) {
            SearchState state = queue.poll();
            positions.add(state.pos);
            queue.addAll(sources.get(state));
        }
        return positions.size();
    }

    static int search(Pos start, Pos goal) {
        int[][] deltas = {
                {-1, 0},
                {0, 1},
                {1, 0},
                {0, -1}
        };
        Map<SearchState, Integer> distances = new HashMap<>();
        Map<SearchState, Set<SearchState>> sources = new HashMap<>();
        PriorityQueue<SearchState> queue = new PriorityQueue<>(Comparator.comparing(distances::get));
        Set<SearchState> visited = new HashSet<>();
        SearchState startSS = new SearchState(start, 1);
        sources.put(startSS, new HashSet<>());
        distances.put(startSS, 0);
        queue.add(startSS);
        visited.add(startSS);
        int goalDist = -1;
        while (!queue.isEmpty()) {
            SearchState state = queue.poll();
            visited.add(state);
            if (state.pos.equals(goal)) {
                if (goalDist == -1) {
                    goalDist = distances.get(state);
                } else if (distances.get(state) > goalDist) {
                    break;
                }
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
                    if (distances.containsKey(newSS)) {
                        if (distances.get(newSS) == dist) {
                            sources.get(newSS).add(state);
                        } else if (distances.get(newSS) > dist) {
                            distances.put(newSS, dist);
                            sources.put(newSS, new HashSet<>(Set.of(state)));
                        }
                    } else {
                        distances.put(newSS, dist);
                        sources.put(newSS, new HashSet<>(Set.of(state)));
                    }
                    queue.add(new SearchState(newPos, deltaIdx));
                }
            }
        }
        // trim overlength goals
        for (int i = 0; i < 4; i++) {
            SearchState ss = new SearchState(goal, i);
            if (distances.containsKey(ss) && distances.get(ss) != goalDist) {
                distances.remove(ss);
                sources.remove(ss);
            }
        }
        return tally(sources, goal);
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
