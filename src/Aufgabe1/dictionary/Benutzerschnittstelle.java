
package Aufgabe1.dictionary;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

public class Benutzerschnittstelle {

    private SortedArrayDictionary<String, String> sad;
    private HashDictionary<String, String> hd;
    private Dictionary<String, String> cur;
    private BinaryTreeDictionary<String, String> btd;

    private boolean parseInput(String line) {
        if (line.equals("create SortedArrayDictionary")) {
            sad = new SortedArrayDictionary<>();
            cur = sad;
        } else if (line.equals("create HashDictionary")) {
            hd = new HashDictionary<>(31);
            cur = hd;
        } else if (line.equals("create BinaryTreeDictionary")) {
            btd = new BinaryTreeDictionary<>();
            cur = btd;
        } else if (line.equals("create")) {
            sad = new SortedArrayDictionary<>();
            cur = sad;
        } else if (line.startsWith("r ") && line.split(" ").length == 3) {
            String[] sp = line.split(" ");
            int counter = Integer.parseInt(sp[1]);
            int s = counter;
            try (Scanner scanner = new Scanner(new File("./src/dictionary/" + sp[2]))) {
                while (scanner.hasNextLine() && counter > 0) {
                    String l = scanner.nextLine();
                    String[] eg = l.split(" ");
                    cur.insert(eg[0], eg[1]);
                    counter--;
                }
                System.out.println("Inserted " + s + " elements.");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else if (line.startsWith("r ") && line.split(" ").length == 2) {
            String[] sp = line.split(" ");
            int c = 0;
            try (Scanner scanner = new Scanner(new File("./src/dictionary/" + sp[1]))) {
                while (scanner.hasNextLine()) {
                    String l = scanner.nextLine();
                    String[] eg = l.split(" ");
                    cur.insert(eg[0], eg[1]);
                    c++;
                }
                System.out.println("Inserted " + c + " elements.");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (line.equals("p")) {
            for (Dictionary.Entry<String, String> e : cur) {
                System.out.println("key: "+ e.getKey() + ", value: " + e.getValue());
            }
        } else if (line.startsWith("s ") && line.split(" ").length == 2) {
            String[] sp = line.split(" ");
            System.out.println(cur.search(sp[1]));
        } else if (line.startsWith("i ") && line.split(" ").length == 3) {
            String[] sp = line.split(" ");
            cur.insert(sp[1], sp[2]);
        } else if (line.startsWith("d ") && line.split(" ").length == 2) {
            String[] sp = line.split(" ");
            System.out.println(cur.remove(sp[1]));
        } else if (line.equals("exit")) {
            return false;
        } else {
            System.out.println("Falsche Eingabe:\n" +
                    "Konsolen-Kommando Bedeutung\n" +
                    "create Implementierung Legt ein Dictionary an. SortedArrayDictionary ist voreingestellt.\n" +
                    "r [n] Dateiname Liest (read) die ersten n Einträge der Datei in das Dictionary ein.\n" +
                    "Wird n weggelassen, dann werden alle Einträge eingelesen.\n" +
                    "Einfachheitshalber kann ein JFileChooser-Dialog verwendet\n" +
                    "werden (siehe Prog2, GUI). Dann wird aber der Dateiname im\n" +
                    "Kommando weggelassen.\n" +
                    "p Gibt alle Einträge des Dictionary in der Konsole aus (print).\n" +
                    "s deutsch Gibt das entsprechende englische Wort aus (search).\n" +
                    "i deutsch englisch Fügt ein neues Wortpaar in das Dictionary ein (insert).\n" +
                    "d deutsch Löscht einen Eintrag (delete).\n" +
                    "exit beendet das Programm.");
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Benutzerschnittstelle b = new Benutzerschnittstelle();
        while (true) {
            System.out.print("Enter Command~ ");
            String lineInput = scanner.nextLine();
            if(!b.parseInput(lineInput)) {
                break;
            }
        }

    }
}
