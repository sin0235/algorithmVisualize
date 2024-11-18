package algorithmVisualize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

import static java.lang.Thread.sleep;

@SuppressWarnings("ALL")
public class InsertionSortVisualizer extends AlgorithmSortVisualizer {

    public InsertionSortVisualizer() {
        super();
    }

    @Override
    public String getCode() {
        return """
                for (int i = 1; i < array.length; i++) {
                    int key = array[i];
                    int j = i - 1;
                    while (j >= 0 && array[j] > key) {
                        array[j + 1] = array[j];
                        j = j - 1;
                    }
                    array[j + 1] = key;
                }
                """;
    }

    @Override
    public void visualize() {
        int len = array.length;

        try {
            long startTime = System.currentTimeMillis();

            for (int i = 1; i < len; i++) {
                int tmp = array[i];
                int j = i - 1;

                JLabel tempLabel = new JLabel(String.valueOf(tmp));
                tempLabel.setFont(new Font("Roboto", Font.BOLD, 18));
                tempLabel.setOpaque(true);
                tempLabel.setBackground(Color.YELLOW);
                tempLabel.setForeground(Color.RED);
                tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
                tempLabel.setPreferredSize(new Dimension(50, 50));

                panel.add(tempLabel);
                panel.setComponentZOrder(tempLabel, 0);
                tempLabel.setLocation(labels[i].getLocation());
                labels[i].setText("");
				delay();
                panel.revalidate();
                panel.repaint();


                while (j >= 0 && array[j] > tmp) {
                    logArea.append("Dời " + array[j] + " từ index " + j + " sang index " + (j + 1) + "\n");


                    animateShift(labels[j], labels[j + 1].getX(), labels[j + 1].getY());

                    array[j + 1] = array[j];

                    labels[j + 1].setText(String.valueOf(array[j]));
                    labels[j].setText("");
                    sleep(200);
                    j--;
                }


                array[j + 1] = tmp;
                logArea.append("Chèn thành công " + tmp + " vào vị trí index " + (j + 1) + "\n");

                animateShift(tempLabel, labels[j + 1].getX(), labels[j + 1].getY());
				sleep(700);
                labels[j + 1].setText(String.valueOf(tmp));
                tempLabel.setVisible(false);
                panel.remove(tempLabel);
                labels[j + 1].setBackground(Color.ORANGE);

                tempLabel.setText("");
                panel.revalidate();
                panel.repaint();

                labels[j + 1].setBackground(originalColor);

            }

            for (JLabel label : labels) {
                label.setBackground(Color.GREEN);
            }

            long endTime = System.currentTimeMillis();
            logArea.append("Thuật toán kết thúc sau: " + (endTime - startTime) + " ms\n");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void animateShift(JLabel label, int targetX, int targetY) {
        int startX = label.getX();
        int startY = label.getY();
        int totalSteps = 10;

        for (int step = 0; step <= totalSteps; step++) {
            final int x = startX + (int) ((targetX - startX) * (step / (double) totalSteps));
            final int y = startY + (int) ((targetY - startY) * (step / (double) totalSteps));

            try {
                SwingUtilities.invokeLater(() -> label.setLocation(x, y));
                Thread.sleep(30); // Reduced sleep time for smoother animation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ensure final position is exactly at target
        label.setLocation(targetX, targetY);
    }

    public static void execute() {
        SwingUtilities.invokeLater(() -> new InsertionSortVisualizer().setVisible(true));
    }
}
