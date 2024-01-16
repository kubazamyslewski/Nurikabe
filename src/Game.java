import java.util.Scanner;

public class Game {
    static int size;
    private static int [][]board;

    private static int[][] playableBoard;
    public static void main(String[]args){

        whatToDo();
        //size = 5;
        //Generator generator = new Generator(size);
        //board = generator.generateBoard(size);
        giveBoard(size);




    }

    static void whatToDo(){
        System.out.println("Co chcesz zrobić?");
        System.out.println("1. Wygenerować planszę do gry");
        System.out.println("2. Zagrać na własnej planszy");

        Scanner scanner = new Scanner(System.in);
        int temp = scanner.nextInt();

        switch(temp){
            case 1:
                size = difficultyLevel();
                break;
            case 2:
                CSVFileReader reader = new CSVFileReader();
                break;
            default: System.out.println("Źle wybrany numer");
        }
    }

    static int difficultyLevel(){
        System.out.println("Wybierz poziom trudności:");
        System.out.println("1. Łatwy");
        System.out.println("2. Średni");
        System.out.println("3. Trudny");

        Scanner scanner = new Scanner(System.in);
        int temporary = scanner.nextInt();
        scanner.close();

        switch(temporary){
            case 1: return 5;
            case 2: return 7;
            case 3: return 10;
            default: System.out.println("Źle wybrany numer");
        }
        return 0;
    }

    static void giveBoard(int size){
        Generator generator = new Generator();
        board = generator.generateBoard(size);

        playableBoard = generator.makeBoardToPlay(board);

    }


}
