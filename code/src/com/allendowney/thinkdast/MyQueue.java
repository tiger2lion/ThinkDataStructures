package com.allendowney.thinkdast;

import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;
/** use two stacks to construct a queue model
 * @param <E>
 */
public class MyQueue<E> {
    private Stack<E> dataStack;

    private Stack<E> swapStack;

    private final ReentrantLock lock;

    public MyQueue() {
        dataStack = new Stack<>();
        swapStack = new Stack<>();
        lock = new ReentrantLock(false);
    }

    /**
     * throw an exception upon to superiors if the stack space beyond limit
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            dataStack.push(e);
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * return false if the stack space beyond limit
     *
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return add(e);
    }

    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (dataStack.size() == 1) {
                return dataStack.firstElement();
            } else {
                while (dataStack.size() != 1) {
                    swapStack.push(dataStack.pop());
                }
                E data = dataStack.firstElement();
                while (swapStack.size() != 1) {
                    dataStack.push(swapStack.pop());
                }
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoSuchElementException();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyQueue<Integer> myQueue = new MyQueue<>();
        myQueue.add(1);
        myQueue.add(2);
        myQueue.add(3);
        myQueue.add(4);
        myQueue.add(5);
        System.out.println(myQueue.poll());
    }
//    public E remove() {
//
//    }
//
//    public E peek() {
//
//    }
//
//    public E element() {
//
//    }
}
