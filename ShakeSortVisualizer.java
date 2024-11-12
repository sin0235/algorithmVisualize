package algorithmVisualize;

import java.awt.Color;

import javax.swing.SwingUtilities;

public class ShakeSortVisualizer extends AlgorithmSortVisualizer {
	public ShakeSortVisualizer() {
		super();
	}

	@Override
	public String getCode() {
		return """
				int left = 0;
				int right = n - 1;
				int k = n - 1;

				while (left < right) {
				    for (int i = right; i > left; i--) {
				        if (a[i] < a[i - 1]) {
				            swap(a[i], a[i - 1]);
				            k = i;
				        }
				    }
				    left = k;

				    for (int j = left; j < right; j++) {
				        if (a[j] > a[j + 1]) {
				            swap(a[j], a[j + 1]);
				            k = j;
				        }
				    }
				    right = k;
				}
				""";
	}

	@Override
	public void visualize() {
		int left = 0;
		int right = array.length - 1;
		int k = array.length - 1;

		try {
			long startTime = System.currentTimeMillis();

			while (left < right) {

				for (int j = left; j < right; j++) {
					labels[j].setBackground(Color.YELLOW);
					labels[j + 1].setBackground(Color.YELLOW);
					Thread.sleep(DELAY);
					if (array[j] > array[j + 1]) {
						logArea.append("Đổi chỗ: " + array[j] + " và " + array[j + 1] + "\n");
						swap(j, j + 1);
						labels[k].setForeground(Color.BLACK);
						k = j;
						labels[k].setForeground(Color.WHITE);
					}

					labels[j].setBackground(originalColor);
					labels[j + 1].setBackground(originalColor);

				}

				for (int r = k + 1; r <= right; r++) {
					labels[r].setBackground(Color.GREEN);
				}
				right = k;
				Thread.sleep(DELAY + 100);
				labels[k].setForeground(Color.BLACK);
				for (int i = right; i > left; i--) {
					labels[i].setBackground(Color.YELLOW);
					labels[i - 1].setBackground(Color.YELLOW);
					Thread.sleep(DELAY);

					if (array[i] < array[i - 1]) {
						logArea.append("Đổi chỗ: " + array[i] + " và " + array[i - 1] + "\n");
						swap(i, i - 1);
						labels[k].setForeground(Color.BLACK);
						k = i;
						labels[k].setForeground(Color.WHITE);

					}
					labels[i].setBackground(originalColor);
					labels[i - 1].setBackground(originalColor);
				}

				for (int l = k - 1; l >= left; l--) {
					labels[l].setBackground(Color.GREEN);
				}
				left = k;
				Thread.sleep(DELAY + 100);
				labels[k].setForeground(Color.BLACK);
			}
			labels[k].setBackground(Color.GREEN);
			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void excute() {
		SwingUtilities.invokeLater(() -> new ShakeSortVisualizer().setVisible(true));
	}

}
