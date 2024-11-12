package algorithmVisualize;

import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.util.Arrays;

public class MergeSortVisualizer extends AlgorithmSortVisualizer {

	public MergeSortVisualizer() {
		super();
	}

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
		Random random = new Random();
		int red1 = random.nextInt(256);
		int green1 = random.nextInt(256);
		int blue1 = random.nextInt(256);
		Color c1 = new Color(red1, green1, blue1);

		int red2 = random.nextInt(256);
		int green2 = random.nextInt(256);
		int blue2 = random.nextInt(256);
		Color c2 = new Color(red2, green2, blue2);

		highlightLine(1);
		if (left < right) {
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			int mid = (left + right) / 2;
			for (int i = left; i < mid + 1; i++) {
				labels[i].setBackground(c1);

			}
			highlightLine(2);
			for (int j = mid + 1; j <= right; j++) {
				labels[j].setBackground(c2);
			}

			mergeSort(arr, left, mid);
			mergeSort(arr, mid + 1, right);

			highlightLine(3);
			merge(arr, left, mid, right);
		}
	}

	private void merge(int[] arr, int left, int mid, int right) {
		highlightLine(6);
		for (int i = left; i <= right; i++) {
			labels[i].setBackground(Color.WHITE);
		}
		int[] leftArray = Arrays.copyOfRange(arr, left, mid + 1);

		int[] rightArray = Arrays.copyOfRange(arr, mid + 1, right + 1);
		Random random = new Random();
		int r = random.nextInt(256);
		int gr = random.nextInt(256);
		int bl = random.nextInt(256);
		int i = 0, j = 0, k = left;
		Color newColor = new Color(r, gr, bl);
		while (i < leftArray.length && j < rightArray.length) {
			if (leftArray[i] <= rightArray[j]) {
				arr[k] = leftArray[i];
				updateLabel(k, leftArray[i], newColor);
				i++;
			} else {
				arr[k] = rightArray[j];
				updateLabel(k, rightArray[j], newColor);
				j++;
			}
			k++;
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (i < leftArray.length) {
			arr[k] = leftArray[i];
			updateLabel(k, leftArray[i], newColor);
			i++;
			k++;
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (j < rightArray.length) {
			arr[k] = rightArray[j];
			updateLabel(k, rightArray[j], newColor);
			j++;
			k++;
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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

	public static void excute() {
		SwingUtilities.invokeLater(() -> new MergeSortVisualizer().setVisible(true));
	}

}
