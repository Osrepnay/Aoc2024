import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day11Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/11"));
        List<Long> stones = Arrays.stream(s.nextLine().split(" "))
                .map(Long::valueOf)
                .collect(Collectors.toCollection(ArrayList<Long>::new));
        for (int i = 0; i < 25; i++) {
            int initialSize = stones.size();
            for (int j = 0; j < initialSize; j++) {
                if (stones.get(j) == 0) {
                    stones.set(j, 1L);
                } else {
                    int digits = (int) Math.log10(stones.get(j)) + 1;
                    if (digits % 2 == 0) {
                        int line = (int) Math.pow(10, digits / 2);
                        long firstHalf = stones.get(j) / line;
                        long secondHalf = stones.get(j) % line;
                        stones.set(j, firstHalf);
                        stones.add(secondHalf);
                    } else {
                        stones.set(j, stones.get(j) * 2024);
                    }
                }
            }
        }
        System.out.println(stones.size());
    }
}
