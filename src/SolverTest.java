import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    public void test_eazy_5boards() {
        for(int i=0; i<5; ++i) {
            Solver solver = new Solver();
            Generator gen= new Generator();
            int[][] boardToSolve =gen.makeBoardToPlay(gen.generateBoard(5));
            int[][] solvedBoard = solver.solve(boardToSolve, 500000, 0);
            Checker ch = new Checker();
            assertTrue(ch.IsCorrect(solvedBoard));
        }
    }
    @Test
    public void test_medium_5boards() {
        for(int i=0; i<5; ++i) {
            Solver solver = new Solver();
            Generator gen= new Generator();
            int[][] boardToSolve =gen.makeBoardToPlay(gen.generateBoard(7));
            int[][] solvedBoard = solver.solve(boardToSolve, 500000, 0);
            Checker ch = new Checker();
           assertTrue(ch.IsCorrect(solvedBoard));
        }
    }
    @Test
    public void test_hard_5boards() {
        for(int i=0; i<5; ++i) {
            Solver solver = new Solver();
            Generator gen= new Generator();
            int[][] boardToSolve =gen.makeBoardToPlay(gen.generateBoard(10));
            int[][] solvedBoard = solver.solve(boardToSolve, 500000, 0);
            Checker ch = new Checker();
            assertTrue(ch.IsCorrect(solvedBoard));
        }
    }

}