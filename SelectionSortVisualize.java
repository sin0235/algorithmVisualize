package algorithmVisualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SelectionSortVisualize extends AlgorithmSortVisualizer {
	public SelectionSortVisualize() {
		super();
	}

	@Override
	public String getCode() {
		return """
				for (int i = 0; i < array.length - 1; i++) {
				    int minIndex = i;
				    for (int j = i + 1; j < array.length; j++) {
				        if (array[j] < array[minIndex]) {
				            minIndex = j;
				        }
				    }
				    if (minIndex != i) {
				        // Swap array[i] and array[minIndex]
				        int temp = array[i];
				        array[i] = array[minIndex];
				        array[minIndex] = temp;
				    }
				}
				""";
	}

	@Override
	public void visualize() {
		int len = array.length;
		JLabel minLabel = new JLabel("Min: ", SwingConstants.CENTER);
		minLabel.setOpaque(true);
		minLabel.setBackground(Color.BLUE);
		minLabel.setPreferredSize(new Dimension(100, 70));
		minLabel.setFont(new Font("Roboto", Font.BOLD, 17));
		minLabel.setForeground(Color.WHITE);
		panel.add(minLabel);
		try {
			long startTime = System.currentTimeMillis();

			for (int i = 0; i < len - 1; i++) {
				int minIndex = i;
				minLabel.setText("Min: " + String.valueOf(array[minIndex]));
				minLabel.setVisible(true);
				labels[i].setBackground(Color.WHITE);
				for (int j = i + 1; j < len; j++) {
					labels[j].setBackground(Color.YELLOW);
					Thread.sleep(500);
					if (array[j] < array[minIndex]) {
						if (minIndex != i) {
							labels[minIndex].setBackground(Color.CYAN);
						}
						minIndex = j;
						labels[minIndex].setBackground(Color.ORANGE);
						minLabel.setText("Min: " + String.valueOf(array[minIndex]));
						minLabel.setVisible(true);
					} else {
						labels[j].setBackground(Color.CYAN);
					}

					Thread.sleep(DELAY);
				}

				if (minIndex != i) {
					logArea.append("Min: " + array[minIndex] + " ");
					logArea.append("Đổi chỗ: " + array[i] + " và " + array[minIndex] + "\n");
					swap(i, minIndex);
					minLabel.setText("arr[" + i + "]: " + array[i]);
					minLabel.setForeground(Color.RED);
					Thread.sleep(500);
					labels[minIndex].setBackground(Color.CYAN);
					minLabel.setForeground(Color.WHITE);

				}
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
		SwingUtilities.invokeLater(() -> new SelectionSortVisualize().setVisible(true));
	}

}
