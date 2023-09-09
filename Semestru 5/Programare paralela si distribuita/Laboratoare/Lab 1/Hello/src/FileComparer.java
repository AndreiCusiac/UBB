import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileComparer {
    boolean isEqual(Path firstFile, Path secondFile)
    {
        try {
            long size = Files.size(firstFile);
            if (size != Files.size(secondFile)) {
                return false;
            }

            if (size < 2048)
            {
                return Arrays.equals(Files.readAllBytes(firstFile),
                        Files.readAllBytes(secondFile));
            }

            // Compare character-by-character
            try (BufferedReader bf1 = Files.newBufferedReader(firstFile);
                 BufferedReader bf2 = Files.newBufferedReader(secondFile))
            {
                int ch;
                while ((ch = bf1.read()) != -1)
                {
                    if (ch != bf2.read()) {
                        return false;
                    }
                }
            }

            return true;
        } catch (IOException e) {
            System.out.println("A apărut o eroare la compararea fișierelor.");
            e.printStackTrace();
        }
        return false;
    }
}
