package dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;

public class TimeStats {
    private HashDictionary<String, String> hd;
    private SortedArrayDictionary<String, String> sad;
    private BinaryTreeDictionary<String, String> btd;
    LinkedList<String[]> ld = new LinkedList<>();

    private void create(int counter, int impl) {
        int s = counter;
        hd = new HashDictionary<>(counter);
        sad = new SortedArrayDictionary<>();
        btd = new BinaryTreeDictionary<>();
        ld = new LinkedList<>();


        try (Scanner scanner = new Scanner(new File("./src/dictionary/dtengl.txt"))) {
            while (scanner.hasNextLine()) {
                if (counter < 0) break;
                String l = scanner.nextLine();
                String[] eg = l.split(" ");
                ld.add(new String[]{eg[0], eg[1]});
                counter--;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (impl == 0) {
            Instant start = Instant.now();
            for(String[] el : ld) {
                sad.insert(el[0], el[1]);
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println(impl + " Execution time create " + s + ": " + timeElapsed.toMillis() + " ms");
        } else if(impl == 1) {
            Instant start = Instant.now();
            for(String[] el : ld) {
                hd.insert(el[0], el[1]);
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println(impl + " Execution time " + s + ": " + timeElapsed.toMillis() + " ms");
        } else {
            Instant start = Instant.now();
            for(String[] el : ld) {
                btd.insert(el[0], el[1]);
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println(impl + " Execution time " + s + ": " + timeElapsed.toMillis() + " ms");
        }


    }

    private void erfolgSearch(int impl, int n) {
        create(n, impl);
        int c = 0;
        if (impl == 0) {
            Instant start = Instant.now();
            for (String[] el : ld) {
                if (sad.search(el[0]) != null) {
                    c++;
                }
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.print("zeit: " + timeElapsed.toMillis() + ", ");
        } else if (impl == 1) {
            Instant start = Instant.now();
            for (String[] el : ld) {
                if (hd.search(el[0]) != null) {
                    c++;
                }
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.print("zeit: " + timeElapsed.toMillis() + ", ");
        } else {
            Instant start = Instant.now();
            for (String[] el : ld) {
                if (btd.search(el[0]) != null) {
                    c++;
                }
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.print("zeit: " + timeElapsed.toMillis() + ", ");
        }

        System.out.println(c + " elemente erfolgreich gesucht in " + impl);
    }





    private void nichtErfolgSearch(int impl, int n) {
        create(n, impl);
        int c = 0;
        if (impl == 0) {
            Instant start = Instant.now();
            for (String[] el : ld) {
                if (sad.search(el[1]) != null) {
                    c++;
                }
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.print("zeit: " + timeElapsed.toMillis() + ", ");
        } else if (impl == 1) {
            Instant start = Instant.now();
            for (String[] el : ld) {
                if (hd.search(el[1]) != null) {
                    c++;
                }
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.print("zeit: " + timeElapsed.toMillis() + ", ");
        } else {
            Instant start = Instant.now();
            for (String[] el : ld) {
                if (btd.search(el[1]) != null) {
                    c++;
                }
            }
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.print("zeit: " + timeElapsed.toMillis() + ", ");
        }

        System.out.println(c + " elemente erfolgreich gesucht in " + impl);
    }
    public TimeStats() {
        hd = new HashDictionary<>(31);
        sad = new SortedArrayDictionary<>();
        btd = new BinaryTreeDictionary<>();
    }
    public static void main(String[] args) {
        TimeStats ts = new TimeStats();
        ts.create(8000, 0);
        ts.create(16000, 0);
        ts.create(8000, 1);
        ts.create(16000, 1);
        ts.create(8000, 2);
        ts.create(16000, 2);

        System.out.println();
        ts.erfolgSearch(0, 8000);
        ts.erfolgSearch(0, 16000);
        ts.erfolgSearch(1, 8000);
        ts.erfolgSearch(1, 16000);
        ts.erfolgSearch(2, 8000);
        ts.erfolgSearch(2, 16000);
        System.out.println();
        ts.nichtErfolgSearch(0, 8000);
        ts.nichtErfolgSearch(0, 16000);
        ts.nichtErfolgSearch(1, 8000);
        ts.nichtErfolgSearch(1, 16000);
        ts.nichtErfolgSearch(2, 8000);
        ts.nichtErfolgSearch(2, 16000);



    }
}
