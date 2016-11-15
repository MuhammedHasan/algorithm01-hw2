import java.util.*;
import java.io.*;

public class TestEditor {

    private int index;
    private boolean isAppend;

    private LinkedList<String> lines;
    private ArrayList<String> commands;

    private TestEditor() {
        this.index = 0;
        this.lines = new LinkedList<>();
        this.commands = new ArrayList<>();
    }

    private void add(String line) {
        this.lines.add(this.index, line);
        this.index++;
    }

    private void delete() {
        this.index--;
        this.lines.remove(this.index);
    }

    private void delete(int i) {
        this.lines.remove(i);
        if (this.index >= i)
            this.index--;
    }

    private void delete(int i, int j) {
        for (; i < j; i++) {
            if (this.index >= i)
                this.index--;
            this.lines.remove(i);
        }
    }

    private void list() {
        for (int i = 0; i < this.lines.size(); i++)
            this.commands.add(i + 1 + ">" + this.lines.get(i));
    }

    private void append(String text) {
        this.index--;
        String updatedLine = this.lines.remove(this.index) + text;
        this.add(updatedLine);
        this.isAppend = false;
    }

    private void save(String filename) throws IOException {
        FileWriter out = new FileWriter(filename);
        for (String i : this.lines) out.write(i + '\n');
        out.close();
    }

    private void exit() throws IOException {
        FileWriter out = new FileWriter("OutputFile.txt");
        for (String i : this.commands) out.write(i + '\n');
        out.close();
    }

    private int getArgs(String input, int i) {
        return Integer.parseInt(input.split(" ")[i]) - 1;
    }

    private void execute(String input) throws IOException {
        this.commands.add(this.index + 1 + ">" + input);

        if (this.isAppend)
            this.append(input);

        else if (input.equals("I"))
            this.index--;

        else if (input.matches("I\\s[0-9]"))
            this.index = this.getArgs(input, 1);

        else if (input.equals("D"))
            this.delete();

        else if (input.matches("D\\s[0-9]+\\s[0-9]+"))
            this.delete(this.getArgs(input, 1), this.getArgs(input, 2));

        else if (input.matches("D\\s[0-9]+"))
            this.delete(this.getArgs(input, 1));

        else if (input.equals("L"))
            this.list();

        else if (input.equals("A"))
            this.isAppend = true;

        else if (input.startsWith("S "))
            this.save(input.split(" ")[1]);

        else if (input.equals("E"))
            this.exit();

        else
            this.add(input);
    }

    private void read() throws IOException {
        Scanner in = new Scanner(new FileReader("InputFile.txt"));
        while (in.hasNextLine()) {
            this.execute(in.nextLine());
        }
        in.close();
    }

    public static void main(String[] args) throws IOException {
        TestEditor t = new TestEditor();
        t.read();
    }

}
