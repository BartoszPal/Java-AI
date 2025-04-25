import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileConverter fileConverterTraining = new FileConverter("src/languagesTraining");
        ArrayList<Pair> trainingData = fileConverterTraining.convertFile();
        FileConverter fileConverterTest = new FileConverter("src/languagesTest");
        ArrayList<Pair> testData = fileConverterTest.convertFile();

        Double[] wages = new Double[26];
        for (int i = 'a'; i <= 'z'; i++)
            wages[i - 'a'] = 1.0;
        double theta = 0;
        double alpha = 0.7;

        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        for (String language : fileConverterTraining.getLanguages()){
            Perceptron perceptron = new Perceptron(language, wages, theta, alpha);
            perceptron.train(trainingData);
            perceptrons.add(perceptron);
        }

        System.out.println("Training:");
        for (Perceptron perceptron : perceptrons)
            perceptron.checkAccuracy(trainingData);

        System.out.println("Test:");
        for (Perceptron perceptron : perceptrons)
            perceptron.checkAccuracy(testData);

        PerceptronService perceptronService = new PerceptronService(perceptrons);

        SwingUtilities.invokeLater(() -> {
            new Gui(perceptronService);
        });
    }
}
