public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    
    // Untuk mengecek Collision
    public boolean getBounds(Sprite other) {
        return (x < other.x + other.width && 
                x + width > other.x &&
                y < other.y + other.height && 
                y + height > other.y);
    }
}
