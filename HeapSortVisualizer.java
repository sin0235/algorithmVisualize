package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HeapSortVisualizer extends JFrame {
	private JPanel treePanel;
	private JTextArea logArea;
	private JTextField inputField;
	private JLabel[] labels;
	private int[] array;
	private int arraySize;

	public HeapSortVisualizer() {
		setTitle("Algorithm Visualizer Application");
		setSize(900, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel controlPanel = new JPanel();
		inputField = new JTextField(20);
		JButton startButton = new JButton("Start");

		controlPanel.add(new JLabel("Nhập dữ liệu đầu vào (Phân cách bởi dấu phẩy): "));
		controlPanel.add(inputField);
		controlPanel.add(startButton);

		add(controlPanel, BorderLayout.NORTH);

		logArea = new JTextArea(5, 40);
		logArea.setEditable(false);
		JScrollPane logScroll = new JScrollPane(logArea);
		add(logScroll, BorderLayout.SOUTH);

		treePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (array != null) {
					drawTree(g, 0, getWidth() / 2, 50, getWidth() / 4);
				}
			}
		};
		treePanel.setPreferredSize(new Dimension(800, 500));
		add(treePanel, BorderLayout.CENTER);

		startButton.addActionListener(e -> onStart());

		setVisible(true);
	}

	private void onStart() {
		array = parseInput();
		if (array.length > 0) {
			arraySize = array.length;
			initializeArrayLabels();
			new Thread(this::heapSort).start();
		}
	}

	private int[] parseInput() {
		String input = inputField.getText();
		String[] tokens = input.split(",");
		int[] numbers = new int[tokens.length];
		try {
			for (int i = 0; i < tokens.length; i++) {
				numbers[i] = Integer.parseInt(tokens[i].trim());
			}
			return numbers;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Lỗi dữ liệu, vui lòng chỉ nhập số và phân cách bới dấu phẩy");
			return new int[0];
		}
	}

	private void initializeArrayLabels() {
		JPanel arrayPanel = new JPanel();
		arrayPanel.setLayout(new FlowLayout());
		labels = new JLabel[array.length];
		for (int i = 0; i < array.length; i++) {

			labels[i] = new JLabel(String.valueOf(array[i]));
			labels[i].setFont(new Font("Roboto", Font.BOLD, 18));
			labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			labels[i].setPreferredSize(new Dimension(40, 40));
			labels[i].setOpaque(true);
			labels[i].setBackground(Color.CYAN);
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			arrayPanel.add(labels[i]);
		}
		add(arrayPanel, BorderLayout.WEST);
		revalidate();
		repaint();
	}

	private void drawTree(Graphics g, int index, int x, int y, int xOffset) {
		if (index >= arraySize)
			return;

		g.setColor(Color.ORANGE);
		g.fillOval(x - 25, y - 25, 50, 50);
		g.setColor(Color.BLACK);
		g.drawOval(x - 25, y - 25, 50, 50);

		// Set font to Roboto, bold, size 20
		g.setFont(new Font("Roboto", Font.BOLD, 20));
		String text = String.valueOf(array[index]);
		FontMetrics fm = g.getFontMetrics();
		int textX = x - fm.stringWidth(text) / 2;
		int textY = y + fm.getAscent() / 2 - 2;
		g.drawString(text, textX, textY);

		if (2 * index + 1 < arraySize) {
			g.drawLine(x, y, x - xOffset, y + 60);
			drawTree(g, 2 * index + 1, x - xOffset, y + 60, xOffset / 2);
		}
		if (2 * index + 2 < arraySize) {
			g.drawLine(x, y, x + xOffset, y + 60);
			drawTree(g, 2 * index + 2, x + xOffset, y + 60, xOffset / 2);
		}
	}

	// HeapSort algorithm
	private void heapSort() {
		long startTime = System.currentTimeMillis();
		buildMaxHeap();
		for (int i = array.length - 1; i > 0; i--) {
			swap(0, i);
			arraySize--; // Reduce the size of the heap
			heapify(0);
			treePanel.repaint();
			updateArrayLabels();
			delay();
			labels[i].setBackground(Color.GREEN);
		}
		labels[0].setBackground(Color.GREEN);
		long endTime = System.currentTimeMillis();
		logArea.append("Thuật toán kết thúc sau " + (endTime - startTime) + " ms\n");
	}

	private void buildMaxHeap() {
		for (int i = array.length / 2 - 1; i >= 0; i--) {
			heapify(i);
			treePanel.repaint();
			updateArrayLabels();
			log("Heapifying tại " + i);
			delay();
		}
	}

	private void heapify(int i) {
		int largest = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < arraySize && array[left] > array[largest]) {
			largest = left;
		}

		if (right < arraySize && array[right] > array[largest]) {
			largest = right;
		}

		if (largest != i) {
			swap(i, largest);
			log("Đổi chỗ vị trí " + i + " và " + largest);
			heapify(largest);
		}
	}

	private void swap(int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;

		String tempText = labels[i].getText();
		labels[i].setText(labels[j].getText());
		labels[j].setText(tempText);

		labels[i].setBackground(Color.RED);
		labels[j].setBackground(Color.RED);

		treePanel.revalidate();
		treePanel.repaint();

		delay();
		labels[i].setBackground(Color.CYAN);
		labels[j].setBackground(Color.CYAN);

	}

	private void updateArrayLabels() {
		for (int i = 0; i < array.length; i++) {
			labels[i].setText(String.valueOf(array[i]));
		}
	}

	private void delay() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void log(String message) {
		logArea.append(message + "\n");
	}

	public static void excute() {
		SwingUtilities.invokeLater(HeapSortVisualizer::new);
	}
}
