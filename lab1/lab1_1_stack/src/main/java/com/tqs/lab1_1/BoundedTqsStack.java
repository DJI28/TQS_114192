package com.tqs.lab1_1;

public class BoundedTqsStack<T> extends TqsStack<T> {
    private final int maxSize;

    public BoundedTqsStack(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    @Override
    public void push(T item) {
        if (this.size() == this.maxSize) {
            throw new IllegalStateException("Cannot push to a full stack");
        }
        super.push(item);
    }
}
