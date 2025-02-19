    package com.tqs.lab1_1;

    import java.util.LinkedList;
    import java.util.NoSuchElementException;

    public class TqsStack<T>
    {
        private final LinkedList<T> collection;

        public TqsStack() {
            collection = new LinkedList<T>();
        }

        public void push(T item) {
            collection.add(item);
        }

        public T pop() {
            if (this.size() == 0) {
                throw new NoSuchElementException("Cannot pop from an empty stack");
            } else {
                return collection.remove(collection.size() - 1);
            }
        }

        public T peek() {
            if (this.size() == 0) {
                throw new NoSuchElementException("Cannot peek into an empty stack");
            } else {
                return collection.get(collection.size() - 1);
            }
        }

        public int size() {
            return collection.size();
        }

        public boolean isEmpty() {
            return collection.isEmpty();
        }

        public T popTopN(int n) {
            /*
            if (n <= 0 || n > this.size()) {
                throw new IllegalArgumentException("Invalid number of elements to pop");
            }
             */
            T top = null;
            for (int i = 0; i < n; i++) {
                top = collection.removeLast();
            }
            return top;
        }
    }
