import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day7Part1 {
    static boolean canValid(long result, List<Long> nums) {
        if (nums.isEmpty()) {
            return false;
        }
        if (nums.size() == 1) {
            return nums.getFirst() == result;
        }
        long last = nums.getLast();
        List<Long> init = nums.subList(0, nums.size() - 1);
        return result % last == 0 && canValid(result / last, init)
                || canValid(result - last, init);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/7"));
        long total = 0;
        while (s.hasNextLine()) {
            Scanner lineScanner = new Scanner(s.nextLine()).useDelimiter(":? ");
            long result = lineScanner.nextLong();
            List<Long> nums = new ArrayList<>();
            while (lineScanner.hasNextLong()) {
                nums.add(lineScanner.nextLong());
            }
            if (canValid(result, nums)) {
                total += result;
            }
        }
        System.out.println(total);
    }
}
