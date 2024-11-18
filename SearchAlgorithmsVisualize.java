package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SearchAlgorithmsVisualize extends JFrame {
    protected JTextField inputField, searchField;
    protected JButton searchButton;
    protected JPanel arrayPanel;
    protected JLabel resultLabel;
    protected int[] array;
    protected JLabel[] labels;
    protected final int DELAY = 500;

    public SearchAlgorithmsVisualize() {
        setTitle("Search Visualizer Application");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // Soft blue-gray background color

        // Panel for input fields and search button
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        inputPanel.setBackground(new Color(240, 248, 255));

        inputField = createTextField(20);
        searchField = createTextField(5);
        searchButton = createButton("Tìm kiếm");

        JLabel inputLabel = createLabel("Nhập dữ liệu đầu vào (cách nhau bởi dấu phẩy): ");
        JLabel searchLabel = createLabel("Phần tử cần tìm: ");

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(searchLabel);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        // Panel to display array elements
        arrayPanel = new JPanel();
        arrayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        arrayPanel.setBackground(new Color(245, 245, 245));
        add(arrayPanel, BorderLayout.CENTER);

        // Result label setup
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(new Color(34, 139, 34)); // Forest Green color
        add(resultLabel, BorderLayout.SOUTH);

        // Add action for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String[] numbers = input.split(",");
                array = new int[numbers.length + 1];
                labels = new JLabel[numbers.length];

                try {
                    arrayPanel.removeAll();
                    for (int i = 0; i < numbers.length; i++) {
                        array[i] = Integer.parseInt(numbers[i].trim());
                        labels[i] = createArrayElementLabel(array[i]);
                        arrayPanel.add(labels[i]);
                    }

                    arrayPanel.revalidate();
                    arrayPanel.repaint();
                    resultLabel.setText("");

                    int target = Integer.parseInt(searchField.getText().trim());
                    new Thread(() -> visualize(target)).start();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu, vui lòng chỉ nhập số và phân cách bởi dấu phẩy");
                }
            }
        });
    }

    // Helper method to create a styled button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        return button;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return textField;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Roboto", Font.PLAIN, 14));
        label.setForeground(new Color(47, 79, 79));
        return label;
    }

    protected JLabel createArrayElementLabel(int value) {
        JLabel label = new JLabel(String.valueOf(value));
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setPreferredSize(new Dimension(50, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Roboto", Font.BOLD, 16));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return label;
    }

    public abstract void visualize(int target);

    protected void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
