package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class RadixSortVisualizer extends JFrame {
	private JPanel arrayPanel, queuePanel;
	private JLabel expLabel;
	private JTextField inputField;
	private JButton startButton;
	private int[] array;
	private JLabel[] labels;
	private Queue<Integer>[] queues = new Queue[10];
	private final int DELAY = 500;
	public Color originalColor = new Color(92, 219, 149);

	public RadixSortVisualizer() {
		setTitle("Radix Sort Visualizer");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 245, 245));

		// Input Panel
		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		inputField = new JTextField(25);
		startButton = new JButton("Start");
		startButton.setFont(new Font("Arial", Font.BOLD, 14));
		startButton.setBackground(new Color(72, 191, 227));
		startButton.setForeground(Color.WHITE);
		startButton.setFocusPainted(false);
		inputPanel.setBackground(new Color(245, 245, 245));

		JLabel instructionLabel = new JLabel("Enter numbers (comma-separated): ");
		instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		inputPanel.add(instructionLabel);
		inputPanel.add(inputField);
		inputPanel.add(startButton);
		add(inputPanel, BorderLayout.NORTH);

		// Array Panel
		arrayPanel = new JPanel();
		arrayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		arrayPanel.setBackground(new Color(255, 255, 255));
		arrayPanel.setBorder(BorderFactory.createTitledBorder("Array Visualization"));
		add(arrayPanel, BorderLayout.CENTER);

		// Queue Panel
		queuePanel = new JPanel(new GridLayout(10, 1, 10, 10));
		queuePanel.setPreferredSize(new Dimension(400, 500));
		queuePanel.setBorder(BorderFactory.createTitledBorder("Digit Queues"));
		add(queuePanel, BorderLayout.EAST);

		for (int i = 0; i < 10; i++) {
			queues[i] = new LinkedList<>();
		}

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initializeArray();
				new Thread(() -> visualize()).start();
			}
		});
	}

	private void initializeArray() {
		String input = inputField.getText();
		String[] numbers = input.split(",");
		array = new int[numbers.length];
		labels = new JLabel[numbers.length];

		try {
			arrayPanel.removeAll();
			queuePanel.removeAll();

			for (int i = 0; i < numbers.length; i++) {
				array[i] = Integer.parseInt(numbers[i].trim());
				labels[i] = createLabel(array[i]);
				arrayPanel.add(labels[i]);
			}

			expLabel = new JLabel("");
			expLabel.setFont(new Font("Arial", Font.BOLD, 16));
			expLabel.setForeground(Color.RED);
			add(expLabel, BorderLayout.SOUTH);

			arrayPanel.revalidate();
			arrayPanel.repaint();

			// Adding Queue Rows
			for (int i = 0; i < 10; i++) {
				JPanel queueRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
				queueRow.setBorder(BorderFactory.createTitledBorder("Queue " + i));
				queueRow.setBackground(new Color(230, 230, 250));
				queuePanel.add(queueRow);
			}
			queuePanel.revalidate();
			queuePanel.repaint();

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please enter valid integers separated by commas.");
		}
	}

	private JLabel createLabel(int value) {
		JLabel label = new JLabel(String.valueOf(value));
		label.setOpaque(true);
		label.setBackground(originalColor);
		label.setPreferredSize(new Dimension(60, 50));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		return label;
	}

	private void visualize() {
		int m = max();
		int exp = 1;

		while (m / exp > 0) {
			expLabel.setText("EXP: " + exp);
			distribute(exp);
			collect();
			exp *= 10;
		}
		for (JLabel label : labels) {
			label.setBackground(Color.GREEN);
		}
	}

	private int max() {
		int max = array[0];
		for (int value : array) {
			if (value > max) max = value;
		}
		return max;
	}

	private void distribute(int exp) {
		for (int i = 0; i < array.length; i++) {
			int digit = (array[i] / exp) % 10;
			queues[digit].add(array[i]);
			enqueue(digit, array[i]);
			labels[i].setVisible(false);
			delay();
		}
	}

	private void collect() {
		int index = 0;
		for (int i = 0; i < 10; i++) {
			while (!queues[i].isEmpty()) {
				int value = queues[i].poll();
				dequeue(i);
				array[index] = value;
				labels[index].setText(String.valueOf(value));
				labels[index].setVisible(true);
				index++;
				delay();
			}
		}
	}

	private void enqueue(int queueIndex, int value) {
		JPanel queueRow = (JPanel) queuePanel.getComponent(queueIndex);
		JLabel queueLabel = new JLabel(String.valueOf(value));
		queueLabel.setOpaque(true);
		queueLabel.setBackground(new Color(255, 204, 153));
		queueLabel.setPreferredSize(new Dimension(60, 40));
		queueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		queueLabel.setFont(new Font("Arial", Font.BOLD, 14));
		queueRow.add(queueLabel);
		queueRow.revalidate();
		queueRow.repaint();
	}

	private void dequeue(int queueIndex) {
		JPanel queueRow = (JPanel) queuePanel.getComponent(queueIndex);
		if (queueRow.getComponentCount() > 0) {
			queueRow.remove(0);
			queueRow.revalidate();
			queueRow.repaint();
		}
	}

	private void delay() {
		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new RadixSortVisualizer().setVisible(true));
	}
}
