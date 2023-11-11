package example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader r = new BufferedReader(new FileReader("test.txt"))) {
            String line = r.readLine();

            int a = Integer.parseInt(line);
            System.out.println(a + 10);
        } catch (IOException e) {
            System.out.println("ошибка");
        } catch (NumberFormatException e) {
            System.out.println("Не число");
        }
    }

}
