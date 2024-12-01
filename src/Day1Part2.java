import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/1"));
        List<Integer> one = new ArrayList<>();
        Map<Integer, Integer> two = new HashMap<>();
        while (s.hasNextInt()) {
            one.add(s.nextInt());
            two.merge(s.nextInt(), 1, Integer::sum);
        }
        long similarity = 0;
        for (int n : one) {
            similarity += Math.abs(n * two.getOrDefault(n, 0));
        }
        System.out.println(similarity);
    }
}
