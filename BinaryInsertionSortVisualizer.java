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
		JLabel tempLabel = new JLabel("", SwingConstants.CENTER);
		tempLabel.setOpaque(true);
		tempLabel.setBackground(Color.CYAN);
		tempLabel.setFont(new Font("Roboto", Font.BOLD, 17));
		tempLabel.setForeground(Color.BLACK);
		panel.add(tempLabel);

		try {
			long startTime = System.currentTimeMillis();

			for (int i = 1; i < len; i++) {
				int temp = array[i];
				tempLabel.setText(String.valueOf(temp)); // Hiển thị phần tử đang xét
				tempLabel.setVisible(true);

				int left = 0;
				int right = i - 1;
				labels[i].setBackground(Color.YELLOW);
				Thread.sleep(500);

				int mid;
				while (left <= right) {
					mid = (left + right) / 2;
					labels[mid].setBackground(Color.MAGENTA);
					Thread.sleep(DELAY);

					if (array[mid] < temp) {
						left = mid + 1;
					} else {
						right = mid - 1;
					}

					labels[mid].setBackground(originalColor);
				}

				// Dời các phần tử sang phải để tạo chỗ cho temp
				for (int j = i - 1; j >= left; j--) {
					logArea.append("Dời " + array[j] + " từ index " + j + " sang index " + (j + 1) + "\n");
					array[j + 1] = array[j];
					labels[j + 1].setText(String.valueOf(array[j]));
					labels[j + 1].setBackground(Color.RED);
					labels[j].setText("");
					Thread.sleep(DELAY);
					labels[j + 1].setBackground(originalColor);
				}

				// Chèn phần tử đang xét vào vị trí left
				array[left] = temp;
				labels[left].setText(String.valueOf(temp));
				labels[left].setBackground(Color.CYAN);
				Thread.sleep(DELAY);
				labels[left].setBackground(originalColor);

				labels[i].setBackground(originalColor);
				tempLabel.setVisible(false); // Ẩn label tạm sau khi chèn
			}

			// Đánh dấu các phần tử đã sắp xếp
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
