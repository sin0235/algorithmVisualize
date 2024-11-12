package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
	public Menu() {
		setTitle("Algorithm Visualizer Application");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("Algorithm Visualizer", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
		add(titleLabel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		JButton sortButton = new JButton("Các thuật toán sắp xếp");
		JButton searchButton = new JButton("Các thuật toán tìm kiếm");

		sortButton.setFont(new Font("Roboto", Font.PLAIN, 18));
		searchButton.setFont(new Font("Roboto", Font.PLAIN, 18));

		sortButton.addActionListener(e -> openSortingAlgorithms());
		searchButton.addActionListener(e -> openSearchingAlgorithms());

		buttonPanel.add(sortButton);
		buttonPanel.add(searchButton);
		add(buttonPanel, BorderLayout.CENTER);

		JButton exitButton = new JButton("Thoát");
		exitButton.setFont(new Font("Roboto", Font.PLAIN, 18));
		exitButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Pái pai, hẹn gặp lại(>_<)");
			System.exit(0);
		});

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(exitButton);
		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void openSortingAlgorithms() {
		String[] sortingOptions = {
				"Đổi chỗ trực tiếp – Interchange Sort", "Chọn trực tiếp – Selection Sort",
				"Nổi bọt – Bubble Sort", "Chèn trực tiếp – Insertion Sort",
				"Chèn nhị phân – Binary Insertion Sort", "Shaker Sort",
				"Shell Sort", "Heap Sort", "Quick Sort", "Merge Sort", "Radix Sort"
		};

		String choice = (String) JOptionPane.showInputDialog(this, "Chọn thuật toán sắp xếp:",
				"Thuật toán sắp xếp", JOptionPane.QUESTION_MESSAGE, null, sortingOptions, sortingOptions[0]);

		if (choice != null) {
			executeSortAlgorithm(choice);
		}
	}

	private void executeSortAlgorithm(String algorithm) {
		switch (algorithm) {
			case "Đổi chỗ trực tiếp – Interchange Sort":
				InterchangeSortVisualizer.excute();
				break;
			case "Chọn trực tiếp – Selection Sort":
				SelectionSortVisualize.excute();
				break;
			case "Nổi bọt – Bubble Sort":
				BubbleSortVisualizer.excute();
				break;
			case "Chèn trực tiếp – Insertion Sort":
				InsertionSortVisualizer.excute();
				break;
			case "Chèn nhị phân – Binary Insertion Sort":
				BinaryInsertionSortVisualizer.excute();
				break;
			case "Shaker Sort":
				ShakeSortVisualizer.excute();
				break;
			case "Shell Sort":
				ShellSortVisualizer.excute();
				break;
			case "Heap Sort":
				HeapSortVisualizer.excute();
				break;
			case "Quick Sort":
				QuickSortVisualizer.excute();
				break;
			case "Merge Sort":
				MergeSortVisualizer.excute();
				break;
			case "Radix Sort":
				RadixSortVisualizer.excute();
				break;
			default:
				JOptionPane.showMessageDialog(this, "Lựa chọn không hợp lệ.");
		}
	}

	private void openSearchingAlgorithms() {
		String[] searchingOptions = { "Tìm kiếm tuyến tính", "Tìm kiếm nhị phân" };

		String choice = (String) JOptionPane.showInputDialog(this, "Chọn thuật toán tìm kiếm:",
				"Thuật toán tìm kiếm", JOptionPane.QUESTION_MESSAGE, null, searchingOptions, searchingOptions[0]);

		if (choice != null) {
			executeSearchAlgorithm(choice);
		}
	}

	private void executeSearchAlgorithm(String algorithm) {
		switch (algorithm) {
			case "Tìm kiếm tuyến tính":
				LinearSearchVisualize.excute();
				break;
			case "Tìm kiếm nhị phân":
				BinarySearchVisualize.excute();
				break;
			default:
				JOptionPane.showMessageDialog(this, "Lựa chọn không hợp lệ.");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Menu::new);
	}
}
