package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.util.Random;

public class QuickSortVisualizer extends AlgorithmSortVisualizer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuickSortVisualizer() {
		super();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
	}

	public String getCode() {
		return """
				       int patition(int[] array, int l, int r) {
				             int i = l - 1;
				             for (int j = l; j < r; j++) {
				                 if (array[j] < array[r]) {
				                     swap(array, ++i, j);
				                 }
				             }
				             swap(array, ++i, r);
				             return i;
				         }

				       void quickSort(int[] array, int l, int r) {
				             if (r > l) {
				                 int flagIndex = patition(array, l, r);
				                 quickSort(array, l, flagIndex - 1);
				                 quickSort(array, flagIndex + 1, r);
				             }
				         }
				""";
	}

	JLabel iLabel = new JLabel("i: ", SwingConstants.CENTER);

	public void visualize() {

		iLabel.setOpaque(true);
		iLabel.setBackground(Color.PINK);
		iLabel.setPreferredSize(new Dimension(100, 70));
		iLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
		iLabel.setForeground(Color.BLACK);
		panel.add(iLabel);
		try {
			Thread.sleep(DELAY + 200);
			long startTime = System.currentTimeMillis();
			quickSort(0, array.length - 1);
			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void quickSort(int left, int right) {
		highlightLine(12);
		if (left <= right) {
			highlightLine(13);
			highlightLine(14);
			int pivotIndex = partition(left, right);
			labels[pivotIndex].setBackground(Color.GREEN);

			Random random = new Random();
			int red1 = random.nextInt(256);
			int green1 = random.nextInt(256);
			int blue1 = random.nextInt(256);

			for (int k = pivotIndex + 1; k <= right; k++) {
				labels[k].setBackground(new Color(red1, green1, blue1));
			}

			highlightLine(15);
			quickSort(left, pivotIndex - 1);

			highlightLine(16);
			quickSort(pivotIndex + 1, right);
		}
	}

	private int partition(int left, int right) {
		highlightLine(1);
		logArea.append("Xét mảng con từ vị trí: " + left + " tới " + right + "\n");

		highlightLine(2);
		int pivot = array[right];
		labels[right].setBackground(Color.MAGENTA);

		highlightLine(2);
		int i = left - 1;
		iLabel.setText("i: " + i);
		iLabel.setVisible(true);

		highlightLine(3);
		for (int j = left; j < right; j++) {
			labels[j].setBackground(Color.YELLOW);
			try {
				Thread.sleep(DELAY);

				highlightLine(4);
				if (array[j] < pivot) {
					i++;
					logArea.append(array[j] + " < " + pivot + " => i = " + i + "\n");

					if (i != j) {
						highlightLine(5);
						panel.setLayout(null);
						swap(i, j);

						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						panel.revalidate();
						panel.repaint();
						Thread.sleep(DELAY);
						logArea.append("Swap: " + array[i] + "<->" + array[j] + "\n");
						labels[i].setBackground(Color.WHITE);
					}
				}
				labels[j].setBackground(Color.WHITE);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		iLabel.setText("i: " + i);
		iLabel.setVisible(true);

		highlightLine(7);
		panel.setLayout(null);
		swap(i + 1, right);

		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
		panel.revalidate();
		panel.repaint();

		logArea.append("Array[pivotIndex]: " + array[i + 1] + "\n");

		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		labels[right].setBackground(Color.WHITE);

		highlightLine(8);
		return i + 1;
	}

	@Override
	protected void swap(int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;

		labels[i].setBackground(Color.ORANGE);
		labels[j].setBackground(Color.ORANGE);

		final int STEPS = 20;
		final int ANIMATION_DELAY = 25;
		final int MAX_SCALE = 8;

		Point startPos1 = labels[i].getLocation();
		Point startPos2 = labels[j].getLocation();
		Dimension originalSize = labels[i].getSize();

		for (int step = 0; step <= STEPS; step++) {
			double progress = (double) step / STEPS;
			int dx = (int) ((startPos2.x - startPos1.x) * progress);
			int dy = (int) ((startPos2.y - startPos1.y) * progress);

			double scaleProgress = Math.sin(progress * Math.PI) * MAX_SCALE;

			labels[i].setLocation(startPos1.x + dx, startPos1.y + dy);
			labels[j].setLocation(startPos2.x - dx, startPos2.y - dy);

			labels[i].setSize(originalSize.width + (int) scaleProgress, originalSize.height + (int) scaleProgress);
			labels[j].setSize(originalSize.width - (int) scaleProgress, originalSize.height - (int) scaleProgress);

			panel.revalidate();
			panel.repaint();

			try {
				Thread.sleep(ANIMATION_DELAY);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		String tempText = labels[i].getText();
		labels[i].setText(labels[j].getText());
		labels[j].setText(tempText);

		labels[i].setLocation(startPos2);
		labels[j].setLocation(startPos1);
		labels[i].setSize(originalSize);
		labels[j].setSize(originalSize);

		panel.revalidate();
		panel.repaint();
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new QuickSortVisualizer().setVisible(true));
	}

}
