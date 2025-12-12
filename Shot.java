public class Shot extends Sprite {
    public Shot(int x, int y) {
        super(x, y);
        this.width = 4;
        this.height = 10;
    }

    public void move() {
        this.y -= 5; // Kecepatan peluru
        if (this.y < 0) {
            this.visible = false;
        }
    }
}