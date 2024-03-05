package si.model;


import java.awt.*;

public interface Hittable {
	public boolean isAlive();
	public int getPoints();
	public boolean isPlayer();
	public boolean isHit(Bullet b);
	public boolean isRush(Aerolite as);
	public Rectangle getHitBoxR();

	public Polygon getHitBox();
	public boolean isSpacecraft();
}

