import java.util.Map;

public class Pair {
    private Map<Character, Double> characterMap;
    private String languageName;

    public Pair(Map<Character, Double> characterMap, String languageName) {
        this.characterMap = characterMap;
        this.languageName = languageName;
    }

    public Map<Character, Double> getCharacterMap() {
        return characterMap;
    }

    public String getLanguageName() {
        return languageName;
    }
}
