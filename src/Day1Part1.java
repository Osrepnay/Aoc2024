import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day1Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/1"));
        List<Integer> one = new ArrayList<>();
        List<Integer> two = new ArrayList<>();
        while (s.hasNextInt()) {
            one.add(s.nextInt());
            two.add(s.nextInt());
        }
        one.sort(null);
        two.sort(null);
        long diff = 0;
        for (int i = 0; i < one.size(); i++) {
            diff += Math.abs(one.get(i) - two.get(i));
        }
        System.out.println(diff);
    }
}
