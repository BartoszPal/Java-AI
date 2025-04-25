import java.util.ArrayList;
import java.util.Random;

public class Pair {
    private ArrayList<Double> coordinates;
    private String name;
    private int group;

    public Pair(ArrayList<Double> coordinates, String name, int k) {
        this.coordinates = coordinates;
        this.name = name;
        Random random = new Random();
        this.group = random.nextInt(k);
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}