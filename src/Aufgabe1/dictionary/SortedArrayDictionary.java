package dictionary;

import java.util.Arrays;
import java.util.Iterator;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>  {

    private Entry<K, V>[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public SortedArrayDictionary() {
        size = 0;
        data = new Entry[100];
    }

    private int searchKey(K key) {
        int li = 0;
        int re = size - 1;
        while (re >= li) {
            int m = (li + re) / 2;
            if (key.compareTo(data[m].getKey()) < 0) {
                re = m - 1;
            } else if (key.compareTo(data[m].getKey()) > 0) {
                li = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);

        if (i != -1) {
            V r = data[i].getValue();
            data[i].setValue(value);
            return r;
        }
        if (data.length == size) {
            data = Arrays.copyOf(data, 2*size);
        }
        int j = size-1;
        while (j >= 0 && key.compareTo(data[j].getKey()) < 0) {
            data[j+1] = data[j];
            j--;
        }
        data[j+1] = new Entry<K,V>(key,value);
        size++;
        return null;
    }



    @Override
    public V search(K key) {
        int ind = searchKey(key);
        if (ind == -1) {
            return null;
        }
        return data[ind].getValue();

    }

    @Override
    public V remove(K key) {
        int ind = searchKey(key);
        if (ind == -1) {
            return null;
        }
        V save = data[ind].getValue();
        if (ind == size) {
            data[ind] = null;
            size--;
            return save;
        }
        while (data[ind + 1] != null) {
            data[ind] = data[ind + 1];
            ind++;
        }
        size--;
        return save;
    }

    @Override
    public int size() {
        return size;
    }

    private class SortedArrayDictionaryIterator implements Iterator<Entry<K, V>> {
        private int ind = 0;
        @Override
        public boolean hasNext() {
            return ind < size;
        }

        @Override
        public Entry<K, V> next() {
            return data[ind++];
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new SortedArrayDictionaryIterator();
    }

}