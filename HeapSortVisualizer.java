package algorithmVisualize;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;

public class HeapSortVisualizer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = new Color(240, 245, 255);
	private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
	private static final Color ACCENT_COLOR = new Color(52, 152, 219);
	private static final Color NODE_COLOR = new Color(255, 255, 255);
	private static final Color NODE_BORDER = new Color(41, 128, 185);
	private static final Color LINE_COLOR = new Color(189, 195, 199);
	private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
	private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font NODE_FONT = new Font("Segoe UI", Font.BOLD, 16);

	private JPanel treePanel;
	private JTextArea logArea;
	private JTextField inputField;
	private JLabel[] labels;
	private int[] array;
	private int arraySize;

	public HeapSortVisualizer() {
		setTitle("Algorithm Visualization");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel mainContainer = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_COLOR, getWidth(), getHeight(),
						new Color(255, 255, 255));
				g2d.setPaint(gradient);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		mainContainer.setLayout(new BorderLayout(20, 20));
		mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel headerPanel = createHeaderPanel();
		mainContainer.add(headerPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
		centerPanel.setOpaque(false);

		treePanel = createTreePanel();

		centerPanel.add(treePanel, BorderLayout.CENTER);

		JPanel arrayContainer = new JPanel(new BorderLayout(10, 20));
		arrayContainer.setOpaque(false);
		arrayContainer.setPreferredSize(new Dimension(200, 0));
		centerPanel.add(arrayContainer, BorderLayout.EAST);

		mainContainer.add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = createBottomPanel();
		mainContainer.add(bottomPanel, BorderLayout.SOUTH);

		add(mainContainer);
		setVisible(true);
	}

	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel(new BorderLayout(15, 15));
		headerPanel.setOpaque(false);

		JLabel titleLabel = new JLabel("Heap Sort Visualizer", SwingConstants.CENTER);
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setForeground(PRIMARY_COLOR);
		headerPanel.add(titleLabel, BorderLayout.NORTH);

		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		inputPanel.setOpaque(false);

		JLabel inputLabel = new JLabel("Nhập dữ liệu (phân cách bởi dấu phẩy):");
		inputLabel.setFont(LABEL_FONT);
		inputField = createStyledTextField(20);
		JButton startButton = createStyledButton("Visualize");

		inputPanel.add(inputLabel);
		inputPanel.add(inputField);
		inputPanel.add(startButton);

		headerPanel.add(inputPanel, BorderLayout.CENTER);

		startButton.addActionListener(e -> onStart());

		return headerPanel;
	}

	private JPanel createTreePanel() {
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (array != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					drawTree(g2, 0, getWidth() / 2, 80, getWidth() / 4);
				}
			}
		};
		panel.setBackground(BACKGROUND_COLOR);
		panel.setBorder(createStyledBorder("Cây Heap"));
		return panel;
	}

	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
		bottomPanel.setOpaque(false);

		logArea = new JTextArea(6, 40);
		logArea.setFont(LABEL_FONT);
		logArea.setEditable(false);
		logArea.setBackground(new Color(250, 250, 250));
		logArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JScrollPane scrollPane = new JScrollPane(logArea);
		scrollPane.setBorder(createStyledBorder("Thực thi"));
		bottomPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel statusPanel = new JPanel(new BorderLayout(10, 0));
		statusPanel.setOpaque(false);

		bottomPanel.add(statusPanel, BorderLayout.SOUTH);

		return bottomPanel;
	}

	private void drawTree(Graphics2D g, int index, int x, int y, int xOffset) {
		if (index >= arraySize)
			return;

		g.setColor(NODE_COLOR);
		g.fillOval(x - 30, y - 30, 60, 60);
		g.setColor(NODE_BORDER);
		g.setStroke(new BasicStroke(2));
		g.drawOval(x - 30, y - 30, 60, 60);

		g.setFont(NODE_FONT);
		String text = String.valueOf(array[index]);
		FontMetrics fm = g.getFontMetrics();
		int textX = x - fm.stringWidth(text) / 2;
		int textY = y + fm.getAscent() / 2 - 2;
		g.drawString(text, textX, textY);

		g.setColor(LINE_COLOR);
		g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		if (2 * index + 1 < arraySize) {
			drawConnection(g, x, y, x - xOffset, y + 80);
			drawTree(g, 2 * index + 1, x - xOffset, y + 80, xOffset / 2);
		}
		if (2 * index + 2 < arraySize) {
			drawConnection(g, x, y, x + xOffset, y + 80);
			drawTree(g, 2 * index + 2, x + xOffset, y + 80, xOffset / 2);
		}
	}

	private void drawConnection(Graphics2D g, int x1, int y1, int x2, int y2) {
		QuadCurve2D curve = new QuadCurve2D.Float(x1, y1, (x1 + x2) / 2, (y1 + y2) / 2 - 20, x2, y2);
		g.draw(curve);
	}

	private JTextField createStyledTextField(int columns) {
		JTextField field = new JTextField(columns);
		field.setFont(LABEL_FONT);
		field.setBorder(BorderFactory.createCompoundBorder(new LineBorder(PRIMARY_COLOR, 1, true),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		return field;
	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (getModel().isPressed()) {
					g2d.setColor(PRIMARY_COLOR.darker());
				} else if (getModel().isRollover()) {
					g2d.setColor(ACCENT_COLOR);
				} else {
					g2d.setColor(PRIMARY_COLOR);
				}
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

				g2d.setColor(Color.WHITE);
				g2d.setFont(getFont());
				FontMetrics metrics = g2d.getFontMetrics();
				int x = (getWidth() - metrics.stringWidth(getText())) / 2;
				int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
				g2d.drawString(getText(), x, y);
			}
		};
		button.setFont(LABEL_FONT);
		button.setForeground(Color.WHITE);
		button.setPreferredSize(new Dimension(120, 35));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return button;
	}

	private Border createStyledBorder(String title) {
		return BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true), title,
						TitledBorder.LEFT, TitledBorder.TOP, LABEL_FONT, PRIMARY_COLOR),
				BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
			JOptionPane.showMessageDialog(this, "Lỗi dữ liệu! Vui lòng chỉ nhập số và phân cách bởi dấu phẩy", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return new int[0];
		}
	}

	private void initializeArrayLabels() {
		JPanel arrayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		arrayPanel.setBorder(BorderFactory.createTitledBorder("Trực quan hóa thuật toán"));
		labels = new JLabel[array.length];
		for (int i = 0; i < array.length; i++) {
			labels[i] = new JLabel(String.valueOf(array[i]));
			labels[i].setFont(new Font("Segoe UI", Font.BOLD, 18));
			labels[i].setBorder(BorderFactory.createLineBorder(Color.CYAN));
			labels[i].setPreferredSize(new Dimension(50, 50));
			labels[i].setOpaque(true);
			labels[i].setBackground(Color.LIGHT_GRAY);
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			arrayPanel.add(labels[i]);
		}
		add(arrayPanel, BorderLayout.WEST);
		revalidate();
		repaint();
	}

	private void heapSort() {
		buildMaxHeap();
		for (int i = array.length - 1; i > 0; i--) {
			swap(0, i);
			arraySize--;
			heapify(0);
			treePanel.repaint();
			updateArrayLabels();
			delay();
			labels[i].setBackground(Color.GREEN);
		}
		labels[0].setBackground(Color.GREEN);
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

		if (left < arraySize && array[left] > array[largest])
			largest = left;
		if (right < arraySize && array[right] > array[largest])
			largest = right;

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

	public static void execute() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(HeapSortVisualizer::new);
	}
}