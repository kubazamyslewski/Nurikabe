import java.security.KeyPair;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {
    int board[][];
    /*
    -1 to czarne pole
    numerki to numerki
    0 to białe pole
     */
    Boolean IsCorrect(int board[][])
    {
        int n= board.length, m=board[0].length;
        int sumofwhite=0, sumofblack=0;
        // sprawdzenie czy jest odpowiednia ilosc zamalowanych pol
        for(int i=0; i<n; ++i)
        {
            for(int j=0; j<m; ++j)
            {
                if(board[i][j] == -1)
                    ++sumofblack;
                else
                    sumofwhite+=board[i][j];
            }
        }
        if(sumofblack !=(m * n )-sumofwhite ) return false;
        // sprawdzenie czy nie ma czarnych kwadratów
        for(int i=0; i<n-1; ++i)
        {
            for(int j=0; j<m-1; ++j)
            {
                if(board[i][j] == -1 && board[i+1][j] ==-1 && board[i][j+1]==-1 && board[i+1][j+1]==-1)
                    return false;
            }
        }
        //sprawdzanie czy czarna linia jest spójna
        for(int i=0; i<n; ++i)
        {
            for(int j=0; j<m; ++j)
            {
                if(board[i][j]==-1)
                {
                    boolean p1=false, p2=false, p3=false, p4=false;
                    if (i > 0 && board[i-1][j]==-1) p1=true;
                    if (j > 0 && board[i][j-1]==-1) p2=true;
                    if (i < n-1 && board[i+1][j]==-1) p3=true;
                    if (j < m-1 && board[i][j+1]==-1) p4=true;
                    if(!(p1||p2||p3||p4)) return false;
                }
            }
        }
        // sprawdzanie czy zgadzają się wielkości wysp
        Queue<Punkt> q =new LinkedList<>();
        for(int i=0; i<n; ++i)
        {
            for(int j=0; j<m; ++j)
            {
                if(board[i][j]>0)
                {
                    int req=board[i][j];
                    int wyn=0;
                    q.add(new Punkt(i, j));

                    while(!q.isEmpty())
                    {
                        Punkt p=q.remove();
                        board[p.first][p.second]=-2;
                        if(p.first>0)
                        {
                            if(board[p.first-1][p.second] > 0)
                                q.add(new Punkt(p.first-1, p.second));
                        }
                        if(p.second>0)
                        {
                            if(board[p.first][p.second-1] > 0)
                                q.add(new Punkt(p.first, p.second-1));
                        }
                        if(p.first<n-1)
                        {
                            if(board[p.first+1][p.second] > 0)
                                q.add(new Punkt(p.first+1, p.second));
                        }
                        if(p.second<m-1)
                        {
                            if(board[p.first][p.second+1] > 0)
                                q.add(new Punkt(p.first, p.second+1));
                        }

                    }
                    if(req != wyn) return false;
                }

            }
        }



    }

    class Punkt {
        int first;
        int second;
        public Punkt(int first, int second) {
            this.first = first;
            this.second = second;
        }


    }
}

