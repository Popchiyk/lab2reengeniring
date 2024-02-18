import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    private static final String FILE_PATH = "src/edu/pro/txt/harry.txt";

    public static String[] readAndCleanText(String url) throws IOException {
        return Files.lines(Paths.get(url))
                .flatMap(line -> Arrays.stream(line.split(" +")))
                .toArray(String[]::new);
    }

    public static void main(String[] args) throws IOException {
        LocalDateTime start = LocalDateTime.now();
        
        String[] words = readAndCleanText(FILE_PATH);

        Map<String, Long> wordFrequencies = Arrays.stream(words)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        String[] distincts = wordFrequencies.keySet().toArray(String[]::new);
        long[] freq = wordFrequencies.values().stream().mapToLong(Long::intValue).toArray();

        Arrays.sort(distincts, Comparator.comparing(str -> Integer.valueOf(str.replaceAll("[^0-9]", ""))));

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 30 && i < distincts.length; i++) {
            result.append(String.format("%s %d", distincts[distincts.length - 1 - i], freq[distincts.length - 1 - i])).append("\n");
        }

        System.out.println(result);
        System.out.println("------");
    }
}
