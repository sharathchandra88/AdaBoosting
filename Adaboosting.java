/**
 * 	@author Kadappanavar, Sharath Chandra Rachappa 
 * 	  
 *  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class Adaboosting {


	public static void main(String[] args){
		// TODO Auto-generated method stub
		int T = 0,n = 0;
		double epsilon;

		//LinkedList<AdaTable> Example = new LinkedList<>();
		if(args.length<1)
		{
			System.out.println("Invalid Input Format");
			System.out.println("Please execute in the following format");
			System.out.println("java -jar executable.jar file_path1 file_path2");
			System.out.println("Example:");
			System.out.println("java -jar Adaboosting.jar C:\\Users\\sharath\\Desktop\\File1.txt");
			System.exit(0);
		}
		LinkedList<Double> l = new LinkedList<Double>();
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		ArrayList<Double> p = new ArrayList<Double>();
		Scanner scan1 = null;
		try {
			scan1 = new Scanner(new FileInputStream(args[0]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scan1.hasNextLine()) {
			T = scan1.nextInt();
			n = scan1.nextInt();
			epsilon = Double.parseDouble(scan1.next());


			for(int i=0;i<n;i++)
			{

				x.add(Double.parseDouble(scan1.next()));

			}

			for(int i=0;i<n;i++)
			{

				y.add( scan1.nextInt());

			}
			for(int i=0;i<n;i++)
			{

				p.add(Double.parseDouble(scan1.next()));

			}


		}
		//LinkedList
		//table temp[] = new table[100] ;
		HashMap<Double, Integer> x_y = new HashMap<Double, Integer>();
		HashMap<Double, Double> x_p = new HashMap<Double, Double>();
		for(int i=0;i<n;i++)
		{
			x_y.put(x.get(i), y.get(i));
			x_p.put(x.get(i), p.get(i));
		}
		ArrayList<Double> hypothesis= new ArrayList<Double>();
		ArrayList<Double> ChosenHypothesis= new ArrayList<Double>();
		ArrayList<Double> hypothesiserror = new ArrayList<Double>();
		ArrayList<String> hypothesistype = new ArrayList<String>();
		ArrayList<String> Chosenhypothesistype = new ArrayList<String>();
		ArrayList<Double> ChosenEpsilon = new ArrayList<Double>();
		ArrayList<Double> Alpha = new ArrayList<Double>();
		ArrayList<Double> Bound = new ArrayList<Double>();
		ArrayList<Integer> HypClassification = new ArrayList<Integer>();
		ArrayList<String> HypCorrectness = new ArrayList<String>();
		HashMap<Double, String> hyperror_hyptype = new HashMap<Double, String>();
		HashMap<Double, Double> hyperror_hyp = new HashMap<Double, Double>();

		for(int i=0;i<n-1;i++)
		{

			hypothesis.add((x.get(i)+x.get(i+1))/2);


		}
		int iteration=0;
	
		double hyperror=0;
		int hypnum =0;
		System.out.println("Kadappanavar, Sharath Chandra Rachappa");
        System.out.println("");
		for(iteration=0;iteration<T;iteration++ )
		{
			//hyperror=0;
			hypnum =0;
			for(int hypnum1=0;hypnum1<hypothesis.size();hypnum1++)
			{
				hyperror=0;
				// check number of errors for x<v
				for(int i=0;i<n;i++)
				{
					if(x.get(i) < hypothesis.get(hypnum1))
					{
						if(x_y.get(x.get(i))==-1)
						{
							//hypothesiserror.add(hypothesis.get(hypnum)+x_p.get(x.get(i)));
							hyperror = hyperror + x_p.get(x.get(i));
						}
					}
					else
					{
						if(x_y.get(x.get(i))==1)
						{
							//hypothesiserror.add(hypothesis.get(hypnum)+x_p.get(x.get(i)));
							hyperror = hyperror + x_p.get(x.get(i));
						}
					}
				}
				if(iteration==0)
				{
					hypothesiserror.add(hypnum,hyperror);
					hypothesistype.add(hypnum,"x<"+hypothesis.get(hypnum1));
					hyperror_hyptype.put(hypothesiserror.get(hypnum), hypothesistype.get(hypnum));
					hyperror_hyp.put(hypothesiserror.get(hypnum), hypothesis.get(hypnum1));
					hypnum++;
					hyperror=0;
					
				}
				else
				{
					hypothesiserror.set(hypnum,hyperror);
					hypothesistype.set(hypnum,"x<"+hypothesis.get(hypnum1));
					hyperror_hyptype.put(hypothesiserror.get(hypnum), hypothesistype.get(hypnum));
					hyperror_hyp.put(hypothesiserror.get(hypnum), hypothesis.get(hypnum1));
					hypnum++;
					hyperror=0;

				}
				//check number of errors with x > v
				for(int i=0;i<n;i++)
				{
					if(x.get(i) > hypothesis.get(hypnum1))
					{
						if(x_y.get(x.get(i))==-1)
						{
							hyperror = hyperror + x_p.get(x.get(i));
						}
					}
					else
					{
						if(x_y.get(x.get(i))==1)
						{
							hyperror = hyperror + x_p.get(x.get(i));
						}
					}
				}
				if(iteration==0)
				{
					hypothesiserror.add(hypnum,hyperror);
					hypothesistype.add(hypnum,"x>"+hypothesis.get(hypnum1));
					hyperror_hyptype.put(hypothesiserror.get(hypnum), hypothesistype.get(hypnum));
					hyperror_hyp.put(hypothesiserror.get(hypnum), hypothesis.get(hypnum1));
					hypnum++;
					
				}
				else
				{
					hypothesiserror.set(hypnum,hyperror);
					hypothesistype.set(hypnum,"x>"+hypothesis.get(hypnum1));
					hyperror_hyptype.put(hypothesiserror.get(hypnum), hypothesistype.get(hypnum));
					hyperror_hyp.put(hypothesiserror.get(hypnum), hypothesis.get(hypnum1));
					hypnum++;
				}

			}

			//choose the best hypothesis
			double min = 999;
			for(int i=0;i<hypothesiserror.size();i++)
			{
				if(min>hypothesiserror.get(i))
				{
					min = hypothesiserror.get(i);
				}

			}
			ChosenEpsilon.add(iteration,min);
			Chosenhypothesistype.add(iteration,hyperror_hyptype.get(min));
			ChosenHypothesis.add(iteration,hyperror_hyp.get(min));
			Alpha.add(iteration,Math.log((1.0 - ChosenEpsilon.get(iteration)) / ChosenEpsilon.get(iteration)) / 2);

			double qRight = 0.0, qWrong = 0.0;
			qRight = Math.exp(-Alpha.get(iteration));
			qWrong = Math.exp(Alpha.get(iteration));
			//System.out.println(Chosenhypothesistype.get(iteration).charAt(1));
			if(Chosenhypothesistype.get(iteration).charAt(1)=='>')
			{
				for(int i=0;i<n;i++)
				{
					if(x.get(i) > ChosenHypothesis.get(iteration))
					{
						if(iteration==0)
						{
						HypClassification.add(i,1);
						}
						else
						{
							HypClassification.set(i,1);	
						}
					}
					else
					{
						if(iteration==0)
						{
						HypClassification.add(i,-1);
						}
						else
						{
							HypClassification.set(i,-1);	
						}
					}
				}
			}
			else
			{
				for(int i=0;i<n;i++)
				{
					if(x.get(i) < ChosenHypothesis.get(iteration))
					{
						if(iteration==0)
						{
						HypClassification.add(i,1);
						}
						else
						{
							HypClassification.set(i,1);	
						}
					}
					else
					{
						if(iteration==0)
						{
						HypClassification.add(i,-1);
						}
						else
						{
							HypClassification.set(i,-1);	
						}
					}
				}
			}
			for(int i=0;i<n;i++)
			{
				if(x_y.get(x.get(i))==HypClassification.get(i) )
				{
					if(iteration==0)
					{
					HypCorrectness.add(i,"y");
					}
					else
					{
						HypCorrectness.set(i,"y");
					}
				}
				else
				{
					if(iteration==0)
					{
					HypCorrectness.add(i,"n");
					}
					else
					{
						HypCorrectness.set(i,"n");
					}
				}
			}
			double newpi =0.0;
			double Z = 0.0;
			//Update the probabilities by calculating p(i) * q(i)
			for(int i=0;i<n;i++)
			{
				if(HypCorrectness.get(i)=="y")
				{
					newpi = p.get(i) * qRight;
				}
				else
				{
					newpi = p.get(i) * qWrong;
				}
				p.set(i, newpi);

			}
			for(int i=0;i<n;i++)
			{
				Z = Z + p.get(i);
			}
			Bound.add(Z);
			double Zpi= 0.0;
			//Calculate normalized pi
			for(int i=0;i<n;i++)
			{
				Zpi = p.get(i)/Z;
				p.set(i, Zpi);
			}
			for(int i=0;i<n;i++)
			{

				x_p.put(x.get(i), p.get(i));
			}
			double Et = 1;
			//Calculate bound of the error
			for(int i=0;i<iteration+1;i++)
			{
				Et = Et * Bound.get(i);	
			}
			
			System.out.println("Iteration "+(iteration+1));
			//System.out.println("-----------------------");
			System.out.println("Classifier h = "+"I("+Chosenhypothesistype.get(iteration)+")");
			System.out.println("Error = "+ChosenEpsilon.get(iteration));
			System.out.println("Alpha = " + Alpha.get(iteration));
			System.out.println("Normalization Factor Z = " + Z);
			System.out.print("Pi after normalization = ");
			for(int i=0;i<n;i++)
			{
				System.out.print(p.get(i));
				System.out.print("    ");
			}
			System.out.println("");
			System.out.print("Boosted classifier f(x) = ");
			for(int i=0;i<iteration+1;i++)
			{
				if(i==0)
				{
				System.out.print(Alpha.get(i)+" * I ("+Chosenhypothesistype.get(i)+")");
				}
				else
				{
					System.out.print(" + " + Alpha.get(i)+" * I ("+Chosenhypothesistype.get(i)+")");
				}
			}
			System.out.println("");
			System.out.println("Bound on Error = " + Et);
			System.out.println("");
		}
		/*for(int i=0;i<n;i++)
		{
			temp[i]= new table();
			temp[i].insert(i, (double)x1.get(i),(int)y1.get(i),(double)p1.get(i));
			//Example.add(i,temp);

		}*/
	}

	/*class table{
		public int index;
		public double x ;
		public int y ;
		public double p ;
		public table()
		{
			index = 0;
			x=0;
			y=0;
			p=0;
		}
		public void insert(int index,double x,int y,double p)
		{
			this.index = index;
			this.x = x;
			this.y = y;
			this.p = p;
		}
	}*/

}
