//HandDetection.java

import java.lang.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class HandDetection
{
	//segment finger from background (thresholding)
	public static PGM thresh(PGM imgin)
	{
		//find mean
		int tsum=0;
		for(int r=0;r<imgin.getRows();r++)
		{
			for(int c=0;c<imgin.getColumns();c++)
			{
				tsum+=imgin.getPixel(r,c);
			}
		}
		double tavg=((double)tsum)/((double)imgin.getRows()*(double)imgin.getColumns());
		int tval=(int)(tavg*2);
		
		//remove background pixels
		PGM imgout=new PGM(imgin.getColumns(),imgin.getRows());
		for(int r=0;r<imgout.getRows();r++)
		{
			for(int c=0;c<imgout.getColumns();c++)
			{
				int inval=imgin.getPixel(r,c);
				int outval=inval>=140?255:0;
				imgout.setPixel(r,c,outval);
			}
		}
		
		return(imgout);
	}
	
	//find finger boundary pixels (edge detection)
	public static PGM findboundary(PGM imgin,int maxdiff)
	{
		PGM imgout=new PGM(imgin.getColumns(),imgin.getRows());
		
		//initialize
		for(int r=0;r<imgout.getRows();r++)
		{
			for(int c=0;c<imgout.getColumns();c++)
			{
				imgout.setPixel(r,c,255);
			}
		}
		
		for(int r=0;r<imgout.getRows();r++)
		{
			for(int c=0;c<imgout.getColumns();c++)
			{
				int inval=imgin.getPixel(r,c); //get current pixel
				
				int tinval[]=new int[2];
				tinval[0]=imgin.getPixel(r,c+1); //get right pixel
				tinval[1]=imgin.getPixel(r+1,c); //get down pixel
				
				//determine if there is an edge
				boolean flag1=java.lang.Math.abs(inval-tinval[0])>maxdiff?true:false;
				boolean flag2=java.lang.Math.abs(inval-tinval[1])>maxdiff?true:false;
				boolean fval=flag1||flag2;
				
				int outval=fval==true?0:255;
				if(c!=imgout.getColumns()-1&&r!=imgout.getRows()-1) imgout.setPixel(r,c,outval);
			}
		}
		
		return(imgout);
	}
	
	//enlarge image to find finger geometry boundary points
	public static PGM enlarge(PGM imgin,int newwidth,int newheight)
	{
		PGM imgout=new PGM(newwidth,newheight);
		int startc=(newwidth-imgin.getColumns())/2;
		int startr=(newheight-imgin.getRows())/2;
		
		//initialize
		for(int r=0;r<imgout.getRows();r++)
		{
			for(int c=0;c<imgout.getColumns();c++)
			{
				imgout.setPixel(r,c,255);
			}
		}
		
		//fit to center
		for(int r=0;r<imgin.getRows();r++)
		{
			for(int c=0;c<imgin.getColumns();c++)
			{
				int inval=imgin.getPixel(r,c);
				imgout.setPixel(startr+r,startc+c,inval);
			}
		}
		
		return(imgout);
	}
	
	//plot geometry points to image
	public static PGM plot(PGM imgin,ArrayList points)
	{
		PGM imgout=new PGM(imgin.getColumns(),imgin.getRows());
		
		//initialize to background
		for(int r=0;r<imgout.getRows();r++)
		{
			for(int c=0;c<imgout.getColumns();c++)
			{
				imgout.setPixel(r,c,255);
			}
		}
		
		//plot the points
		for(int t=0;t<points.size();t++)
		{
			Point pt=(Point)points.get(t);
			imgout.setPixel(pt.y,pt.x,0);
		}
		
		return(imgout);
	}
}
