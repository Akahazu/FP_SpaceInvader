import java.awt.event.KeyEvent;

public class Player extends Sprite {
    private final int BOARD_WIDTH = 400;

    public Player(int x, int y) {
        super(x, y);
        this.width = 30;
        this.height = 20;
    }

    public void move(int dx) {
        x += dx;
        
        // Batasi agar tidak keluar layar
        if (x <= 0) x = 0;
        if (x >= BOARD_WIDTH - width) x = BOARD_WIDTH - width;
    }
    
    // Method untuk menembak
    public Shot shoot() {
        // Munculkan peluru di tengah pesawat
        return new Shot(x + (width / 2) - 2, y);
    }
}