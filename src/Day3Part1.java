import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class Day3Part1 {
    record ParseResult<A>(String rest, Optional<A> output) {
    }

    static ParseResult<Object> take(String input, String take) {
        if (input.startsWith(take)) {
            return new ParseResult<>(input.substring(take.length()), Optional.of(new Object()));
        } else {
            return new ParseResult<>(input, Optional.empty());
        }
    }

    static ParseResult<Long> takeDigits(String input) {
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < input.length() && Character.isDigit(input.charAt(i)); i++) {
            digits.append(input.charAt(i));
        }
        if (digits.length() >= 1 && digits.length() <= 3) {
            return new ParseResult<>(input.substring(digits.length()), Optional.of(Long.parseLong(digits.toString())));
        } else {
            return new ParseResult<>(input, Optional.empty());
        }
    }

    public static void main(String[] args) throws IOException {
        long mulSum = 0;
        String input = Files.readString(Path.of("inputs/3"));
        while (!input.isEmpty()) {
            ParseResult<Object> muled = take(input, "mul(");
            if (muled.output.isPresent()) {
                ParseResult<Long> first = takeDigits(muled.rest);
                if (first.output.isPresent()) {
                    ParseResult<Object> comma = take(first.rest, ",");
                    if (comma.output.isPresent()) {
                        ParseResult<Long> second = takeDigits(comma.rest);
                        if (second.output.isPresent()) {
                            ParseResult<Object> paren = take(second.rest, ")");
                            if (paren.output.isPresent()) {
                                mulSum += first.output.get() * second.output.get();
                                input = paren.rest;
                                continue;
                            }
                        }
                    }
                }
            }
            input = input.substring(1);
        }
        System.out.println(mulSum);
    }
}
