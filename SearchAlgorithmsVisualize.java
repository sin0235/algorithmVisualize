package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public abstract class SearchAlgorithmsVisualize extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField inputField, searchField;
    protected JButton searchButton;
    protected JPanel arrayPanel;
    protected JLabel resultLabel;
    protected int[] array;
    protected JLabel[] labels;
    protected final int DELAY = 500;

    private final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private final Color SECONDARY_COLOR = new Color(245, 245, 245);
    private final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private final Color TEXT_COLOR = new Color(33, 33, 33);
    private final Color SUCCESS_COLOR = new Color(46, 125, 50);

    public SearchAlgorithmsVisualize() {
        initializeFrame();
        setupComponents();
        setupListeners();
    }

    private void initializeFrame() {
        setTitle("Thuật Toán Tìm Kiếm - Visualization");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(BACKGROUND_COLOR);
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
    }

    private void setupComponents() {
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        arrayPanel = createArrayPanel();
        JScrollPane scrollPane = new JScrollPane(arrayPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        resultLabel = createResultLabel();
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(BACKGROUND_COLOR);
        resultPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        resultPanel.add(resultLabel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Visualization Thuật Toán Tìm Kiếm");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        controlsPanel.setBackground(BACKGROUND_COLOR);

        inputField = createTextField(30);
        searchField = createTextField(8);
        searchButton = createButton("Tìm Kiếm");

        controlsPanel.add(createLabelWithField("Nhập mảng (phân cách bởi dấu phẩy):", inputField));
        controlsPanel.add(createLabelWithField("Giá trị cần tìm:", searchField));
        controlsPanel.add(searchButton);

        panel.add(controlsPanel);
        return panel;
    }

    private JPanel createLabelWithField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);

        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JPanel createArrayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new WrapLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JLabel createResultLabel() {
        JLabel label = new JLabel("");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(SUCCESS_COLOR);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return textField;
    }

    protected JLabel createArrayElementLabel(int value) {
        JLabel label = new JLabel(String.valueOf(value));
        label.setOpaque(true);
        label.setBackground(SECONDARY_COLOR);
        label.setPreferredSize(new Dimension(60, 60));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(TEXT_COLOR);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        return label;
    }

    private void setupListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSearch();
            }
        });

        InputMap inputMap = searchField.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = searchField.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "search");
        actionMap.put("search", new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                searchButton.doClick();
            }
        });
    }

    private void processSearch() {
        try {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                showError("Vui lòng nhập dữ liệu mảng!");
                return;
            }

            String[] numbers = input.split(",");
            array = new int[numbers.length];
            labels = new JLabel[numbers.length];

            arrayPanel.removeAll();

            for (int i = 0; i < numbers.length; i++) {
                try {
                    array[i] = Integer.parseInt(numbers[i].trim());
                    labels[i] = createArrayElementLabel(array[i]);
                    arrayPanel.add(labels[i]);
                } catch (NumberFormatException ex) {
                    showError("Giá trị không hợp lệ: " + numbers[i]);
                    return;
                }
            }

            int target;
            try {
                target = Integer.parseInt(searchField.getText().trim());
            } catch (NumberFormatException ex) {
                showError("Giá trị cần tìm không hợp lệ!");
                return;
            }

            arrayPanel.revalidate();
            arrayPanel.repaint();
            resultLabel.setText("");

            new Thread(() -> visualize(target)).start();

        } catch (Exception ex) {
            showError("Có lỗi xảy ra: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public abstract void visualize(int target);

    protected void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class WrapLayout extends FlowLayout {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return computeSize(target, false);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return computeSize(target, true);
        }

        private Dimension computeSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int hgap = getHgap();
                int vgap = getVgap();
                int w = target.getWidth();

                if (w == 0)
                    w = Integer.MAX_VALUE;

                Insets insets = target.getInsets();
                if (insets == null)
                    insets = new Insets(0, 0, 0, 0);

                int reqdWidth = 0;
                int maxwidth = w - (insets.left + insets.right + hgap * 2);
                int n = target.getComponentCount();
                int x = 0;
                int y = insets.top + vgap;
                int rowHeight = 0;

                for (int i = 0; i < n; i++) {
                    Component c = target.getComponent(i);
                    if (c.isVisible()) {
                        Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                        if ((x == 0) || ((x + d.width) <= maxwidth)) {
                            if (x > 0)
                                x += hgap;
                            x += d.width;
                            rowHeight = Math.max(rowHeight, d.height);
                        } else {
                            x = d.width;
                            y += vgap + rowHeight;
                            rowHeight = d.height;
                        }
                        reqdWidth = Math.max(reqdWidth, x);
                    }
                }
                y += rowHeight + vgap;
                return new Dimension(reqdWidth + insets.left + insets.right, y);
            }
        }
    }
}