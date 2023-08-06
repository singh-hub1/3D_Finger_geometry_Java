//ViewDatabase.java

import java.lang.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.imageio.*;

public class ViewDatabase implements ActionListener
{
	//mainform declarations
	String strTitle="View Database";
	JFrame frmMain=new JFrame("View Database");
	MyPictureBox pbMain=new MyPictureBox();
	JButton btRefresh=new JButton("Refresh");
	JButton btPrevious=new JButton("Prev");
	JButton btNext=new JButton("Next");
	
	//system declarations
	private int CurrentIndex=1;
	
	public ViewDatabase()
	{
		//mainform definitions
		frmMain.setResizable(false);
		frmMain.setBounds(50,50,280,285);
		frmMain.getContentPane().setLayout(null);
		frmMain.setLocationRelativeTo(null);
		
		pbMain.setBounds(15,15,frmMain.getWidth()-40,frmMain.getHeight()-85);
		frmMain.getContentPane().add(pbMain);
		
		btRefresh.setBounds(15,frmMain.getHeight()-60,80,20);
		btRefresh.addActionListener(this);
		frmMain.getContentPane().add(btRefresh);
		
		btPrevious.setBounds(frmMain.getWidth()-145,frmMain.getHeight()-60,60,20);
		btPrevious.addActionListener(this);
		frmMain.getContentPane().add(btPrevious);
		
		btNext.setBounds(frmMain.getWidth()-85,frmMain.getHeight()-60,60,20);
		btNext.addActionListener(this);
		frmMain.getContentPane().add(btNext);
		
		ShowCurrentImage();
		frmMain.setVisible(true);
	}
	
	//events
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource()==btNext)
		{
			CurrentIndex+=1;
			if(CurrentIndex>Globals.TrainCount)
			{
				CurrentIndex=1;
			}
		}
		else if(evt.getSource()==btPrevious)
		{
			CurrentIndex-=1;
			if(CurrentIndex<1)
			{
				CurrentIndex=Globals.TrainCount;
			}
		}
		
		ShowCurrentImage();
	}
	
	//private methods
	private void ShowCurrentImage()
	{
		String strPath=Globals.TrainPath+"\\"+CurrentIndex+".pgm";
		pbMain.setTitle(new File(strPath).getName());
		if(new File(strPath).exists()==true)
		{
			PGM tpgm=new PGM(strPath);
			PGM resized=Globals.resize(tpgm,120,120);
			pbMain.setImage(resized.getBufferedImage());
		}
	}
}
