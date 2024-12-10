import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9Part1 {
    record Chunk(int id, int length) {
    }

    static boolean filesContiguous(List<Chunk> chunks) {
        boolean blanked = false;
        for (Chunk c : chunks) {
            if (!blanked) {
                if (c.id == -1) {
                    blanked = true;
                }
            } else {
                if (c.id != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("inputs/9"));
        List<Chunk> chunks = new ArrayList<>();
        int id = 0;
        boolean isFile = true;
        for (char c : s.nextLine().toCharArray()) {
            if (isFile) {
                if (c != '0') {
                    chunks.add(new Chunk(id, c - '0'));
                }
                id++;
            } else {
                if (c != '0') {
                    chunks.add(new Chunk(-1, c - '0'));
                }
            }
            isFile = !isFile;
        }

        while (!filesContiguous(chunks)) {
            int lastFileIdx = chunks.size() - 1;
            while (lastFileIdx >= 0 && chunks.get(lastFileIdx).id == -1) {
                lastFileIdx--;
            }
            int firstEmptyIdx = 0;
            while (firstEmptyIdx < chunks.size() && chunks.get(firstEmptyIdx).id != -1) {
                firstEmptyIdx++;
            }
            Chunk fileChunk = chunks.get(lastFileIdx);
            Chunk emptyChunk = chunks.get(firstEmptyIdx);
            if (fileChunk.length == emptyChunk.length) {
                chunks.set(firstEmptyIdx, new Chunk(fileChunk.id, emptyChunk.length));
                chunks.remove(lastFileIdx);
            } else if (fileChunk.length > emptyChunk.length) {
                chunks.set(firstEmptyIdx, new Chunk(fileChunk.id, emptyChunk.length));
                chunks.set(lastFileIdx, new Chunk(fileChunk.id, fileChunk.length - emptyChunk.length));
            } else {
                chunks.remove(lastFileIdx);
                chunks.set(firstEmptyIdx, new Chunk(fileChunk.id, fileChunk.length));
                chunks.add(firstEmptyIdx + 1, new Chunk(-1, emptyChunk.length - fileChunk.length));
            }
        }

        int pos = 0;
        BigInteger sum = BigInteger.ZERO;
        for (Chunk chunk : chunks) {
            if (chunk.id == -1) {
                break;
            }
            int endPos = pos + chunk.length;
            sum = sum.add(
                    BigInteger.valueOf(chunk.id)
                            .multiply(BigInteger.valueOf(pos + endPos - 1))
                            .multiply(BigInteger.valueOf(chunk.length))
                            .divide(BigInteger.TWO)
            );
            pos += chunk.length;
        }
        System.out.println(sum);
    }
}
