public class MyHashTable<K, V> {
    private static int DEFAULT_SIZE = 11;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private static class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int size;
    private double loadFactor;

    @SuppressWarnings("unchecked")
    public MyHashTable() {
        this(DEFAULT_SIZE);
    }

    @SuppressWarnings("unchecked")
    public MyHashTable(int size) {
        this(size, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashTable(int size, double loadFactor) {
        chainArray = new HashNode[size];
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % chainArray.length);
    }

    private void resize() {
        int newSize = chainArray.length * 2;
        HashNode<K, V>[] newChainArray = new HashNode[newSize];
        for (HashNode<K, V> node : chainArray) {
            HashNode<K, V> current = node;
            while (current != null) {
                HashNode<K, V> next = current.next;
                int newIndex = Math.abs(current.key.hashCode() % newSize);
                current.next = newChainArray[newIndex];
                newChainArray[newIndex] = current;
                current = next;
            }
        }
        chainArray = newChainArray;
    }

    public void put(K key, V value) {
        if ((double) size / chainArray.length >= loadFactor) {
            resize();
        }
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);
        if (chainArray[index] == null) {
            chainArray[index] = newNode;
        } else {
            HashNode<K, V> current = chainArray[index];
            while (current.next != null && !current.key.equals(key)) {
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value;
            } else {
                current.next = newNode;
            }
        }
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        HashNode<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    chainArray[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public boolean contains(V value) {
        for (HashNode<K, V> node : chainArray) {
            HashNode<K, V> current = node;
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public K getKey(V value) {
        for (HashNode<K, V> node : chainArray) {
            HashNode<K, V> current = node;
            while (current != null) {
                if (current.value.equals(value)) {
                    return current.key;
                }
                current = current.next;
            }
        }
        return null;
    }

    public int length() {
        return size;
    }

    public void output() {
        for (int i = 0; i < chainArray.length; i++) {
            HashNode<K, V> current = chainArray[i];
            System.out.print("Bucket " + i + ": ");
            while (current != null) {
                System.out.print(current + " ");
                current = current.next;
            }
            System.out.println();
        }
    }
}
