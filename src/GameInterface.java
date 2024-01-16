

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

public class GameInterface extends JFrame {

    private static final int SIZE = 10;  // Rozmiar planszy
    private JButton[][] buttons;  // Tablica przycisków reprezentujących komórki planszy
    private int[][] intBoard;
    public GameInterface(int[][] intBoard) {
        this.intBoard = intBoard;

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

                if(intBoard[i][j]>0){
                    //button.setText(Integer.toString(intBoard[i][j]));
                    updateButtonIcon(i, j);
                }


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
            // Obsługa zdarzenia kliknięcia przycisku (możesz dostosować tę część)
            JButton clickedButton = (JButton) e.getSource();
            // Przykład: Zmiana tekstu przycisku po kliknięciu
            clickedButton.setText("X");
        }
    }

    private void updateButtonIcon(int row, int col) {
        int value = intBoard[row][col];
        if (value >= 0) {
            String imagePath = "resources/1.jpg";  // Zakładamy, że obrazy mają nazwy image0.jpg, image1.jpg, itd.
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

    public static void main(String[] args) {

        Generator generator = new Generator();
        int[][] generated;
        generated = generator.generateBoard(10);
        int[][] playable = generator.makeBoardToPlay(generated);
        SwingUtilities.invokeLater(() -> new GameInterface(playable));
    }
}
