package algorithmVisualize;

import javax.swing.*;
import java.awt.*;

import javax.swing.SwingUtilities;

public class ShakeSortVisualizer extends AlgorithmSortVisualizer {
	public ShakeSortVisualizer() {
		super();
		panel.setLayout(null);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
		panel.revalidate();
		panel.repaint();
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
					Thread.sleep(500);
					if (array[j] > array[j + 1]) {
						logArea.append("Đổi chỗ: " + array[j] + "<->" + array[j + 1] + "\n");panel.setLayout(null);
						swap(j, j + 1);

						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
						Thread.sleep(500);
						panel.revalidate();
						panel.repaint();
						labels[k].setForeground(Color.BLACK);
						k = j;
						labels[k].setForeground(Color.MAGENTA);
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
						logArea.append("Đổi chỗ: " + array[i] + "<->" + array[i - 1] + "\n");
						panel.setLayout(null);
						swap(i - 1, i);
						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
						panel.revalidate();
						panel.repaint();

						labels[k].setForeground(Color.BLACK);
						k = i;
						labels[k].setForeground(Color.MAGENTA);

					}
					labels[i].setBackground(originalColor);
					labels[i - 1].setBackground(originalColor);
				}

				for (int l = k - 1; l >= left; l--) {
					labels[l].setBackground(Color.GREEN);
				}
				left = k;
				Thread.sleep(700);
				labels[k].setForeground(Color.BLACK);
			}
			labels[k].setBackground(Color.GREEN);
			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void swap(int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;

		labels[i].setBackground(Color.ORANGE);
		labels[j].setBackground(Color.ORANGE);

		final int STEPS = 25;
		final int DELAY = 10;
		final int ARC_HEIGHT = 100;

		int startX = labels[i].getLocation().x;
		int startY = labels[i].getLocation().y;
		int endX = labels[j].getLocation().x;
		int endY = labels[j].getLocation().y;
		Dimension originalSize = labels[i].getSize();

		for (int step = 0; step <= STEPS; step++) {
			double progress = (double) step / STEPS;

			double smoothProgress = progress < 0.5
					? 4 * progress * progress * progress
					: 1 - Math.pow(-2 * progress + 2, 3) / 2;

			int dx = (int)((endX - startX) * smoothProgress);
			int dy = (int)(ARC_HEIGHT * Math.sin(progress * Math.PI) - ARC_HEIGHT/2);

			labels[i].setLocation(startX + dx, startY + dy);
			labels[j].setLocation(endX - dx, endY - dy);

			panel.revalidate();
			panel.repaint();

			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		labels[i].setBackground(originalColor);
		labels[j].setBackground(originalColor);
		labels[i].setLocation(startX, startY);
		labels[j].setLocation(endX, endY);

		String tempText = labels[i].getText();
		labels[i].setText(labels[j].getText());
		labels[j].setText(tempText);

		panel.revalidate();
		panel.repaint();
	}
	public static void execute() {
		SwingUtilities.invokeLater(() -> new ShakeSortVisualizer().setVisible(true));
	}

}
