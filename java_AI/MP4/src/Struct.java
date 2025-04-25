import java.util.ArrayList;

public class Struct {
    private ArrayList<Double> coordinates;

    public Struct(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public void add(ArrayList<Double> listToAdd) {
        ArrayList<Double> newCoordinates = new ArrayList<>(coordinates);
        for (int i = 0; i < newCoordinates.size(); i++) {
            Double element = newCoordinates.get(i);
            Double elementToAdd = listToAdd.get(i);
            newCoordinates.set(i, element + elementToAdd);
        }
        this.coordinates = newCoordinates;
    }

    public void subtract(int numberOfElements) {
        ArrayList<Double> newCoordinates = new ArrayList<>(coordinates);
        for (int i = 0; i < newCoordinates.size(); i++) {
            Double element = newCoordinates.get(i);
            if (numberOfElements == 0)
                newCoordinates.set(i, 0.0);
            else
                newCoordinates.set(i, element / numberOfElements);
        }
        this.coordinates = newCoordinates;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }
}
