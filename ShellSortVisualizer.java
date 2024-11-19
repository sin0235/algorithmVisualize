package algorithmVisualize;

import java.awt.*;

import javax.swing.*;

public class ShellSortVisualizer extends AlgorithmSortVisualizer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel gapLabel = createCircleLabel("Gap");
	JLabel compareLabel = createCircleLabel("CMP:");

	public ShellSortVisualizer() {
		super();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
	}

	private JLabel createCircleLabel(String text) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		label.setPreferredSize(new Dimension(135, 90));
		label.setFont(new Font("Segoe UI", Font.BOLD, 14));
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

			highlightLine(1);
			for (int gap = len / 2; gap > 0; gap /= 2) {
				gapLabel.setText("Gap: " + gap);
				gapLabel.setBackground(new Color(0, 150, 0));
				Thread.sleep(500);
				gapLabel.setBackground(Color.BLACK);

				highlightLine(3);
				for (int j = gap; j < len; j++) {
					int value = array[j];
					int k = j;
					highlightLine(4);
					highlightLabel(labels[j], Color.YELLOW, 200);

					highlightLine(5);
					compareLabel.setText("Compare: " + array[k] + " : " +
							(k >= gap ? array[k - gap] : "N/A"));
					compareLabel.setBackground(new Color(100, 100, 255));

					logArea.append("Xét phần tử: " + array[j] + " tại vị trí " + j + "\n");

					highlightLine(6);
					while (k >= gap && value < array[k - gap]) {
						highlightLine(7);
						highlightLabel(labels[k], Color.RED, 100);
						highlightLabel(labels[k - gap], Color.WHITE, 100);
						delay();
						compareLabel.setText("Swap: " + value + "<->" + array[k - gap]);
						compareLabel.setForeground(Color.RED);

						swap(k, k - gap);

						Thread.sleep(DELAY);
						resetLabelColor(labels[k]);
						resetLabelColor(labels[k - gap]);

						highlightLine(8);
						k -= gap;
					}

					highlightLine(10);
					Thread.sleep(DELAY);
					array[k] = value;
					labels[k].setText(String.valueOf(array[k]));

					panel.setLayout(null);
					panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
					panel.revalidate();
					panel.repaint();

					highlightLabel(labels[k], Color.GREEN, 300);

					logArea.append("Chèn: " + value + " tại index " + k + "\n");

					resetLabelColor(labels[k]);
				}

				highlightLine(12);
			}

			for (JLabel label : labels) {
				highlightLabel(label, Color.GREEN, 500);
			}

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void highlightLabel(JLabel label, Color targetColor, int duration) {
		Color startColor = label.getBackground();
		for (int i = 0; i <= 10; i++) {
			float progress = i / 10f;
			int r = interpolateColor(startColor.getRed(), targetColor.getRed(), progress);
			int g = interpolateColor(startColor.getGreen(), targetColor.getGreen(), progress);
			int b = interpolateColor(startColor.getBlue(), targetColor.getBlue(), progress);

			label.setBackground(new Color(r, g, b));
			panel.revalidate();
			panel.repaint();
            try {
                Thread.sleep(duration / 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
	}

	@Override
	protected void swap(int i, int j) {
		Point startPos1 = labels[i].getLocation();
		Point startPos2 = labels[j].getLocation();
		Dimension originalSize = labels[i].getSize();
		Color originalColor = labels[i].getBackground();

		final int STEPS = 30;
		final int DELAY = 20;
		final int MAX_SCALE = 8;
		final int ARC_HEIGHT = 50;

		Timer timer = new Timer(DELAY, null);
		final int[] step = {0};

		timer.addActionListener(e -> {
			if (step[0] >= STEPS) {
				timer.stop();
				finalizeSwap(i, j, startPos1, startPos2, originalSize, originalColor);
				return;
			}

			double progress = (double) step[0] / STEPS;
			double easedProgress = easeInOutQuad(progress);

			Point pos1 = calculateBezierPoint(startPos1, startPos2, easedProgress, ARC_HEIGHT);
			Point pos2 = calculateBezierPoint(startPos2, startPos1, easedProgress, ARC_HEIGHT);

			int scale = calculateScale(progress, MAX_SCALE);

			updateLabel(labels[i], pos1, originalSize, scale, Color.ORANGE);
			updateLabel(labels[j], pos2, originalSize, -scale, Color.ORANGE);

			step[0]++;
			panel.repaint();
		});

		array[i] = array[j];

		timer.start();

		try {
			Thread.sleep(STEPS * DELAY + 50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void finalizeSwap(int i, int j, Point startPos1, Point startPos2,
							  Dimension originalSize, Color originalColor) {
		labels[i].setText(labels[j].getText());
		labels[j].setText("");

		labels[i].setLocation(startPos2);
		labels[j].setLocation(startPos1);
		labels[i].setSize(originalSize);
		labels[j].setSize(originalSize);
		labels[i].setBackground(originalColor);
		labels[j].setBackground(originalColor);

		panel.revalidate();
		panel.repaint();
	}
	private void resetLabelColor(JLabel label) {
		label.setBackground(originalColor);
	}

	private int interpolateColor(int start, int end, float progress) {
		return (int)(start + (end - start) * progress);
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new ShellSortVisualizer().setVisible(true));
	}

}
