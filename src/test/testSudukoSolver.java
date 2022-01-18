package test;

import static org.junit.jupiter.api.assertions.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Suduko.SudukoSolver;

class testSudukoSolver {
	int[][] matrix = 
		{{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},};
	static SudukoSolver solver;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	void emptySoduko() {
		solver = new SudukoSolver(matrix);
		assertTrue(solver.solve(), "Empty Suduko was not solved");
	}
	
	@Test 
	void solvableSoduko() {
		matrix[0][2] = 8;
		matrix[0][5] = 9;
		matrix[0][7] = 6;
		matrix[0][8] = 2;
		matrix[1][8] = 5;
		matrix[2][0] = 1;
		matrix[2][2] = 2;
		matrix[2][3] = 5;
		matrix[3][3] = 2;
		matrix[3][4] = 1;
		matrix[3][7] = 9;
		matrix[4][1] = 5;
		matrix[4][6] = 6;
		matrix[5][0] = 6;
		matrix[5][7] = 2;
		matrix[5][8] = 8;
		matrix[6][0] = 4;
		matrix[6][1] = 1;
		matrix[6][3] = 6;
		matrix[6][5] = 8;
		matrix[7][0] = 8;
		matrix[7][1] = 6;
		matrix[7][4] = 3;
		matrix[7][6] = 1;
		matrix[8][6] = 4;
		int[][] correctResult = 
				{{5,4,8,1,7,9,3,6,2},
				{3,7,6,8,2,4,9,1,5},
				{1,9,2,5,6,3,8,7,4},
				{7,8,4,2,1,6,5,9,3},
				{2,5,9,3,8,7,6,4,1},
				{6,3,1,9,4,5,7,2,8},
				{4,1,5,6,9,8,2,3,7},
				{8,6,7,4,3,2,1,5,9},
				{9,2,3,7,5,1,4,8,6},};
		solver = new SudukoSolver(matrix);
		solver.solve();
		int[][] resultFromSolver = new int[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				resultFromSolver[i][j] = solver.getValue(i, j);
			}
		}
		solver.print();
		assertTrue(matrixCompare(resultFromSolver, correctResult));
	}
	
	private boolean matrixCompare(int[][] a, int[][] b) {
		if (a.length != b.length || a[0].length != b[0].length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Test
	void unsolvableSudukoSameRow() {
		matrix[0][1] = 5;
		matrix[0][5] = 5;
		solver = new SudukoSolver(matrix);
		assertFalse(solver.solve(), "Solver solved unsolvable suduko, with duplicates in same row");
	}
	
	@Test
	void unsolvableSudukoSameSquare() {
		matrix[0][1] = 5;
		matrix[1][0] = 5;
		solver = new SudukoSolver(matrix);
		assertFalse(solver.solve(), "Solver solved unsolvable suduko, with dublicates in same square");
	}
	
	@Test
	void unsolvableSudukoSameCol() {
		matrix[7][7] = 6;
		matrix[1][7] = 6;
		solver = new SudukoSolver(matrix);
		assertFalse(solver.solve(), "Solver solved unsolvable suduko, with duplicates in same column");
	}

}
