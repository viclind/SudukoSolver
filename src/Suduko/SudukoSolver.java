package Suduko;

import java.util.Arrays;

public class SudukoSolver {
	private int[][] matrix;

	/**
	 * Constructor for class SudukoSolver
	 * pre: matrix size is [9][9]
	 * throws: IllegalArgumentException if matrix is not size [9][9]
	 * @param matrix 2D array that represents a suduko board *
	 */
	public SudukoSolver(int[][] matrix) {
		if (matrix.length == 9 && matrix[1].length == 9){
			this.matrix = matrix;
		} else {
			throw new IllegalArgumentException("Matrix must be size [9][9]");
		}
	}

	/**
	 * Returns the int value of matrix in location row r and column c.
	 * 
	 * @param r row value in matrix
	 * @param c column value in matrix
	 * @return int value of matrix in row r and column c
	 */
	public int getValue(int r, int c) {
		return matrix[r][c];
	}

	/**
	 * Set int value of matrix in row r and column c
	 * pre: value between and including 1 and 9
	 * throws: IllegalArgumentException if value is not between and including 1 and 9
	 * @param r     row value
	 * @param c     column value
	 * @param value int value to be inserted
	 */
	public void setValue(int r, int c, int value) {
		if (value > 0 && value < 10) {
			matrix[r][c] = value;
		} else {
			throw new IllegalArgumentException("Value must be 1-9");
		}
	}

	/**
	 * Fills matrix according to rules of suduko. Returns true if possible otherwise
	 * returns false.
	 * 
	 * @return true if suduko is solved otherwise false
	 */
	public boolean solve() {
		int num;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				num = matrix[row][col];
				if (num != 0) {
					matrix[row][col] = 0;
					if (!isAllowed(row, col, num)) {
						return false;
					} else {
						matrix[row][col] = num;
					}
				}

			}
		}

		return solveSuduko(matrix);
	}

	private boolean solveSuduko(int[][] board) {
		int row = -1;
		int col = -1;
		boolean isFull = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == 0) {
					row = i;
					col = j;
					isFull = false;
					break;
				}
			}
			if (!isFull) {
				break;
			}
		}
		if (isFull) {
			return true;
		}
		for (int num = 1; num <= 9; num++) {
			if (isAllowed(row, col, num)) {
				board[row][col] = num;
				if (solveSuduko(board)) {
					return true;
				} else {
					board[row][col] = 0;
				}
			}
		}
		return false;
	}

	private boolean isAllowed(int r, int c, int num) {
		return !(existsInRow(r, num) || existsInCol(c, num) || existsInBox(r, c, num));
	}

	private boolean existsInRow(int r, int num) {
		for (int i = 0; i < 9; i++) {
			if (matrix[r][i] == num) {
				return true;
			}
		}
		return false;
	}

	private boolean existsInCol(int c, int num) {
		for (int i = 0; i < 9; i++) {
			if (matrix[i][c] == num) {
				return true;
			}
		}
		return false;
	}

	private boolean existsInBox(int r, int c, int num) {
		int row = r - r % 3;
		int col = c - c % 3;
		for (int i = row; i < row + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if (matrix[i][j] == num) {
					return true;
				}
			}
		}
		return false;
	}

	public void print() {
		for (int[] i : matrix) {
			System.out.println(Arrays.toString(i));
		}
	}

}
