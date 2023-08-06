//MyPictureBox.java

import java.lang.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
 
public class MyPictureBox extends JPanel
{
	//members
	private String Title;
	private BufferedImage Picture;
	
	private JScrollPane tScrollPane=new JScrollPane();
	private JLabel tLabel=new JLabel();
	
	//internal members
	private int TitleHeight=22;

	//constructor
	public MyPictureBox()
	{
		Title="";
		Picture=null;
		this.setLayout(null);
	}
	
	//get functions
	public String getTitle()
	{
		return(Title);
	}
	
	public BufferedImage getImage()
	{
		return(Picture);
	}
	
	//set functions
	public void setTitle(String tTitle)
	{
		Title=tTitle;
		repaint();
	}
	
	public void setImage(BufferedImage tPicture)
	{
		Picture=tPicture;
		repaint();
	}
	
	//default methods
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		try
		{
			//draw title
			if(Title.equals("")==false)
			{
				g.setColor(new Color(128,0,0));
				g.setFont(new Font("Verdana",Font.BOLD,12));
				g.drawString(Title+":",4,16);
			}
			
			//draw image
			if(Picture!=null)
			{
				tLabel.setIcon(new ImageIcon(Picture));
				tScrollPane.setBounds(1,TitleHeight+1,getWidth()-2,getHeight()-TitleHeight-2);
				tScrollPane.getViewport().add(tLabel);
				this.add(tScrollPane);
			}
			
			//draw border
			g.setColor(new Color(0,0,0));
			g.drawRect(0,0,getWidth()-1,getHeight()-1);
			g.drawLine(0,TitleHeight,getWidth()-1,TitleHeight);
		}
		catch(Exception err)
		{
			System.out.println("Error: "+err);
			System.exit(-1);
		}
	}
}
