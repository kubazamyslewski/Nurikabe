import java.util.Scanner;

public class Game {
    static int size;
    public static void main(String[]args){
        size = difficultyLevel();
        Generator generator = new Generator(size);

    }

    static int difficultyLevel(){
        System.out.println("Wybierz poziom trudności:");
        System.out.println("1. Łatwy");
        System.out.println("2. Średni");
        System.out.println("3. Trudny");

        Scanner scanner = new Scanner(System.in);
        int temp = scanner.nextInt();
        scanner.close();

        switch(temp){
            case 1: return 5;
            case 2: return 7;
            case 3: return 10;
            default: System.out.println("Źle wybrany numer");
        }
        return 0;
    }
}
