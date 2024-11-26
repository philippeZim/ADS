package Aufgabe1.dictionary;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class HashDictionary<K, V> implements Dictionary<K, V>{

    private LinkedList<Entry<K, V>>[] data;
    private int size;
    private int m;

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;

        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false;
        }
        return true;
    }

    private int nextPrime(int n) {
        if (n <= 2) return 2;
        if (n % 2 == 0) n++;

        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    @SuppressWarnings("unchecked")
    public HashDictionary(int size) {
        m = nextPrime(size);
        this.size = 0;
        data = new LinkedList[m];

    }

    private int keyToInd(K key) {
        int adr = key.hashCode();
        if (adr < 0) {
            adr = -adr;
        }
        return adr % m;
    }

    @SuppressWarnings("unchecked")
    private void makeLarger() {
        LinkedList<Entry<K, V>>[] save = data;
        m = nextPrime(m * 2);
        data = new LinkedList[m];
        size = 0;
        for (int i = 0; i < save.length; i++) {
            if (save[i] == null) continue;
            for(Entry<K, V> e : save[i]) {
                insert(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public V insert(K key, V value) {

        int load_factor = size / m;
        if (load_factor >= 2) {
            makeLarger();
        }
        int adr = keyToInd(key);
        if (data[adr] == null) data[adr] = new LinkedList<>();
        for (Entry<K, V> e : data[adr]) {
            if (e.getKey().equals(key)) {
                V save = e.getValue();
                e.setValue(value);
                return save;
            }
        }
        data[adr].add(new Entry<>(key, value));
        size++;
        return null;
    }

    @Override
    public V search(K key) {
        int adr = keyToInd(key);
        if (data[adr] == null) return null;
        return data[adr].stream()
                .filter(e -> e.getKey().equals(key))
                .map(Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    @Override
    public V remove(K key) {
        int adr = keyToInd(key);
        if (data[adr] == null) {
            return null;
        }
        for (Entry<K, V> e : data[adr]) {
            if (e.getKey().equals(key)) {
                V save = e.getValue();
                data[adr].remove(e);
                size--;
                return save;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private class HashDictionaryIterator implements Iterator<Entry<K, V>> {
        private int ind;
        private ArrayList<Entry<K, V>> table_arr;

        @SuppressWarnings("unchecked")
        public HashDictionaryIterator() {
            ind = 0;
            table_arr = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                if (data[i] != null) {
                    table_arr.addAll(data[i]);
                }
            }
        }
        @Override
        public boolean hasNext() {
            return ind < table_arr.size();
        }

        @Override
        public Entry<K, V> next() {
            return table_arr.get(ind++);
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashDictionaryIterator();
    }
}
