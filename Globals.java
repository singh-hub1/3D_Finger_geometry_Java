//Globals.java

import java.lang.*;
import java.io.*;
import javax.swing.filechooser.*;

public class Globals
{
	public static String DataPath="data";
	public static String TrainPath="data\\train";
	public static String TestPath="data\\test";
	public static String TempPath="data\\temp";
	public static boolean TempFlag=true;
	
	public static int TrainCount=68;
	public static int SizeX=128,SizeY=128;
	public static int xDiv=128,yDiv=128;
	
	public static int BackGround=0,ForeGround=255;
	public static int NW=0,N=1,NE=2,E=3,SE=4,S=5,SW=6,W=7;
	public static int ENLARGEWIDTH=200,ENLARGEHEIGHT=200;
	
	//system methods
	public static void initialize()
	{
		TrainCount=findImageCount();
	}
	
	//file methods
	public static int findImageCount()
	{
		FileSystemView fv=FileSystemView.getFileSystemView();
		File files[]=fv.getFiles(new File(TrainPath),true);

		int tcount=0;
		for(int t=0;t<files.length;t++)
		{
			String tfname=files[t].getName();
			String ext=getExtensionFromFileName(tfname);
			if(ext.compareToIgnoreCase("pgm")==0) tcount+=1;
		}
		
		return(tcount);
	}
	
	public static String getExtensionFromFileName(String tpath)
	{
		int tpos=tpath.lastIndexOf(".");
		String ext=tpos==-1?"":tpath.substring(tpos+1);
		return(ext);
	}
	
	public static int findRandom(int max)
	{
		double trandom=Math.random();
		int ans=(int)(max*trandom);
		return(ans);
	}
	
	//pgm utils
	public static PGM resize(PGM imgin,int wout,int hout)
	{
		PGM imgout=new PGM(wout,hout);
		
		//resize algorithm (linear)
		int win=imgin.getColumns(),hin=imgin.getRows();
		for(int r=0;r<imgout.getRows();r++)
		{
			for(int c=0;c<imgout.getColumns();c++)
			{
				int xi=c,yi=r;
				int ci=(int)(xi*((double)win/(double)wout));
				int ri=(int)(yi*((double)hin/(double)hout));
				int inval=imgin.getPixel(ri,ci);
				int outval=inval;
				imgout.setPixel(yi,xi,outval);
			}
		}
	
		return(imgout);
	}
}
