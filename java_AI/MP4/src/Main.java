public class Main {
    public static void main(String[] args) throws Exception {
        KMeans kMeans = new KMeans(5, "src/iris_training.txt");
        kMeans.calculate();
        kMeans.showStats();
    }
}
