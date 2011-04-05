package rogueshadow.pingpong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Image;

import javax.swing.JFrame;

public class PingPong implements MouseMotionListener {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 400;
	
	JFrame j;
	Graphics2D g2d;
	Canvas c;
	Image buffer;
	float mouseX;
	float mouseY;
	
	Paddle p1;
	Paddle p2;
	Ball ball = new Ball(WIDTH/2,HEIGHT/2);
	
	private void init() {
		p1 = new Paddle(WIDTH / 2 - 25, 20, 50, 10,1);
		p2 = new Paddle(WIDTH / 2 - 25, HEIGHT - 30, 50, 10,0);
		ball.push(4f, 8f);
		
	}
	
	private void render(Graphics2D g) {
		p1.render(g);
		p2.render(g);
		ball.render(g);
	}

	private void update() {
		//p1.setX(mouseX - p1.getWidth()/2);
		p1.updateAI(ball);
		p2.updateAI(ball);
		
		
		ball.update();
		p1.update();
		p2.update();
		
		if (ball.getX() < 0 || ball.getX() > WIDTH - ball.getSize() )ball.vx*=-1;
		if (ball.getY() < 0 || ball.getY() > HEIGHT - ball.getSize() )ball.vy*=-1;
		if (ball.getShape().intersects(p1.getShape())){
			ball.vy *= -1;
		}
		if ( ball.getShape().intersects(p2.getShape())){
			ball.vy *= -1;
		}
	}
	
	public PingPong() {
		j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setTitle("Ping Pong");
		c = new Canvas();
		c.setSize(WIDTH, HEIGHT);
		c.setVisible(true);
		j.setResizable(false);
		j.add(c);
		j.pack();
		j.setVisible(true);
		buffer = c.getGraphicsConfiguration().createCompatibleImage(WIDTH, HEIGHT);
		g2d = (Graphics2D) buffer.getGraphics();
		c.addMouseMotionListener(this);
		
	}

	public void predraw() {
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.white);
	}

	public void play() {
		init();
		while (true) {
			update();
			predraw();
			render(g2d);
			c.getGraphics().drawImage(buffer, 0, 0, null);
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		PingPong game = new PingPong();
		game.play();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point jloc = j.getLocationOnScreen();
		mouseX = (float)e.getLocationOnScreen().getX();
		mouseY = (float)e.getLocationOnScreen().getY();
		mouseX -= jloc.x;
		mouseY -= jloc.y;
	}
}
