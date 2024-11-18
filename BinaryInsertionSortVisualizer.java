package algorithmVisualize;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("ALL")
public class BinaryInsertionSortVisualizer extends AlgorithmSortVisualizer {

	public BinaryInsertionSortVisualizer() {
		super();
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

	public void visualize() {
		int len = array.length;

		try {
			long startTime = System.currentTimeMillis();

			for (int i = 1; i < len; i++) {
				int temp = array[i];
				labels[i].setBackground(Color.YELLOW);

				// Binary Search Visualization
				int left = 0, right = i - 1;
				while (left <= right) {
					int mid = left + (right - left) / 2;

					// Highlight search range
					for (int j = left; j <= right; j++) {
						labels[j].setBackground(j == mid ? Color.MAGENTA : Color.CYAN);
					}
					Thread.sleep(500);

					// Explain search logic
					if (array[mid] > temp) {
						// Search left half
						right = mid - 1;
					} else {
						// Search right half
						left = mid + 1;
					}
					labels[left].setBackground(Color.WHITE);
					Thread.sleep(500);
				}

				// Shift elements for insertion
				for (int j = i - 1; j >= left; j--) {
					array[j + 1] = array[j];
					labels[j + 1].setText(String.valueOf(array[j]));
					labels[j + 1].setBackground(Color.RED);
					Thread.sleep(400);
					labels[j + 1].setBackground(originalColor);
				}

				// Insert element
				array[left] = temp;
				labels[left].setText(String.valueOf(temp));
				Thread.sleep(500);
				for(int ii = 0; ii <= left; ii++) {
					labels[i].setBackground(originalColor);
				}
			}


			for (JLabel label : labels) {
				label.setBackground(Color.GREEN);
			}

			long endTime = System.currentTimeMillis();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new BinaryInsertionSortVisualizer().setVisible(true));
	}
}
