package algorithmVisualize;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.SwingUtilities;

public class InterchangeSortVisualizer extends AlgorithmSortVisualizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterchangeSortVisualizer() {
		super();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
	}

	@Override
	public String getCode() {
		return """
          for (int i = 0; i < array.length - 1; i++) {
              for (int j = i + 1; j < array.length; j++) {
                  if (array[i] > array[j]) {
                      // Swap array[i] and array[j]
                      int temp = array[i];
                      array[i] = array[j];
                      array[j] = temp;
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
				Thread.sleep(DELAY);

				for (int j = i + 1; j < array.length; j++) {
					highlightLine(2);
					labels[i].setBackground(Color.YELLOW);
					labels[j].setBackground(Color.YELLOW);
					Thread.sleep(DELAY);

					highlightLine(3);
					Thread.sleep(DELAY);

					if (array[i] > array[j]) {
						highlightLine(4);
						Thread.sleep(DELAY);

						highlightLine(5);
						highlightLine(6);
						highlightLine(7);

						logArea.append("Swapping: " + array[i] + "<->" + array[j] + "\n");
						resetLabelColor(i, j);
						panel.setLayout(null);
						swap(i, j);
						Thread.sleep(100);
						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
						panel.revalidate();
						panel.repaint();
					}

					resetLabelColor(i, j);
					Thread.sleep(DELAY);
				}
				labels[i].setBackground(Color.GREEN);
			}
			labels[array.length - 1].setBackground(Color.GREEN);

			long endTime = System.currentTimeMillis();
			logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute() {
		SwingUtilities.invokeLater(() -> new InterchangeSortVisualizer().setVisible(true));
	}
}
