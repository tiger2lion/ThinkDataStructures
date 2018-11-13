package com.allendowney.thinkdast;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/** use two queues to construct a stack model
 * @param <E>
 */
public class MyStack<E> {
    private Queue<E> dataQueue;

    private Queue<E> swapQueue;

    int size;

    private final ReentrantLock lock;

    public MyStack() {
        dataQueue = new LinkedList<>();
        swapQueue = new LinkedList<>();
        lock = new ReentrantLock(false);
    }

    public boolean push(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            dataQueue.add(e);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }
    }

    public E pop() {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        lock.lock();
        try {
            if (dataQueue.size() == 1) {
                return dataQueue.poll();
            } else {
                while (dataQueue.size() != 1) {
                    E e = dataQueue.poll();
                    swapQueue.add(e);
                }
                E data = dataQueue.poll();
                while (!swapQueue.isEmpty()) {
                    dataQueue.add(swapQueue.poll());
                }
                return data;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return dataQueue.isEmpty() && swapQueue.isEmpty();
    }

    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        myStack.push(5);
        System.out.println(myStack.pop());
    }
}
