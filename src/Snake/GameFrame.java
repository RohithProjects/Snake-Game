package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame{
	GamePanel panel;
	JButton startBtn; 
   GameFrame(){
	   panel = new GamePanel(); 
	   //button
	   startBtn = new JButton("Start Game");
	   startBtn.setFont(new Font("Arial",Font.BOLD,20));
	   startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
	   panel.setLayout(null);
       startBtn.setBounds(220, 280, 160, 45);
       startBtn.setForeground(Color.WHITE); //text color
       startBtn.setContentAreaFilled(false); //removes default color 
       startBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
       startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
       startBtn.setFocusPainted(false); //removes highlights after enter and click

       panel.add(startBtn);
       
	   startBtn.addActionListener(new ActionListener() { 
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   startBtn.setVisible(false);
			   panel.restart();
			   panel.startGame(); 
			   panel.requestFocusInWindow();
		   }
	   });
	   this.add(panel);
	   this.setTitle("Snake Game");
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setResizable(false);
	   this.pack(); 
	   this.setVisible(true);
	   this.setLocationRelativeTo(null);
	   }
       //restart
       public void restartButton() {
	      startBtn.setText("Try Again");
	      startBtn.setBounds(233, 540, 140, 40);
	      startBtn.setVisible(true); 
   }
}
