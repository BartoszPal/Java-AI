import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Pair> irisesTraining = getFromFile("src/iris_training.txt");
        ArrayList<Double> wages = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));

        Perceptron perceptron = new Perceptron(wages, 1, 0.27);
        perceptron.calculatePerceptron(irisesTraining);

        ArrayList<Pair> test = getFromFile("src/iris_tets.txt");
        perceptron.calculate(test);

        ArrayList<Pair> test1 = inputYourOwnData();
        perceptron.calculateMyOwnData(test1);
    }


    private static ArrayList<Pair> getFromFile(String fileName) {
        ArrayList<Pair> irises = new ArrayList<>();
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splittedLine = line.split(" {2,}");
                ArrayList<Double> element = new ArrayList<>();
                double d;
                for (int i = 0; i < splittedLine.length - 1; i++) {
                    d = Double.parseDouble(splittedLine[i].replaceAll("\\s+", "").replace(",", "."));
                    element.add(d);
                }
                String name = splittedLine[splittedLine.length - 1].replaceAll("\\s+", "");
                irises.add(new Pair(element, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return irises;
    }


    private static ArrayList<Pair> inputYourOwnData() {
        ArrayList<Pair> irises = new ArrayList();
        System.out.println("Do you want to input your own data [yes/no].");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if (!line.equals("no")) {
            if (line.equals("yes")) {
                while (true) {
                    System.out.println("To break the process write [yes], input format example   5,1 3,2 1,0 0,2");
                    String coordinates = scanner.nextLine();
                    if (Objects.equals(coordinates, "yes")) {
                        break;
                    }
                    try {
                        String[] splittedLine = coordinates.split(" +|\\t");
                        ArrayList<Double> element = new ArrayList<>();
                        double d;
                        for (String s : splittedLine) {
                            d = Double.parseDouble(s.replaceAll("\\s+", "").replace(",", "."));
                            element.add(d);
                        }
                        irises.add(new Pair(element));
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException var14) {
                        System.out.println("Bad input: " + coordinates);
                    }
                }
            }
        }
        return irises;
    }
}