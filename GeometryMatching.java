//GeometryMatching.java

import java.lang.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class GeometryMatching
{
	private ArrayList features=new ArrayList();
	private String fnames[]=new String[Globals.TrainCount];
	
	//constructors
	public GeometryMatching()
	{
		//
	}
	
	//internal methods
	private ArrayList findGeometry(PGM imgin)
	{
		//find circlepoints
		Circle circle=new Circle();
		int midx=Globals.ENLARGEWIDTH/2,midy=Globals.ENLARGEHEIGHT/2;
		circle.findPoints(midx,midy,90);
		ArrayList circlepoints=circle.getPoints();
		
		//find lines from center to each circlepoint
		ArrayList ans=new ArrayList();
		for(int t=0;t<circlepoints.size();t++)
		{
			Point circlept=(Point)circlepoints.get(t);
			GeometryLine line=new GeometryLine();
			line.findPoints(midx,midy,circlept.x,circlept.y);
			ArrayList linepoints=line.getPoints();
			
			//mark face boundary
			for(int j=linepoints.size()-1;j>=0;j--)
			{
				Point linept=(Point)linepoints.get(j);
				int inval=imgin.getPixel(linept.y,linept.x);
				if(inval==0)
				{
					ans.add(linept);
					break;
				}
			}
		}
		
		return(ans);
	}
	
	private ArrayList findDistances(ArrayList tpoints)
	{
		ArrayList tarr=new ArrayList();
		int midx=Globals.ENLARGEWIDTH/2,midy=Globals.ENLARGEHEIGHT/2;
		for(int t=0;t<tpoints.size();t++)
		{
			Point pt=(Point)tpoints.get(t);
			int dist=GeometryLine.findDistance(pt.x,pt.y,midx,midy);
			tarr.add(new Integer(dist));
		}
		return(tarr);
	}
	
	private ArrayList findFeature(String tpath)
	{
		PGM pgmin=new PGM(tpath);
		PGM pgm1=HandDetection.thresh(pgmin);
		PGM pgm2=HandDetection.findboundary(pgm1,4);
		PGM pgm3=HandDetection.enlarge(pgm2,Globals.ENLARGEWIDTH,Globals.ENLARGEHEIGHT);
		ArrayList geometrypoints=findGeometry(pgm3);
		PGM pgm4=HandDetection.plot(pgm3,geometrypoints);
		
		//write intermediate images
		if(Globals.TempFlag==true)
		{
			pgm1.writeImageAs(Globals.TempPath+"\\1_thres_["+new File(tpath).getName()+"].pgm");
			pgm2.writeImageAs(Globals.TempPath+"\\2_boundary_["+new File(tpath).getName()+"].pgm");
			pgm3.writeImageAs(Globals.TempPath+"\\3_enlarge_["+new File(tpath).getName()+"].pgm");
			pgm4.writeImageAs(Globals.TempPath+"\\4_geometry_["+new File(tpath).getName()+"].pgm");
		}
		
		ArrayList tfeature=findDistances(geometrypoints);
		for(int t=0;t<2;t++) tfeature.add(new Integer(Globals.findRandom(10)));
		return(tfeature);
	}
	
	private int findSimilarity(ArrayList arr1,ArrayList arr2)
	{
		int tcount=arr1.size();
		if(arr2.size()<tcount) tcount=arr2.size();
	
		int tsum=0;
		for(int t=0;t<tcount;t++)
		{
			int val1=((Integer)arr1.get(t)).intValue();
			int val2=((Integer)arr2.get(t)).intValue();
			int diff=(val1-val2);
			tsum+=diff*diff;
		}
		int sim=(int)Math.sqrt(tsum);
		return(sim);
	}
	
	//methods
	public void training()
	{
		int tindex=0;
		System.out.println("Training...");
		for(int t=0;t<Globals.TrainCount;t++)
		{
			String fname=Globals.TrainPath+"\\"+(t+1)+".pgm";
			fnames[tindex]=fname;
			System.out.println(fname);

			ArrayList tfeature=findFeature(fname);
			features.add(tfeature);
			tindex+=1;
		}
	}
	
	public RecognitionResult recognize(String testpath)
	{
		ArrayList testfeature=findFeature(testpath);
		
		//find matched image
		int smallest=-1,matchedindex=-1,max=-1;
		for(int t=0;t<features.size();t++)
		{
			ArrayList trainfeature=(ArrayList)features.get(t);
			int sim=findSimilarity(testfeature,trainfeature);
			System.out.println(fnames[t]+": "+sim);
			
			if(t==0)
			{
				smallest=sim;
				matchedindex=0;
				max=sim;
			}
			else
			{
				if(smallest>sim)
				{
					smallest=sim;
					matchedindex=t;
				}
				if(max<sim) max=sim;
			}
		}
		
		//find matched imagepath
		String matchedfname=fnames[matchedindex];
		System.out.println("matched: "+matchedfname);
		
		RecognitionResult tresult=new RecognitionResult();
		tresult.matchedpath=matchedfname;
		tresult.matched=new PGM(matchedfname);
		tresult.diff=smallest;
		tresult.accuracy=100.0-(((double)smallest/(double)max)*100.0);
		return(tresult);
	}
}
