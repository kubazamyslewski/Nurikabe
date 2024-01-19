import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface extends JFrame {

    private  final int SIZE;  // Rozmiar planszy
    private JButton[][] buttons;  // Tablica przycisków reprezentujących komórki planszy
    private int[][] intBoard;

    public GameInterface(int[][] intBoard) {
        //Generator generator = new Generator();
        this.intBoard = intBoard;
        this.SIZE = intBoard.length;
        // Konfiguracja głównego okna JFrame
        setTitle("Gra Nurikabe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));
        setResizable(false);

        // Inicjalizacja tablicy przycisków
        buttons = new JButton[SIZE][SIZE];

        // Tworzenie przycisków i dodawanie ich do JFrame
        initializeButtons();

        // Pakowanie i wyświetlanie JFrame
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
                    //button.setText(Integer.toString(intBoard[i][j]));
                    updateButtonIcon(i, j);
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
            if (intBoard[row][col] == 0){
                intBoard[row][col] = -1;
            }
            else if (intBoard[row][col] == -1){
                intBoard[row][col] = 0;
            }
            updateButtonIcon(row, col);
        }
    }

    private void updateButtonIcon(int row, int col) {
        int value = intBoard[row][col];
        if (value == -1){
            // Obsługa zdarzenia kliknięcia przycisku (możesz dostosować tę część)
            String imagePath = "Pictures/" + "zamalowane" + ".jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image originalImage = originalIcon.getImage();

            // Skalowanie obrazu
            int targetSize = 50;  // Docelowy rozmiar przycisku
            Image scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);

            // Utworzenie nowego ImageIcon ze skalowanym obrazem
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            buttons[row][col].setIcon(scaledIcon);
        }
        if (value == 0){
            String imagePath = "Pictures/" + "puste" + ".jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image originalImage = originalIcon.getImage();

            // Skalowanie obrazu
            int targetSize = 50;  // Docelowy rozmiar przycisku
            Image scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);

            // Utworzenie nowego ImageIcon ze skalowanym obrazem
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            buttons[row][col].setIcon(scaledIcon);
        }
        if (value > 0) {
            //String imagePath = "resources/" + value + ".jpg";
            String imagePath = "Pictures/" + value + ".jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image originalImage = originalIcon.getImage();

            // Skalowanie obrazu
            int targetSize = 50;  // Docelowy rozmiar przycisku
            Image scaledImage = originalImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);

            // Utworzenie nowego ImageIcon ze skalowanym obrazem
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            buttons[row][col].setIcon(scaledIcon);
        }

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