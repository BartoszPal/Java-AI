import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Iris> irisesTraining = getFromFile("src/iris_training.txt");
        ArrayList<Iris> irisesTest = getFromFile("src/iris_test.txt");

//        DecimalFormat decimalFormat = new DecimalFormat("#.####");
//        for (int i = 1; i < 121; i++) {
//            double result = calculate(irisesTraining, irisesTest, i);
//            System.out.println(i + " " + decimalFormat.format(result));
//        }

        int k = getKValue();

        double result = calculate(irisesTraining, irisesTest, k);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Test completed with " + decimalFormat.format(result * 100) + "% accuracy.");

        ArrayList<Iris> myIrises = inputYourOwnData();
        if (myIrises.size() != 0)
            calculateMyOwnData(irisesTraining, myIrises, k);
    }

    private static ArrayList<Iris> inputYourOwnData() {
        ArrayList<Iris> irises = new ArrayList<>();
        System.out.println("Do you want to input your own data [yes/no].");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String coordinates;
        if (line.equals("no")) {
            return irises;
        } else if (line.equals("yes")) {
            while (true) {
                System.out.println("To break the process write [yes], input format example   5,1 3,2 1,0 0,2");
                coordinates = scanner.nextLine();
                if (Objects.equals(coordinates, "yes"))
                    break;
                else {
                    try {
                        String[] splittedLine = coordinates.split(" +");
                        double coordinate1 = Double.parseDouble(splittedLine[0].replaceAll("\\s+", "").replace(",", "."));
                        double coordinate2 = Double.parseDouble(splittedLine[1].replaceAll("\\s+", "").replace(",", "."));
                        double coordinate3 = Double.parseDouble(splittedLine[2].replaceAll("\\s+", "").replace(",", "."));
                        double coordinate4 = Double.parseDouble(splittedLine[3].replaceAll("\\s+", "").replace(",", "."));
                        Iris iris = new Iris(coordinate1, coordinate2, coordinate3, coordinate4, null);
                        irises.add(iris);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Bad input: " + coordinates);
                    }

                }
            }
        }
        return irises;
    }

    private static int getKValue() {
        System.out.println("Input value for k.");
        Scanner scanner = new Scanner(System.in);
        int k;
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.matches("[0-9]+")) {
                k = Integer.parseInt(line);
                if (k < 1 || k > 120)
                    System.out.println("k value must be in ranges from 1 to 120 inclusive.");
                else
                    break;
            } else {
                System.out.println("You didn't provide a number. Provide a number from ranges 1 to 120 inclusive.");
            }
        }
        return k;

    }

    private static ArrayList<Iris> getFromFile(String fileName) {
        ArrayList<Iris> irises = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            double coordinate1;
            double coordinate2;
            double coordinate3;
            double coordinate4;
            String name;

            while ((line = bufferedReader.readLine()) != null) {
                String[] splittedLine = line.split(" {2,}");
                coordinate1 = Double.parseDouble(splittedLine[0].replaceAll("\\s+", "").replace(",", "."));
                coordinate2 = Double.parseDouble(splittedLine[1].replaceAll("\\s+", "").replace(",", "."));
                coordinate3 = Double.parseDouble(splittedLine[2].replaceAll("\\s+", "").replace(",", "."));
                coordinate4 = Double.parseDouble(splittedLine[3].replaceAll("\\s+", "").replace(",", "."));
                name = splittedLine[4].replaceAll("\\s+", "");

                Iris iris = new Iris(coordinate1, coordinate2, coordinate3, coordinate4, name);
                irises.add(iris);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return irises;
    }

    private static double calculate(ArrayList<Iris> training, ArrayList<Iris> test, int k) {
        double goodGuesses = 0.0;
        int setosa;
        int versicolor;
        int virginica;
        double distanceSetosa;
        double distanceVersicolor;
        double distanceVirginica;
        for (Iris iris : test) {
            ArrayList<Pair> results = new ArrayList<>();
            setosa = 0;
            versicolor = 0;
            virginica = 0;
            distanceSetosa = 0;
            distanceVersicolor = 0;
            distanceVirginica = 0;
            for (Iris trainedIris : training) {
                results.add(new Pair(iris.distance(trainedIris), trainedIris.getName())); // oblicznie dystansu do kazdego elementu z treningu
            }
            results.sort(Comparator.comparingDouble(Pair::getDistance));
            for (int i = 0; i < k; i++) {
                if (Objects.equals(results.get(i).getName(), "Iris-setosa")) {
                    setosa += 1;
                    distanceSetosa += results.get(i).getDistance();
                }
                if (Objects.equals(results.get(i).getName(), "Iris-versicolor")) {
                    versicolor += 1;
                    distanceVersicolor += results.get(i).getDistance();
                }
                if (Objects.equals(results.get(i).getName(), "Iris-virginica")) {
                    virginica += 1;
                    distanceVirginica += results.get(i).getDistance();
                }
            }
            String result = findBest(setosa, versicolor, virginica, distanceSetosa, distanceVersicolor, distanceVirginica);
//            System.out.println("setosa = " + setosa + " versicolor = " + versicolor + " virginica = " + virginica +
//                                "\niris.getName = " + iris.getName() + " guessedName = " + result);
            if (Objects.equals(iris.getName(), result))
                goodGuesses += 1;
        }
        return goodGuesses / test.size();
    }


    private static String findBest(int setosa, int versicolor, int virginica, double distanceSetosa, double distanceVersicolor, double distanceVirginica) {
        if (setosa > versicolor && setosa > virginica)
            return "Iris-setosa";
        else if (versicolor > setosa && versicolor > virginica)
            return "Iris-versicolor";
        else if (virginica > setosa && virginica > versicolor)
            return "Iris-virginica";


        // warunki gdzie jeden skladnik jest 0 (wyzanczanie irysa ze wzgledu na srednia odleglosc)
        if (setosa == 0 && distanceVersicolor < distanceVirginica)
            return "Iris-versicolor";
        else if (setosa == 0 && distanceVersicolor > distanceVirginica)
            return "Iris-virginica";
        else if (versicolor == 0 && distanceSetosa < distanceVirginica)
            return "Iris-setosa";
        else if (versicolor == 0 && distanceSetosa > distanceVirginica)
            return "Iris-virginica";
        else if (virginica == 0 && distanceSetosa < distanceVersicolor)
            return "Iris-setosa";
        else if (virginica == 0 && distanceSetosa > distanceVersicolor)
            return "Iris-versicolor";


        // warunki gdzie zaden skladnik nie jest 0 (wyzanczanie irysa ze wzgledu na srednia odleglosc)
        if (distanceSetosa/setosa < distanceVersicolor/versicolor && distanceSetosa/setosa < distanceVirginica/virginica)
            return "Iris-setosa";
        else if (distanceVersicolor/versicolor < distanceSetosa/setosa && distanceVersicolor/versicolor < distanceVirginica/virginica)
            return "Iris-versicolor";
        else if (distanceVirginica/virginica < distanceSetosa/setosa && distanceVirginica/virginica < distanceVersicolor/versicolor)
            return "Iris-virginica";
        return null;
    }


    private static void calculateMyOwnData(ArrayList<Iris> training, ArrayList<Iris> test, int k) {
        int setosa;
        int versicolor;
        int virginica;
        double distanceSetosa;
        double distanceVersicolor;
        double distanceVirginica;
        for (Iris iris : test) {
            ArrayList<Pair> results = new ArrayList<>();
            setosa = 0;
            versicolor = 0;
            virginica = 0;
            distanceSetosa = 0;
            distanceVersicolor = 0;
            distanceVirginica = 0;
            for (Iris trainedIris : training) {
                results.add(new Pair(iris.distance(trainedIris), trainedIris.getName()));
            }
            results.sort(Comparator.comparingDouble(Pair::getDistance));
            for (int i = 0; i < k; i++) {
                if (Objects.equals(results.get(i).getName(), "Iris-setosa")) {
                    setosa += 1;
                    distanceSetosa += results.get(i).getDistance();
                }
                if (Objects.equals(results.get(i).getName(), "Iris-versicolor")) {
                    versicolor += 1;
                    distanceVersicolor += results.get(i).getDistance();
                }
                if (Objects.equals(results.get(i).getName(), "Iris-virginica")) {
                    virginica += 1;
                    distanceVirginica += results.get(i).getDistance();
                }
            }
            String result = findBest(setosa, versicolor, virginica, distanceSetosa, distanceVersicolor, distanceVirginica);
            System.out.println("For data: " + iris.getCoordinate1() + "  "
                    + iris.getCoordinate2() + "  "
                    + iris.getCoordinate3() + "  "
                    + iris.getCoordinate4() + "  " +
                    "Guessed iris: " + result);
        }
    }
}