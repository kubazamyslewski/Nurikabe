import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface extends JFrame {

    private final int SIZE;  // Rozmiar planszy
    private JButton[][] buttons;  // Tablica przycisków reprezentujących komórki planszy
    private int[][] intBoard;

    public GameInterface(int[][] intBoard) {
        this.intBoard = intBoard;
        this.SIZE = intBoard.length;
        setTitle("Gra Nurikabe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));
        setResizable(false);

        buttons = new JButton[SIZE][SIZE];

        initializeButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initializeButtons() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                button.addActionListener(new CellClickListener(i, j));

                buttons[i][j] = button;

                if (intBoard[i][j] >= 0) {
                    updateButtonIconOnStart(i, j);
                }

                //button.doClick(0);    // <- zabawny item
                add(button);
            }
        }
    }

    private class CellClickListener implements ActionListener {
        private int row;
        private int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            intBoard[row][col] = -1 - intBoard[row][col];
            updateButtonIcon(row, col);
        }
    }

    private void updateButtonIcon(int row, int col) {
        int value = intBoard[row][col];
        String imagePath;
        ImageIcon originalIcon;
        Image originalImage;
        int targetSize = 50;
        Image scaledImage;
        ImageIcon scaledIcon;

        if (value == -1) {
            if (isItSquare(intBoard, row, col)) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (isItSquare(intBoard, row + i, col + j) && intBoard[row + i][col + j] == -1) {
                            imagePath = "Pictures/x.jpg";
                            originalIcon = new ImageIcon(getClass().getResource(imagePath));
                            originalImage = originalIcon.getImage();
                            scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                            scaledIcon = new ImageIcon(scaledImage);
                            buttons[row + i][col + j].setIcon(scaledIcon);
                        }
                    }
                }
            } else {
                imagePath = "Pictures/zamalowane.jpg";
                originalIcon = new ImageIcon(getClass().getResource(imagePath));
                originalImage = originalIcon.getImage();
                scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                scaledIcon = new ImageIcon(scaledImage);

                buttons[row][col].setIcon(scaledIcon);
            }

        }
        if (value == 0) {
            if (isItSquare(intBoard, row, col)) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (!isItSquare(intBoard, row + i, col + j) && (row + i >= 0 && col + j >= 0 && row + i < SIZE && col + j < SIZE)) {
                            if (intBoard[row + i][col + j] == -1) {
                                imagePath = "Pictures/" + "zamalowane" + ".jpg";
                                originalIcon = new ImageIcon(getClass().getResource(imagePath));
                                originalImage = originalIcon.getImage();
                                scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                                scaledIcon = new ImageIcon(scaledImage);
                                buttons[row + i][col + j].setIcon(scaledIcon);
                            }
                        }
                    }
                }

            }
            imagePath = "Pictures/puste.jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
            originalIcon = new ImageIcon(getClass().getResource(imagePath));
            originalImage = originalIcon.getImage();
            scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
            scaledIcon = new ImageIcon(scaledImage);

            buttons[row][col].setIcon(scaledIcon);
        }
        if (value > 0) {
            imagePath = "Pictures/" + value + ".jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
            originalIcon = new ImageIcon(getClass().getResource(imagePath));
            originalImage = originalIcon.getImage();
            scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
            scaledIcon = new ImageIcon(scaledImage);

            buttons[row][col].setIcon(scaledIcon);
        }

    }


    private void updateButtonIconOnStart(int row, int col) {
        int value = intBoard[row][col];
        String imagePath;
        ImageIcon originalIcon;
        Image originalImage;
        int targetSize = 50;
        Image scaledImage;
        ImageIcon scaledIcon;

        if (value == 0) {
            imagePath = "Pictures/puste.jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
            originalIcon = new ImageIcon(getClass().getResource(imagePath));
            originalImage = originalIcon.getImage();
            scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
            scaledIcon = new ImageIcon(scaledImage);

            buttons[row][col].setIcon(scaledIcon);
        }
        if (value > 0) {
            imagePath = "Pictures/" + value + ".jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
            originalIcon = new ImageIcon(getClass().getResource(imagePath));
            originalImage = originalIcon.getImage();
            scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
            scaledIcon = new ImageIcon(scaledImage);

            buttons[row][col].setIcon(scaledIcon);
        }

    }


    boolean isItSquare(int[][] tempBoard, int x, int y) {
        int size = tempBoard.length;
        if (x < 0 || y < 0 || x >= size || y >= size) {
            return false;
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            if ((tempBoard[x - 1][y - 1] == -1) && (tempBoard[x][y - 1] == -1) && (tempBoard[x - 1][y] == -1)) {
                return true;
            }
        }
        if (x - 1 >= 0 && y + 1 < size) {
            if ((tempBoard[x - 1][y + 1] == -1) && (tempBoard[x - 1][y] == -1) && (tempBoard[x][y + 1] == -1)) {
                return true;
            }
        }
        if (x + 1 < size && y + 1 < size) {
            if ((tempBoard[x + 1][y + 1] == -1) && (tempBoard[x][y + 1] == -1) && (tempBoard[x + 1][y] == -1)) {
                return true;
            }
        }
        if (x + 1 < size && y - 1 >= 0) {
            if ((tempBoard[x + 1][y - 1] == -1) && (tempBoard[x + 1][y] == -1) && (tempBoard[x][y - 1] == -1)) {
                return true;
            }
        }
        return false;
    }


    static int selectDifficulty() {
        Object[] options = {"Trudny", "Średni", "Łatwy"};

        // Wyświetlanie okna dialogowego z trzema opcjami
        int selectedOption = JOptionPane.showOptionDialog(
                null,
                "Wybierz jedną z opcji:",
                "Okno z Opcjami",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        int level = 0;
        // Obsługa wybranej opcji
        if (selectedOption == 0) {
            System.out.println("Wybrano poziom trudny");
            level = 3;
        } else if (selectedOption == 1) {
            System.out.println("Wybrano poziom średni");
            level = 2;
        } else if (selectedOption == 2) {
            System.out.println("Wybrano poziom łatwy");
            level = 1;
        } else {
            System.out.println("Nie wybrano żadnej opcji");
        }
        return level;
    }

    static int whichBoard() {
        Object[] options = {"Wygeneruj planszę", "Wczytaj planszę"};

        // Wyświetlanie okna dialogowego z trzema opcjami
        int selectedOption = JOptionPane.showOptionDialog(
                null,
                "Wybierz jedną z opcji:",
                "Okno z Opcjami",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        int option = 0;
        // Obsługa wybranej opcji
        if (selectedOption == 0) {
            System.out.println("Generuję planszę");
            option = 1;
        } else if (selectedOption == 1) {
            System.out.println("Wczytuję planszę");
            option = 2;
        } else {
            System.out.println("Nie wybrano żadnej opcji");
        }
        return option;
    }


}