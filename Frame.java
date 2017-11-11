import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class Frame
{
	private double mouseX;
	private double mouseY;
	JTextArea tekst;
	JFrame ramka;
	Thread watek;
	Thread klikacz;
	JPanel panelGlowny;
	ThreadGroup grupaWatkowSkanowania = new ThreadGroup("Watki skanowania");
	ThreadGroup grupaWatkowKlikacza = new ThreadGroup("Watki klikacza");
	JTextField pozycjaX;
	JTextField pozycjaY;
	int delay;
	
	public Frame()
	{
		funkcja1();
		
	}
	
	public void funkcja1()
	{
		ramka = new JFrame();
		ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BorderLayout uklad = new BorderLayout();
		
		JPanel panelTla = new JPanel(uklad);
		panelTla.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		
		
		Box obszarPrzyciskow = new Box(BoxLayout.Y_AXIS);
		
		JButton przycisk = new JButton("Start skanowania");
		obszarPrzyciskow.add(przycisk);
		przycisk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				watek = new Thread(grupaWatkowSkanowania, new PozycjaMyszy());
				watek.start();
			}
		});
		
		JButton przycisk2 = new JButton("Stop skanowania");
		obszarPrzyciskow.add(przycisk2);
		przycisk2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				grupaWatkowSkanowania.interrupt();
				tekst.append("\n"+"Zakończono skanowanie pozycji myszy");
			}
		});
		
		JButton przycisk3 = new JButton("Rozpocznij klikanie");
		obszarPrzyciskow.add(przycisk3);
		przycisk3.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				klikacz = new Thread(grupaWatkowKlikacza, new Klikacz());
				klikacz.start();
			}
			
		});
		
		JButton przycisk4 = new JButton("Zakończ klikanie");
		obszarPrzyciskow.add(przycisk4);
		przycisk4.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				grupaWatkowKlikacza.interrupt();
				tekst.append("\n"+"Zakończono klikanie myszą");
			}
			
		});
		
		JLabel podpisCzestotliwościKlikania = new JLabel("W oknie poniżej wpisz częstotliwość kliknięć (milisekundy)");
		obszarPrzyciskow.add(podpisCzestotliwościKlikania);
		
		pozycjaX = new JTextField("");
		obszarPrzyciskow.add(pozycjaX);
		
		JLabel podpisDodawanegoRandomDelay = new JLabel("W oknie poniżej wpisz zakres losowego opóźnienia klikania (milisekundy)");
		obszarPrzyciskow.add(podpisDodawanegoRandomDelay);
		
		pozycjaY = new JTextField("");
		obszarPrzyciskow.add(pozycjaY);
		
		tekst = new JTextArea(15,50);
		tekst.setLineWrap(true);
		tekst.setWrapStyleWord(true);
		tekst.setEditable(false);
		tekst.append("Rozpocznij skanowanie pozycji myszy");
		
		JScrollPane przewijanie = new JScrollPane(tekst);
		przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panelTla.add(BorderLayout.EAST, obszarPrzyciskow);
		ramka.getContentPane().add(panelTla);
		
		BorderLayout siatkaPolWyboru = new BorderLayout();
		panelGlowny = new JPanel(siatkaPolWyboru);
		panelGlowny.add(przewijanie);
		panelTla.add(BorderLayout.WEST, panelGlowny);
		
		ramka.setBounds(50,50,300,300);
		ramka.pack();
		ramka.setVisible(true);
		
		ramka.setFocusable(true);
		tekst.setFocusable(true);
		pozycjaY.setFocusable(true);
		pozycjaX.setFocusable(true);
		panelGlowny.setFocusable(true);
		panelTla.setFocusable(true);
		obszarPrzyciskow.setFocusable(true);
		ramka.requestFocusInWindow();
		
		ramka.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e) 
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) 
			{
				if((Integer.parseInt(pozycjaX.getText()) & Integer.parseInt(pozycjaY.getText())) != 0)
				{
					if(e.getKeyCode() == KeyEvent.VK_UP)
					{
						System.out.println("Klik");
						klikacz = new Thread(grupaWatkowKlikacza, new Klikacz());
						klikacz.start();
					}
				}
					
				
			}

			@Override
			public void keyReleased(KeyEvent e) 
			{
				// TODO Auto-generated method stub
			}
			
		});
		
//		ramka.addKeyListener(new KeyAdapter()
//		{
//			public void keyPressed(KeyEvent e)
//			{
//				
//				if(e.getKeyCode() == KeyEvent.VK_UP)
//				{
//					System.out.println("Klik");
//					klikacz = new Thread(grupaWatkowKlikacza, new Klikacz());
//					klikacz.start();
//				}
//			}
//		});
		
		
	}
	
//	public class Hotkey implements KeyListener
//	{
//		
//		
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//				
//					
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) 
//			{
//				if(e.getKeyCode() == KeyEvent.VK_UP)
//				{
//					klikacz = new Thread(grupaWatkowKlikacza, new Klikacz());
//					klikacz.start();
//				}
//				
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		
//	};
	
	public class PozycjaMyszy implements Runnable
	{
	@Override
	public void run() 
		{
		try 
 	    {
			while(!Thread.currentThread().isInterrupted()) //mouseX !=0 || mouseY !=0
			{			
			   mouseX = MouseInfo.getPointerInfo().getLocation().getX();
			   mouseY = MouseInfo.getPointerInfo().getLocation().getY();
			   String mouseXX = String.valueOf(mouseX);
			   String mouseYY = String.valueOf(mouseY);
			   tekst.setText("Aktualna pozycja myszy"+"\n"+ "X "+mouseXX+" "+"     Y"+mouseYY);
			 	    //System.out.println("Pozycja myszy X i Y"+"\n"+"Y " + mouseYY +" X "+ mouseXX);
				Thread.sleep(1000);
			}  
		}catch (InterruptedException e)
		{
			System.out.println("Zakończono skanowanie pozycji myszy");
		}
		}
	}
	
	public class Klikacz implements Runnable
	{
	
	public int delay = Integer.parseInt(pozycjaX.getText());
	public int randomDelayBetweenClicks = delay +((int) (Math.random() * Integer.parseInt(pozycjaY.getText())));
	public int rate2 = ((int) (Math.random() * delay));
	public int randomWait = (50+ (int) (Math.random()*100));
	
		@Override
		public void run() 
		{
			try 
			{
			    Robot robot = new Robot();
			        while (!Thread.currentThread().isInterrupted()) 
			        {
			           try
			           {
			           Thread.sleep(randomDelayBetweenClicks);
			           robot.mousePress(InputEvent.BUTTON1_MASK);
			           Thread.sleep(randomWait);
					   robot.mouseRelease(InputEvent.BUTTON1_MASK);

			           }catch (Exception e) {}
			           
			        Thread.sleep(10);
			        }
				
			}catch (AWTException  | InterruptedException e)
			{
				System.out.println("Zakończono klikanie myszką");
			}

		}
		
	}
	
}

	
	
	// Dodać Hotkey i dać użytkownikowi możliwość ustawienia hotkey'a i odpalenia nim clickera
	// 



