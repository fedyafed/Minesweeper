package minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final int LEN = 9;
    private final boolean[][] field = new boolean[LEN][LEN];
    private final boolean[][] marks = new boolean[LEN][LEN];
    private final boolean[][] opened = new boolean[LEN][LEN];
    private static Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final int mineCount;
    private boolean initialized = false;
    private boolean exploded = false;

    public Main(int mineCount) {
        this.mineCount = mineCount;
    }

    public static void main(String[] args) {
        // write your code here
        System.out.println("How many mines do you want on the field?");
        int mineCount = scanner.nextInt();

        Main main = new Main(mineCount);

        main.printField();

        while (main.isGameNotFinished()) {
            main.move();
            main.printField();
        }

        main.finishGame();
    }

    private void finishGame() {
        if(exploded) {
            System.out.println("You stepped on a mine and failed!");
        } else {
            System.out.println("Congratulations! You found all the mines!");
        }
    }

    private void move() {
        while (true) {
            System.out.println("Set/unset mines marks or claim a cell as free:");
            int y = scanner.nextInt() - 1;
            int x = scanner.nextInt() - 1;
            String action = scanner.next();

            if (x < 0 || x >= LEN ||
                    y < 0 || y >= LEN) {
                System.out.printf("Invalid coordinates: %d, %d%n", x, y);
                continue;
            }

            switch (action) {
                case "mine":
                    if (opened[x][y]) {
                        return;
                    }
                    marks[x][y] ^= true;
                    return;
                case "free":
                    if (field[x][y]) {
                        exploded = true;
                        return;
                    }
                    if (!initialized) {
                        initField(x, y);
                    }
                    open(x, y);
                    return;
                default:
                    System.out.println("Invalid action: " + action);
            }
        }
    }

    private void open(int x, int y) {
        if (x < 0 || x >= LEN ||
                y < 0 || y >= LEN) {
            return;
        }
        if (opened[x][y]) {
            return;
        }
        opened[x][y] = true;
        marks[x][y] = false;
        int minesAround = getMinesAround(x, y);
        if (minesAround != 0) {
            return;
        }

        open(x - 1, y - 1);
        open(x - 1, y + 1);
        open(x + 1, y - 1);
        open(x + 1, y + 1);
        open(x - 1, y);
        open(x + 1, y);
        open(x, y - 1);
        open(x, y + 1);
    }

    private boolean isGameNotFinished() {
        if (!initialized) {
            return true;
        }
        if (exploded || Arrays.deepEquals(field, marks)) {
            return false;
        }

        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                if (field[i][j] == opened[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }


    private void initField(int x, int y) {
        field[x][y] = true;

        for (int i = 0; i < mineCount; i++) {
            int row;
            int col;
            do {
                row = random.nextInt(LEN);
                col = random.nextInt(LEN);
            } while (field[row][col]);
            field[row][col] = true;
        }

        field[x][y] = false;
        initialized = true;
    }

    private void printField() {
        System.out.println(" |123456789|\n" +
                "-|---------|");
        for (int i = 0; i < LEN; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < LEN; j++) {
                if (opened[i][j]) {
                    int minesAround = getMinesAround(i, j);
                    System.out.print(minesAround > 0 ? minesAround : "/");
                } else if (exploded && field[i][j]) {
                    System.out.print("X");
                } else if (marks[i][j]) {
                    System.out.print("*");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    private int getMinesAround(int x, int y) {
        int result = 0;
        result += getMine(x - 1, y - 1);
        result += getMine(x - 1, y + 1);
        result += getMine(x + 1, y - 1);
        result += getMine(x + 1, y + 1);
        result += getMine(x - 1, y);
        result += getMine(x + 1, y);
        result += getMine(x, y - 1);
        result += getMine(x, y + 1);
        return result;
    }

    private int getMine(int x, int y) {
        if (x < 0 || x >= LEN ||
                y < 0 || y >= LEN) {
            return 0;
        }
        return field[x][y] ? 1 : 0;
    }
}
