import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KMeans {
    private final int k;
    private final ArrayList<Pair> pairs;
    private Struct[] centroids;
    private final ArrayList<String> possibleNames;

    public KMeans(int k, String fileName) throws Exception {
        if (k <= 0)
            throw new Exception("Bad input for k, must be higher than 0");
        this.possibleNames = new ArrayList<>();
        this.pairs = getFromFile(fileName, k);
        if (k > pairs.size())
            throw new Exception("Bad input for k, must be in range of 1 to " + pairs.size());
        this.k = k;
        this.centroids = new Struct[k];
    }

    private ArrayList<Pair> getFromFile(String fileName, int k) {
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
                irises.add(new Pair(element, name, k)); // zainicjowanie kazdego irysa rondomowa wartoscia k
                if (!possibleNames.contains(name))
                    possibleNames.add(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return irises;
    }

    private void calculateCentroid() {
        Struct[] innerCentroids = new Struct[k];
        ArrayList<Double> defaultCoordinates = new ArrayList<>();
        for (int i = 0; i < pairs.get(0).getCoordinates().size(); i++)
            defaultCoordinates.add(0.0);

        for (int i = 0; i < innerCentroids.length; i++)
            innerCentroids[i] = new Struct(defaultCoordinates);

        for (Pair pair : pairs)
            innerCentroids[pair.getGroup()].add(pair.getCoordinates());

        for (int i = 0; i < innerCentroids.length; i++) {
            innerCentroids[i].subtract(getNumberOfElements(i));
        }

        this.centroids = innerCentroids;
    }

    public void calculate() {
        boolean runner = true;
        int iterator = 0;
        while (runner) {
            iterator++;
            calculateCentroid();
            runner = assignNewGroups();
        }
        System.out.println("Number of iterations " + iterator);
    }

    private int getNumberOfElements(int i) {
        int count = 0;
        for (Pair pair : pairs) {
            if (pair.getGroup() == i)
                count++;
        }
        return count;
    }

    private boolean assignNewGroups() {
        boolean change = false;
        double[] sumDistances = new double[k];
        for (Pair pair : pairs) {
            double[] distances = calculateDistance(pair);
            for (int i = 0; i < distances.length; i++) {
                if (pair.getGroup() == i)
                    sumDistances[i] += Math.pow(distances[i], 2);
            }
            double min = distances[0];
            int groupNumber = 0;
            for (int i = 0; i < distances.length; i++) {
                if (distances[i] < min) {
                    min = distances[i];
                    groupNumber = i;
                }
            }
            if (pair.getGroup() != groupNumber) {
                change = true;
                pair.setGroup(groupNumber);
            }
        }
        double sum = 0.0;
        for (double sumDistance : sumDistances) {
            sum += sumDistance;
        }
        System.out.println(sum);
        return change;
    }

    private double[] calculateDistance(Pair pair) {
        double[] distances = new double[k];
        for (int i = 0; i < distances.length; i++) {
            double distance = 0.0;
            for (int j = 0; j < pair.getCoordinates().size(); j++) {
                distance += Math.pow(pair.getCoordinates().get(j) - centroids[i].getCoordinates().get(j), 2);
            }
            distances[i] = Math.sqrt(distance);
        }
        return distances;
    }

    public void showStats() {
        for (int i = 0; i < k; i++) {
            Map<String, Integer> stats = new HashMap<>();
            for (String possibleName : possibleNames)
                stats.put(possibleName, 0);
            for (Pair pair : pairs) {
                if (pair.getGroup() == i) {
                    String name = pair.getName();
                    int howMany = stats.get(name);
                    stats.put(name, ++howMany);
                }
            }
            System.out.print("Group " + i);
            int numberOfElements = getNumberOfElements(i);
            double entropy = 0.0;
            for (Map.Entry<String, Integer> map : stats.entrySet()) {
                System.out.print(", " + map.getKey() + " " + map.getValue());
                if (numberOfElements != 0)
                    if (map.getValue() != 0)
                        entropy -= ((double) map.getValue() / numberOfElements) * (Math.log((double) map.getValue() / numberOfElements) / Math.log(2));
            }
            System.out.print("\t\t Entropy = " + entropy);
            System.out.println();
        }
    }
}
