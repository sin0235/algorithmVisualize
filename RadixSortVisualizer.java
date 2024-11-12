package algorithmVisualize;

import java.util.Random;
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
	public Color originalColor = new Color(175, 238, 238);

	public RadixSortVisualizer() {
		setTitle("Algorithm Visualizer Application");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		inputField = new JTextField(30);
		startButton = new JButton("Start");
		inputPanel.add(new JLabel("Nhập dữ liệu đầu vào(Phân cách bởi dấu phẩy): "));
		inputPanel.add(inputField);
		inputPanel.add(startButton);
		add(inputPanel, BorderLayout.NORTH);

		arrayPanel = new JPanel();
		arrayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(arrayPanel, BorderLayout.CENTER);

		queuePanel = new JPanel(new GridLayout(10, 1, 10, 10));
		queuePanel.setPreferredSize(new Dimension(400, 500));
		add(queuePanel, BorderLayout.WEST);

		for (int i = 0; i < 10; i++) {
			queues[i] = new LinkedList<>();
		}

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
					expLabel.setFont(new Font("Roboto", Font.BOLD, 16));
					expLabel.setForeground(Color.RED);
					add(expLabel, BorderLayout.SOUTH);

					arrayPanel.revalidate();
					arrayPanel.repaint();

					for (int i = 0; i < 10; i++) {
						JPanel queueRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
						queueRow.setBorder(BorderFactory.createTitledBorder("Queue " + i));
						queuePanel.add(queueRow);
					}

					queuePanel.revalidate();
					queuePanel.repaint();

					new Thread(() -> visualize()).start();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Lỗi dữ liệu, vui lòng chỉ nhập số và phân cách bới dấu phẩy");
				}
			}
		});
	}

	private JLabel createLabel(int value) {
		JLabel label = new JLabel(String.valueOf(value));
		label.setOpaque(true);
		label.setBackground(originalColor);
		label.setPreferredSize(new Dimension(60, 50));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Roboto", Font.BOLD, 14));
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
		for (int i = 0; i < array.length; i++) {
			labels[i].setBackground(Color.GREEN);
		}
	}

	private int max() {
		int max = array[0];
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max)
				max = array[i];
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
		queueLabel.setBackground(Color.ORANGE);
		queueLabel.setPreferredSize(new Dimension(60, 40));
		queueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		queueLabel.setFont(new Font("Roboto", Font.BOLD, 14));
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

	public static void excute() {
		SwingUtilities.invokeLater(() -> new RadixSortVisualizer().setVisible(true));
	}
}
