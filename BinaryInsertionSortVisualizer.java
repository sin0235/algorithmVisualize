package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.lang.Thread;

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
				int left = 0;
				int right = i - 1;
				labels[i].setBackground(Color.YELLOW);
				Thread.sleep(500);

				int mid = 0;
				while (left <= right) {
					mid = (left + right) / 2;
					labels[mid].setBackground(Color.MAGENTA);
					Thread.sleep(DELAY);

					if (array[mid] < temp) {
						left = mid + 1;
					} else {
						right = mid - 1;
					}

					labels[mid].setBackground(Color.CYAN);
				}
				logArea.append("Left: " + left + "\n");
				labels[left].setBackground(Color.GRAY);
				for (int j = i - 1; j >= left; j--) {
					logArea.append("Dời " + array[j] + " từ index " + j + " sang index " + (j + 1) + "\n");
					array[j + 1] = array[j];
					labels[j + 1].setText(String.valueOf(array[j]));
					labels[j + 1].setBackground(Color.RED);
					labels[j].setBackground(Color.WHITE);// Color shift to indicate swap
					labels[j].setText("");
					Thread.sleep(DELAY);
					labels[j + 1].setBackground(Color.CYAN);
					labels[j].setBackground(Color.CYAN);
				}

				array[left] = temp;
				logArea.append("Chèn thành công " + temp + " vào vị trí index " + left + "\n");
				labels[left].setText(String.valueOf(temp));
				Thread.sleep(DELAY + 100);
				labels[left].setBackground(Color.CYAN);
			}

			// Mark all elements as sorted at the end
			for (JLabel label : labels) {
				label.setBackground(Color.GREEN);
			}

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void excute() {
		SwingUtilities.invokeLater(() -> new BinaryInsertionSortVisualizer().setVisible(true));
	}

}
