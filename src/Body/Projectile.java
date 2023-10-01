package Body;

import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    private boolean alive;
    private long createTime;
    private Character owner;
    
	public Projectile(int x, int y,Character owner) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        this.alive=true;
        this.createTime = System.currentTimeMillis();
        this.owner = owner;
        
    }

	public void setAlive(boolean alive) {
		this.alive=alive;
	}
	
	public boolean isAlive() {
		return alive;
	}
	public long getCreateTime() {
	    return createTime;
	}
	 // create getter ºÍ setter methods
    public Character getOwner() {
        return owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }
}