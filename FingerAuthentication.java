//FingerAuthentication.java

import java.lang.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.imageio.*;

public class FingerAuthentication extends JFrame implements ActionListener
{
	//mainform declarations
	String strTitle="Finger Authentication";
	JFrame frmMain=new JFrame("Personal Authentication using 3D Finger Geometry");
	JLabel lblPath=new JLabel("Path:");
	JTextField txtPath=new JTextField(Globals.TestPath+"\\test1.pgm");
	JButton btBrowse=new JButton("Browse");
	JButton btRecognize=new JButton("Recognize");
	JButton btRefresh=new JButton("Refresh");
	JButton btViewDB=new JButton("View DB");
	JButton btSeparator1=new JButton("");
	MyPictureBox pbInput=new MyPictureBox();
	MyPictureBox pbMatched=new MyPictureBox();
	JTextArea txtResult=new JTextArea("");
	JScrollPane spResult=new JScrollPane(txtResult);
	
	public FingerAuthentication()
	{
		//mainform definitions
		frmMain.setResizable(false);
		frmMain.setBounds(50,50,480,395);
		frmMain.getContentPane().setLayout(null);
		frmMain.setLocationRelativeTo(null);
		frmMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		lblPath.setBounds(17,15,100,20);
		frmMain.getContentPane().add(lblPath);
		txtPath.setBounds(50,15,frmMain.getWidth()-295,20);
		txtPath.setEditable(false);
		frmMain.getContentPane().add(txtPath);
		
		btBrowse.setBounds(frmMain.getWidth()-237,15,100,20);
		btBrowse.addActionListener(this);
		frmMain.getContentPane().add(btBrowse);
		
		btRecognize.setBounds(frmMain.getWidth()-127,15,100,20);
		btRecognize.addActionListener(this);
		frmMain.getContentPane().add(btRecognize);
		
		btRefresh.setBounds(frmMain.getWidth()-127,60,100,20);
		btRefresh.addActionListener(this);
		frmMain.getContentPane().add(btRefresh);
		
		btViewDB.setBounds(frmMain.getWidth()-127,85,100,20);
		btViewDB.addActionListener(this);
		frmMain.getContentPane().add(btViewDB);
		
		btSeparator1.setBounds(15,45,frmMain.getWidth()-40,3);
		btSeparator1.setEnabled(false);
		frmMain.getContentPane().add(btSeparator1);
		
		pbInput.setBounds(15,60,155,155);
		pbInput.setTitle("Input-Image");
		frmMain.getContentPane().add(pbInput);
		
		pbMatched.setBounds(180,60,155,155);
		pbMatched.setTitle("Matched-Image");
		frmMain.getContentPane().add(pbMatched);
		
		txtResult.setEditable(false);
		spResult.setBounds(15,230,320,120);
		spResult.setColumnHeaderView(new JLabel("Result"));
		frmMain.getContentPane().add(spResult);
		
		Globals.initialize();
		frmMain.setVisible(true);
	}
	
	//events
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource()==btBrowse) //browse for inputimage
		{
			JFileChooser tFileChooser=new JFileChooser(Globals.DataPath);
			tFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int tResult=tFileChooser.showOpenDialog(this);
			
			if(tResult==JFileChooser.APPROVE_OPTION)
			{
				txtPath.setText(tFileChooser.getSelectedFile().getPath());
				PGM tpgm=new PGM(txtPath.getText());
				pbInput.setImage(tpgm.getBufferedImage());
			}
		}
		else if(evt.getSource()==btRecognize) //process
		{
			if(new File(txtPath.getText()).exists()==false) return;
			PGM tpgm=new PGM(txtPath.getText());
			pbInput.setImage(tpgm.getBufferedImage());
			
			GeometryMatching geom=new GeometryMatching();
			geom.training();
			RecognitionResult tresult=geom.recognize(txtPath.getText());
			pbMatched.setImage(tresult.matched.getBufferedImage());
			
			String strResult="Matched: "+new File(tresult.matchedpath).getName()+"\r\n";
			strResult+="Match%: "+tresult.accuracy;
			txtResult.setText(strResult);
		}
		else if(evt.getSource()==btRefresh)
		{
			pbInput.revalidate();
			pbMatched.revalidate();
		}
		else if(evt.getSource()==btViewDB)
		{
			new ViewDatabase();
		}
	}

	public static void main(String args[])
	{
		new FingerAuthentication();
	}
}
