package Suduko;

import java.util.Arrays;

public class SudukoModel {
	int[][] matrix;
	SudukoSolver sudukoSolver;

	public SudukoModel(int[][] matrix) {
		this.matrix = matrix;
		sudukoSolver = new SudukoSolver(matrix);
	}

	public void setValue(int r, int c, int value) {
		sudukoSolver.setValue(r, c, value);
	}

	public int getValue(int r, int c) {
		return sudukoSolver.getValue(r, c);
	}

	public boolean solve() {
		return sudukoSolver.solve();
	}

	public void print() {
		sudukoSolver.print();
	}

	public void clear() {
		for (int[] array : matrix)
			Arrays.fill(array, 0);
	}
}
