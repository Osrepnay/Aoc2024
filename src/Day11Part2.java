import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day11Part2 {
    record StoneState(long stone, int blinksIn) {
    }

    static Map<StoneState, Long> stoneExpansions = new HashMap<>();

    static void expand(long stone, int blinks) {
        StoneState thisState = new StoneState(stone, blinks);
        if (stoneExpansions.containsKey(thisState)) {
            return;
        }
        if (blinks == 75) {
            stoneExpansions.put(thisState, 1L);
            return;
        }
        if (stone == 0) {
            expand(1, blinks + 1);
            stoneExpansions.put(thisState, stoneExpansions.get(new StoneState(1, blinks + 1)));
        } else {
            int digits = (int) Math.log10(stone) + 1;
            if (digits % 2 == 0) {
                int line = (int) Math.pow(10, digits / 2);
                long firstHalf = stone / line;
                long secondHalf = stone % line;
                expand(firstHalf, blinks + 1);
                expand(secondHalf, blinks + 1);
                stoneExpansions.put(
                        thisState,
                        stoneExpansions.get(new StoneState(firstHalf, blinks + 1)) +
                                stoneExpansions.get(new StoneState(secondHalf, blinks + 1))
                );
            } else {
                expand(stone * 2024, blinks + 1);
                stoneExpansions.put(thisState, stoneExpansions.get(new StoneState(stone * 2024, blinks + 1)));
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/11"));
        List<Long> startStones = Arrays.stream(s.nextLine().split(" "))
                .map(Long::valueOf)
                .collect(Collectors.toCollection(ArrayList<Long>::new));
        long sum = 0;
        for (long stone : startStones) {
            expand(stone, 0);
            sum += stoneExpansions.get(new StoneState(stone, 0));
        }
        System.out.println(sum);
    }
}
