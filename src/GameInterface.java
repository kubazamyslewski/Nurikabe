import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class GameInterface extends JFrame {

    private int SIZE;  // Rozmiar planszy
    private JButton[][] buttons;  // Tablica przycisków reprezentujących komórki planszy
    private int[][] intBoard;
    private int[][] solvedBoard;
    private int[][] startingBoard;
    Solver solver = new Solver();
    Checker checker = new Checker();
    Generator generator = new Generator();
    private ImageIcon[] bufferedIcons = new ImageIcon[33];
    private int targetSize;

    public GameInterface(int[][] intBoard) {
        this.intBoard = intBoard;
        this.SIZE = intBoard.length;
        this.targetSize = 500 / SIZE;
        this.startingBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(intBoard[i], 0, startingBoard[i], 0, SIZE);
        }
        initializeBufferedIcons();

        setTitle("Gra Nurikabe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Dodanie przycisków Solve i Check nad planszą
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(SIZE, SIZE));
        buttonPanel.setPreferredSize(new Dimension(SIZE * targetSize, SIZE * targetSize));
        buttons = new JButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                button.addActionListener(new CellClickListener(i, j));

                buttons[i][j] = button;

                if (intBoard[i][j] >= 0) {
                    updateButtonIconOnStart(i, j);
                }

                buttonPanel.add(button);
            }
        }
        return buttonPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(50, 75));
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new SolveButtonClickListener());
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(new CheckButtonClickListener());
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonClickListener());
        JButton newBoardButton = new JButton("New Board");
        newBoardButton.addActionListener(new NewBoardButtonClickListener());


        JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"Difficulty", "Hard", "Medium", "Easy"});
        difficultyComboBox.addActionListener(new DifficultyComboBoxActionListener());

        JLabel sizeLabel = new JLabel("Size: " + SIZE + "x" + SIZE);


        JButton fileChooserButton = new JButton("Choose File");
        fileChooserButton.addActionListener(new FileChooserButtonClickListener());


        //JButton myBoardsButton = new JButton("My Boards");
        // myBoardsButton.addActionListener(new MyBoardsButtonClickListener());


        controlPanel.add(solveButton);
        controlPanel.add(checkButton);
        controlPanel.add(clearButton);
        controlPanel.add(newBoardButton);
        controlPanel.add(difficultyComboBox);
        controlPanel.add(fileChooserButton);

        return controlPanel;
    }


    private class FileChooserButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nazwaPliku = JOptionPane.showInputDialog("Name of your file:");
            CSVFileReader reader = new CSVFileReader("src/myBoards/" + nazwaPliku + ".csv", ",");
            int[][] boardFromFile = reader.intSwapper(reader.toString());
            SIZE = boardFromFile.length;

            intBoard = boardFromFile;
//            new GameInterface(board);



            startingBoard = new int[SIZE][SIZE];

            solvedBoard = new int[SIZE][SIZE];
            targetSize = 500 / SIZE;
            initializeBufferedIcons();

            initializeBoard();
        }
    }



private class DifficultyComboBoxActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selectedDifficulty = (String) comboBox.getSelectedItem();

        if ("Hard".equals(selectedDifficulty)) {
            setSizeAndInitializeBoard(8);
        } else if ("Medium".equals(selectedDifficulty)) {
            setSizeAndInitializeBoard(6);
        } else if ("Easy".equals(selectedDifficulty)) {
            setSizeAndInitializeBoard(4);
        }

    }


    private void setSizeAndInitializeBoard(int size) {
        // Zmiana rozmiaru planszy
        SIZE = size;
        targetSize = 500 / SIZE;
        initializeBufferedIcons();

        // Ponowna inicjalizacja planszy
        startingBoard = new int[SIZE][SIZE];
        intBoard = new int[SIZE][SIZE];
        solvedBoard = new int[SIZE][SIZE];
        int[][] generated = generator.generateBoard(SIZE);
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(generator.makeBoardToPlay(generated)[i], 0, intBoard[i], 0, SIZE);
            System.arraycopy(intBoard[i], 0, startingBoard[i], 0, SIZE);
        }

        initializeBoard();
    }
}

    private void initializeBoard() {
        // Usunięcie poprzednich komponentów
        getContentPane().removeAll();
        revalidate();
        repaint();

        // Dodanie przycisków Solve i Check nad planszą
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        // Dodanie przycisków do planszy
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setPreferredSize(new Dimension(SIZE * targetSize, SIZE * targetSize));
        add(buttonPanel, BorderLayout.CENTER);

        // Ponowne ustawienie rozmiaru okna
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

private class NewBoardButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int[][] generated = generator.generateBoard(SIZE);
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(generator.makeBoardToPlay(generated)[i], 0, intBoard[i], 0, SIZE);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateButtonIcon(i, j);
            }
        }
    }
}

