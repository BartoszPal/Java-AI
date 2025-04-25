import java.io.*;
import java.util.ArrayList;

public class FileConverter {
    private String fileContent;
    private int numberOfElements;

    public FileConverter(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent += line + "\n";
                if (line.contains("length")) {
                    String[] splittedLine = line.split(",")[0].split(" ");
                    numberOfElements = Integer.parseInt(splittedLine[splittedLine.length - 1]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + path, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Plecak> getBackPacks() {
        ArrayList<Plecak> result = new ArrayList<>();
        ArrayList<Integer> sizes = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        int dataSet = -1;
        for (String line : fileContent.split("\n")) {
            if (line.contains("capacity")) {
                String[] splittedLine = line.split(" ");
                Plecak.setCapacity(Integer.parseInt(splittedLine[splittedLine.length - 1]));
            } else if (line.contains("dataset")) {
                dataSet = Integer.parseInt(line.replaceAll("dataset ", "").replaceAll(":", ""));
            } else if (line.contains("sizes")) {
                line = line.replaceAll("[sizes ={}]", "");
                int index = 0;
                for (String number : line.split(",")) {
                    if(index++ < numberOfElements)
                        sizes.add(Integer.parseInt(number));
                    else
                        break;
                }
            } else if (line.contains("vals")) {
                line = line.replaceAll("[vals ={}]", "");
                int index = 0;
                for (String number : line.split(",")) {
                    if(index++ < numberOfElements)
                        values.add(Integer.parseInt(number));
                    else
                        break;
                }
            }

            if (sizes.size() > 0 && values.size() > 0 && dataSet != -1) {
                Plecak plecak = new Plecak(dataSet, sizes, values);
                result.add(plecak);
                sizes = new ArrayList<>();
                values = new ArrayList<>();
                dataSet = -1;
            }
        }
        return result;
    }

    public int getNumberOfDataSets() {
        int maxDataset = 0;
        for (String line : fileContent.split("\n")) {
            if (line.contains("dataset ")) {
                maxDataset = Integer.parseInt(line.replaceAll("dataset ", "").replaceAll(":", ""));
            }
        }
        return maxDataset;
    }

}
