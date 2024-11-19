package algorithmVisualize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Menu extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color GRADIENT_START = new Color(41, 128, 185);
    private static final Color GRADIENT_END = new Color(109, 213, 237);
    private static final Color BUTTON_HOVER = new Color(52, 152, 219);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 22);

    public Menu() {
        setTitle("Algorithm Visualizer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 600, 400, 20, 20));

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, GRADIENT_START, getWidth(), getHeight(), GRADIENT_END);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(20, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Algorithm Visualizer");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        JButton closeButton = createIconButton("×");
        closeButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn thoát không?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(closeButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JButton sortButton = createStyledButton("Các thuật toán sắp xếp");
        JButton searchButton = createStyledButton("Các thuật toán tìm kiếm");
        
        sortButton.addActionListener(e -> openSortingAlgorithms());
        searchButton.addActionListener(e -> openSearchingAlgorithms());
        
        buttonPanel.add(sortButton);
        buttonPanel.add(searchButton);

        backgroundPanel.add(headerPanel, BorderLayout.NORTH);
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        add(backgroundPanel);

        addWindowDragability();
        
        setVisible(true);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(BUTTON_HOVER.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(BUTTON_HOVER);
                } else {
                    g2d.setColor(new Color(255, 255, 255, 200));
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                FontMetrics metrics = g2d.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                
                g2d.setColor(Color.BLACK);
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private JButton createIconButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void addWindowDragability() {
        Point[] dragPoint = {null};
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragPoint[0] = e.getPoint();
            }
            
            public void mouseReleased(MouseEvent e) {
                dragPoint[0] = null;
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragPoint[0] != null) {
                    Point currentLocation = getLocation();
                    setLocation(
                        currentLocation.x + e.getX() - dragPoint[0].x,
                        currentLocation.y + e.getY() - dragPoint[0].y
                    );
                }
            }
        });
    }
	private void openSortingAlgorithms() {
		String[] sortingOptions = {
				"Đổi chỗ trực tiếp – Interchange Sort", "Chọn trực tiếp – Selection Sort",
				"Nổi bọt – Bubble Sort", "Chèn trực tiếp – Insertion Sort",
				"Chèn nhị phân – Binary Insertion Sort", "Shaker Sort",
				"Shell Sort", "Heap Sort", "Quick Sort", "Merge Sort", "Radix Sort"
		};

		String choice = (String) JOptionPane.showInputDialog(this, "Chọn thuật toán sắp xếp:",
				"Thuật toán sắp xếp", JOptionPane.QUESTION_MESSAGE, null, sortingOptions, sortingOptions[0]);

		if (choice != null) {
			executeSortAlgorithm(choice);
		}
	}

	private void executeSortAlgorithm(String algorithm) {
		switch (algorithm) {
			case "Đổi chỗ trực tiếp – Interchange Sort":
				InterchangeSortVisualizer.execute();
				break;
			case "Chọn trực tiếp – Selection Sort":
				SelectionSortVisualize.execute();
				break;
			case "Nổi bọt – Bubble Sort":
				BubbleSortVisualizer.execute();
				break;
			case "Chèn trực tiếp – Insertion Sort":
				InsertionSortVisualizer.execute();
				break;
			case "Chèn nhị phân – Binary Insertion Sort":
				BinaryInsertionSortVisualizer.execute();
				break;
			case "Shaker Sort":
				ShakeSortVisualizer.execute();
				break;
			case "Shell Sort":
				ShellSortVisualizer.execute();
				break;
			case "Heap Sort":
				HeapSortVisualizer.execute();
				break;
			case "Quick Sort":
				QuickSortVisualizer.execute();
				break;
			case "Merge Sort":
				MergeSortVisualizer.execute();
				break;
			case "Radix Sort":
				RadixSortVisualizer.execute();
				break;
			default:
				JOptionPane.showMessageDialog(this, "Lựa chọn không hợp lệ.");
		}
	}

	private void openSearchingAlgorithms() {
		String[] searchingOptions = { "Tìm kiếm tuyến tính", "Tìm kiếm nhị phân" };

		String choice = (String) JOptionPane.showInputDialog(this, "Chọn thuật toán tìm kiếm:",
				"Thuật toán tìm kiếm", JOptionPane.QUESTION_MESSAGE, null, searchingOptions, searchingOptions[0]);

		if (choice != null) {
			executeSearchAlgorithm(choice);
		}
	}

	private void executeSearchAlgorithm(String algorithm) {
		switch (algorithm) {
			case "Tìm kiếm tuyến tính":
				LinearSearchVisualize.execute();
				break;
			case "Tìm kiếm nhị phân":
				BinarySearchVisualize.execute();
				break;
			default:
				JOptionPane.showMessageDialog(this, "Lựa chọn không hợp lệ.");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Menu::new);
	}
}
