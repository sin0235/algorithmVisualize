package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.util.Random;

public class QuickSortVisualizer extends AlgorithmSortVisualizer {
	public QuickSortVisualizer() {
		super();
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
		iLabel.setFont(new Font("Roboto", Font.BOLD, 17));
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
		if (left <= right) {

			int pivotIndex = partition(left, right);
			labels[pivotIndex].setBackground(Color.GREEN);
			Random random = new Random();
			int red1 = random.nextInt(256);
			int green1 = random.nextInt(256);
			int blue1 = random.nextInt(256);
			Color c1 = new Color(red1, green1, blue1);
			for (int k = pivotIndex + 1; k <= right; k++) {
				labels[k].setBackground(new Color(red1, green1, blue1));
			}
			quickSort(left, pivotIndex - 1);
			quickSort(pivotIndex + 1, right);
		}
	}

	private int partition(int left, int right) {
		logArea.append("Xét mảng con từ vị trí: " + left + " tới " + right + "\n");
		int pivot = array[right];
		labels[right].setBackground(Color.MAGENTA);

		int i = left - 1;
		iLabel.setText("i: " + i);
		iLabel.setVisible(true);
		for (int j = left; j < right; j++) {
			labels[j].setBackground(Color.YELLOW);
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			if (array[j] < pivot) {
				i++;
				logArea.append(array[j] + "<" + pivot + " => i = " + i + "\n");

				if (i != j) {
					swap(i, j);
					try {
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					logArea.append("Đổi chỗ: " + array[i] + ":" + array[j] + "\n");
					labels[i].setBackground(Color.WHITE);
				}
			}
			labels[j].setBackground(Color.WHITE);
		}
		iLabel.setText("i: " + i);
		iLabel.setVisible(true);
		swap(i + 1, right);
		logArea.append("Array[pivotIndex]: " + array[i + 1] + "\n");

		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		labels[right].setBackground(Color.WHITE);
		return i + 1;
	}

	public static void excute() {
		SwingUtilities.invokeLater(() -> new QuickSortVisualizer().setVisible(true));
	}

}
