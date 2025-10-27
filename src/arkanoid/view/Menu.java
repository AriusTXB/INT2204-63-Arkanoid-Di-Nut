package arkanoid.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menu chọn độ khó -> khởi chạy Game tương ứng.
 */
public class Menu extends JFrame {

    public Menu() {
        setTitle("Arkanoid Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JLabel title = new JLabel("Select Difficulty", SwingConstants.CENTER);
        JButton easyButton = new JButton("Easy (1)");
        JButton normalButton = new JButton("Normal (2)");
        JButton hardButton = new JButton("Hard (3)");

        add(title);
        add(easyButton);
        add(normalButton);
        add(hardButton);

        easyButton.addActionListener(e -> startGame(1));
        normalButton.addActionListener(e -> startGame(2));
        hardButton.addActionListener(e -> startGame(3));
    }

    private void startGame(int difficulty) {
        dispose(); // Đóng menu
        Game game = new Game(difficulty);
        JFrame frame = new JFrame("Arkanoid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu().setVisible(true));
    }
}
