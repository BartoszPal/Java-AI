import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PerceptronService {
    private final ArrayList<Perceptron> perceptrons;

    public PerceptronService(ArrayList<Perceptron> perceptrons) {
        this.perceptrons = perceptrons;
    }

//    public String getResult(String data) {
//        Map<Perceptron, Integer> activationMap = new HashMap<>();
//        for (Perceptron perceptron : perceptrons) {
//            activationMap.put(perceptron, perceptron.checkLanguage(data));
//        }
//        ArrayList<String> languages = new ArrayList<>();
//        for (Map.Entry<Perceptron, Integer> entry : activationMap.entrySet()) {
//            if (entry.getValue() == 1)
//                languages.add(entry.getKey().getLanguage());
//        }
//        if(languages.size() == 0){
//            return "Cannot decide";
//        }
//        else if (languages.size() == 1)
//            return languages.get(0);
//        else {
//            StringBuilder output = new StringBuilder("Cannot decide, it can be: ");
//            for (String language : languages) {
//                output.append(language).append(" or ");
//            }
//            return output.substring(0, output.length() - (" or ").length());
//        }
//    }

    public String getResult(String data) {
        Map<Perceptron, Double> activationMap = new HashMap<>();
        for (Perceptron perceptron : perceptrons) {
            activationMap.put(perceptron, perceptron.checkLanguage(data));
        }
        double max = -1.0;
        String language = null;
        for (Map.Entry<Perceptron, Double> entry : activationMap.entrySet()) {
            double current = entry.getValue();
            if(max < current){
                max = current;
                language = entry.getKey().getLanguage();
            }
        }
        if(max == -1.0){
            return "Cannot decide";
        }
        else {
            return language;
        }
    }
}
