import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileConverter {
    private final String path;
    private ArrayList<String> languages;

    public FileConverter(String path) {
        this.languages = new ArrayList<>();
        this.path = path;
    }

    public ArrayList<Pair> convertFile() {
        Path startPath = Path.of(path);
        ArrayList<Pair> listOfPairs = new ArrayList<>();
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if(file.toString().endsWith(".txt")){
                        String language = file.getName(2).toString();
                        listOfPairs.add(applyLogic(file, language));
                        if(!languages.contains(language))
                            languages.add(language);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.err.println("Failed to visit file: " + file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listOfPairs;
    }

    private Pair applyLogic(Path file, String languageName){
        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 'a'; i <= 'z'; i++)
            charCounts.put((char)i, 0);

        int howManyElements = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                for (char c: line.toLowerCase().toCharArray()) {
                    if(c >= 'a' && c <= 'z'){
                        int count = charCounts.get(c);
                        charCounts.put(c, ++count);
                        howManyElements++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<Character, Double> result = new HashMap<>();

        for (Map.Entry<Character, Integer> entry : charCounts.entrySet())
            result.put(entry.getKey(), entry.getValue()/((double)howManyElements));

        return new Pair(result, languageName);
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }
}
