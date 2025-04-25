public class Pair {
    public int nr;
    public int value;
    public int size;
    public double density;

    public Pair(int value, int size, int nr) {
        this.value = value;
        this.size = size;
        this.nr = nr;
        this.density = (value + 0.0)/size;
    }

    @Override
    public String toString() {
        return "[" +
                "nr przedmiotu=" + nr +
                ", size=" + size +
                ", value=" + value +
                ']';
    }
}
