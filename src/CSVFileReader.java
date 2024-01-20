import java.io.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {
    private List<int[]> rows = new ArrayList<>();

    public CSVFileReader(String filePath, String separator) {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(filePath));
            while (inFile.ready()) {
                String[] values = inFile.readLine().split(separator);
                int[] intValues = new int[values.length];

                for (int i = 0; i < values.length; i++) {
                    intValues[i] = Integer.parseInt(values[i]);
                }

                rows.add(intValues);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            // Handle the case where a value couldn't be parsed as an integer
            nfe.printStackTrace();
        }
    }
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
}