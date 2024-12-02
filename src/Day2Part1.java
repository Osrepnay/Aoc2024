import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day2Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/2"));
        int safe = 0;
        reportLoop:
        while (s.hasNextLine()) {
            int[] nums = Arrays.stream(s.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            boolean ascending = nums[1] - nums[0] > 0;
            for (int i = 0; i < nums.length - 1; i++) {
                int diff = Math.abs(nums[i + 1] - nums[i]);
                if (diff < 1 || diff > 3 || (nums[i + 1] - nums[i] > 0) != ascending) {
                    continue reportLoop;
                }
            }
            safe++;
        }
        System.out.println(safe);
    }
}
