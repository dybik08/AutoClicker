import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


public class Frame
{
	private double mouseX;
	private double mouseY;
	JTextArea tekst;
	JFrame ramka;
	Thread watek;
	JPanel panelGlowny;
	ThreadGroup grupaWatkow = new ThreadGroup("Grupa w�tk�w");
	
	
	public Frame()
	{
	
		funkcja1();
		
	}
	
	public void funkcja1()
	{
		ramka = new JFrame();
		ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		
		BorderLayout uklad = new BorderLayout();
		
		JPanel panelTla = new JPanel(uklad);
		panelTla.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		Box obszarPrzyciskow = new Box(BoxLayout.Y_AXIS);
		
		
//		panelGlowny = new JPanel();
		
		JButton przycisk = new JButton("Start");
		obszarPrzyciskow.add(przycisk);
		
//		
//		JScrollPane przewijanie = new JScrollPane(tekst);
//		przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		przycisk.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				watek = new Thread(grupaWatkow, new PozycjaMyszy());
				watek.start();
				
			}
			
		});
		
		JButton przycisk2 = new JButton("Stop");
		obszarPrzyciskow.add(przycisk2);
		przycisk2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				grupaWatkow.interrupt();
				tekst.append("\n"+"Zako�czono skanowanie pozycji myszy");
			}
			
		});
		
//		panelGlowny.add(przycisk);
//		panelGlowny.add(przycisk2);
//		panelGlowny.add(przewijanie);
		
//		ramka.getContentPane().add(BorderLayout.WEST,przycisk);
//		ramka.getContentPane().add(BorderLayout.EAST, przycisk2);
//		ramka.getContentPane().add(BorderLayout.CENTER,tekst);
		
		tekst = new JTextArea(15,50);
		tekst.setLineWrap(true);
		tekst.setWrapStyleWord(true);
		tekst.setEditable(false);
		tekst.append("Rozpocznij skanowanie pozycji myszy");
		//obszarPrzyciskow.add(tekst);
		
		JScrollPane przewijanie = new JScrollPane(tekst);
		przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panelTla.add(BorderLayout.EAST, obszarPrzyciskow);
		
		ramka.getContentPane().add(panelTla);
		
		//ramka.getContentPane().add(BorderLayout.CENTER, przewijanie);
//		ramka.setSize(400,400);
//		ramka.setVisible(true);
		
		BorderLayout siatkaPolWyboru = new BorderLayout();
		
		
		
		panelGlowny = new JPanel(siatkaPolWyboru);
		//panelGlowny.add(tekst);
		panelGlowny.add(przewijanie);
		
		
		
//		JScrollPane przewijanie = new JScrollPane(tekst);
//		przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		panelTla.add(BorderLayout.WEST, panelGlowny);
		ramka.setBounds(50,50,300,300);
		ramka.pack();
		ramka.setVisible(true);
		
		
		
	}
	
	
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
			System.out.println("Zako�czono skanowanie pozycji myszy");
		
		}
		}
		
	}
	
}

	
	
	


