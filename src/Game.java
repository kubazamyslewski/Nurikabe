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
                GameInterface GUI = new GameInterface(board);
                //giveBoard();
                break;
            case 2:
                CSVFileReader reader = new CSVFileReader("src/ExampleFile.csv", ",");
                board=intSwapper(reader.toString());
                //reader.writeCSVFile("src/ExampleFile.csv",",");
                GameInterface GUIi = new GameInterface(board);
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
        /*System.out.println("Wybierz poziom trudności:");
        System.out.println("1. Łatwy");
        System.out.println("2. Średni");
        System.out.println("3. Trudny");

        Scanner scanner = new Scanner(System.in);
        int temporary = scanner.nextInt();
        scanner.close();*/


        switch(GameInterface.selectDifficulty()){
            case 1: return 5;
            case 2: return 7;
            case 3: return 10;
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