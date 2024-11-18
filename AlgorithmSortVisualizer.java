package algorithmVisualize;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultHighlighter;

import java.awt.*;

public abstract class AlgorithmSortVisualizer extends JFrame {
    protected JPanel panel;
    protected JTextField inputField;
    protected JButton startButton;
    protected JTextArea codeArea;
    protected JTextArea logArea;
    protected int[] array;
    protected JLabel[] labels;
    public int DELAY = 500;
    public Color originalColor = new Color(230, 247, 255);  // Lighter blue background
    private static final Color ACCENT_COLOR = new Color(24, 144, 255);  // Modern blue accent
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);  // Light gray background
    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);

    public AlgorithmSortVisualizer() {
        setupMainFrame();
        setupInputPanel();
        setupVisualizationPanel();
        setupInfoPanel();
        
        codeArea.setText(getCode());
        startButton.addActionListener(e -> onSearchAction());
    }

    private void setupMainFrame() {
        setTitle("Algorithm Visualizer");
        setSize(1200, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void setupInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Title Panel
        JLabel titleLabel = new JLabel("Algorithm Visualization");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Input Controls Panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        controlsPanel.setBackground(BACKGROUND_COLOR);

        JLabel inputLabel = new JLabel("Nhập dữ liệu đầu vào cách nhau bởi dấu phẩy:");
        inputLabel.setFont(MAIN_FONT);
        inputField = createStyledTextField(30);
        startButton = createStyledButton("Visualize");

        controlsPanel.add(inputLabel);
        controlsPanel.add(inputField);
        controlsPanel.add(startButton);

        inputPanel.add(titleLabel);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(controlsPanel);
        add(inputPanel, BorderLayout.NORTH);
    }

    private void setupVisualizationPanel() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        panel.setBackground(Color.WHITE);
        panel.setBorder(createRoundedBorder("Trực quan hóa thuật toán"));
        add(panel, BorderLayout.CENTER);
    }

    private void setupInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        infoPanel.setBackground(BACKGROUND_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        codeArea = createTextArea("Mã giả thuật toán", new Color(250, 250, 250));
        logArea = createTextArea("Thực thi", Color.WHITE);

        JScrollPane codeScroll = createStyledScrollPane(codeArea);
        JScrollPane logScroll = createStyledScrollPane(logArea);

        infoPanel.add(codeScroll);
        infoPanel.add(logScroll);
        add(infoPanel, BorderLayout.SOUTH);
    }

    private JScrollPane createStyledScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(BACKGROUND_COLOR);
        return scrollPane;
    }

    private JTextArea createTextArea(String title, Color bgColor) {
        JTextArea area = new JTextArea(12, 30);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setEditable(false);
        area.setBackground(bgColor);
        area.setBorder(createRoundedBorder(title));
        return area;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(MAIN_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 36));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(HEADER_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(64, 169, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
        });
        
        return button;
    }

    protected JLabel createLabel(int value) {
        JLabel label = new JLabel(String.valueOf(value));
        label.setOpaque(true);
        label.setBackground(originalColor);
        label.setPreferredSize(new Dimension(60, 60));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2, true));
        return label;
    }

    private Border createRoundedBorder(String title) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                HEADER_FONT,
                ACCENT_COLOR
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    // Rest of the methods remain the same...
    protected void highlightLine(int lineNumber) {
        try {
            int start = codeArea.getLineStartOffset(lineNumber);
            int end = codeArea.getLineEndOffset(lineNumber);
            codeArea.getHighlighter().removeAllHighlights();
            codeArea.getHighlighter().addHighlight(start, end, 
                new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 255, 200)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	protected void swap(int i, int j) {
	    // Lưu trữ vị trí và kích thước ban đầu
	    Point startPos1 = labels[i].getLocation();
	    Point startPos2 = labels[j].getLocation();
	    Dimension originalSize = labels[i].getSize();
	    Color originalColor = labels[i].getBackground();

	    // Constants cho animation
	    final int STEPS = 30;
	    final int DELAY = 20;
	    final int MAX_SCALE = 8;
	    final int ARC_HEIGHT = 50; // Độ cao của đường cong

	    // Timer để tạo animation mượt mà
	    Timer timer = new Timer(DELAY, null);
	    final int[] step = {0};

	    timer.addActionListener(e -> {
	        if (step[0] >= STEPS) {
	            timer.stop();
	            // Hoàn thành hoán đổi
	            finalizeSwap(i, j, startPos1, startPos2, originalSize, originalColor);
	            return;
	        }

	        // Tính toán progress với easing function
	        double progress = (double) step[0] / STEPS;
	        double easedProgress = easeInOutQuad(progress);

	        // Tính toán đường cong Bezier cho chuyển động
	        Point pos1 = calculateBezierPoint(startPos1, startPos2, easedProgress, ARC_HEIGHT);
	        Point pos2 = calculateBezierPoint(startPos2, startPos1, easedProgress, ARC_HEIGHT);

	        // Tính toán scale với bouncing effect
	        int scale = calculateScale(progress, MAX_SCALE);

	        // Cập nhật vị trí và kích thước
	        updateLabel(labels[i], pos1, originalSize, scale, Color.ORANGE);
	        updateLabel(labels[j], pos2, originalSize, -scale, Color.ORANGE);

	        step[0]++;
	        panel.repaint();
	    });

	    // Hoán đổi giá trị trong mảng
	    int temp = array[i];
	    array[i] = array[j];
	    array[j] = temp;

	    // Bắt đầu animation
	    timer.start();

	    // Đợi animation hoàn thành
	    try {
	        Thread.sleep(STEPS * DELAY + 50);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}

	// Hàm easing để tạo chuyển động mượt mà
	private double easeInOutQuad(double t) {
	    return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
	}

	// Tính toán điểm trên đường cong Bezier
	private Point calculateBezierPoint(Point start, Point end, double t, int arcHeight) {
	    // Điểm kiểm soát cho đường cong Bezier
	    Point control = new Point(
	        (start.x + end.x) / 2,
	        Math.min(start.y, end.y) - arcHeight
	    );

	    // Công thức đường cong Bezier bậc 2
	    int x = (int) (Math.pow(1 - t, 2) * start.x +
	                   2 * (1 - t) * t * control.x +
	                   Math.pow(t, 2) * end.x);
	    int y = (int) (Math.pow(1 - t, 2) * start.y +
	                   2 * (1 - t) * t * control.y +
	                   Math.pow(t, 2) * end.y);

	    return new Point(x, y);
	}

	// Tính toán scale với hiệu ứng bounce
	private int calculateScale(double progress, int maxScale) {
	    // Tạo hiệu ứng bounce nhẹ ở giữa animation
	    double bounce = Math.sin(progress * Math.PI) * maxScale;
	    return (int) bounce;
	}

	// Cập nhật thuộc tính của label
	private void updateLabel(JLabel label, Point position, Dimension originalSize, int scale, Color color) {
	    label.setLocation(position);
	    label.setSize(originalSize.width + scale, originalSize.height + scale);
	    label.setBackground(color);
	}

	// Hoàn thiện hoán đổi
	private void finalizeSwap(int i, int j, Point startPos1, Point startPos2, 
	                         Dimension originalSize, Color originalColor) {
	    // Hoán đổi text
	    String tempText = labels[i].getText();
	    labels[i].setText(labels[j].getText());
	    labels[j].setText(tempText);

	    // Reset vị trí và thuộc tính
	    labels[i].setLocation(startPos2);
	    labels[j].setLocation(startPos1);
	    labels[i].setSize(originalSize);
	    labels[j].setSize(originalSize);
	    labels[i].setBackground(originalColor);
	    labels[j].setBackground(originalColor);

	    panel.revalidate();
	    panel.repaint();
	}


	protected void resetLabelColor(int i, int j) {
		labels[i].setBackground(originalColor);
		labels[j].setBackground(originalColor);
	}

	protected void delay() {
		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void onSearchAction() {
		String input = inputField.getText();
		String[] numbers = input.split(",");
		array = new int[numbers.length];
		labels = new JLabel[numbers.length];

		try {
			panel.removeAll();
			for (int i = 0; i < numbers.length; i++) {
				array[i] = Integer.parseInt(numbers[i].trim());
				labels[i] = createLabel(array[i]);
				panel.add(labels[i]);
			}
			panel.revalidate();
			panel.repaint();
			logArea.setText("");
			new Thread(this::visualize).start();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi dữ liệu, vui lòng chỉ nhập số và phân cách bởi dấu phẩy");
		}
	}

	public abstract String getCode();

	public abstract void visualize();
}
