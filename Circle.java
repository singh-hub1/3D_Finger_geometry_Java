//Circle.java

import java.lang.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class Circle
{
	private ArrayList points=new ArrayList();
	
	//constructors
	public Circle()
	{
		clear();
	}
	
	//get functions
	public ArrayList getPoints()
	{
		return(points);
	}
	
	public Point getPoint(int tindex)
	{
		Point pt=(Point)points.get(tindex);
		return(pt);
	}
	
	//internal methods
	private void addPoint(int x,int y)
	{
		boolean found=false;
		for(int t=0;t<size();t++)
		{
			Point pt=getPoint(t);
			if(x==pt.x&&y==pt.y)
			{
				found=true;
				break;
			}
		}
		
		if(found==false)
		{
			Point pt=new Point(x,y);
			points.add(pt);
		}
	}
	
	private void circlePoints(int cx,int cy,int x,int y)
	{
		if(x==0)
		{
			addPoint(cx,cy+y);
			addPoint(cx,cy-y);
			addPoint(cx+y,cy);
			addPoint(cx-y,cy);
		}
		else if(x==y)
		{
			addPoint(cx+x,cy+y);
			addPoint(cx-x,cy+y);
			addPoint(cx+x,cy-y);
			addPoint(cx-x,cy-y);
		}
		else if(x<y)
		{
			addPoint(cx+x,cy+y);
			addPoint(cx-x,cy+y);
			addPoint(cx+x,cy-y);
			addPoint(cx-x,cy-y);
			addPoint(cx+y,cy+x);
			addPoint(cx-y,cy+x);
			addPoint(cx+y,cy-x);
			addPoint(cx-y,cy-x);
		}
	}
	
	//methods
	public int size()
	{
		return(points.size());
	}
	
	public void clear()
	{
		points.clear();
	}
	
	public void findPoints(int xcenter,int ycenter,int radius)
	{
		int r2=radius*radius;
		int x,y,p;
		
		x=0;
		y=radius;
		p=(5-radius*4)/4;
		
		circlePoints(xcenter,ycenter,x,y);
		while(x<y)
		{
			x+=1;
			if(p<0)
			{
				p+=2*x+1;
			}
			else
			{
				y-=1;
				p+=2*(x-y)+1;
			}
			circlePoints(xcenter,ycenter,x,y);
		}
	}
}
