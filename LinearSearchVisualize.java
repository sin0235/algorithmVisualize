package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LinearSearchVisualize extends SearchAlgorithmsVisualize {

    public LinearSearchVisualize(){
        super();
    }

    public void visualize(int target){
        linearSearch(target);
    }


    private void linearSearch(int target) {
        int n = array.length - 1;
        array[n] = target;
        int i = 0;
        while (array[i] != target) {
            labels[i].setBackground(Color.YELLOW);
            delay();
            i++;
            if(array[i] == target && i < n){
                labels[i].setBackground(Color.GREEN);
            }
        }

        if (i < n) {
            resultLabel.setText("Phần tử được tìm thấy ở vị trí " + i);
        } else {
            resultLabel.setText("Not Found");
        }

    }

    public static void execute() {
        SwingUtilities.invokeLater(() -> new LinearSearchVisualize().setVisible(true));
    }
}
