package de.seyfarth.sudokulve;

import javax.swing.*;
import de.seyfarth.sudokulve.exceptions.KeineLoesungException;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SudokuloeserGUI {

	JFrame frame;
	JPanel settings;
	JPanel sudoku;
	JButton sudokuLoesen;
	JTextField dim;
	JTextField anzBlockZeilenField;
	JTextField anzBlockSpaltenField;
	JLabel dimLabel;
	JLabel anzBlockZeilenLabel;
	JLabel anzBlockSpaltenLabel;
	JButton settingsBest;
	ArrayList<JTextField> felder;
	GridBagConstraints gbc;
	Matrix matrix;

	public SudokuloeserGUI() {

		createMatrix(9, 3, 3);
		felder = new ArrayList<JTextField>();
		gbc = new GridBagConstraints();
		frame = new JFrame("Sudoku L�ser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settings = new JPanel();
		settings.setLayout(new GridBagLayout());
		sudoku = new JPanel();
		sudoku.setLayout(new GridBagLayout());

		dim = new JTextField(3);
		dim.setText("9");
		anzBlockZeilenField = new JTextField(3);
		anzBlockZeilenField.setText("3");
		anzBlockSpaltenField = new JTextField(3);
		anzBlockSpaltenField.setText("3");
		dimLabel = new JLabel("Seitenl�nge");
		anzBlockZeilenLabel = new JLabel("Zeilen pro Block");
		anzBlockSpaltenLabel = new JLabel("Spalten pro Block");
		settingsBest = new JButton("Best�tigen");
		settingsBest.addActionListener(new settingsBestActionListener());
		dim.addActionListener(new settingsBestActionListener());
		anzBlockZeilenField.addActionListener(new settingsBestActionListener());
		anzBlockSpaltenField
				.addActionListener(new settingsBestActionListener());

		gbc.gridx = 0;
		gbc.gridy = 0;
		settings.add(dimLabel, gbc);
		gbc.gridx = 1;
		settings.add(anzBlockZeilenLabel, gbc);
		gbc.gridx = 2;
		settings.add(anzBlockSpaltenLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		settings.add(dim, gbc);
		gbc.gridx = 1;
		settings.add(anzBlockZeilenField, gbc);
		gbc.gridx = 2;
		settings.add(anzBlockSpaltenField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		settings.add(new JLabel("     "), gbc);
		gbc.gridy = 3;
		settings.add(settingsBest, gbc);
		gbc.gridwidth = 1;

		refreshSudokuPanel();
		frame.getContentPane().add(BorderLayout.NORTH, settings);
		frame.getContentPane().add(BorderLayout.CENTER, sudoku);
		ImageIcon icon = new ImageIcon("Sudoku.gif");
		Image image = icon.getImage();
		frame.setIconImage(image);
		frame.setSize(400, 400);
		frame.setVisible(true);

	}

	private void createMatrix(int dim, int anzBlockZeilen, int anzBlockSpalten) {
		try {
			matrix = new Matrix(dim, anzBlockZeilen, anzBlockSpalten);
		} catch (KeineLoesungException e) {
			e.printStackTrace();
		}
	}

	private void refreshSudokuPanel() {
		gbc.gridy = 0;
		gbc.gridx = 0;
		sudoku.removeAll();
		for (int y = 0; y < matrix.getDimension(); y++) {
			gbc.gridy = y;
			for (int x = 0; x < matrix.getDimension(); x++) {
				gbc.gridx = x;

				JTextField feld = new JTextField(2);
				felder.add(feld);
				sudoku.add(feld, gbc);
			}

		}
		sudokuLoesen = new JButton("Sudoku l�sen");
		sudokuLoesen.addActionListener(new SudokuLoesenActionListener());
		frame.getContentPane().add(BorderLayout.SOUTH, sudokuLoesen);
		sudoku.repaint();
		frame.validate();
	}

	private class settingsBestActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {

			int dimint = Integer.valueOf(dim.getText());
			int anzZint = Integer.valueOf(anzBlockZeilenField.getText());
			int anzSint = Integer.valueOf(anzBlockSpaltenField.getText());
			if (anzSint * anzZint != dimint) {
				return;
			}
			createMatrix(dimint, anzZint, anzSint);
			refreshSudokuPanel();
		}

	}

	private class SudokuLoesenActionListener implements ActionListener {

		// TODO
		@Override
		public void actionPerformed(ActionEvent ev) {
			int x;
			int y;
			int i;
			for (JTextField input : felder) {

				x = felder.indexOf(input);
				for (y = 0; x >= 0 && x <= matrix.getDimension() - 1; y++) {
					x = x - matrix.getDimension();
				}
				try {
						if (!(input.getText().equals(""))) {
							i = Integer.parseInt(input.getText());
							matrix.setValue(y + 1, x + 1, i);
						}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.exit(0);

				}

			}
		}
	}

}
