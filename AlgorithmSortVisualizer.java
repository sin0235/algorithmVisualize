package algorithmVisualize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public abstract class AlgorithmSortVisualizer extends JFrame {
	protected JPanel panel;
	protected JTextField inputField;
	protected JButton startButton;
	protected JTextArea codeArea;
	protected JTextArea logArea;
	protected int[] array;
	protected JLabel[] labels;
	public int DELAY = 500;

	public AlgorithmSortVisualizer() {
		setTitle("Algorithm Visualizer Application");
		setSize(900, 600);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		inputField = new JTextField(30);
		startButton = new JButton("Start");
		inputPanel.add(new JLabel("Nhập dữ liệu đầu vào(Phân cách bởi dấu phẩy): "));
		inputPanel.add(inputField);
		inputPanel.add(startButton);
		add(inputPanel, BorderLayout.NORTH);

		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
		add(panel, BorderLayout.CENTER);

		JPanel infoPanel = new JPanel(new GridLayout(1, 2));
		codeArea = new JTextArea(10, 30);
		logArea = new JTextArea(10, 30);

		codeArea.setEditable(false);
		logArea.setEditable(false);

		JScrollPane codeScroll = new JScrollPane(codeArea);
		JScrollPane logScroll = new JScrollPane(logArea);

		infoPanel.add(codeScroll);
		infoPanel.add(logScroll);
		add(infoPanel, BorderLayout.SOUTH);

		codeArea.setText(getCode());

		startButton.addActionListener(e -> {
			String input = inputField.getText();
			String[] numbers = input.split(",");
			array = new int[numbers.length];
			labels = new JLabel[numbers.length];

			try {
				panel.removeAll();
				for (int i = 0; i < numbers.length; i++) {
					array[i] = Integer.parseInt(numbers[i].trim());
					labels[i] = createLabel(array[i]);
					panel.add(labels[i]);
				}
				panel.revalidate();
				panel.repaint();
				logArea.setText("");
				new Thread(this::visualize).start();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Lỗi dữ liệu, vui lòng chỉ nhập số và phân cách bới dấu phẩy");
			}
		});
	}

	protected JLabel createLabel(int value) {
		JLabel label = new JLabel(String.valueOf(value));
		label.setOpaque(true);
		label.setBackground(Color.CYAN);
		label.setPreferredSize(new Dimension(50, 50));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Roboto", Font.BOLD, 18));
		return label;
	}

	public abstract String getCode();

	public abstract void visualize();

	protected void highlightLine(int lineNumber) {
		try {
			int start = codeArea.getLineStartOffset(lineNumber);
			int end = codeArea.getLineEndOffset(lineNumber);
			codeArea.select(start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setLabelColor(int i, int j) {
		labels[i].setBackground(Color.YELLOW);
		labels[j].setBackground(Color.YELLOW);
	}

	protected void resetLabelColor(int i, int j, Color color) {
		labels[i].setBackground(color);
		labels[j].setBackground(color);
	}

	protected void swap(int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;

		String tempText = labels[i].getText();
		labels[i].setText(labels[j].getText());
		labels[j].setText(tempText);

		labels[i].setBackground(Color.RED);
		labels[j].setBackground(Color.RED);

		panel.revalidate();
		panel.repaint();

		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
