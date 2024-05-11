import java.util.Iterator;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<K> {
    private Node root;
    private class Node {
        private K key;
        private V value;
        int length;
        private Node left, right;
        private Node (K key, V value) {
            this.key = key;
            this.value = value;
            length = 1;
        }
    }

    public void put(K key, V value) {

        Node newNode = new Node(key, value);

        if (root == null) {
            root = newNode;
            return;
        }

        Node current = root;

        while (true) {
            int compareNum = newNode.key.compareTo(current.key);

            if (compareNum < 0) {
                if (current.left == null) {
                    current.left = newNode;
                    break;
                } else {
                    current = current.left;
                }
            } else if (compareNum > 0) {
                if (current.right == null) {
                    current.right = newNode;
                    break;
                } else {
                    current = current.right;
                }
            } else {
                current.value = newNode.value;
                break;
            }
        }

        int leftLength = 0, rightLength = 0;

        if (current.left != null) {
            rightLength = current.left.length;
        }
        if (current.right != null) {
            leftLength = current.right.length;
        }

        current.length = 1 + leftLength + rightLength;

    }

    public V get(K key) {

        Node current = root;

        while (current != null) {

            int compareNum = key.compareTo(current.key);

            if (compareNum < 0) {
                current = current.left;
            } else if (compareNum > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }

        return null;
    }

    public void delete(K key) {
        Node parent = null;
        Node current = root;

        while (current != null) {
            int compareNum = key.compareTo(current.key);

            if (compareNum < 0) {
                parent = current;
                current = current.left;
            } else if (compareNum > 0) {
                parent = current;
                current = current.right;
            } else {
                if (current.right == null) {
                    if (parent == null) {
                        root = current.left;
                    } else if (current == parent.left) {
                        parent.left = current.left;
                    } else {
                        parent.right = current.left;
                    }
                    break;
                }

                if (current.left == null) {
                    if (parent == null) {
                        root = current.right;
                    } else if (current == parent.left) {
                        parent.left = current.right;
                    } else {
                        parent.right = current.right;
                    }
                    break;
                }

                Node temp = min(current.right);
                current.key = temp.key;
                current.value = temp.value;
                current.right = deleteMin(current.right);
            }
        }
    }


    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        return x;
    }

    public int length() {
        return root.length;
    }

    @Override
    public Iterator<K> iterator() {
        return new InOrderIterator(root);
    }

    private class InOrderIterator implements Iterator<K> {
        private Stack<Node> stack;

        public InOrderIterator(Node root) {
            stack = new Stack<>();
            pushAll(root);
        }

        public void pushAll(Node root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node node = stack.pop();
            pushAll(node.right);
            return node.key;
        }

    }


}
