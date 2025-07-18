package algorithmVisualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

import static java.lang.Thread.sleep;

@SuppressWarnings("all")
public class InsertionSortVisualizer extends AlgorithmSortVisualizer {

	public InsertionSortVisualizer() {
		super();
	}

	@Override
	public String getCode() {
		return """
				for (int i = 1; i < array.length; i++) {
				    int key = array[i]
				    int j = i - 1;

				    while (j >= 0 && array[j] > key) {
				        array[j + 1] = array[j];
				        j = j - 1;
				    }
				    array[j + 1] = key;
				}
				""";
	}

	@Override
	public void visualize() {
		int len = array.length;

		try {
			long startTime = System.currentTimeMillis();

			for (int i = 1; i < len; i++) {
				highlightLine(1);
				Thread.sleep(300);

				highlightLine(2);
				int tmp = array[i];
				highlightLine(3);
				int j = i - 1;

				JLabel tempLabel = new JLabel(String.valueOf(tmp));
				tempLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
				tempLabel.setOpaque(true);
				tempLabel.setBackground(Color.YELLOW);
				tempLabel.setForeground(Color.RED);
				tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
				tempLabel.setPreferredSize(new Dimension(50, 50));

				panel.add(tempLabel);
				panel.setComponentZOrder(tempLabel, 0);
				tempLabel.setLocation(labels[i].getLocation());
				labels[i].setText("");
				delay();
				panel.revalidate();
				panel.repaint();

				while (j >= 0 && array[j] > tmp) {
					highlightLine(5);
					Thread.sleep(300);

					logArea.append("Dời " + array[j] + " từ index " + j + " sang index " + (j + 1) + "\n");

					highlightLine(6);
					animateShift(labels[j], labels[j + 1].getX(), labels[j + 1].getY());
					array[j + 1] = array[j];
					labels[j + 1].setText(String.valueOf(array[j]));
					labels[j].setText("");
					sleep(200);

					highlightLine(7);
					j--;
					Thread.sleep(300);
				}

				highlightLine(9);
				array[j + 1] = tmp;
				logArea.append("Chèn thành công " + tmp + " vào vị trí index " + (j + 1) + "\n");

				animateShift(tempLabel, labels[j + 1].getX(), labels[j + 1].getY());
				sleep(700);
				labels[j + 1].setText(String.valueOf(tmp));
				tempLabel.setVisible(false);
				panel.remove(tempLabel);
				labels[j + 1].setBackground(Color.ORANGE);

				tempLabel.setText("");
				panel.revalidate();
				panel.repaint();

				labels[j + 1].setBackground(originalColor);
			}

			SwingUtilities.invokeLater(() -> {
				if (currentHighlight != null) {
					codeArea.getHighlighter().removeHighlight(currentHighlight);
				}
			});

			for (JLabel label : labels) {
				label.setBackground(Color.GREEN);
			}

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void animateShift(JLabel label, int targetX, int targetY) {
		int startX = label.getX();
		int startY = label.getY();

		for (int step = 0; step <= 10; step++) {
			final int x = startX + (int) ((targetX - startX) * (step / 10.0));
			final int y = startY + (int) ((targetY - startY) * (step / 10.0));
			SwingUtilities.invokeLater(() -> label.setLocation(x, y));

			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		label.setLocation(targetX, targetY);
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new InsertionSortVisualizer().setVisible(true));
	}
}
