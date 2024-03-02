import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.SystemColor.text;

public class Main {
    public static int count3;
    public static int count4;
    public static int count5;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger int3 = new AtomicInteger(count3);
        AtomicInteger int4 = new AtomicInteger(count4);
        AtomicInteger int5 = new AtomicInteger(count5);
        Random random = new Random();

        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread palindrom = new Thread(() -> {
            for (String s : texts) {
                if (isPalindrom(s)) {
                    if (s.length() == 3) {
                        int3.addAndGet(1);
                    } else if (s.length() == 4) {
                        int4.addAndGet(1);
                    } else if (s.length() == 5) {
                        int5.addAndGet(1);
                    }
                }
            }
        });

        Thread oneChar = new Thread(() -> {
            for (String s : texts) {
                if (isOnlyOneChar(s)) {
                    if (s.length() == 3) {
                        int3.addAndGet(1);
                    } else if (s.length() == 4) {
                        int4.addAndGet(1);
                    } else if (s.length() == 5) {
                        int5.addAndGet(1);
                    }
                }
            }
        });

        Thread inOrder = new Thread(() -> {
            for (String s : texts) {
                if (isInOrder(s)) {
                    if (s.length() == 3) {
                        int3.addAndGet(1);
                    } else if (s.length() == 4) {
                        int4.addAndGet(1);
                    } else if (s.length() == 5) {
                        int5.addAndGet(1);
                    }
                }
            }
        });
        palindrom.start();
        oneChar.start();
        inOrder.start();

        palindrom.join();
        oneChar.join();
        inOrder.join();

        System.out.println("Красивых слов с длиной 3: " + int3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + int4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + int5.get() + " шт");
    }

    public static boolean isPalindrom(String text) {      // палиндром
        boolean palindrom = false;
        int count = 0;
        if (!isOnlyOneChar(text)) {                       // исключаем из расчета палиндромы, состоящие из одного символа
            for (int i = 0; i < text.length() / 2; i++) {
                if (text.charAt(i) == text.charAt(text.length() - i - 1)) {
                    count++;
                }
            }
            if (count == text.length() / 2) {
                palindrom = true;
            }
        }
        return palindrom;
    }

    public static boolean isOnlyOneChar(String text) {      // все символы одинаковы
        boolean oneChar = false;
        int count = 1;
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) == text.charAt(i + 1)) {
                count++;
            }
        }
        if (count == text.length()) {
            oneChar = true;
        }
        return oneChar;
    }

    public static boolean isInOrder(String text) {      // все символы в порядке возрастания
        boolean oneChar = false;
        int count = 1;
        if (!isOnlyOneChar(text)) {                    // исключаем из расчета слова, состоящие из одного символа
            for (int i = 0; i < text.length() - 1; i++) {
                if (text.charAt(i) <= text.charAt(i + 1)) {
                    count++;
                }
            }
            if (count == text.length()) {
                oneChar = true;
            }
        }
        return oneChar;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}