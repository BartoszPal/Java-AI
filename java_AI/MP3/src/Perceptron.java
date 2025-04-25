import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Perceptron {
    private String language;
    private double theta;
    private Double[] wages;
    private Double alpha;

    public Perceptron(String language, Double[] wages, double theta, double alpha) {
        this.language = language;
        this.theta = theta;
        this.wages = wages;
        this.alpha = alpha;
    }

    public void train(ArrayList<Pair> data) {
        boolean isCorrect = false;
        int iterator = 0;
        while (!isCorrect && iterator++ < 300) {
            isCorrect = true;
            for (Pair pair : data) {
                double net = 0;
                Map<Character, Double> map = pair.getCharacterMap();

                for (int i = 'a'; i <= 'z'; i++) {
                    net += map.get((char) i) * wages[i - 'a'];
                }
                Double[] newWages = wages.clone();
                if (net < theta) {
                    if (Objects.equals(pair.getLanguageName(), language)) {
                        isCorrect = false;
                        for (int j = 'a'; j <= 'z'; j++) {
                            newWages[j - 'a'] = wages[j - 'a'] + alpha * map.get((char) j);
                        }
                        if (theta != 0)
                            theta -= theta * alpha;
                        else
                            theta -= alpha;
                    }
                } else {
                    if (!Objects.equals(pair.getLanguageName(), language)) {
                        isCorrect = false;
                        for (int j = 'a'; j <= 'z'; j++) {
                            newWages[j - 'a'] = wages[j - 'a'] - alpha * map.get((char) j);
                        }
                        if (theta != 0)
                            theta += theta * alpha;
                        else
                            theta += alpha;
                    }
                }
                wages = newWages;
            }
        }
    }

    public void checkAccuracy(ArrayList<Pair> data) {
        double correctness = data.size();
        for (Pair pair : data) {
            double net = 0;
            Map<Character, Double> map = pair.getCharacterMap();
            for (int i = 'a'; i <= 'z'; i++) {
                net += map.get((char) i) * wages[i - 'a'];
            }
            if (net < theta) {
                if (Objects.equals(pair.getLanguageName(), language)) {
                    correctness -= 1;
                }
            } else {
                if (!Objects.equals(pair.getLanguageName(), language)) {
                    correctness -= 1;
                }
            }
        }
        String formattedLanguage = language.substring(0, 1).toUpperCase() + language.substring(1);
        System.out.println(formattedLanguage + ": " + String.format("%.2f", (correctness / (double) data.size() * 100)) + "% accuracy");
    }

    public double checkLanguage(String data) { // todo bylo int
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 'a'; i <= 'z'; i++)
            map.put((char) i, 0);

        int howManyElements = 0;
        for (char c : data.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int count = map.get(c);
                map.put(c, ++count);
                howManyElements++;
            }
        }

        Map<Character, Double> result = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet())
            result.put(entry.getKey(), entry.getValue() / ((double) howManyElements));

        double net = 0;
        for (int i = 'a'; i <= 'z'; i++) {
            net += result.get((char) i) * wages[i - 'a'];
        }
        net -= theta;
        double output = 2.0/(1.0+Math.exp(net*(-1))) - 1;
//        System.out.println(output + " " + getLanguage());
//        if (net < theta)
//            return 0;
//        else
//            return 1;
        return output;
    }

    public String getLanguage() {
        return language.substring(0, 1).toUpperCase() + language.substring(1);
    }
}
