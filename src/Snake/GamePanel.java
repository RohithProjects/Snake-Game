package Snake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	static final int screen_width = 600;
	static final int screen_height= 600;
	static final int unit_size = 25; 
	static final int game_units = (screen_width*screen_height)/(unit_size*unit_size); 
	static final int delay = 75;
	//snake body position
	final int x[] = new int[game_units]; //x axis
	final int y[] = new int[game_units]; //y axis
	
	int bodyParts = 5;
	int appleEaten = 0;
	int appleX,appleY;
	char direction = 'R';
	boolean gameStarted = false;
	boolean running = false;
	String score = "";
	Timer timer;
	Random random;
    GamePanel(){
    	random = new Random();
    	this.setPreferredSize((new Dimension(screen_width,screen_height)));
    	this.setBackground(Color.black); 
    	this.setFocusable(true); //receive user input
    	this.addKeyListener(new MyKeyAdapter()); //Create a new key listener object 
    }
    //seven methods
    public void startGame() {
		newApple();
		gameStarted = true;
		running = true;
		timer = new Timer(delay,this);
		timer.start();
	}
    public void paintComponent(Graphics g) { 
    	super.paintComponent(g); 
    	draw(g);
    }
    public void draw(Graphics g) {
    	 if(!gameStarted) {
    		 return; 
    	 }
         else if(running) {
    	/* //grid
    	  for(int i=0;i<screen_height/unit_size;i++) {
    		//for y axis 
    		g.drawLine(i*unit_size, 0, i*unit_size, screen_height); 
    		//for x axis 
    		g.drawLine(0, i*unit_size, screen_width, i*unit_size);   
    	 } */
    	  //score
    	  g.setColor(Color.white);
          g.setFont(new Font("SansSerif",Font.PLAIN,20));   
       	  FontMetrics metrics = getFontMetrics(g.getFont());
      	  score = "Score: "+appleEaten;
      	  g.drawString(score, (screen_width-metrics.stringWidth(score))-10, g.getFont().getSize()); 
    	  //apple
    	  g.setColor(Color.red);
    	  g.fillOval(appleX, appleY, unit_size, unit_size);
    	  //snake
    	  for(int i=0;i<bodyParts;i++) {
    		 if(i==0) {
    			g.setColor(Color.green);
    			g.fillRect(x[i], y[i], unit_size, unit_size);   
    			}
    		 else {
    		 	g.setColor(new Color(45,180,0));
    			g.fillRect(x[i], y[i], unit_size, unit_size); 
    		}
    	 }
    	}
    	else {
    		gameOver(g);  
    	}
    }
    public void move() {
    	 for(int i=bodyParts-1;i>0;i--) {
    		 x[i] = x[i-1];
    		 y[i] = y[i-1];
    	 }
    	 switch(direction){
    	   case 'U': 
    		   y[0] -= unit_size;
    		   break;
    	   case 'D':
    		   y[0] +=unit_size;
    		   break;
    	   case 'L':
    		   x[0] -= unit_size;
    		   break;
    	   case 'R':
    		   x[0] +=unit_size; 
    		   break;
    	   
    	 }
    }
    public void newApple() {
    	appleX = random.nextInt((int)screen_width/unit_size)*unit_size;
    	appleY = random.nextInt((int)((screen_height/unit_size)-2)+2)*unit_size;     
    }
    public void checkApple() {
    	if((x[0] == appleX)&&(y[0]==appleY)) {
    		x[bodyParts] = x[bodyParts - 1];
            y[bodyParts] = y[bodyParts - 1]; 
    		bodyParts++;
    		appleEaten++;  
    		newApple();
    	}
    }
    public void checkCollisions() {
    	//check head collides with body
    	for(int i = bodyParts-1; i>0; i--) {
    		if((x[0]==x[i])&&(y[0]==y[i])) {
    			running= false;
    		}
    	}
    	//check head collides with left & right border
    	if(x[0]<0 || (x[0]+unit_size>screen_width)) {
    		running = false;
    	}
    	//check head collides with top & bottom border 
    	if(y[0]<0 || (y[0]+unit_size>screen_height)) { 
    		running = false;
    	}
    	if(!running) {
    		timer.stop();
    		//gives window of current panel,then casts to jframe
    		JFrame topFrame = (JFrame)SwingUtilities.getWindowAncestor(this);
    		if(topFrame instanceof GameFrame) {
    			 ((GameFrame) topFrame).restartButton();  
    		}
    	}
    } 
     
    public void gameOver(Graphics g) {  
    	g.setColor(Color.red);
    	g.setFont(new Font("Ink Free",Font.BOLD,75));
    	FontMetrics metrics = getFontMetrics(g.getFont());
    	String text = "Game Over";
    	g.drawString(text, (screen_width-metrics.stringWidth(text))/2, screen_height/2);
    	//score text
    	g.setColor(Color.white);
    	g.setFont(new Font("Arial",Font.BOLD,22));
    	FontMetrics metrics2 = getFontMetrics(g.getFont());
    	g.drawString(score, (screen_width-metrics2.stringWidth(score))/2,(screen_height/2)+45); 
    	}
	@Override
	public void actionPerformed(ActionEvent e) {
	if(running) {
		move();
    	checkApple();
    	checkCollisions();
	}
	repaint(); 
		
	}  
	public void restart() {
		bodyParts = 5;
		appleEaten = 0;
		direction = 'R';
		running = false;
		gameStarted = false;
		for (int i = 0; i < bodyParts; i++) {
	        x[i] = 0 - (i * unit_size); // tail goes left outside screen
	        y[i] = 0;
	    }

	    repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override 
		public void keyPressed(KeyEvent e) { 
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction= 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				} 
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
    
} 
