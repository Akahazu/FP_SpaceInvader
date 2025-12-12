import javax.swing.JFrame;

public class SpaceInvadersGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders OOP");
        
        // Memasukkan Board ke dalam Frame
        frame.add(new Board());
        
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}