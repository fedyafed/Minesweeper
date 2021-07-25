import java.util.*;

class Main {

    private static Map<Character, Character> pairs = new HashMap<>();

    public static void main(String[] args) {
        // put your code here
        pairs.put(']', '[');
        pairs.put('}', '{');
        pairs.put(')', '(');

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        System.out.println(isMatched(line));
    }

    private static boolean isMatched(String line) {
        Deque<Character> deque = new ArrayDeque<>();
        for (Character ch : line.toCharArray()) {
            if (pairs.containsValue(ch)) {
                deque.offerFirst(ch);
            } else if (pairs.containsKey(ch)) {
                if (pairs.get(ch) != deque.pollFirst()) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return deque.isEmpty();
    }
}