package algorithmVisualize;

import javax.swing.JOptionPane;

public class Menu {
	public static void main(String[] args) {
		boolean flag = true;
		while (flag) {
			String[] options = { "Các thuật toán sắp xếp", "Các thuật toán tìm kiếm", "Thoát" };
			int luaChon = JOptionPane.showOptionDialog(null, "Chọn thuật toán muốn trực quan:",
					"Algorithm Visualizer Application", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
					null, options, options[0]);

			switch (luaChon) {
			case 0:
				luaChonThuatToanSapXep();
				flag = false;
				break;
			case 1:
				luaChonThuatToanTimKiem();
				flag = false;
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Pái pai, hẹn gặp lại(>_<)");
				System.exit(0);
			default:
				System.exit(0);
			}
		}
	}

	private static void luaChonThuatToanSapXep() {
		String[] sortingOptions = { "Đổi chỗ trực tiếp – Interchange Sort", "Chọn trực tiếp – Selection Sort",
				"Nổi bọt – Bubble Sort", "Chèn trực tiếp – Insertion Sort", "Chèn nhị phân – Binary Insertion Sort",
				"Shaker Sort", "Shell Sort", "Heap Sort", "Quick Sort", "Merge Sort", "Radix Sort" };

		String luaChon = (String) JOptionPane.showInputDialog(null, "Chọn thuật toán sắp xếp:", "Thuật toán sắp xếp",
				JOptionPane.QUESTION_MESSAGE, null, sortingOptions, sortingOptions[0]);

		if (luaChon != null) {
			sapXep(luaChon);
		}
	}

	private static void sapXep(String algorithm) {
		switch (algorithm) {
		case "Đổi chỗ trực tiếp – Interchange Sort":
			InterchangeSortVisualizer.excute();
			break;
		case "Chọn trực tiếp – Selection Sort":
			SelectionSortVisualize.excute();
			break;
		case "Nổi bọt – Bubble Sort":
			BubbleSortVisualizer.excute();
			break;
		case "Chèn trực tiếp – Insertion Sort":
			InsertionSortVisualizer.excute();
			break;
		case "Chèn nhị phân – Binary Insertion Sort":
			BinaryInsertionSortVisualizer.excute();
			break;
		case "Shaker Sort":
			ShakeSortVisualizer.excute();
			break;
		case "Shell Sort":
			ShellSortVisualizer.excute();
			break;
		case "Heap Sort":
			HeapSortVisualizer.excute();
			break;
		case "Quick Sort":
			QuickSortVisualizer.excute();
			break;
		case "Merge Sort":
			MergeSortVisualizer.excute();
			break;
		case "Radix Sort":
			RadixSortVisualizer.excute();
			break;
		default:
			JOptionPane.showMessageDialog(null, "Lựa chọn không hợp lệ.");
		}
	}

	private static void luaChonThuatToanTimKiem() {
		String[] searchingOptions = { "Tìm kiếm tuyến tính", "Tìm kiếm nhị phân" };

		String selectedAlgorithm = (String) JOptionPane.showInputDialog(null, "Chọn thuật toán tìm kiếm:",
				"Thuật toán tìm kiếm", JOptionPane.QUESTION_MESSAGE, null, searchingOptions, searchingOptions[0]);

		if (selectedAlgorithm != null) {
			timKiem(selectedAlgorithm);
		}
	}

	private static void timKiem(String algorithm) {
		switch (algorithm) {
		case "Tìm kiếm tuyến tính":
			break;
		case "Tìm kiếm nhị phân":
			break;
		}
	}
}
