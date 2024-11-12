package algorithmVisualize;

import java.awt.Color;

import javax.swing.SwingUtilities;

public class BubbleSortVisualizer extends AlgorithmSortVisualizer {
	public BubbleSortVisualizer() {
		super();
	}

	@Override
	public String getCode() {
		return """
				for (int i = 0; i < array.length - 1; i++) {
				    for (int j = 0; j < array.length - i - 1; j++) {
				        if (array[j] > array[j + 1]) {
				            // Swap array[j] and array[j + 1]
				            int temp = array[j];
				            array[j] = array[j + 1];
				            array[j + 1] = temp;
				        }
				    }
				}
				""";
	}

	@Override
	public void visualize() {
		long startTime = System.currentTimeMillis();

		try {
			for (int i = 0; i < array.length - 1; i++) {
				highlightLine(1);
				for (int j = 0; j < array.length - i - 1; j++) {
					highlightLine(2);

					labels[j].setBackground(Color.YELLOW);
					labels[j + 1].setBackground(Color.YELLOW);

					Thread.sleep(DELAY);
					if (array[j] > array[j + 1]) {
						highlightLine(3);
						logArea.append("Đổi chỗ: " + array[j] + " và " + array[j + 1] + "\n");
						swap(j, j + 1);
					}

					resetLabelColor(j, j + 1);
					Thread.sleep(DELAY);
				}
				labels[array.length - i - 1].setBackground(Color.GREEN);
			}
			labels[0].setBackground(Color.GREEN);

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void excute() {
		SwingUtilities.invokeLater(() -> new BubbleSortVisualizer().setVisible(true));
	}
}
