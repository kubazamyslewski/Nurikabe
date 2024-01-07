import java.util.Random;

public class Generator {
    private int x;
    private int y;
    private int size;
    private static int [][]board;

    Generator(int size){
        this.size=size;
        x = generateNumber(size);
        y = generateNumber(size);

        //System.out.println(x);
        //System.out.println(y);

        board=generateBoard(size);
        /*for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }*/

    }

    int generateNumber(int s){
        Random random = new Random();
        return random.nextInt(s);
    }

    int [][] generateBoard(int size) {
        int [][]tempBoard = new int [size][size];

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                tempBoard[i][j]=0;
            }
        }

        Random random = new Random();

        int counter = 1;
        tempBoard[x][y] = 1;
        int currentX = x;
        int currentY = y;

        while(counter<0.6*size*size){
            int direction = random.nextInt(4);

            switch(direction){
                case 0:
                    if(currentX!=0){
                        currentX--;
                    }
                    break;
                case 1:
                    if(currentX!=size-1){
                        currentX++;
                    }
                    break;
                case 2:
                    if(currentY!=0){
                        currentY--;
                    }
                    break;
                case 3:
                    if(currentY!=size-1){
                        currentY++;
                    }
                    break;
            }

            if(tempBoard[currentX][currentY]==0){
                counter++;
                tempBoard[currentX][currentY]=1;
            }
        }


        return tempBoard;
    }

    public static int [][]getBoard(){
        return board;
    }
}
