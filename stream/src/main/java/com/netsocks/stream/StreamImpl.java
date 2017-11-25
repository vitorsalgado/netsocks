package com.netsocks.stream;

class StreamImpl implements Stream {
    private final String input;
    private final int length;
    private int count;

    StreamImpl(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null or empty");
        }

        this.input = input;
        this.length = input.length();
        this.count = 0;
    }

    @Override
    public char getNext() {
        return this.input.charAt(count++);
    }

    @Override
    public boolean hasNext() {
        return count < length;
    }
}
