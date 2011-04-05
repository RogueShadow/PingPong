package rogueshadow.pingpong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball {
	float x;
	float y;
	float vx;
	float vy;
	int size = 16;
	
	float[] prediction = {0,0};
	
	Rectangle shape;

	public Ball(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getShape(){
		return new Rectangle((int)x,(int)y,getSize(),getSize());
	}
	int getSize() {
		return size;
	}

	public void push(float x, float y){
		vx += x;
		vy += y;
	}
	
	public void pull(float x, float y){
		vx -= x;
		vy -= y;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
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
	public void reset(){
		setX(PingPong.WIDTH/2);
		setY(PingPong.HEIGHT/2);
		vx = (float)Math.random()*10;
		vy = (float)Math.random()*30;
	}
	
	public float[] getPrediction(float endy){
		float dy = Math.abs(endy - y);
		float dyt = dy/Math.abs(vy);
		float xloc = x + vx*dyt;
		if (xloc > 640 - getSize()){
			xloc = 640 - (xloc%(640-getSize()));
		}
		if (xloc < 0){
			xloc = Math.abs(xloc);
		}
		prediction[0] = xloc;
		prediction[1] = endy;
		return prediction;
	}

	public void render(Graphics2D g) {
		g.fill(getShape());
		g.setColor(Color.green);
		g.fillOval((int)prediction[0],(int)prediction[1],5,5);
	}
	public void update(){
		this.x += this.vx;
		this.y += this.vy;
	}
	public float getSpeed(){
		return (float)Math.sqrt(vx*vx+vy*vy);
	}
}
