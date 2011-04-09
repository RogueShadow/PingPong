package rogueshadow.pingpong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Paddle {
	float x, y;
	float vx;
	int width, height;
	int speed = 8;
	int position;
	int score = 0;

	public Paddle(float x, float y, int width, int height, int position){
		this.x = x; this.y = y;
		this.width = width; this.height = height;
		this.position = position;
	}
	public Rectangle getShape(){
		return new Rectangle((int)x,(int)y,width,height);
	}
	public void render(Graphics2D g){
		g.setColor(Color.white);
		g.fill(getShape());
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void update() {
		if (x < 0)x = 0;
		if (x > PingPong.WIDTH - width)x = PingPong.WIDTH - width;
	}
	public void score(){
		score += 1;
	}
	public int getScore(){
		return score;
	}
	public void updateAI(Ball ball) {
		if (ball.vy > 0 && position == 0 || ball.vy <= 0 && position == 1){
			float[] p = (position == 0) ? ball.getPrediction(y-ball.getSize()):ball.getPrediction(y+height);
			if (Math.abs(p[0] - x - width/2) > speed)x -= (p[0] < x + width/2) ? speed:-speed; else
				x = p[0] - width/2;
		}else{
			float[] p = {PingPong.WIDTH/2 - width/2};
			if (Math.abs(p[0] - x - width/2) > speed)x -= (p[0] < x + width/2) ? speed:-speed; else
				x = p[0] - width/2;
		}
		
	}

}
