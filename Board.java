import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 400;
    private final int DELAY = 15;

    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;
    
    private int direction = 1;
    private boolean inGame = true;
    private String message = "Game Over";
    private Timer timer;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        gameInit();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void gameInit() {
        aliens = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                aliens.add(new Alien(30 + (j * 40), 30 + (i * 30)));
            }
        }
        player = new Player(180, 340);
        shot = new Shot(0, 0);
        shot.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            drawObjects(g);
        } else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        // Gambar Player
        g.setColor(Color.GREEN);
        g.fillRect(player.getX(), player.getY(), 30, 20); // Hardcoded width/height visual

        // Gambar Alien
        g.setColor(Color.RED);
        for (Alien a : aliens) {
            if (a.isVisible()) {
                g.fillRect(a.getX(), a.getY(), 20, 20);
            }
        }

        // Gambar Shot
        if (shot.isVisible()) {
            g.setColor(Color.YELLOW);
            g.fillRect(shot.getX(), shot.getY(), 4, 10);
        }

        // Score
        g.setColor(Color.WHITE);
        g.drawString("Musuh: " + aliens.size(), 5, 15);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.WHITE);
        Font font = new Font("Helvetica", Font.BOLD, 20);
        g.setFont(font);
        int w = g.getFontMetrics().stringWidth(message);
        g.drawString(message, (B_WIDTH - w) / 2, B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            updateAliens();
            updateShot();
            checkCollisions();
        }
        repaint();
    }

    private void updateAliens() {
        // Cek apakah ada alien menyentuh pinggir layar
        boolean changeDir = false;
        for (Alien a : aliens) {
            if (a.isVisible()) {
                a.act(direction); 
                if (a.getX() >= B_WIDTH - 20 || a.getX() <= 0) {
                    changeDir = true;
                }
            }
        }
        
        if (changeDir) {
            direction *= -1;
            for (Alien a : aliens) {
                a.moveDown();
            }
        }
    }

    private void updateShot() {
        if (shot.isVisible()) {
            shot.move();
        }
    }

    private void checkCollisions() {
        // Jika peluru kena Alien
        if (shot.isVisible()) {
            Iterator<Alien> it = aliens.iterator();
            while (it.hasNext()) {
                Alien alien = it.next();
                if (alien.isVisible() && shot.getBounds(alien)) {
                    alien.setVisible(false);
                    shot.setVisible(false);
                    it.remove();
                }
            }
        }

        // Cek Menang
        if (aliens.isEmpty()) {
            inGame = false;
            message = "YOU WIN!";
        }

        // Cek Kalah 
        for (Alien a : aliens) {
            if (a.isVisible()) {
                if (a.getBounds(player)) {
                    inGame = false;
                    message = "GAME OVER";
                }
                if (a.getY() > B_HEIGHT - 40) {
                    inGame = false;
                    message = "GAME OVER";
                }
            }
        }
    }

    // Input Keyboard
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (inGame) {
                if (key == KeyEvent.VK_LEFT) player.move(-15);
                if (key == KeyEvent.VK_RIGHT) player.move(15);
                if (key == KeyEvent.VK_SPACE) {
                    if (!shot.isVisible()) {
                        shot = player.shoot();
                        shot.setVisible(true);
                    }
                }
            }
        }
    }
}
