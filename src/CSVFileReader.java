import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {
    private List<String[]> rows = new ArrayList<>();

    public CSVFileReader(String filePath, String separator) {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(filePath));
            while (inFile.ready()) {
                rows.add(inFile.readLine().split(separator));
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    /**
     * Writes the CSV data into the file.
     * @param filePath of a file to write.
     * @param separator to be used in written CSV.
     */
    public void writeCSVFile(String filePath, String separator) {
        try {
            BufferedWriter outFile = new BufferedWriter(new FileWriter(filePath));
            for (int i=0;i< rows.size();i++){
                for (int j = 0; j< rows.get(i).length-1; j++){
                    outFile.write(rows.get(i)[j]+separator);
                }
                outFile.write(rows.get(i)[rows.get(i).length-1]);
                outFile.newLine();
            }
            outFile.close();
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * @return {@code String} created from array of tables storing CSV file. Separates the data with ", " and rows with "\n"
     */
    @Override
    public String toString() {
        String file = "";
        for (int i=0;i< rows.size();i++){
            for (int j = 0; j< rows.get(i).length-1; j++){
                file = file + rows.get(i)[j]+", ";
            }
            file = file + rows.get(i)[rows.get(i).length-1] + " \n";
        }
        return file;
    }

    public int[][] intSwapper (String inputString) {
        String[] rows = inputString.split("[\\n\\r]+");
        List<int[]> resultArrayList = new ArrayList<>();
        for (String row : rows) {
            String[] numbersStr = row.trim().split("[,\\s]+");
            List<Integer> rowList = new ArrayList<>();
            for (String numStr : numbersStr) {
                try {
                    int num = Integer.parseInt(numStr);
                    rowList.add(num);
                } catch (NumberFormatException e) {
                }
            }
            int[] rowArray = new int[rowList.size()];
            for (int i = 0; i < rowList.size(); i++) {
                rowArray[i] = rowList.get(i);
            }
            resultArrayList.add(rowArray);
        }
        int[][] resultArray = resultArrayList.toArray(new int[0][]);
        return resultArray;
    }

}