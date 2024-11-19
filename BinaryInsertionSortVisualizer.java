package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

@SuppressWarnings("ALL")
public class BinaryInsertionSortVisualizer extends AlgorithmSortVisualizer {
	private final DefaultHighlighter.DefaultHighlightPainter highlightPainter;
	private Object currentHighlight;

	public BinaryInsertionSortVisualizer() {
		super();
		highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 255, 200));
		currentHighlight = null;

	}

	public String getCode() {
		return """
             int len = arr.length;
             for (int i = 1; i < len; i++) {
                 int tmp = arr[i];
                 int left = 0;
                 int right = i - 1;
                 while (left <= right) {
                     int mid = (right + left) / 2;
                     if (arr[mid] > tmp) {
                         right = mid - 1;
                     } else {
                         left = mid + 1;
                     }
                 }
                 for (int j = i - 1; j >= left; j--) {
                     arr[j + 1] = arr[j];
                 }
                 arr[left] = tmp;
             }
             """;
	}
@Override
	protected void highlightLine(int lineNumber) {
		SwingUtilities.invokeLater(() -> {
			try {
				if (currentHighlight != null) {
					codeArea.getHighlighter().removeHighlight(currentHighlight);
				}

				int start = codeArea.getLineStartOffset(lineNumber - 1);
				int end = codeArea.getLineEndOffset(lineNumber - 1);

				currentHighlight = codeArea.getHighlighter().addHighlight(start, end, highlightPainter);

				Rectangle rect = codeArea.modelToView(start);
				codeArea.scrollRectToVisible(rect);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void visualize() {
		int len = array.length;

		try {
			long startTime = System.currentTimeMillis();

			for (int i = 1; i < len; i++) {
				highlightLine(2);
				int temp = array[i];
				labels[i].setBackground(Color.YELLOW);

				highlightLine(3);
				int left = 0, right = i - 1;

				while (left <= right) {
					highlightLine(4);
					int mid = left + (right - left) / 2;
					delay();

					highlightLine(5);
					for (int j = left; j <= right; j++) {
						labels[j].setBackground(j == mid ? Color.MAGENTA : Color.CYAN);
					}
					Thread.sleep(500);

					if (array[mid] > temp) {
						highlightLine(6);
						right = mid - 1;
					} else {
						highlightLine(8);
						left = mid + 1;
					}
					labels[left].setBackground(Color.WHITE);
					Thread.sleep(500);
				}

				highlightLine(11);
				for (int j = i - 1; j >= left; j--) {
					animateShift(labels[j], labels[j + 1].getX(), labels[j + 1].getY());
					array[j + 1] = array[j];
					labels[j + 1].setText(String.valueOf(array[j]));
					labels[j].setText("");
					Thread.sleep(200);
				}

				highlightLine(12);
				array[left] = temp;
				labels[left].setText(String.valueOf(temp));
				Thread.sleep(500);

				for(int ii = 0; ii <= left; ii++) {
					labels[i].setBackground(originalColor);
				}
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
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		label.setLocation(targetX, targetY);
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new BinaryInsertionSortVisualizer().setVisible(true));
	}
}