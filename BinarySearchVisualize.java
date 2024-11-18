package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinarySearchVisualize extends SearchAlgorithmsVisualize {
    public BinarySearchVisualize(){
        super();
    }

    public void visualize(int target){
        binarySearch(0, labels.length - 1, target);
    }

    private void binarySearch(int left, int right, int target) {
        if (left > right) {
            resultLabel.setText("Not Found");
            resetColor();
            return;
        }

        int middle = (left + right) / 2;
        labels[middle].setBackground(Color.YELLOW);

        delay();

        if (array[middle] == target) {
            resultLabel.setText("Phần tử được tìm thấy ở vị trí " + middle);
            labels[middle].setBackground(Color.GREEN);
            return;
        } else if (array[middle] < target) {
            resetColor();
            updateRange(middle, right, Color.CYAN);
            binarySearch(middle + 1, right, target);
        } else {
            resetColor();
            updateRange(left, middle, Color.CYAN);
            binarySearch(left, middle - 1, target);
        }
    }

    private void resetColor() {
        for (JLabel label : labels) {
            label.setBackground(Color.white);
        }
    }

    private void updateRange(int from, int to, Color color) {
        for (int i = from; i <= to; i++) {
            labels[i].setBackground(color);
        }
        delay();
    }

    public static void execute() {
        SwingUtilities.invokeLater(() -> new BinarySearchVisualize().setVisible(true));
    }
}
