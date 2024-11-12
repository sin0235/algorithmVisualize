package algorithmVisualize;

import java.awt.Color;

import javax.swing.SwingUtilities;

public class InterchangeSortVisualizer extends AlgorithmSortVisualizer {
	public InterchangeSortVisualizer() {
		super();
	}

	@Override
	public String getCode() {
		return """
				for (int i = 0; i < array.length - 1; i++) {
				    for (int j = i + 1; j < array.length; j++) {
				        if (array[i] > array[j]) {
				            // Swap array[i] and array[j]
				            int temp = array[i];
				            array[i] = array[j];
				            array[j] = temp;
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

				for (int j = i + 1; j < array.length; j++) {
					highlightLine(2);
					labels[i].setBackground(Color.YELLOW);
					labels[j].setBackground(Color.YELLOW);
					Thread.sleep(DELAY);
					if (array[i] > array[j]) {
						highlightLine(3);
						logArea.append("Đổi chỗ: " + array[i] + " và " + array[j] + "\n");
						swap(i, j);
					}
					labels[i].setBackground(Color.YELLOW);
					labels[j].setBackground(Color.CYAN);
					Thread.sleep(DELAY);
				}
				labels[i].setBackground(Color.CYAN);

				labels[i].setBackground(Color.GREEN);
			}
			labels[array.length - 1].setBackground(Color.GREEN);

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void excute() {
		SwingUtilities.invokeLater(() -> new InterchangeSortVisualizer().setVisible(true));
	}

}
