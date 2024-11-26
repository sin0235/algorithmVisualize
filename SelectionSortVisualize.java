package algorithmVisualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

public class SelectionSortVisualize extends AlgorithmSortVisualizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionSortVisualize() {
		super();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
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
		minLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
		minLabel.setForeground(Color.WHITE);
		panel.add(minLabel);

		try {
			long startTime = System.currentTimeMillis();

			for (int i = 0; i < len - 1; i++) {
				int minIndex = i;
				minLabel.setText("Min: " + array[minIndex]);
				minLabel.setVisible(true);
				labels[i].setBackground(Color.WHITE);
				labels[i].setForeground(Color.WHITE);

				for (int j = i + 1; j < len; j++) {
					labels[j].setBackground(Color.YELLOW);
					Thread.sleep(500);
					if (array[j] < array[minIndex]) {
						if (minIndex != i) {
							labels[minIndex].setBackground(originalColor);

						}
						minIndex = j;
						labels[minIndex].setBackground(Color.RED);
						delay();
						minLabel.setText("Min: " + array[minIndex]);
					} else {
						labels[j].setBackground(originalColor);
					}
				}

				if (minIndex != i) {
					logArea.append("Min: " + array[minIndex] + " ");
					logArea.append("Swapping: " + array[i] + "<->" + array[minIndex] + "\n");

					panel.setLayout(null);
					labels[i].setBackground(originalColor);
					labels[i].setForeground(Color.BLACK);

					swap(i, minIndex);
					labels[i].setBackground(originalColor);
					panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
					panel.revalidate();
					panel.repaint();

					minLabel.setForeground(Color.WHITE);
				}
				delay();
				labels[i].setBackground(Color.GREEN);
				labels[i].setForeground(Color.BLACK);
			}
			labels[array.length - 1].setBackground(Color.GREEN);

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new SelectionSortVisualize().setVisible(true));
	}
}
