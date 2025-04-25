public class Iris {
    private final double coordinate1;
    private final double coordinate2;
    private final double coordinate3;
    private final double coordinate4;
    private final String name;

    public Iris(double coordinate1, double coordinate2, double coordinate3, double coordinate4, String name) {
        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;
        this.coordinate3 = coordinate3;
        this.coordinate4 = coordinate4;
        this.name = name;
    }

    public double getCoordinate1() {
        return coordinate1;
    }

    public double getCoordinate2() {
        return coordinate2;
    }

    public double getCoordinate3() {
        return coordinate3;
    }

    public double getCoordinate4() {
        return coordinate4;
    }

    public double distance(Iris iris){
        return Math.sqrt(Math.pow(iris.coordinate1 - this.coordinate1, 2) +
                Math.pow(iris.coordinate2 - this.coordinate2, 2) +
                Math.pow(iris.coordinate3 - this.coordinate3, 2) +
                Math.pow(iris.coordinate4 - this.coordinate4, 2));
    }

    @Override
    public String toString() {
        return "Iris{" +
                "coordinate1=" + coordinate1 +
                ", coordinate2=" + coordinate2 +
                ", coordinate3=" + coordinate3 +
                ", coordinate4=" + coordinate4 +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