private class ClearButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (intBoard[i][j] == -1) {
                    intBoard[i][j] = 0;
                    buttons[i][j].setIcon(bufferedIcons[31]);
                }
            }
        }
    }
}

private class SolveButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        solvedBoard = solver.solve(intBoard, 50_000, 0);
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(solvedBoard[i], 0, intBoard[i], 0, SIZE);
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateButtonIcon(i, j);
            }
        }
    }
}

private class CheckButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (checker.IsCorrect(intBoard)) {
            JOptionPane.showMessageDialog(null, "Correct!", "Check", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect!", "Check", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private class CellClickListener implements ActionListener {
    private final int row;
    private final int col;

    public CellClickListener(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (intBoard[row][col] < 1) {
            intBoard[row][col] = -1 - intBoard[row][col];
            updateButtonIcon(row, col);
        }
    }

}

    private void updateButtonIcon(int row, int col) {
        int value = intBoard[row][col];

        if (value == -1) {
            if (isItSquare(intBoard, row, col)) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (row + i >= 0 && row + i < SIZE && col + j >= 0 && col + j < SIZE) {
                            if (intBoard[row + i][col + j] == -1) {
                                if (isItSquare(intBoard, row + i, col + j)) {
                                    buttons[row + i][col + j].setIcon(bufferedIcons[32]);//x
                                }
                            }
                        }
                    }
                }
            } else {
                buttons[row][col].setIcon(bufferedIcons[33]);
            }

        }
        if (value == 0) {
            if (isItSquare(intBoard, row, col)) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (!isItSquare(intBoard, row + i, col + j) && (row + i >= 0 && col + j >= 0 && row + i < SIZE && col + j < SIZE)) {
                            if (intBoard[row + i][col + j] == -1) {
                                buttons[row + i][col + j].setIcon(bufferedIcons[33]);
                            }
                        }
                    }
                }
            }
            buttons[row][col].setIcon(bufferedIcons[31]);
        }
        if (value > 0) {
            buttons[row][col].setIcon(bufferedIcons[value]);
        }

    }


    private void updateButtonIconOnStart(int row, int col) {
        int value = intBoard[row][col];
        if (value == 0) {
            buttons[row][col].setIcon(bufferedIcons[31]);
        }
        if (value > 0) {
            buttons[row][col].setIcon(bufferedIcons[value]);
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
            return (tempBoard[x + 1][y - 1] == -1) && (tempBoard[x + 1][y] == -1) && (tempBoard[x][y - 1] == -1);
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

    private void initializeBufferedIcons() {
        bufferedIcons = new ImageIcon[34];
        String imagePath;
        ImageIcon originalIcon;
        Image originalImage;
        //int targetSize = 40;
        Image scaledImage;
        for (int i = 1; i <= 30; i++) {
            imagePath = "Pictures/" + i + ".jpg";
            originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
            originalImage = originalIcon.getImage();
            scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
            bufferedIcons[i] = new ImageIcon(scaledImage);
        }
        imagePath = "Pictures/puste.jpg";
        originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        originalImage = originalIcon.getImage();
        scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
        bufferedIcons[31] = new ImageIcon(scaledImage);

        imagePath = "Pictures/x.jpg";
        originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        originalImage = originalIcon.getImage();
        scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
        bufferedIcons[32] = new ImageIcon(scaledImage);

        imagePath = "Pictures/zamalowane.jpg";
        originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        originalImage = originalIcon.getImage();
        scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
        bufferedIcons[33] = new ImageIcon(scaledImage);
    }


}