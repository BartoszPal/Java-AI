import java.util.ArrayList;

public class Perceptron {
    private ArrayList<Double> wages;
    private double theta;
    private final double alpha;

    public Perceptron(ArrayList<Double> wages, double theta, double alpha) {
        this.wages = wages;
        this.theta = theta;
        this.alpha = alpha;
    }

    public void calculatePerceptron(ArrayList<Pair> data) {
        boolean isCorrect = false;
        if (wages.size() != data.get(0).getCoordinates().size())
            return;
        int iterator = 0;
        while (!isCorrect && iterator++ < 300) {
            isCorrect = true;
            for (Pair pair : data) {
                int calculation = 0;
                int i = 0;
                for (double coordinate : pair.getCoordinates()) {
                    calculation += coordinate * wages.get(i++);
                }
                ArrayList<Double> coordinates = pair.getCoordinates();
                ArrayList<Double> newWages = new ArrayList<>();
                if (calculation < theta) {
                    if (pair.getName().equals("Iris-setosa")) {
                        isCorrect = false;
                        for (int j = 0; j < wages.size(); j++) {
                            newWages.add(wages.get(j) + alpha * coordinates.get(j));
                        }
                        if(theta != 0)
                            theta -= theta * alpha; //todo theta
                        else
                            theta -= alpha;
                    } else {
                        newWages = wages;
                    }
                } else {
                    if (pair.getName().equals("Iris-setosa")) {
                        newWages = wages;
                    } else {
                        isCorrect = false;
                        for (int j = 0; j < wages.size(); j++) {
                            newWages.add(wages.get(j) - alpha * coordinates.get(j));
                        }
                        if(theta != 0)
                            theta += theta * alpha; //todo theta
                        else
                            theta = alpha;
                    }
                }
                wages = newWages;
            }
        }
    }

    public void calculate(ArrayList<Pair> data) {
        double correctness = data.size();
        for (Pair pair : data) {
            int calculation = 0;
            int i = 0;
            for (double coordinate : pair.getCoordinates()) {
                calculation += coordinate * wages.get(i++);
            }
            if (calculation < theta) {
                if (pair.getName().equals("Iris-setosa")) {
                    correctness -= 1;
                }
            } else {
                if (!pair.getName().equals("Iris-setosa")) {
                    correctness -= 1;
                }
            }
        }
        System.out.println(correctness / data.size() * 100 + "%");
    }

    public void calculateMyOwnData(ArrayList<Pair> data) {
        for (Pair pair : data) {
            int calculation = 0;
            int i = 0;
            for (double coordinate : pair.getCoordinates()) {
                calculation += coordinate * wages.get(i++);
            }
            if (calculation < theta) {
                System.out.println("For data: " + pair.getCoordinates() + " = not Setosa" );
            } else {
                System.out.println("For data: " + pair.getCoordinates() + " = Setosa" );
            }
        }
    }
}
