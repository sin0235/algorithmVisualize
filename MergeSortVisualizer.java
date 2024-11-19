package algorithmVisualize;

import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.util.Arrays;

public class MergeSortVisualizer extends AlgorithmSortVisualizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MergeSortVisualizer() {
		super();
	}
@Override
	public String getCode() {
		return """
				        void merge(int[] arr, int l, int r, int m) {
				             int[] arr1 = new int[m - l + 1];
				             int[] arr2 = new int[r - m];
				             for (int i = 0; i < m - l + 1; i++) {
				                 arr1[i] = arr[l + i];
				             }
				             for (int i = 0; i < r - m; i++) {
				                 arr2[i] = arr[m + i + 1];
				             }
				             int index1 = 0;
				             int index2 = 0;
				             int len1 = arr1.length;
				             int len2 = arr2.length;
				             while (index1 < len1 && index2 < len2) {
				                 if (arr1[index1] <= arr2[index2]) {
				                     arr[l++] = arr1[index1++];
				                 } else {
				                     arr[l++] = arr2[index2++];
				                 }
				             }
				             while (index1 < len1) {
				                 arr[l++] = arr1[index1++];
				             }
				             while (index2 < len2) {
				                 arr[l++] = arr2[index2++];
				             }
				         }

				        void mergeSort(int[] arr, int l, int r) {
				             if (l < r) {
				                 int m = (l + r) / 2;
				                 mergeSort(arr, l, m);
				                 mergeSort(arr, m + 1, r);
				                 merge(arr, l, r, m);
				             }
				         }
				""";
	}

	private void mergeSort(int[] arr, int left, int right) {
		highlightLine(28);
		if (left < right) {
			try {
				Thread.sleep(DELAY);
				int mid = (left + right) / 2;

				Random random = new Random();
				Color leftColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
				Color rightColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

				highlightLine(29);
				for (int i = left; i < mid + 1; i++) {
					labels[i].setBackground(leftColor);
				}
				Thread.sleep(DELAY);

				highlightLine(30);
				for (int j = mid + 1; j <= right; j++) {
					labels[j].setBackground(rightColor);
				}
				Thread.sleep(DELAY);

				mergeSort(arr, left, mid);
				logArea.append("Merge thành công 1 mảng con\n");
				mergeSort(arr, mid + 1, right);

				highlightLine(31);
				merge(arr, left, mid, right);
				logArea.append("Merge thành công 1 mảng con\n");

			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void merge(int[] arr, int left, int mid, int right) {
		try {
			highlightLine(1);
			Thread.sleep(DELAY);
			for (int i = left; i <= right; i++) {
				labels[i].setBackground(Color.WHITE);
			}

			highlightLine(2);
			int[] leftArray = Arrays.copyOfRange(arr, left, mid + 1);
			Thread.sleep(DELAY);

			highlightLine(3);
			int[] rightArray = Arrays.copyOfRange(arr, mid + 1, right + 1);
			Thread.sleep(DELAY);

			Random random = new Random();
			Color mergedColor = new Color(
					random.nextInt(256),
					random.nextInt(256),
					random.nextInt(256)
			);

			int i = 0, j = 0, k = left;

			highlightLine(13);
			while (i < leftArray.length && j < rightArray.length) {
				highlightLine(14);
				if (leftArray[i] <= rightArray[j]) {
					highlightLine(15);
					arr[k] = leftArray[i];
					updateLabel(k, leftArray[i], mergedColor);
					i++;
				} else {
					highlightLine(17);
					arr[k] = rightArray[j];
					updateLabel(k, rightArray[j], mergedColor);
					j++;
				}
				k++;
				Thread.sleep(DELAY);
			}

			highlightLine(20);
			while (i < leftArray.length) {
				arr[k] = leftArray[i];
				updateLabel(k, leftArray[i], mergedColor);
				i++;
				k++;
				Thread.sleep(DELAY);
			}

			highlightLine(23);
			while (j < rightArray.length) {
				arr[k] = rightArray[j];
				updateLabel(k, rightArray[j], mergedColor);
				j++;
				k++;
				Thread.sleep(DELAY);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private void updateLabel(int index, int value, Color color) {
		labels[index].setText(String.valueOf(value));
		labels[index].setBackground(color);
		panel.revalidate();
		panel.repaint();
	}

	public void visualize() {

		long startTime = System.currentTimeMillis();
		mergeSort(array, 0, array.length - 1);
		long endTime = System.currentTimeMillis();
		logArea.append("Thuật toán kết thúc sau " + (endTime - startTime) + " ms\n");
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new MergeSortVisualizer().setVisible(true));
	}

}
