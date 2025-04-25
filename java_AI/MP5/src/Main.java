import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        FileConverter fileConverter = new FileConverter("src/plecak.txt");

        int maxDataSets = fileConverter.getNumberOfDataSets();
        ArrayList<Plecak> plecaczki = fileConverter.getBackPacks();

        Random random = new Random();
        int dataSet = random.nextInt(maxDataSets) + 1;

        Plecak plecak = null;
        for (Plecak p : plecaczki){
            if(p.getDataset() == dataSet){
                plecak = p;
            }
        }
        if(plecak != null){
            plecak.calculateHeuristics();
            plecak.bruteForce();
        }
    }
}
