package Suduko;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

public class SudukoController {
	private final int ROWS = 9;
	private final int COLS = 9;
	private JButton b1, b2;
	private JTextField tf;
	private JTextField[][] fields;
	private JPanel bPanel;
	private JPanel tfPanel;

	private SudukoSolver suduko;
	private int[][] matrix = new int[9][9];

	public SudukoController(SudukoSolver suduko) {
		SwingUtilities.invokeLater(() -> createWindow(suduko, "Suduko", 700, 700));
		this.suduko = suduko;
		fields = new JTextField[ROWS][COLS];
	}

	private void fillMatrix() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				matrix[row][col] = suduko.getValue(row, col);
			}
		}
	}

	private void createWindow(SudukoSolver suduko, String title, int width, int height) {

		fillMatrix();
		SudukoModel model = new SudukoModel(matrix);
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();

		bPanel = new JPanel();
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.LINE_AXIS));
		tfPanel = new JPanel();
		tfPanel.setLayout(new GridLayout(9, 9));

		// NumberFormat f = NumberFormat.getInstance();
		// f.setMaximumIntegerDigits(1);
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				tf = new JTextField();
				tf.setDocument(new JTextFieldLimit(2));
				tf.setFont(new Font("Verdana", Font.BOLD, 30));
				tf.setHorizontalAlignment(JTextField.CENTER);
				if (!(col > 2 && col < 6) && !(row > 2 && row < 6) || ((col > 2 && col < 6) && (row > 2 && row < 6))) {
					tf.setBackground(new Color(255, 102, 0));
				}
				fields[row][col] = tf;
				tfPanel.add(tf);
			}
		}

		b1 = new JButton("Solve");
		b1.addActionListener(event -> {
			boolean solveAllowed = true;
			model.clear();
			outerloop: for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					String value = fields[row][col].getText();
					if (!value.isEmpty()) {
						try {
							int val = Integer.parseInt(value);
							if (val > 0 && val < 10) {
								model.setValue(row, col, val);
							} else {
								JOptionPane.showMessageDialog(frame, "Only numbers 1-9 allowed");
								solveAllowed = false;
								break outerloop;
							}
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(frame, "Only numbers allowed");
							solveAllowed = false;
							break outerloop;

						}
					}
				}
			}
			if (solveAllowed) {
				if (model.solve()) {
					for (int row = 0; row < ROWS; row++) {
						for (int col = 0; col < COLS; col++) {
							fields[row][col].setText(Integer.toString(model.getValue(row, col)));
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Not solvable!");
				}
			}
		});

		b2 = new JButton("Clear");
		b2.addActionListener(event -> {
			model.clear();
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					fields[row][col].setText("");
				}
			}
		});

		bPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		bPanel.add(b1);
		bPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		bPanel.add(b2);

		pane.add(bPanel, BorderLayout.SOUTH);
		pane.add(tfPanel);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		int[][] matrix = new int[8][8];

		SudukoSolver solver = new SudukoSolver(matrix);

		new SudukoController(solver);

		// System.out.println(solver.solve());

		// solver.print();

	}
}
