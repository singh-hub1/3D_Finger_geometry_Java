//GeometryLine.java

import java.lang.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class GeometryLine
{
	private ArrayList points=new ArrayList();
	
	//constructors
	public GeometryLine()
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
	
	//methods
	public int size()
	{
		return(points.size());
	}
	
	public void clear()
	{
		points.clear();
	}
	
	public void findPoints(int x0,int y0,int x1,int y1)
	{
		int dx,dy;
		double m,b;
		
		dx=x1-x0;
		dy=y1-y0;
		addPoint(x0,y0);
		
		if(Math.abs(dx)>Math.abs(dy)) //slope<1
		{
			m=(double)dy/(double)dx; //compute slope
			b=y0-m*x0;
			dx=dx<0?-1:1;
			while(x0!=x1)
			{
				x0+=dx;
				addPoint(x0,(int)(m*x0+b));
			}
		}
		else
		{
			if(dy!=0) //slope>=1
			{
				m=(double)dx/(double)dy; //compute slope
				b=x0-m*y0;
				dy=dy<0?-1:1;
				while(y0!=y1)
				{
					y0+=dy;
					addPoint((int)(m*y0+b),y0);
				}
			}
		}
	}
	
	//static methods
	public static int findDistance(int x1,int y1,int x2,int y2)
	{
		double d=Math.sqrt(Math.pow((double)(x2-x1),2.0)+Math.pow((double)(y2-y1),2.0));
		return((int)d);
	}
}
