
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Generator {
    private int x;
    private int y;
    private int size;
    private int [][]board;
    private int [][] boardToPlay;

//    Generator(int size){
//        this.size=size;
//        x = generateNumber(size);
//        y = generateNumber(size);
//
//        //System.out.println(x);
//        //System.out.println(y);
//
//        //board=generateBoard(size);
//        board = new int[size][size];
//
//        boardToPlay = new int[size][size];
//
//
//
//    }

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
        int currentX = random.nextInt(size);
        int currentY = random.nextInt(size);
        int counter = 1;
        tempBoard[currentX][currentY] = -1;


        Stack<ArrayList<Integer>> checkpointDirections = new Stack<>();
        Stack<Integer> checkpointPlace = new Stack<>();
        ArrayList<Integer> directions;

        while(counter<0.7*size*size){
            int direction = random.nextInt(4);
            if (isInbounds(tempBoard, currentX, currentY) && !isItSquare(tempBoard,currentX,currentY)) {
                if (tempBoard[currentX][currentY] == 0) {
                    tempBoard[currentX][currentY] = -1;
                    counter++;
                }
            }

            directions = possibleDirection(tempBoard, currentX, currentY);


            if (directions.isEmpty() && !checkpointDirections.isEmpty()) {
                currentY = checkpointPlace.pop();
                currentX = checkpointPlace.pop();
                directions = checkpointDirections.removeLast();
            } else if (directions.isEmpty() && checkpointDirections.isEmpty()) {
                break;
            }

            direction = directions.get(random.nextInt(directions.size()));

            //if possible directions more than 1 then save as checkpoint
            if (directions.size() > 2) {
                directions.remove(directions.indexOf(direction));
                checkpointDirections.push(directions);
                checkpointPlace.push(currentX);
                checkpointPlace.push(currentY);
            }

            switch(direction){
                case 0:
                    currentX--;
                    break;
                case 1:
                    currentY++;
                    break;
                case 2:
                    currentX++;
                    break;
                case 3:
                    currentY--;
                    break;
            }
        }
        return tempBoard;
    }
    int[][] makeBoardToPlay(int[][] board){
        int n = board.length;
        int m = board[0].length;
        int iteration = 0;
        Cell current;
        int[][] tempBoard = new int[n][m];
        ArrayList<NumberCell> numberCells = new ArrayList<>();
        ArrayList<Cell> cells = new ArrayList<>();

        AnalyzeMap analyzeBoard = new AnalyzeMap();
        cells = (analyzeBoard.findWhiteLakes(board));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(!cells.isEmpty()){
                    current=cells.getFirst();
                    if(current.getR() == i && current.getC()==j){
                        tempBoard[i][j] = current.getSize();
                        cells.removeFirst();
                    }

                }

            }
        }
        return tempBoard;
    }

    boolean isItSquare(int [][]tempBoard, int x, int y){
        int size = tempBoard.length;
        if (!isInbounds(tempBoard,x, y)) {
            return true;
        }
        if (x!=0 && y!=0){
            if((tempBoard[x-1][y] == -1) && (tempBoard[x-1][y-1] == -1) && (tempBoard[x][y-1] == -1)){
                return true;
            }
        }
        if (x!=0 && y!=size-1){
            if((tempBoard[x-1][y] == -1) && (tempBoard[x-1][y+1] == -1) && (tempBoard[x][y+1] == -1)){
                return true;
            }
        }
        if (x!=size-1 && y!=size-1){
            if((tempBoard[x+1][y] == -1) && (tempBoard[x+1][y+1] ==- 1) && (tempBoard[x][y+1] == -1)){
                return true;
            }
        }
        if (x!=size-1 && y!=0){
            if((tempBoard[x+1][y] == -1) && (tempBoard[x+1][y-1] == -1) && (tempBoard[x][y-1] == -1)){
                return true;
            }
        }
        return false;
    }

    public int [][]getBoard(){
        return board;
    }

    ArrayList<Integer> possibleDirection(int[][] tempBoard, int r, int c) {
        ArrayList<Integer> possibleDirections = new ArrayList<>(4);
        if (!isItSquare(tempBoard, r - 1, c)) {
            if (tempBoard[r - 1][c] == 0) {
                possibleDirections.add(0);
            }

        }
        if (!isItSquare(tempBoard, r, c + 1)) {
            if (tempBoard[r][c + 1] == 0) {
                possibleDirections.add(1);
            }

        }
        if (!isItSquare(tempBoard, r + 1, c)) {
            if (tempBoard[r + 1][c] == 0) {
                possibleDirections.add(2);
            }

        }
        if (!isItSquare(tempBoard, r, c - 1)) {
            if (tempBoard[r][c - 1] == 0) {
                possibleDirections.add(3);
            }
        }
        return possibleDirections;
    }

    boolean isInbounds(int[][] board, int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
            return false;
        }
        return true;
    }
}
