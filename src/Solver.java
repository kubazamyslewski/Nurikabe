import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Solver {


    public int[][] solve(int[][] boardToSolve, int k, int kCount) {
        int[][] board = copyBoard(boardToSolve);
        int[][] bestNeighbour;
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
            boardToSolve = copyBoard(bestNeighbour);

        }

        while (neighbourSumState >= currentSumState && kCount <= k) {

            currentSumState = sumState(findBoardState(bestNeighbour));

            bestNeighbour = findBestBoard(findNeighbourBoards(bestNeighbour));
            neighbourSumState = sumState(findBoardState(bestNeighbour));
            boardToSolve = copyBoard(bestNeighbour);
            ++kCount;
        }
        if (kCount > k) {
            System.out.println("KCount too big ending ");
            return boardToSolve;
        }
        boardToSolve = solve(boardToSolve, k, 0);
        return boardToSolve;
    }


    int[][] findBestBoard(ArrayList<int[][]> boards) {
        int[][] possibleBest = boards.get(0);
        int currentBestSumState = sumState(findBoardState(possibleBest));
        int possibleBestSumState;
        int bestBoardIndex = 0;
        for (int i = 1; i < boards.size(); i++) {
            possibleBestSumState = sumState(findBoardState(boards.get(i)));
            if (possibleBestSumState < currentBestSumState) {
                currentBestSumState = possibleBestSumState;
                bestBoardIndex = i;
            }
        }

        return boards.get(bestBoardIndex);
    }


    public ArrayList<int[][]> findNeighbourBoards(int[][] board) {
        int n = board.length;
        int m = board[0].length;

        ArrayList<int[][]> boards = new ArrayList<>();

        ArrayList<Pair> blackCellsFromBoard = new ArrayList<>();
        ArrayList<Pair> whiteCellsFromBoard = new ArrayList<>();

        int index;
        int r = board.length - 1;
        int c = r;
        int rWhite=0;
        int cWhite=0;

        int whiteIndex;


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == -1) {
                    blackCellsFromBoard.add(new Pair(i, j));
                } else if (board[i][j] == 0) {
                    whiteCellsFromBoard.add(new Pair(i, j));
                }
            }
        }

        while (blackCellsFromBoard.isEmpty() || blackCellsFromBoard.size() < board.length - 1) {
            if (board[r][c] == 0) {
                board[r][c] = -1;
                blackCellsFromBoard.add(new Pair(r, c));
            }
            if (r <= c) {
                c--;
            } else {
                r--;
            }
        }

        index = ThreadLocalRandom.current().nextInt(blackCellsFromBoard.size());
        r = blackCellsFromBoard.get(index).getR();
        c = blackCellsFromBoard.get(index).getC();

        if (!whiteCellsFromBoard.isEmpty()) {
            whiteIndex = ThreadLocalRandom.current().nextInt(whiteCellsFromBoard.size());
            rWhite = whiteCellsFromBoard.get(whiteIndex).getR();
            cWhite = whiteCellsFromBoard.get(whiteIndex).getC();
        }


        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (isValid(board, r + i, c + j)) {
                    int[][] tempBoard = copyBoard(board);
                    if (board[r + i][c + j] == -1) {
                        tempBoard[r + i][c + j] = 0;
                        boards.add(tempBoard);

                    } else if (board[r + i][c + j] == 0) {
                        tempBoard[r + i][c + j] = -1;
                        boards.add(tempBoard);
                    }
                }
                if (isValid(board, rWhite + i, cWhite + j)) {
                    int[][] tempBoard = copyBoard(board);
                    if (board[rWhite + i][cWhite + j] == -1) {
                        tempBoard[rWhite + i][cWhite + j] = 0;
                        boards.add(tempBoard);

                    } else if (board[rWhite + i][cWhite + j] == 0) {
                        tempBoard[rWhite + i][cWhite + j] = -1;
                        boards.add(tempBoard);
                    }
                }
            }
        }
        return boards;
    }


    public int[] findBoardState(int[][] board) {
        int x; //ilosc jezior czaarnych
        int y; //roznica wielkosci jezior dla cyfr a wartości tych cyfr
        int z; //ilosc kwadratow
        int w; //rożnica (ilosc bialych i cyfrowych jezior) a numberIslands.size
        int a; //ilosc polaczonych cyfr ze soba
        int[] result = new int[5];
        ArrayList<NumberIsland> numberIslands;

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


    // dla liczenia wartości y
    public int diffNumberLakes(ArrayList<NumberIsland> numberIslands) {
        int difference = 0;
        NumberIsland currentNumberIsland;


        for (NumberIsland numberIsland : numberIslands) {
            currentNumberIsland = numberIsland;
            difference += Math.abs(currentNumberIsland.getNumber() - currentNumberIsland.getArea());
        }
        return difference;
    }

    public boolean isValid(int[][] board, int r, int c) {
        return r >= 0 && r < board.length && c >= 0 && c < board[0].length;
    }

    public int[][] copyBoard(int[][] toCopy) {
        int n = toCopy.length;
        int m = toCopy[0].length;
        int[][] newBoard = new int[n][m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(toCopy[i], 0, newBoard[i], 0, m);
        }
        return newBoard;
    }

}
