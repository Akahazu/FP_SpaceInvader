public class Alien extends Sprite {
    public Alien(int x, int y) {
        super(x, y);
        this.width = 20;
        this.height = 20;
    }
    
    // Gerakan alien horizontal
    public void act(int direction) {
        this.x += direction;
    }
    
    // Gerakan turun
    public void moveDown() {
        this.y += 15;
    }
}
