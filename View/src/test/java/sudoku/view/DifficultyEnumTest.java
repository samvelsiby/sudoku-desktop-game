package sudoku.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DifficultyEnum class
 * Checking that easy/medium/hard modes clear the right number of cells
 */
public class DifficultyEnumTest {

    private SudokuBoard board;

    @BeforeEach
    void setUp() {
        board = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            board.solveGame();
        } catch (Exception e) {
            fail("Failed to solve board: " + e.getMessage());
        }
    }

    // Helper method to count how many cells are empty (value = 0)
    private int countEmptyFields(SudokuBoard sudokuBoard) {
        int emptyCount = 0;
        for (int x = 0; x < SudokuBoard.BOARD_SIZE; x++) {
            for (int y = 0; y < SudokuBoard.BOARD_SIZE; y++) {
                if (sudokuBoard.getField(x, y).getValue() == 0) {
                    emptyCount++;
                }
            }
        }
        return emptyCount;
    }

    // Test that EASY mode clears 30 cells
    @Test
    void testEasyDifficultyClears30Fields() {
        // Given: A solved board with no empty fields
        assertEquals(0, countEmptyFields(board), "Board should be fully solved");

        // When: EASY difficulty is applied
        DifficultyEnum.EASY.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(board);

        // Then: Exactly 30 fields should be cleared
        assertEquals(30, countEmptyFields(board), "EASY should clear exactly 30 fields");
    }

    // Test that MEDIUM mode clears 50 cells
    @Test
    void testMediumDifficultyClears50Fields() {
        assertEquals(0, countEmptyFields(board), "Board should be fully solved");

        DifficultyEnum.MEDIUM.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(board);

        assertEquals(50, countEmptyFields(board), "MEDIUM should clear exactly 50 fields");
    }

    // Test that HARD mode clears 70 cells
    @Test
    void testHardDifficultyClears70Fields() {
        assertEquals(0, countEmptyFields(board), "Board should be fully solved");

        DifficultyEnum.HARD.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(board);

        assertEquals(70, countEmptyFields(board), "HARD should clear exactly 70 fields");
    }

    // Make sure the board is still valid after clearing cells
    // (no duplicates in rows/columns/boxes)
    @Test
    void testBoardRemainsValidAfterClearingFields() {
        DifficultyEnum.MEDIUM.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(board);

        // Check that filled cells don't violate Sudoku rules
        assertTrue(board.isValidSudoku(), "Board should remain valid after clearing fields");
    }

    // Test that each difficulty level clears the expected number of fields
    // Testing EASY - should clear 30 cells
    @Test
    void testEasyDifficultyParameterized() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            testBoard.solveGame();
        } catch (Exception e) {
            fail("Failed to solve board: " + e.getMessage());
        }

        DifficultyEnum.EASY.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(testBoard);

        assertEquals(30, countEmptyFields(testBoard),
            "EASY should clear exactly 30 fields");
    }

    // Testing MEDIUM - should clear 50 cells
    @Test
    void testMediumDifficultyParameterized() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            testBoard.solveGame();
        } catch (Exception e) {
            fail("Failed to solve board: " + e.getMessage());
        }

        DifficultyEnum.MEDIUM.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(testBoard);

        assertEquals(50, countEmptyFields(testBoard),
            "MEDIUM should clear exactly 50 fields");
    }

    // Testing HARD - should clear 70 cells
    @Test
    void testHardDifficultyParameterized() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            testBoard.solveGame();
        } catch (Exception e) {
            fail("Failed to solve board: " + e.getMessage());
        }

        DifficultyEnum.HARD.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(testBoard);

        assertEquals(70, countEmptyFields(testBoard),
            "HARD should clear exactly 70 fields");
    }

    // Check that the board is actually modified (not copied)
    @Test
    void testBoardIsModifiedInPlace() {
        int originalFieldCount = SudokuBoard.BOARD_SIZE * SudokuBoard.BOARD_SIZE;

        DifficultyEnum.EASY.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(board);

        int nonEmptyFields = originalFieldCount - countEmptyFields(board);
        assertEquals(81 - 30, nonEmptyFields, "Board should have 51 non-empty fields after EASY");
    }
}
