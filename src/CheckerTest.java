import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    @Test
    public void kilkatestow() {
        Checker ch = new Checker();
        int[][] validBoard1 = {
                {1, -1, -1},
                {-1, -1, 2},
                {1, -1, 0}
        };
        assertTrue(ch.IsCorrect(validBoard1));
        int[][] validBoard2 ={
                {9, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        assertTrue(ch.IsCorrect(validBoard2));
        int[][] validBoard3 ={
                {3, 0, 0},
                {-1, -1, -1},
                {-1, 1, -1}
        };
        assertTrue(ch.IsCorrect(validBoard3));
        int[][] invalidBoard ={
                {-1, -1, -1},
                {-1, 1, -1},
                {-1, -1, 0}
        };
        assertFalse(ch.IsCorrect(invalidBoard));
    }
    @Test
    public void generowanetesty(){
        Checker ch = new Checker();
        Solver solver = new Solver();
        Generator gen = new Generator();
        for(int i=0; i<5; ++i)
            assertTrue(ch.IsCorrect(solver.solve(gen.makeBoardToPlay(gen.generateBoard(4)), 500000, 0)));
        for(int i=0; i<5; ++i)
            assertTrue(ch.IsCorrect(solver.solve(gen.makeBoardToPlay(gen.generateBoard(6)), 500000, 0)));
        for(int i=0; i<5; ++i)
            assertTrue(ch.IsCorrect(solver.solve(gen.makeBoardToPlay(gen.generateBoard(8)), 500000, 0)));


    }
}