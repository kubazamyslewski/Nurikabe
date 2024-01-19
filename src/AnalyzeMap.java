import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AnalyzeMap {
    /**
     * Stores sizes of all islands (number of "1" each island consists of)
     */
    private final ArrayList<Integer> islandSizes;

    private ArrayList<Integer> indexes;
    private int[][] board;

    public AnalyzeMap(int[][] board) {
        this.islandSizes = new ArrayList<>();
        this.board = board;
        this.indexes = new ArrayList<>();
    }

    public ArrayList<Integer> getIslandSizes() {
        return islandSizes;
    }

    /**
     * Implementation of Breadth First Search algorithm, traversing through the given array of enum constants
     * @param arr array to count islands in
     * @param r index of a Chunks[] inside a Chunks[][]
     * @param c index of element in the 2D array
     * @return size of the currently traversing island
     */
    private int bfs(int[][] arr, int r, int c) {
        int islandSize = 1;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{r, c});
        int n = arr.length;
        int m = arr[0].length;

        while (!q.isEmpty()) {
            int[] a = q.remove();
            int i = a[0];
            int j = a[1];
            arr[i][j] = 1;

            if (j + 1 < m && arr[i][j + 1] == 0) {
                arr[i][j + 1] = 1;
                q.add(new int[]{i, j + 1});
                islandSize++;
            }

            if (j - 1 >= 0 && arr[i][j - 1] == 0) {
                arr[i][j - 1] = 1;
                q.add(new int[]{i, j - 1});
                islandSize++;
            }

            if (i + 1 < n && arr[i + 1][j] == 0) {
                arr[i + 1][j] = 1;
                q.add(new int[]{i + 1, j});
                islandSize++;
            }

            if (i - 1 >= 0 && arr[i - 1][j] == 0) {
                arr[i - 1][j] = 1;
                q.add(new int[]{i - 1, j});
                islandSize++;
            }
        }
        return islandSize;
    }

    public int countIslands() {
        int ans = 0;
        int n = board.length;
        int m = board[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 0) {
                    ans++;
                    islandSizes.add(bfs(board, i,j));
                    indexes.add(i);
                    indexes.add(j);
                }
            }
        }
        return ans;
    }

    public ArrayList<Integer> getIndexes() { return indexes; } //  getIndexes
}
