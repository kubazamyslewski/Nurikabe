import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                }
                new GameInterface(board);
                break;
            default: System.out.println("Źle wybrany numer");
        }
    }
    static int[][] intSwapper (String inputString) {
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
    static int difficultyLevel(){
        switch(GameInterface.selectDifficulty()){
            case 1: return 4;
            case 2: return 6;
            case 3: return 8;
            default: System.out.println("Źle wybrany numer");
        }
        return 0;
    }

    static void giveBoard(){
        Generator generator = new Generator();
        int[][] playableBoard = generator.makeBoardToPlay(generator.generateBoard(size));
        board = playableBoard;
    }

}