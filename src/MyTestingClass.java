import java.util.HashMap;
import java.util.Random;

public class MyTestingClass {
    private String data;

    public MyTestingClass(String data) {
        this.data = data;
    }

    // Custom hashCode method to ensure uniform distribution
    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash * 31) + data.charAt(i);
        }
        return hash;
    }

    public static void main(String[] args) {
        MyHashTable<MyTestingClass, String> table = new MyHashTable<>();
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            String randomData = generateRandomString(5);
            MyTestingClass key = new MyTestingClass(randomData);
            table.put(key, "Value" + i);
        }

        table.output();

         }


    private static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            builder.append(chars.charAt(index));
        }
        return builder.toString();
    }
}
