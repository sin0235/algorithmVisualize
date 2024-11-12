package algorithmVisualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ShellSortVisualizer extends AlgorithmSortVisualizer {
	JLabel gapLabel = createCircleLabel("Gap");
	JLabel compareLabel = createCircleLabel("CMP:");

	public ShellSortVisualizer() {
		super();
	}

	private JLabel createCircleLabel(String text) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		label.setPreferredSize(new Dimension(100, 80));
		label.setFont(new Font("Roboto", Font.BOLD, 14));
		label.setForeground(Color.WHITE);
		return label;
	}

	@Override
	public String getCode() {
		return """
				int gap = n / 2;
				while (gap > 0) {
				    for (int i = gap; i < n; i++) {
				        int temp = a[i];
				        int j = i;
				        while (j >= gap && a[j - gap] > temp) {
				            a[j] = a[j - gap];
				            j -= gap;
				        }
				        a[j] = temp;
				    }
				    gap /= 2;
				}
				""";
	}

	@Override
	public void visualize() {
		int len = array.length;
		panel.add(gapLabel);
		panel.add(compareLabel);
		panel.revalidate();
		panel.repaint();
		try {
			long startTime = System.currentTimeMillis();

			for (int gap = len / 2; gap > 0; gap /= 2) {
				gapLabel.setText("Gap: " + gap);
				gapLabel.setVisible(true);
				for (int j = gap; j < len; j++) {
					int value = array[j];
					int k = j;

					labels[j].setBackground(Color.YELLOW);
					compareLabel.setText("CMP " + array[k] + " : " + array[k - gap]);
					logArea.append("Đang xét: " + array[j] + " tại " + j + "\n");
					Thread.sleep(DELAY);
					while (k >= gap && value < array[k - gap]) {
						labels[k].setBackground(Color.RED);
						labels[k - gap].setBackground(Color.WHITE);
						compareLabel.setText("CMP " + value + " : " + array[k - gap]);
						compareLabel.setVisible(true);
						logArea.append("Dời: " + array[k - gap] + " từ " + (k - gap) + " đến " + k + "\n");

						array[k] = array[k - gap];
						labels[k].setText(String.valueOf(array[k]));
						labels[k - gap].setText("");
						compareLabel.setText("Swap " + value + " : " + array[k - gap]);
						compareLabel.setForeground(Color.RED);

						Thread.sleep(DELAY);
						compareLabel.setForeground(Color.WHITE);
						labels[k].setBackground(originalColor);
						k -= gap;
					}

					array[k] = value;
					labels[k].setText(String.valueOf(array[k]));
					labels[k].setBackground(Color.BLUE);
					logArea.append("Chèn thành công " + value + " vào vị trí index " + k + "\n");
					Thread.sleep(DELAY);
					labels[k].setBackground(originalColor);
				}
			}

			for (JLabel label : labels) {
				label.setBackground(Color.GREEN);
			}

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void excute() {
		SwingUtilities.invokeLater(() -> new ShellSortVisualizer().setVisible(true));
	}

}
