package com.tqs.lab1_1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class TqsStackTest {
    TqsStack stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack();
    }

    @DisplayName("A stack is empty on construction")
    @Test
    void testIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @DisplayName("A stack has size 0 on construction")
    @Test
    void testSize() {
        assertEquals(0, stack.size());
    }

    @DisplayName("After n > 0 pushes to an empty stack, the stack is not empty and its size is n")
    @Test
    void testPush() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size());
    }

    @DisplayName("If one pushes x then pops, the value popped is x")
    @Test
    void testPushPop() {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x but the size stays the same")
    @Test
    void testPushPeek() {
        stack.push(1);
        assertEquals(1, stack.peek());
        assertEquals(1, stack.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    @Test
    void testPop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.pop();
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @DisplayName("Popping from an empty stack throws a NoSuchElementException")
    @Test
    void testPopEmpty() {
        assertTrue(stack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @DisplayName("Peeking into an empty stack throws a NoSuchElementException")
    @Test
    void testPeekEmpty() {
        assertTrue(stack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Disabled
    @DisplayName("For bounded stacks only, pushing onto a full stack throws an IllegalStateException")
    @Test
    void testPushFull() {
        BoundedTqsStack boundedStack = new BoundedTqsStack(2);
        boundedStack.push(1);
        boundedStack.push(2);
        assertEquals(2, boundedStack.size());
        assertThrows(IllegalStateException.class, () -> boundedStack.push(3));
    }

    @DisplayName("The top n elements can be popped from the stack")
    @Test
    void testPopTopN() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(1, stack.popTopN(3));
        assertTrue(stack.isEmpty());
    }
/*
    @DisplayName("Popping more elements than the stack has throws an IllegalArgumentException")
    @Test
    void testPopTopNTooMany() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThrows(IllegalArgumentException.class, () -> stack.popTopN(4));
    }

    @DisplayName("Popping 0 elements throws an IllegalArgumentException")
    @Test
    void testPopTopNZero() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThrows(IllegalArgumentException.class, () -> stack.popTopN(0));
    }
 */
}