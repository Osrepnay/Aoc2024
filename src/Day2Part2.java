import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day2Part2 {
    public static boolean valid(List<Integer> nums) {
        boolean ascending = nums.get(1) - nums.get(0) > 0;
        for (int i = 0; i < nums.size() - 1; i++) {
            int diff = Math.abs(nums.get(i + 1) - nums.get(i));
            if (diff < 1 || diff > 3 || (nums.get(i + 1) - nums.get(i) > 0) != ascending) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/2"));
        int safe = 0;
        reportLoop:
        while (s.hasNextLine()) {
            List<Integer> nums = Arrays.stream(s.nextLine().split(" "))
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(ArrayList::new));
            if (!valid(nums)) {
                for (int i = 0; i < nums.size(); i++) {
                    int item = nums.get(i);
                    nums.remove(i);
                    if (valid(nums)) {
                        safe++;
                        break;
                    } else {
                        nums.add(i, item);
                    }
                }
            } else {
                safe++;
            }
        }
        System.out.println(safe);
    }
}
