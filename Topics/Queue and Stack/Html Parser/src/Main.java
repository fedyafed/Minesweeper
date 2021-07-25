import java.util.Scanner;

class Main {
    private final String line;
    private int pos = 0;

    public Main(String line) {
        this.line = line;
    }

    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        Main main = new Main(scanner.nextLine());
        main.parse();
    }

    private void parse() {
        int startPos = pos;

        if (line.charAt(pos) == '<') {
            int closingStart = line.indexOf(">", pos);
            String tagName = line.substring(startPos + 1, closingStart);
            pos = closingStart + 1;
            while (!line.startsWith("</", pos)) {
                parse();
            }
            System.out.println(line.substring(closingStart + 1, pos));
            pos += tagName.length() + 3;
        } else {
            pos = line.indexOf("<", pos);
        }
    }
}