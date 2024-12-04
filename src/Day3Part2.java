import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Day3Part2 {
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
        boolean enabled = true;
        while (!input.isEmpty()) {
            ParseResult<Object> doIns = take(input, "do()");
            ParseResult<Object> dontIns = take(input, "don't()");
            if (doIns.output.isPresent()) {
                enabled = true;
            } else if (dontIns.output.isPresent()) {
                enabled = false;
            }
            if (enabled) {
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
            }
            input = input.substring(1);
        }
        System.out.println(mulSum);
    }
}
