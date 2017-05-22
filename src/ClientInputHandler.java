import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

public class ClientInputHandler  implements ActionListener, KeyListener{

	private boolean left,right,up,down;
	javax.swing.Timer t;

	public ClientInputHandler()
	{
		t= new javax.swing.Timer(10,this);
		t.start();
	}




	public boolean isLeft() {
		return left;
	}
	public boolean isRight() {
		return right;
	}
	public boolean isUp() {
		return up;
	}
	public boolean isDown() {
		return down;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TO DO Auto-generated method stub
	

	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			up = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}

	}
	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			up = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down = false;
		}

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}




}
