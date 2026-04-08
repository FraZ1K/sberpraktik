package zadachaa3;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;


public class BinarySearchTree<E extends Comparable<E>> implements Collection<E> {

    private Node root;
    private int size;

    private class Node {
        E value;
        Node left;
        Node right;
        Node parent;

        Node(E value) {
            this.value = value;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    public BinarySearchTree() {
        root = null;
        size = 0;
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        try {
            @SuppressWarnings("unchecked")
            E value = (E) o;
            return containsRecursive(root, value);
        } catch (ClassCastException e) {
            return false;
        }
    }

    private boolean containsRecursive(Node node, E value) {
        if (node == null) return false;

        int cmp = value.compareTo(node.value);
        if (cmp == 0) return true;
        if (cmp < 0) return containsRecursive(node.left, value);
        return containsRecursive(node.right, value);
    }

    @Override
    public boolean add(E value) {
        if (value == null) throw new NullPointerException("Null values not allowed");

        if (root == null) {
            root = new Node(value);
            size++;
            return true;
        }

        Node current = root;
        Node parent = null;
        int cmp = 0;

        while (current != null) {
            parent = current;
            cmp = value.compareTo(current.value);
            if (cmp == 0) return false; // дубликаты не добавляем
            if (cmp < 0) current = current.left;
            else current = current.right;
        }

        Node newNode = new Node(value);
        newNode.parent = parent;

        if (cmp < 0) parent.left = newNode;
        else parent.right = newNode;

        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        try {
            @SuppressWarnings("unchecked")
            E value = (E) o;
            Node toRemove = findNode(root, value);
            if (toRemove == null) return false;

            removeNode(toRemove);
            size--;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    private Node findNode(Node node, E value) {
        if (node == null) return null;

        int cmp = value.compareTo(node.value);
        if (cmp == 0) return node;
        if (cmp < 0) return findNode(node.left, value);
        return findNode(node.right, value);
    }

    private void removeNode(Node node) {

        if (node.isLeaf()) {
            replaceInParent(node, null);
        }

        else if (node.left == null) {
            replaceInParent(node, node.right);
            node.right.parent = node.parent;
        }
        else if (node.right == null) {
            replaceInParent(node, node.left);
            node.left.parent = node.parent;
        }

        else {
            Node successor = findMin(node.right);
            node.value = successor.value;
            removeNode(successor);
        }
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private void replaceInParent(Node node, Node newChild) {
        if (node.parent == null) {
            root = newChild;
        } else if (node.parent.left == node) {
            node.parent.left = newChild;
        } else {
            node.parent.right = newChild;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<E> {
        private final Stack<Node> stack = new Stack<>();
        private Node current;

        public BSTIterator() {
            pushLeftmost(root);
        }

        private void pushLeftmost(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();

            current = stack.pop();
            E result = current.value;

            if (current.right != null) {
                pushLeftmost(current.right);
            }

            return result;
        }
    }



    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (E value : this) {
            array[index++] = value;
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int index = 0;
        for (E value : this) {
            a[index++] = (T) value;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) modified = true;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
}
