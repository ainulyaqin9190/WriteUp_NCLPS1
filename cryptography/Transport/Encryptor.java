import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Encryptor {
    static class KeyIndexPair implements Comparable<KeyIndexPair> {
        char character;
        int index;

        public KeyIndexPair(char character, int index) {
            this.character = character;
            this.index = index;
        }

        @Override
        public int compareTo(KeyIndexPair other) {
            return Character.compare(this.character, other.character);
        }
    }

    public static String encrypt(String plain) throws IOException {
        String key = new String(Files.readAllBytes(Paths.get("lorem.txt"))).substring(0, 5).toLowerCase();

        int col = key.length();
        int row = (int) Math.ceil((double) plain.length() / col);

        char[][] grid = new char[row][col];
        int charIndex = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (charIndex < plain.length()) {
                    grid[i][j] = plain.charAt(charIndex++);
                } else {
                    grid[i][j] = ' ';
                }
            }
        }

        List<KeyIndexPair> keyMap = new ArrayList<>();
        for (int i = 0; i < key.length(); i++) {
            keyMap.add(new KeyIndexPair(key.charAt(i), i));
        }

        Collections.sort(keyMap);

        StringBuilder cipher = new StringBuilder();
        for (KeyIndexPair pair : keyMap) {
            int originalIndex = pair.index;
            for (int i = 0; i < row; i++) {
                cipher.append(grid[i][originalIndex]);
            }
        }

        return cipher.toString();
    }

    public static void main(String[] args) {
        String FLAG = "NCLPS1{REDACTED}";

        if (FLAG.length() % 5 != 0) {
            System.err.println("Error: Panjang flag harus kelipatan 5.");
            return;
        }

        try {
            String result = encrypt(FLAG);
            try (FileWriter writer = new FileWriter("output.txt")) {
                writer.write(result);
            }
            
            System.out.println("done...");

        } catch (IOException e) {
            System.err.println("Terjadi kesalahan file I/O: " + e.getMessage());
        }
    }
}