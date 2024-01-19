import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Solver {


    public int[][] solve(int[][] boardToSolve, int k, int kCount) {
        int[][] board = copyBoard(boardToSolve);
        int[][] bestNeighbour;
        int[] win = {1, 0, 0, 0};
        int[] boardState = findBoardState(board);
        int currentSumState = sumState(boardState);
        int neighbourSumState;

        if (kCount > k) {
            System.out.println("KCount too big");
            return boardToSolve;
        }
        if (boardState[0] == 1 && boardState[1] == 0 && boardState[2] == 0 && boardState[3] == 0 && boardState[4] == 0) {
            System.out.println("ESSAAAAAA");
            return boardToSolve;
        }

        bestNeighbour = findBestBoard(findNeighbourBoards(board));
        neighbourSumState = sumState(findBoardState(bestNeighbour));


        if (neighbourSumState < currentSumState) {
            //System.out.println("changing current");
            boardToSolve = copyBoard(bestNeighbour);
            //boardToSolve = solve(boardToSolve, k, 0);

        }

        while (neighbourSumState >= currentSumState && kCount <= k) {
            //System.out.println("while loop " + kCount);
            if (neighbourSumState < currentSumState) {
                currentSumState = sumState(findBoardState(bestNeighbour));
            }
            bestNeighbour = findBestBoard(findNeighbourBoards(bestNeighbour));
            neighbourSumState = sumState(findBoardState(bestNeighbour));
            boardToSolve = copyBoard(bestNeighbour);

            ++kCount;
            //System.out.println(currentSumState+" current State");
            //System.out.println(neighbourSumState + " neihgbour state");
//            System.out.println(kCount + "kcount");
        }
        if (kCount > k) {
            System.out.println("KCount too big ending ");
            return boardToSolve;
        }

        boardToSolve = solve(boardToSolve, k, 0);

        return boardToSolve;
    }

    //int[][] findBestBoard(ArrayList<NurikabeBoard> boards) {
    int[][] findBestBoard(ArrayList<int[][]> boards) {
        int[][] bestNeighbour;
        int[][] possibleBest = boards.get(0);//.getBoard();
        int currentBestSumState = sumState(findBoardState(possibleBest));
        int possibleBestSumState;
        int bestBoardIndex = 0;
        for (int i = 1; i < boards.size(); i++) {
            //possibleBestSumState = sumState(findBoardState(boards.get(i).getBoard()));
            possibleBestSumState = sumState(findBoardState(boards.get(i)));
            if (possibleBestSumState < currentBestSumState) {
                currentBestSumState = possibleBestSumState;
                bestBoardIndex = i;
            }
        }

        return boards.get(bestBoardIndex);
    }


    // public ArrayList<NurikabeBoard> findNeighbourBoards(int[][] board) {
    public ArrayList<int[][]> findNeighbourBoards(int[][] board) {
        //Random random = new Random();
        int n = board.length;
        int m = board[0].length;
        ArrayList<int[][]> neighbourBoards = new ArrayList<>();
        //ArrayList<NurikabeBoard> tempNurikabeboard = new ArrayList<>();

        ArrayList<Pair> blackCells;
        ArrayList<Pair> blackCellsFromBoard = new ArrayList<>();
        ArrayList<Pair> whiteCellsFromBoard = new ArrayList<>();

        int index;
        int r = board.length - 1;
        int c = r;


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == -1) {
                    blackCellsFromBoard.add(new Pair(i, j));
                } else if (board[i][j] == 0) {
                    whiteCellsFromBoard.add(new Pair(i, j));
                }
            }
        }

        //nie zadziala caly solver jesli to beda zera i jesli wczesniej nie bylo ani jednej -1
        while (blackCellsFromBoard.isEmpty()) {
            if (board[r][c] == 0) {
                board[r][c] = -1;
                //blackCells.add(new Pair(r, c));
                blackCellsFromBoard.add(new Pair(r, c));
                //nurikabeBoard.addBlackCells(new Pair(r,c));
            }
            r--;
            c--;
        }

        //NurikabeBoard nurikabeBoard = new NurikabeBoard(board, blackCellsFromBoard);

        Pair blackCell;

        index = ThreadLocalRandom.current().nextInt(blackCellsFromBoard.size());
        r = blackCellsFromBoard.get(index).getR();
        c = blackCellsFromBoard.get(index).getC();

        int whiteIndex = ThreadLocalRandom.current().nextInt(whiteCellsFromBoard.size());
        int rWhite = whiteCellsFromBoard.get(whiteIndex).getR();
        int cWhite = whiteCellsFromBoard.get(whiteIndex).getC();


        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (isValid(board, r + i, c + j)) {
                    int[][] tempBoard = copyBoard(board);
                    blackCells = new ArrayList<>(blackCellsFromBoard);
                    blackCell = new Pair(r + i, c + j);

                    if (board[r + i][c + j] == -1) {
                        tempBoard[r + i][c + j] = 0;
                        blackCells.remove(blackCell);
                        neighbourBoards.add(tempBoard);
                        //tempNurikabeboard.add(new NurikabeBoard(tempBoard, blackCells));

                    } else if (board[r + i][c + j] == 0) {
                        blackCells.add(blackCell);
                        //whiteCells.remove(whiteCell);
                        tempBoard[r + i][c + j] = -1;
                        neighbourBoards.add(tempBoard);
                        //tempNurikabeboard.add(new NurikabeBoard(tempBoard, blackCells));
                    }

                }
                if (isValid(board, rWhite + i, cWhite + j)) {
                    int[][] tempBoard = copyBoard(board);
                    blackCells = new ArrayList<>(blackCellsFromBoard);
                    blackCell = new Pair(rWhite + i, cWhite + j);
                    if (board[rWhite + i][cWhite + j] == -1) {
                        tempBoard[rWhite + i][cWhite + j] = 0;
                        blackCells.remove(blackCell);
                        neighbourBoards.add(tempBoard);
                        //tempNurikabeboard.add(new NurikabeBoard(tempBoard, blackCells));

                    } else if (board[rWhite + i][cWhite + j] == 0) {
                        blackCells.add(blackCell);
                        tempBoard[rWhite + i][cWhite + j] = -1;
                        neighbourBoards.add(tempBoard);
                        //tempNurikabeboard.add(new NurikabeBoard(tempBoard, blackCells));
                    }

                }
            }
        }
        return neighbourBoards;
        //return tempNurikabeboard;
    }


    public int[] findBoardState(int[][] board) {
        int x = 0; //ilosc jezior czaarnych
        int y = 0; //roznica wielkosci jezior dla cyfr a wartości tych cyfr
        int z = 0; //ilosc kwadratow
        int w = 0; //rożnica (ilosc bialych i cyfrowych jezior) a numberIslands.size
        int a = 0; //ilosc polaczonych cyfr ze soba
        int[] result = new int[5];
        ArrayList<NumberIsland> numberIslands = new ArrayList<>();
        ArrayList<Integer> blackIslands;

        AnalyzeMap analyzeMap = new AnalyzeMap();
        numberIslands = analyzeMap.findNumberIslands(board);

        x = analyzeMap.countIslands(board, -1);
        y = diffNumberLakes(numberIslands);
        z = analyzeMap.countBlackSquares(board);
        w = Math.abs(analyzeMap.findAllLakes(board) - numberIslands.size());
        a = analyzeMap.findConnectedNumbers(board);

        result[0] = x;
        result[1] = y;
        result[2] = z;
        result[3] = w;
        result[4] = a;
        return result;
    }

    int sumState(int[] state) {
        return state[0] + state[1] + state[2] + state[3] + state[4];
    }


    // dla liczenia y
    public int diffNumberLakes(ArrayList<NumberIsland> numberIslands) {
        int difference = 0;
        NumberIsland currentNumberIsland;


        for (int i = 0; i < numberIslands.size(); i++) {
            currentNumberIsland = numberIslands.get(i);
            difference += Math.abs(currentNumberIsland.getNumber() - currentNumberIsland.getArea());
        }
        return difference;
    }

    public boolean isValid(int[][] board, int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
            return false;
        }
        return true;
    }


    public int[][] copyBoard(int[][] toCopy) {
        int n = toCopy.length;
        int m = toCopy[0].length;
        int[][] newBoard = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newBoard[i][j] = toCopy[i][j];
            }
        }
        return newBoard;
    }

}

