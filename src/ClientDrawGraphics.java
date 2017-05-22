import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;

public class ClientDrawGraphics extends JFrame{
	private ClientPlayer[] playerArray;
	private Drawing drawing;
	private ClientInputHandler handler;
	private javax.swing.Action anAction;
	private ClientInputHandler inputH;
	private int screenX = 350;
	private int screenY = 250;
	private int maxClients;
	

	public ClientDrawGraphics(ClientInputHandler inputH, int maxClients) {
		this.inputH = inputH;
		initUI();
		this.maxClients = maxClients;
		playerArray = new ClientPlayer[maxClients];
		ClientUpdate.playerArray = playerArray;
		
		
	}

	private void initUI() {

		drawing = new Drawing();
		handler = new ClientInputHandler();
		setVisible(true);
		this.add(drawing);
		setTitle("Points");
		setSize(screenX, screenY);
		setLocationRelativeTo(null);
		drawing.requestFocusInWindow();
		drawing.addKeyListener(inputH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		


		}

		public void Repaint()
		{
			drawing.repaint();
		}
		
		public int getScreenX()
		{
			return screenX;
		}
		public int getScreenY()
		{
			return screenY;
		}

	}

	class Drawing extends JPanel
	{

		private void doDrawing(Graphics g) {
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setBackground(Color.blue);

			for(int i = 0; i < ClientUpdate.playerArray.length; i++)
			{
				if(ClientUpdate.playerArray[i] != null)
				{
					g2d.fillRect(ClientUpdate.playerArray[i].getX(), ClientUpdate.playerArray[i].getY(),ClientUpdate.playerArray[i].getSize(),ClientUpdate.playerArray[i].getSize());
				}
			}
			




		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			doDrawing(g);
		}
		
		


	}
