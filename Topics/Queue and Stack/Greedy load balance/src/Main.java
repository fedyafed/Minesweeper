import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Queue<String> queue1 = new ArrayDeque<>();
        Queue<String> queue2 = new ArrayDeque<>();
        int load1 = 0;
        int load2 = 0;
        for (int i = 0; i < n; i++) {
            int taskId = scanner.nextInt();
            int load = scanner.nextInt();

            if (load1 > load2) {
                load2 += load;
                queue2.offer(String.valueOf(taskId));
            } else {
                load1 += load;
                queue1.offer(String.valueOf(taskId));
            }
        }

        System.out.println(String.join(" ", queue1));
        System.out.println(String.join(" ", queue2));
    }
}