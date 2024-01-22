
import java.util.*;

public class Generator {

    private int [][]board;

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
//        int currentX = random.nextInt(size);
//        int currentY = random.nextInt(size);
        int currentX = 2;
        int currentY = 2;
        int counter = 1;
        tempBoard[currentX][currentY] = -1;

        //ArrayList<int[]> checkpointPlaces = new ArrayList<>();
        Queue<int[]> checkpointPlaces = new ArrayDeque<>();
        int[] checkpoint = new int[]{currentX, currentY};
        checkpointPlaces.add(checkpoint);
        ArrayList<Integer> possDirections = possibleDirection(tempBoard, currentX, currentY);
        int direction = 0;

        while(counter<0.7*size*size){
            possDirections = possibleDirection(tempBoard, currentX, currentY);

            while(possDirections.isEmpty() && !checkpointPlaces.isEmpty()){
                //checkpoint = checkpointPlaces.remove(random.nextInt(checkpointPlaces.size()));
                checkpoint = checkpointPlaces.poll();
                currentX = checkpoint[0];
                currentY = checkpoint[1];
                possDirections = possibleDirection(tempBoard, currentX, currentY);
            }
            if(!possDirections.isEmpty()){
                direction = possDirections.get(random.nextInt(possDirections.size()));
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
                tempBoard[currentX][currentY]=-1;
                //checkpointPlaces.offer((new int[]{currentX,currentY}));
                counter++;
            }else{
                break;
            }
            if(possDirections.size()>1){
                checkpointPlaces.offer((new int[]{currentX,currentY}));
            }


//            if (isInbounds(tempBoard, currentX, currentY) && !isItSquare(tempBoard,currentX,currentY)) {
//                if (tempBoard[currentX][currentY] == 0) {
//                    tempBoard[currentX][currentY] = -1;
//                    counter++;
//                    checkpoint = new int[]{currentX, currentY};
//                    checkpointPlaces.add(checkpoint);
//                }
//            }

//            switch(direction){
//                case 0:
//                    currentX--;
//                    break;
//                case 1:
//                    currentY++;
//                    break;
//                case 2:
//                    currentX++;
//                    break;
//                case 3:
//                    currentY--;
//                    break;
//            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(tempBoard[i][j]+" ");
            }
            System.out.println();
        }
        return tempBoard;
    }
    int[][] makeBoardToPlay(int[][] board){
        int n = board.length;
        int m = board[0].length;
        Cell current;
        int[][] tempBoard = new int[n][m];
        ArrayList<Cell> cells;

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
