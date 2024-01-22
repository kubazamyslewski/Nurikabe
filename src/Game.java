import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Game {
    static int size;
    private static int [][]board;
    public static void main(String[]args){
        whatToDo();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void whatToDo(){
        switch(GameInterface.whichBoard()){
            case 1:
                size = difficultyLevel();
                Generator generator = new Generator();
                board = generator.makeBoardToPlay(generator.generateBoard(size));
                new GameInterface(board);
                break;
            case 2:
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(JFrame.getFrames()[0]);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + filePath);
                    CSVFileReader reader = new CSVFileReader(filePath, ",");
                    reader.writeCSVFile("src/Pictures/UserBoards/MyBoard.csv", ",");
                    board = reader.intSwapper(reader.toString());
                    new GameInterface(board);
                }

                break;
            default: System.out.println("Źle wybrany numer");
        }
    }

    static int difficultyLevel(){
        switch(GameInterface.selectDifficulty()){
            case 1: return 4;
            case 2: return 6;
            case 3: return 8;
            default: System.out.println("Źle wybrany numer");
        }
        return 0;
    }


}