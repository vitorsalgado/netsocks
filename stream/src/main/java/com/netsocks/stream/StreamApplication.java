package com.netsocks.stream;

import java.util.*;

/**
 * Stream App
 * This program expects one command line argument that will be the string
 * which we are going to perform the process proposed in task 3
 */
class StreamApplication {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("Provide at least one input argument");
            System.exit(1);
            return;
        }

        String input = args[0];
        Stream stream = new StreamImpl(input);
        Optional<Character> result = firstChar(stream);

        System.out.println("INPUT: " + input);
        System.out.println("RESULT: " + (result.isPresent() ? result.get() : "NO RESULT FOUND"));
        System.exit(0);
    }

    static Optional<Character> firstChar(Stream stream) {
        int cmd = 0;
        Map<Character, Integer> charCounter = new HashMap<>();
        Set<Character> charSet = new HashSet<>();

        while (stream.hasNext()) {
            char ch = stream.getNext();

            if (cmd == 0 && isVowel(ch)) {
                cmd = 1;
            } else if (cmd == 1 && isConsonant(ch)) {
                cmd = 2;
            } else if (cmd == 2 && isVowel(ch) && !charSet.contains(ch)) {
                charCounter.putIfAbsent(ch, charCounter.getOrDefault(ch, 1));
                cmd = 0;
            } else {
                cmd = 0;
            }

            charSet.add(ch);
        }

        Optional<Map.Entry<Character, Integer>> first = charCounter.entrySet().stream()
                .filter(x -> x.getValue() == 1)
                .findFirst();

        if (first.isPresent()) {
            return Optional.ofNullable(first.get().getKey());
        }

        return Optional.empty();
    }

    private static boolean isVowel(char ch) {
        return "AEIOUaeiou".indexOf(ch) >= 0;
    }

    private static boolean isConsonant(char ch) {
        return "BbCcDdFfGgHhJjKkLlMmNnPpQqRrSsTtVvWwXxYyZz".indexOf(ch) >= 0;
    }
}
