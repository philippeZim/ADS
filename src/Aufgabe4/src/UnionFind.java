import java.util.*;

public class UnionFind<T> {
    HashMap<T, T> p;
    HashMap<T, Integer> h;
    int size;

    public UnionFind(Set<T> s) {
        p = new HashMap<>();
        h = new HashMap<>();
        size = s.size();
        for (T e : s) {
            p.put(e, null);
            h.put(e, 0);
        }
    }
    //
    T find(T e) {
        if (!p.containsKey(e)) {
            throw new IllegalArgumentException();
        }
        while (p.get(e) != null) // e ist keine Wurzel
            e = p.get(e);
        return e;
    }

    public void union(T s1, T s2) {
        if (!p.containsKey(s1) || !p.containsKey(s2)) {
            throw new IllegalArgumentException();
        }
        if (p.get(s1) != null || p.get(s2) != null)
            return;
        size--;
        if (s1.equals(s2))
            return;
        if (h.get(s1) < h.get(s2)) // Höhe von s1 < Höhe von s2
            p.put(s1, s2);
        else {
            if (h.get(s1).equals(h.get(s2)))
                h.put(s1, h.get(s1) + 1); // Höhe von s1 erhöht sich um 1
            p.put(s2, s1);
        }
    }

    public int size() {
        return size;
    }

    public void print() {
        HashMap<T, HashSet<T>> roots = new HashMap<>();
        for (T e : p.keySet()) {
            if (p.get(e) == null) {
                roots.put(e, new HashSet<>());
            }
        }

        for (T e : p.keySet()) {
            if (p.get(e) != null) {
                roots.get(find(e)).add(e);
            }
        }

        for (HashMap.Entry<T, HashSet<T>> e : roots.entrySet()) {
            System.out.print(e.getKey() + ": ");
            System.out.println(e.getValue());
        }
    }
}
