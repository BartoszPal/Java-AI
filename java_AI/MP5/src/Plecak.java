import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Plecak {
    private static int capacity;
    private final int dataset;
    private final ArrayList<Integer> sizes;
    private final ArrayList<Integer> values;

    public Plecak(int dataset, ArrayList<Integer> sizes, ArrayList<Integer> values) {
        this.dataset = dataset;
        this.sizes = sizes;
        this.values = values;
    }

    public static void setCapacity(int capacity) {
        Plecak.capacity = capacity;
    }

//    public void bruteForce() {
//        ArrayList<Pair> pairs = new ArrayList<>();
//        for (int i = 0; i < sizes.size(); i++) {
//            pairs.add(new Pair(values.get(i), sizes.get(i), i));
//        }
//        ArrayList<ArrayList<Pair>> subsets = new ArrayList<>();
//        generateSubsets(pairs, new ArrayList<>(), subsets);
//
//        ArrayList<Pair> result = new ArrayList<>();
//        int currentCapacity;
//        int currentValue;
//        int maxValue = -1;
//        for (ArrayList<Pair> subset : subsets) {
//            currentCapacity = 0;
//            currentValue = 0;
//            for (Pair pair : subset) {
//                currentCapacity += pair.size;
//                if(currentCapacity > capacity)
//                    break;
//                currentValue += pair.value;
//            }
//            if(currentCapacity <= capacity && currentValue > maxValue)
//                maxValue = currentValue;
//        }
//        System.out.println(maxValue);
//    }
//
//    private void generateSubsets(ArrayList<Pair> list, ArrayList<Pair> result, ArrayList<ArrayList<Pair>> subsets) {
//        if (list.isEmpty()) {
//            subsets.add(new ArrayList<>(result));
//        } else {
//            Pair firstElement = list.remove(0);
//            generateSubsets(new ArrayList<>(list), new ArrayList<>(result), subsets);
//            result.add(firstElement);
//            generateSubsets(new ArrayList<>(list), new ArrayList<>(result), subsets);
//            list.add(0, firstElement);
//        }
//    }

    public void calculateHeuristics() {
        LocalDateTime start = LocalDateTime.now();
        ArrayList<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < sizes.size(); i++) {
            pairs.add(new Pair(values.get(i), sizes.get(i), i));
        }
        pairs.sort((e, e1) -> Double.compare(e1.density, e.density));

        int currentCapacity = 0;
        int currentValue = 0;
        for (Pair pair : pairs) {
            if (currentCapacity + pair.size <= capacity) {
                System.out.println(pair);
                currentCapacity += pair.size;
                currentValue += pair.value;
            }
        }
        System.err.println("For dataset " + dataset + " max size = " + currentValue);

        LocalDateTime finish = LocalDateTime.now();
        Duration duration = Duration.between(start, finish);
        long millis = duration.toMillis();
        if (millis > 1000){
            String milliseconds = String.valueOf(millis);
            milliseconds = milliseconds.substring(0, milliseconds.length() - 3);
            millis -= Long.parseLong(milliseconds) * 1000;
        }

        System.err.println("Duration: " + duration.toSeconds() + " seconds and " + millis +" milliseconds");
    }

    public void bruteForce() {
        LocalDateTime start = LocalDateTime.now();
        ArrayList<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < sizes.size(); i++) {
            pairs.add(new Pair(values.get(i), sizes.get(i), i));
        }
        ArrayList<Pair> result = new ArrayList<>();
        int[] maxValue = {-1};
        generateSubsets(pairs, new ArrayList<>(), 0, 0, result, maxValue);

        for (Pair pair : result) {
            System.out.println(pair);
        }
        System.err.println("For dataset " + dataset + " max size = " + maxValue[0]);

        LocalDateTime finish = LocalDateTime.now();
        Duration duration = Duration.between(start, finish);
        long millis = duration.toMillis();
        if (millis > 1000){
            String milliseconds = String.valueOf(millis);
            milliseconds = milliseconds.substring(0, milliseconds.length() - 3);
            millis -= Long.parseLong(milliseconds) * 1000;
        }

        System.err.println("Duration: " + duration.toSeconds() + " seconds and " + millis +" milliseconds");
        System.out.println(count);
    }
    static int count = 0;
    private void generateSubsets(ArrayList<Pair> list, ArrayList<Pair> result, int currentCapacity, int currentValue, ArrayList<Pair> optimalSubset, int[] maxValue) {
        if (list.isEmpty()) {
            if (currentCapacity <= capacity && currentValue > maxValue[0]) {
                maxValue[0] = currentValue;
                optimalSubset.clear();
                optimalSubset.addAll(result);
            }
        } else {
            Pair firstElement = list.remove(0);

            generateSubsets(new ArrayList<>(list), new ArrayList<>(result), currentCapacity, currentValue, optimalSubset, maxValue);

            if (currentCapacity + firstElement.size <= capacity) {
                result.add(firstElement);
                generateSubsets(new ArrayList<>(list), new ArrayList<>(result), currentCapacity + firstElement.size, currentValue + firstElement.value, optimalSubset, maxValue);
                result.remove(result.size() - 1);
            }

            list.add(0, firstElement);
        }
        count++;
    }

    public int getDataset() {
        return dataset;
    }
}
