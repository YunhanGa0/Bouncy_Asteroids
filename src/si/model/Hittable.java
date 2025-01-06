package si.model;


import java.awt.*;

public interface Hittable {
	public boolean isAlive();
	public int getPoints();
	public boolean isPlayer();
	//public boolean isHit(Bullet b);
	public boolean isHit(Hittable b);
	public Rectangle getHitBox();
}

