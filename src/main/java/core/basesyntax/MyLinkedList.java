package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<T>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeNext = searchElementByIndex(index);
        Node<T> nodePrev = nodeNext.prev;
        Node<T> newTail = new Node<>(nodePrev, value, nodeNext);
        if (nodePrev != null) {
            nodePrev.next = newTail;
        }
        nodeNext.prev = newTail;
        if (index == 0) {
            head = newTail;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T getElementList : list) {
            add(getElementList);
        }
    }

    @Override
    public T get(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return searchElementByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> node = searchElementByIndex(index);
        T nodeFirst = node.item;
        node.item = value;
        return nodeFirst;
    }

    @Override
    public T remove(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return unlink(searchElementByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, node.item)) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public T unlink(Node<T> node) {
        Node<T> nodeNext = node.next;
        Node<T> nodePrev = node.prev;
        if (nodePrev != null) {
            nodePrev.next = nodeNext;
            node.prev = null;
        } else {
            head = nodeNext;
        }
        if (nodeNext != null) {
            nodeNext.prev = nodePrev;
            node.next = null;
        } else {
            tail = nodePrev;
        }
        size--;
        return node.item;
    }

    public Node<T> searchElementByIndex(int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> node = head;
        if (index < (size >> 1) || index == 0) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
}

