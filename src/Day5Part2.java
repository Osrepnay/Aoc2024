import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day5Part2 {
    record Ordering(int before, int after) {}
    static <A> void swap(List<A> list, int one, int two) {
        A tmp = list.get(one);
        list.set(one, list.get(two));
        list.set(two, tmp);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/5"));
        List<Ordering> orderings = new ArrayList<>();
        String line;
        while (!(line = s.nextLine()).isEmpty()) {
            String[] split = line.split("\\|");
            orderings.add(new Ordering(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }

        int sum = 0;
        while (s.hasNextLine()) {
            List<Integer> pages = Arrays.stream(s.nextLine().split(","))
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(ArrayList::new));
            boolean valid;
            boolean modified = false;
            do {
                valid = true;
                for (Ordering order : orderings) {
                    int indexBefore = pages.indexOf(order.before);
                    if (indexBefore == -1) {
                        continue;
                    }
                    int indexAfter = pages.indexOf(order.after);
                    if (indexAfter == -1) {
                        continue;
                    }
                    if (indexBefore >= indexAfter) {
                        swap(pages, indexBefore, indexAfter);
                        modified = true;
                        valid = false;
                        break;
                    }
                }
            } while (!valid);
            if (modified) {
                sum += pages.get(pages.size() / 2);
            }
        }
        System.out.println(sum);
    }
}
