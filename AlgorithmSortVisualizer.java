package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


import java.awt.event.ActionEvent;

public abstract class AlgorithmSortVisualizer extends JFrame {
	protected JPanel panel;
	protected JTextField inputField;
	protected JButton startButton;
	protected JTextArea codeArea;
	protected JTextArea logArea;
	protected int[] array;
	protected JLabel[] labels;
	public int DELAY = 500;
	public Color originalColor = new Color(175, 238, 238);

	public AlgorithmSortVisualizer() {
		setTitle("Algorithm Visualizer Application");
		setSize(1000, 650);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 245, 245));

		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
		inputPanel.setBackground(new Color(240, 240, 240));

		JLabel inputLabel = new JLabel("Nhập dữ liệu đầu vào (cách nhau bởi dấu phẩy): ");
		inputLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
		inputField = createStyledTextField(30);

		startButton = createStyledButton("Start");

		inputPanel.add(inputLabel);
		inputPanel.add(inputField);
		inputPanel.add(startButton);
		add(inputPanel, BorderLayout.NORTH);

		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);

		JPanel infoPanel = new JPanel(new GridLayout(1, 2, 15, 0));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		codeArea = createTextArea("Code Area", Color.LIGHT_GRAY);
		logArea = createTextArea("Log Area", Color.WHITE);

		JScrollPane codeScroll = new JScrollPane(codeArea);
		JScrollPane logScroll = new JScrollPane(logArea);

		infoPanel.add(codeScroll);
		infoPanel.add(logScroll);
		add(infoPanel, BorderLayout.SOUTH);

		codeArea.setText(getCode());

		startButton.addActionListener(e -> onSearchAction());
	}

	private JTextArea createTextArea(String title, Color bgColor) {
		JTextArea area = new JTextArea(10, 30);
		area.setFont(new Font("Roboto", Font.PLAIN, 14));
		area.setEditable(false);
		area.setBackground(bgColor);
		area.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title));
		return area;
	}

	private JTextField createStyledTextField(int columns) {
		JTextField field = new JTextField(columns);
		field.setFont(new Font("Roboto", Font.PLAIN, 14));
		field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(100, 149, 237)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		return field;
	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Roboto", Font.BOLD, 14));
		button.setBackground(new Color(65, 105, 225));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(65, 105, 225)),
				BorderFactory.createEmptyBorder(8, 20, 8, 20)));
		return button;
	}

	protected JLabel createLabel(int value) {
		JLabel label = new JLabel(String.valueOf(value));
		label.setOpaque(true);
		label.setBackground(new Color(175, 238, 238));
		label.setPreferredSize(new Dimension(50, 50));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Roboto", Font.BOLD, 18));
		label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		return label;
	}

	protected void highlightLine(int lineNumber) {
		try {
			int start = codeArea.getLineStartOffset(lineNumber);
			int end = codeArea.getLineEndOffset(lineNumber);
			codeArea.select(start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void swap(int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;


		labels[i].setBackground(Color.ORANGE);
		labels[j].setBackground(Color.ORANGE);

		int steps = 20;
		int delay = 50;

		int startX = labels[i].getLocation().x;
		int startY = labels[i].getLocation().y;
		int endX = labels[j].getLocation().x;
		int endY = labels[j].getLocation().y;
		Dimension originalSize = labels[i].getSize();


		int maxScale = 10;

		for (int step = 0; step <= steps; step++) {
			// Calculate movement and scale changes
			int dx = (endX - startX) * step / steps;
			int dy = (endY - startY) * step / steps;

			int scale = maxScale - maxScale * step / steps;

			labels[i].setLocation(startX + dx, startY - dy);
			labels[j].setLocation(endX - dx, endY + dy);

			labels[i].setSize(originalSize.width + scale, originalSize.height + scale);
			labels[j].setSize(originalSize.width - scale, originalSize.height - scale);

			panel.revalidate();
			panel.repaint();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String tempText = labels[i].getText();
		labels[i].setText(labels[j].getText());
		labels[j].setText(tempText);
		labels[i].setLocation(endX, endY);
		labels[j].setLocation(startX, startY);
		labels[i].setSize(originalSize);
		labels[j].setSize(originalSize);
		labels[i].setBackground(originalColor);
		labels[j].setBackground(originalColor);
		panel.revalidate();
		panel.repaint();

	}


	protected void resetLabelColor(int i, int j) {
		labels[i].setBackground(new Color(175, 238, 238));
		labels[j].setBackground(new Color(175, 238, 238));
	}

	protected void delay() {
		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void onSearchAction() {
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
			JOptionPane.showMessageDialog(this, "Lỗi dữ liệu, vui lòng chỉ nhập số và phân cách bởi dấu phẩy");
		}
	}

	public abstract String getCode();

	public abstract void visualize();
}
