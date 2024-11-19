package algorithmVisualize;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.*;
import javax.swing.text.*;

public class BubbleSortVisualizer extends AlgorithmSortVisualizer {

	private static final long serialVersionUID = 1L;

	public BubbleSortVisualizer() {
		super();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
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
					delay();

					if (array[j] > array[j + 1]) {
						resetLabelColor(j, j + 1);
						highlightLine(3);
						logArea.append("Swapping: " + array[j] + "<->" + array[j + 1] + "\n");
						panel.setLayout(null);

						highlightLine(5);
						Thread.sleep(DELAY/2);
						highlightLine(6);
						Thread.sleep(DELAY/2);
						highlightLine(7);

						swap(j, j + 1);

						resetLabelColor(j, j + 1);
						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
						panel.revalidate();
						panel.repaint();
						Thread.sleep(DELAY);
					}

					resetLabelColor(j, j + 1);
				}
				labels[array.length - i - 1].setBackground(Color.GREEN);
			}
			labels[0].setBackground(Color.GREEN);

			SwingUtilities.invokeLater(() -> {
				if (currentHighlight != null) {
					codeArea.getHighlighter().removeHighlight(currentHighlight);
				}
			});

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void resetLabelColor(int j, int j1) {
		labels[j].setBackground(originalColor);
		labels[j1].setBackground(originalColor);
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new BubbleSortVisualizer().setVisible(true));
	}
}