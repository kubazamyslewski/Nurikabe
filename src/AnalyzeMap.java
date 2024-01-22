import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AnalyzeMap {

    //bfs z porownaniem ==
    private int bfs(int[][] arr, int r, int c, int land) {
        if (r < 0 || r >= arr.length || c < 0 || c >= arr[0].length) {
            return 0;
        }
        int islandSize = 1;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{r, c});
        int n = arr.length;
        int m = arr[0].length;
        while (!q.isEmpty()) {
            int[] a = q.remove();
            int i = a[0];
            int j = a[1];
            arr[i][j] = -2;
            if (j + 1 < m && arr[i][j + 1] == land) {
                arr[i][j + 1] = -2;
                q.add(new int[]{i, j + 1});
                islandSize++;
            }

            if (j - 1 >= 0 && arr[i][j - 1] == land) {
                arr[i][j - 1] = -2;
                q.add(new int[]{i, j - 1});
                islandSize++;
            }

            if (i + 1 < n && arr[i + 1][j] == land) {
                arr[i + 1][j] = -2;
                q.add(new int[]{i + 1, j});
                islandSize++;
            }

            if (i - 1 >= 0 && arr[i - 1][j] == land) {
                arr[i - 1][j] = -2;
                q.add(new int[]{i - 1, j});
                islandSize++;
            }
        }
        return islandSize;
    }


    //bfs tylko z porownaniem >=
    private void bfsFindAllLakes(int[][] arr, int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{r, c});
        int n = arr.length;
        int m = arr[0].length;

        while (!q.isEmpty()) {
            int[] a = q.remove();
            int i = a[0];
            int j = a[1];
            arr[i][j] = -2;

            if (j + 1 < m && arr[i][j + 1] >= 0) {
                arr[i][j + 1] = -2;
                q.add(new int[]{i, j + 1});
            }

            if (j - 1 >= 0 && arr[i][j - 1] >= 0) {
                arr[i][j - 1] = -2;
                q.add(new int[]{i, j - 1});
            }

            if (i + 1 < n && arr[i + 1][j] >= 0) {
                arr[i + 1][j] = -2;
                q.add(new int[]{i + 1, j});
            }

            if (i - 1 >= 0 && arr[i - 1][j] >= 0) {
                arr[i - 1][j] = -2;
                q.add(new int[]{i - 1, j});
            }
        }
    }

    //bfs nie zmienia tablicy && >= land
    private int bfsFindNumberIslands(int[][] arr, int r, int c) {
        if (r < 0 || r >= arr.length || c < 0 || c >= arr[0].length || arr[r][c] < 0) {
            return 0;
        }

        int islandSize = 1;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c});
        int n = arr.length;
        int m = arr[0].length;
        boolean[][] visited = new boolean[n][m];

        while (!q.isEmpty()) {
            int[] a = q.poll();
            int i = a[0];
            int j = a[1];
            visited[i][j] = true;

            if (j + 1 < m && arr[i][j + 1] >= 0 && !visited[i][j + 1]) {
                visited[i][j + 1] = true;
                q.offer(new int[]{i, j + 1});
                islandSize++;
            }

            if (j - 1 >= 0 && arr[i][j - 1] >= 0 && !visited[i][j - 1]) {
                visited[i][j - 1] = true;
                q.offer(new int[]{i, j - 1});
                islandSize++;
            }

            if (i + 1 < n && arr[i + 1][j] >= 0 && !visited[i + 1][j]) {
                visited[i + 1][j] = true;
                q.offer(new int[]{i + 1, j});
                islandSize++;
            }

            if (i - 1 >= 0 && arr[i - 1][j] >= 0 && !visited[i - 1][j]) {
                visited[i - 1][j] = true;
                q.offer(new int[]{i - 1, j});
                islandSize++;
            }
        }
        return islandSize;
    }


    //bfs liczy ilsoc polaczonych cyfr >=land
    private int bfsCountConnectedNumbers(int[][] arr, int r, int c) {
        if (r < 0 || r >= arr.length || c < 0 || c >= arr[0].length || arr[r][c] < 0) {
            return 0;
        }
        int connectedNumbers = 1;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c});
        int n = arr.length;
        int m = arr[0].length;
        boolean[][] visited = new boolean[n][m];

        while (!q.isEmpty()) {
            int[] a = q.poll();
            int i = a[0];
            int j = a[1];
            visited[i][j] = true;

            if (j + 1 < m && arr[i][j + 1] >= 0 && !visited[i][j + 1]) {
                visited[i][j + 1] = true;
                q.offer(new int[]{i, j + 1});
                if(arr[i][j+1]>0){
                    connectedNumbers++;
                }
            }
            if (j - 1 >= 0 && arr[i][j - 1] >= 0 && !visited[i][j - 1]) {
                visited[i][j - 1] = true;
                q.offer(new int[]{i, j - 1});
                if(arr[i][j-1]>0){
                    connectedNumbers++;
                }
            }
            if (i + 1 < n && arr[i + 1][j] >= 0 && !visited[i + 1][j]) {
                visited[i + 1][j] = true;
                q.offer(new int[]{i + 1, j});
                if(arr[i+1][j]>0){
                    connectedNumbers++;
                }
            }
            if (i - 1 >= 0 && arr[i - 1][j] >= 0 && !visited[i - 1][j]) {
                visited[i - 1][j] = true;
                q.offer(new int[]{i - 1, j});
                if(arr[i-1][j]>0){
                    connectedNumbers++;
                }
            }
        }
        return connectedNumbers-1;
    }


    //bfs ze zmiana
    //szukanie land
    //solver szuka -1
    //wynik: wielkosc szukanej wyspy
    public int countIslands(int[][] board, int land) {
        int ans = 0;
        int[][] tempBoard = new int[board.length][board[0].length];
        int n = tempBoard.length;
        int m = tempBoard[0].length;

        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, m);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tempBoard[i][j] == land) {
                    ans++;
                    bfs(tempBoard, i, j, land);
                }
            }
        }
        return ans;
    }

    //bfs bez zmiany tablicy szukanie 0
    //wynik: lista NumberIsland(row, col, numer, wielkosc wyspy)
    public ArrayList<NumberIsland> findNumberIslands(int[][] board) {
        int ans;
        int n = board.length;
        int m = board[0].length;
        ArrayList<NumberIsland> numberIslands = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] > 0) {
                    ans = bfsFindNumberIslands(board, i, j);
                    numberIslands.add(new NumberIsland(i, j, board[i][j], ans));
                }
            }
        }
        return numberIslands;
    }

    //bfs zmiana tablicy, szukanie >=0
    //wynik: wsyzstkie białe wyspy, nawet z przejsciem przez numery
    public int findAllLakes(int[][] board) {
        int ans = 0;
        int n = board.length;
        int m = board[0].length;
        int[][] tempBoard = new int[n][m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, m);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tempBoard[i][j] >= 0) {
                    ans++;
                    bfsFindAllLakes(tempBoard, i, j);
                }
            }
        }
        return ans;
    }

    //bfs ze zmiana tablicy szukanie 0
    //generator tego uzywa
    public ArrayList<Cell> findWhiteLakes(int[][] board) {
        int n = board.length;
        int m = board[0].length;
        ArrayList<Cell> whiteCells = new ArrayList<>();

        int[][] tempBoard = new int[n][m];

        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, m);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tempBoard[i][j] == 0) {
                    whiteCells.add(new Cell(i, j, bfs(tempBoard, i, j, 0)));
                }
            }
        }
        return whiteCells;
    }

    public int countBlackSquares(int[][] board) {
        int ans = 0;
        int n = board.length;
        int m = board[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (has2x2Cube(board, i, j)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public boolean has2x2Cube(int[][] tempBoard, int r, int c) {
        if (r + 1 < tempBoard.length && c + 1 < tempBoard.length) {
            return tempBoard[r][c] == -1 && tempBoard[r][c + 1] == -1 && tempBoard[r + 1][c + 1] == -1 && tempBoard[r + 1][c] == -1;
        }
        return false;
    }

    public int findConnectedNumbers(int[][] board) {
        int ans = 0;
        int n = board.length;
        int m = board[0].length;
        int[][] tempBoard = new int[n][m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, m);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tempBoard[i][j] > 0) {
                    ans += bfsCountConnectedNumbers(tempBoard, i, j);
                }
            }
        }
        return ans;
    }
}
