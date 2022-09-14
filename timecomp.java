package pro2;

import java.util.Arrays;
import java.util.stream.IntStream;

public class timecomp {

	interface FPFunction
	  {
	    double eval(double n);
	  }
	
	 int maxThreads = Runtime.getRuntime().availableProcessors() ;
	
	public static int[] generate(int length) 
	{
	      int[] result=new int[length]; 
	      for(int x=0;x<length;x+=1)
	      {
	        result[x]=(int)(Math.random()*100+1);
	      }      
	      return result;
	}
	
	public static double standardSort(int [] input)
	{
	    long start=System.nanoTime();
	    Arrays.sort(input);
	    long end = System.nanoTime();
	    return (double) (end-start);
	}
	
	// regular code 
//	public static double trapezium(double a, double b, int n, FPFunction f)
//	  {
//	    double range = checkParamsGetRange(a, b, n);
//	    double nFloat = (double)n;
//	    double sum = 0.0;
//	    for (int i = 1; i < n; i++)
//	    {
//	      double x = a + range * (double)i / nFloat;
//	      sum += f.eval(x);
//	    }
//	    sum += (f.eval(a) + f.eval(b)) / 2.0;
//	    return sum * range / nFloat;
//	  }
//	
	private static double checkParamsGetRange(double a, double b, int n)
	  {
	    if (n <= 0)
	      throw new IllegalArgumentException("Invalid value of n");
	    double range = b - a;
	    if (range <= 0)
	      throw new IllegalArgumentException("Invalid range");
	    return range;
	  }
	
	//streams code 
	public static double trapezium(double a, double b, int n, FPFunction f)
	  {
	    double range = checkParamsGetRange(a, b, n);
	    double nFloat = (double)n;
	    IntStream.range(0,n).mapToDouble(i ->
	    	range * (double)i/nFloat).map(x -> f.eval(x)).sum();
	    a += (f.eval(a) + f.eval(b))/2.0;
	    return a * range/nFloat;
	    		
	  }


	private static void testFunction(String fname, double a, double b, int n, FPFunction f)
	  {
	    System.out.println("Testing function \"" + fname + "\", a=" + a + ", b=" + b + ", n=" + n);
	    System.out.println("trapezium: " + trapezium(a, b, n, f));
	    System.out.println();
	    return;
	  }
	 
	  public static void main(String[] args)
	  {
	    testFunction("x^3", 0.0, 1.0, 100, new FPFunction() {
	        public double eval(double n) {
	          return n * n * n;
	        }
	      }
	    );
	 
	    testFunction("1/x", 1.0, 100.0, 1000, new FPFunction() {
	        public double eval(double n) {
	          return 1.0 / n;
	        }
	      }
	    );
	 
	    testFunction("x", 0.0, 5000.0, 5000000, new FPFunction() {
	        public double eval(double n) {
	          return n;
	        }
	      }
	    );
	 
	    testFunction("x", 0.0, 6000.0, 6000000, new FPFunction() {
	        public double eval(double n) {
	          return n;
	        }
	      }
	    );
	 
	    return;
	  }
	}
	
