import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class gamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; //size of each side of  unit in the game i.e. length of each side of box or pixel
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;  //number of units in the game
	static final int DELAY = 80 ; //HIGHER the delay number slower will be the game
	
	//x and y coordinate of different parts of snake body
	int xcor[] = new int[GAME_UNITS];
	int ycor[] = new int[GAME_UNITS];
	
	int bodyParts = 5;//we start with 5 length snake i.e. 5 box
	
	int appleseaten; //to count number of apples eaten
	
	//coordinates of apple
	int appleX; 
	int appleY;
	
	//direction in which snake is moving. Initially Right
	char direction = 'R';
	
	boolean running = false;
	
	Timer time;
	
	Random random;
	
	gamePanel(){
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH , SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		random = new Random();
		gameStart();  // if we are calling function of same class we need not to use object to call method
	
	}
	
	public void gameStart(){
	
		createApple();
		running = true;
		time = new Timer(DELAY , this);
		time.start();
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) 
		{
			//We will draw a grid to visualize better
		//	for(int i=0 ; i<SCREEN_HEIGHT/UNIT_SIZE ; i++)
			///	g.drawLine(i*UNIT_SIZE , 0 , i*UNIT_SIZE , SCREEN_HEIGHT); // x axis is fixed , y start from 
																		//zero and goes till screen size
			//for(int i=0 ; i<SCREEN_HEIGHT/UNIT_SIZE ; i++)
				//g.drawLine(0 , i*UNIT_SIZE , SCREEN_WIDTH , i*UNIT_SIZE);
		
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);  //size of each apple is equal size of each cell
		
			for(int i=0 ; i<bodyParts ; i++) {
				if(i == 0)
				{
					g.setColor(Color.green);
					g.fillRect(xcor[i], ycor[i], UNIT_SIZE,UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45,180,0));
					g.fillRect(xcor[i], ycor[i], UNIT_SIZE,UNIT_SIZE);
				}
			
			}
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free" , Font.BOLD , 50));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("socre : " + appleseaten, (SCREEN_WIDTH - metrics.stringWidth("socre : " + appleseaten))/2, 50);
		}
		
		else {
			gameOver(g);
		}
		
	}
	public void move() {
		
		for(int i=bodyParts ; i>0 ; i--)
		{
			xcor[i] = xcor[i-1];
			ycor[i] = ycor[i-1];
		}
		
		switch(direction)
		{
			case 'U' :
				ycor[0] = ycor[0] - UNIT_SIZE;
				break;
			case 'D' : 
				ycor[0] = ycor[0] + UNIT_SIZE;
				break;
			case 'L':
				xcor[0] = xcor[0] - UNIT_SIZE;
				break;
			case 'R': 
				xcor[0] = xcor[0] + UNIT_SIZE;
				break;
		}
	}
	public void checkAppleEaten() {
		if((xcor[0] == appleX) && (ycor[0] == appleY))
		{
			bodyParts++;
			appleseaten++;
			createApple();
		}
	}
	public void createApple() {
		
		//nextint fetches number of boxes in a row . suppose we get randomly 16 . Then 16 * unitsize will give
		//position of the apple
		appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE))*UNIT_SIZE; 
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void checkCollision() {
		
		//when head coolides with any body parts
		for(int i= bodyParts; i>0 ; i--) {
			if((xcor[0] == xcor[i]) && (ycor[0] == ycor[i]))
				running = false;
		}
		
		//to check for left border collison - 
		if(xcor[0] < 0)
			running = false;
		
		//for right border collision -
		if(xcor[0] > SCREEN_WIDTH)
			running = false;
		
		//for upper border collision-
		if(ycor[0] <0)
			running = false;
		
		//for lower border collision - 
		if(ycor[0] > SCREEN_HEIGHT)
			running = false;
		
		if(running == false)
			time.stop();
	}
	public void gameOver(Graphics g) {
		
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free" , Font.BOLD , 50));
		FontMetrics metrics0 = getFontMetrics(g.getFont());
		g.drawString("socre : " + appleseaten, (SCREEN_WIDTH - metrics0.stringWidth("socre : " + appleseaten))/2, 50);
		
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free" , Font.BOLD , 50));
		
		//setting game over message on the center
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_LEFT : //will give key code for left button
					if(direction != 'R') { 
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT : 
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP : 
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN : 
					if(direction != 'U') {
						direction = 'D';
					}
					break;
			}

		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkAppleEaten();
			checkCollision();
		}
		repaint();
		
	}
}
