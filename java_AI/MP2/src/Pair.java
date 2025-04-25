import java.util.ArrayList;

public class Pair {
    private ArrayList<Double> coordinates;
    private String name;

    public Pair(ArrayList<Double> coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    public Pair(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }
}
