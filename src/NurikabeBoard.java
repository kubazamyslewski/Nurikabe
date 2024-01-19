import java.util.ArrayList;

public class NurikabeBoard {
    private int[][] board;
    private ArrayList<Pair> blackCells;
    private ArrayList<Pair> whiteCells;

    NurikabeBoard(int[][] board, ArrayList<Pair> blackCells, ArrayList<Pair> whiteCells) {
        this.board = board;
        this.blackCells = blackCells;
        this.whiteCells = whiteCells;
    }

    NurikabeBoard(int[][] board, ArrayList<Pair> blackCells) {
        this.board = board;
        blackCells = new ArrayList<>();
    }

    public int[][] getBoard() {
        return board;
    }

    public ArrayList<Pair> getBlackCells() {
        return blackCells;
    }

    public ArrayList<Pair> getWhiteCells() {
        return whiteCells;
    }

    public void addWhiteCell(Pair pair){
        this.whiteCells.add(pair);
    }

    public void removeWhiteCell(Pair pair){
        this.whiteCells.remove(pair);
    }

    public void addBlackCells(Pair pair) {
        this.blackCells.add(pair);
    }

    public void removeBlackCell(Pair pair) {
        this.blackCells.remove(pair);
    }

}