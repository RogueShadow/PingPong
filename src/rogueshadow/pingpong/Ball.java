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
		float angle = (float)(Math.random()*(Math.PI*2));
		float speed = (float)(Math.random()*5+5);
		vx = (float) Math.sin(angle)*speed;
		vy = (float) Math.cos(angle)*speed;
	}
	
	public float[] getPrediction(float endy){
		float dy = Math.abs(endy - y);
		float dyt = dy/Math.abs(vy);
		float xloc = x + vx*dyt;
		while (xloc < 0 || xloc > (640-getSize())){
			if (xloc > 640 - getSize())xloc = 640-getSize() - (xloc%(640-getSize()));
			if (xloc < 0)xloc = Math.abs(xloc);
		}
		prediction[0] = xloc;
		prediction[1] = endy;
		return prediction;
	}

	public void render(Graphics2D g) {
		g.fillOval((int)x,(int)y,getSize(),getSize());
		g.setColor(Color.blue);
		g.fillOval((int)prediction[0],(int)prediction[1],getSize(),getSize());
	}
	public void update(Paddle p1, Paddle p2){
		this.x += this.vx;
		this.y += this.vy;
		if (getShape().intersects(p1.getShape()) || getShape().intersects(p2.getShape())){
			this.x -= this.vx;
			this.y -= this.vy;
			this.vx /= 2f;
			this.vy /= 2f;
			this.x += this.vx;
			this.y += this.vy;
			if (getShape().intersects(p1.getShape()) || getShape().intersects(p2.getShape())){
				this.x -= this.vx;
				this.y -= this.vy;
				this.vy *= -1;
				this.vy *= 2;
				this.vx *= 2;
				this.y += this.vy;
			}else{
				this.vx *= 2;
				this.vy *= 2;
			}
		}
		if (this.x < 0){
			this.x -= this.vx;
			this.vx *= -1;
			//this.x += this.vx;
		}
		if (this.x > PingPong.WIDTH - getSize()){
			this.x -= this.vx;
			this.vx *= -1;
			//this.vx += this.vx;
		}
		if (this.y < -getSize()){
			p1.score();
			reset();
		}
		if (this.y > PingPong.HEIGHT){
			p2.score();
			reset();
		}
	}
	public float getSpeed(){
		return (float)Math.sqrt(vx*vx+vy*vy);
	}
}
