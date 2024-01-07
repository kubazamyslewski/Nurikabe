import java.io.*;

public class CSVFileReader {
    private String file = "src/ExampleFile.csv";
    CSVFileReader() {
        readFile();

    }

    void readFile(){
        BufferedReader reader = null;
        String line = "";

        try{
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){
                String []row = line.split(",");
            }
        }
        catch(Exception e){

        }
        finally{

        }
    }
}
