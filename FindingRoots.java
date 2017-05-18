/**
 * Author: Colin Koo
 * Professor: Raheja
 * Program: Implement methods to find a root of an equation including
 * Bisection, Secant, Newton-Raphson, False-Position, and Modified Secant.
 */
import java.util.ArrayList;

public class FindingRoots {

	public static void main(String[] args) {
		ArrayList<Double> equation = new ArrayList<Double>(); //roots: x = 0.365098, x = 1.92174, x = 3.56316
		equation.add(2.0);	//2x3 – 11.7x2 + 17.7x – 5
		equation.add(-11.7);
		equation.add(17.7);
		equation.add(-5.0);
		
		bisection(0, 0.5, equation, 0.365098, false);
		bisection(1, 2, equation, 1.92174, false);
		bisection(3, 4, equation, 3.56316, false);
		bisection(120, 130, equation, 126.632, true);
		
		newton(1, equation, 0.365098, false);
		newton(2, equation, 1.92174, false);
		newton(4, equation, 3.56316, false);
		newton(130, equation, 126.632, true);
		
		secant(0, 0.5, equation, 0.365098, false);
		secant(1, 2, equation, 1.92174, false);
		secant(3, 4, equation, 3.56316, false);
		secant(120, 130, equation, 126.632, true);
		
		modSecant(0.5, equation, 0.365098, false);
		modSecant(2, equation, 1.92174, false);
		modSecant(4, equation, 3.56316, false);
		modSecant(130, equation, 126.632, true);
		
		falsePosition(0, 0.5, equation, 0.365098, false);
		falsePosition(1, 2, equation, 1.92174, false);
		falsePosition(3, 4, equation, 3.56316, false);
		falsePosition(120, 130, equation, 126.632, true);
		
	}
	// no special checks, assumes root exists between brackets
	public static void bisection(double a, double b, ArrayList<Double> equation, double trueRoot, boolean partB){
		if(!partB){ System.out.println("Bisection for Equation 1"); }
		else { System.out.println("Bisection for Equation 2");}
	    System.out.printf("%2s %5s %7s %7s %9s %7s %6s %9s %7s\n", "n","a","b","c","f(a)","f(b)","f(c)","true %", "error %");
	    
		double prevC = Double.NaN, c, errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;
		double fa, fb, fc = Double.NaN;
		int iterations = 0; 

		while (errorA > 0.01 && iterations < 100){
			c = (a+b)/2;
			if (!partB){
				fa = calcEquation(a, equation);
				fb = calcEquation(b, equation);
				fc = calcEquation(c, equation);
			}
			else{
				fa = calcEquation2(a);
				fb = calcEquation2(b);
				fc = calcEquation2(c);
			}

			if (iterations == 0){
				prevC = c;
			}
			else{
				errorA = Math.abs(c - prevC)/c;
				prevC = c;
			}
			errorT = (Math.abs(trueRoot - c))/trueRoot;
			
	    	System.out.printf("%2d %7.3f %7.3f %7.3f %7.3f %7.3f %7.3f %7.3f %7.3f\n", 
	    			+ iterations, a, b, c, fa, fb, fc, errorT, errorA);
/*			System.out.println("n: " + iterations
					+ "\na: " + a + ",\tb: " + b + ",\tc: " + c
					+"\nfa:\t" + fa + ", fb:\t" + fb + ", fc:\t" + fc
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");*/
			if ((fc < 0 && fa >= 0) || (fc >= 0 && fa < 0))
				b = c;
			else if ((fc < 0 && fb >= 0) || (fc >= 0 && fb < 0)){
				a = c;
			}
			++iterations;
		}
	}
	public static void newton(double a,  ArrayList<Double> equation, double trueRoot, boolean partB){
		if(!partB){ System.out.println("Newton for Equation 1"); }
		else { System.out.println("Newton for Equation 2");}
		System.out.printf("%2s %6s %8s %8s %7s %7s %7s\n", "n","x_i","x_i+1","f(x_i)","f'(x_i)","true %", "error %");	  
		
		double start = a, prevStart = Double.NaN, next, fderiv, fstart, errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;
		int iterations = 0;

		while (iterations < 100 && Math.abs(errorA) > 0.01){
			if (iterations == 0){
				prevStart = start;
			}
			else{
				errorA = Math.abs(start - prevStart)/start;
				prevStart = start;
			}
			errorT = Math.abs(trueRoot - start)/trueRoot;
			if (!partB){
				fstart = calcEquation(start, equation);
				//				ArrayList<Double> derivative = derivative(equation);
				fderiv = calcEquation(start, derivative(equation));
			}
			else{
				fstart = calcEquation2(start);
				fderiv = calcEquation2Deriv(start);
			}
			next = start - ((fstart)/(fderiv));	//no need?

			System.out.printf("%2d %7.3f %7.3f %7.3f %7.3f %7.3f %7.3f\n", 
			    	+ iterations,start,next,fstart,fderiv,errorT, errorA);
/*			System.out.println("n: " + iterations
					+ "\nstart: " + start + ",\tnext: " + next
					+"\nfstart:\t" + fstart + ", fderiv:\t" + fderiv
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");*/
			start = next;

			++iterations;
		}
	}

	public static ArrayList<Double> derivative(ArrayList<Double> equation){
		ArrayList<Double> derivative = new ArrayList<Double>();
		for (int i = 0; i < equation.size()-1; ++i){
			derivative.add(equation.get(i) * (equation.size()-1-i));
		}
		return derivative;
	}

	public static void secant(double a, double b, ArrayList<Double> equation, double trueRoot, boolean partB){
		if(!partB){ System.out.println("Secant for Equation 1"); }
		else { System.out.println("Secant for Equation 2");}
		System.out.printf("%2s %7s %6s %8s %8s %7s\n", "n","x_i-1","x_i","x_i+1","true %","error %");
		
		double start = 0, end = 0, next, prevNext = Double.NaN;
		double fstart, fend, fnext;
		double errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;
		if (!partB){
			if (Math.abs(calcEquation(a, equation)) < Math.abs(calcEquation(b, equation))){
				start = a;
				end = b;
			}
			else{
				start = b;
				end = a;
			}
		}
		else{
			if (Math.abs(calcEquation2(a)) < Math.abs(calcEquation2(b))){
				start = a;
				end = b;
			}
			else{
				start = b;
				end = a;
			}
		}
		int iterations = 0;
		while (iterations < 100 && errorA > 0.01){
			if (!partB){
				fstart = calcEquation(start, equation);
				fend = calcEquation(end, equation);
				next = end - ( (fend*(end-start)) / (fend - fstart) );
				fnext = calcEquation(next, equation);
			}
			else{
				fstart = calcEquation2(start);
				fend = calcEquation2(end);
				next = end - ( (fend*(end-start)) / (fend - fstart) );
				fnext = calcEquation2(next);
			}

			if (iterations == 0){
				prevNext = next;
			}
			else{
				errorA = Math.abs(next  - prevNext)/next;
				prevNext = next;
			}
			errorT = (Math.abs(trueRoot - next))/trueRoot;
			
	    	System.out.printf("%2d %7.3f %7.3f %7.3f %7.3f %7.3f\n", iterations,start,end,next,errorT,errorA);
/*			System.out.println("n: " + iterations
					+ "\nstart: " + start + ",\tend: " + end + ",\tnext: " + next
					+"\nfstart:\t" + fstart + ", fend:\t" + fend + ", fnext:\t" + fnext
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");*/

			if (Math.abs(fend) < Math.abs(fnext)){
				start = end;
				end = next;
			}
			else{
				start = next;
			}
			++iterations;
		}
	}

	public static void modSecant(double a, ArrayList<Double> equation, double trueRoot, boolean partB){
		if(!partB){ System.out.println("Modified Secant for Equation 1"); }
		else { System.out.println("Modified Secant for Equation 2");}
		System.out.printf("%2s %6s %8s %8s %8s\n", "n","x_i","x_i+1","true %","error %");
		
		int iterations = 0;
		final double delta = 0.01;
		
		double start = a, prevStart = Double.NaN, next, fstart, fstartdelta, errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;
		while (iterations < 100 && Math.abs(errorA) > 0.01){
			if (!partB){
				fstart = calcEquation(start, equation);
				fstartdelta = calcEquation(start + start*delta, equation);
			}
			else{
				fstart = calcEquation2(start);
				fstartdelta = calcEquation2(start + start*delta);
			}
			next = start - ((fstart*delta*start)/(fstartdelta-fstart));
			errorT = Math.abs(trueRoot - start)/trueRoot;
			if (iterations == 0){
				prevStart = start;
			}
			else{
				errorA = Math.abs(start - prevStart)/start;
				prevStart = start;
			}
/*			System.out.println("n: " + iterations
					+ "\nstart: " + start + ",\tnext: " + next
					+"\nfstart:\t" + fstart + ", fstartdelta:\t" + fstartdelta
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");*/
	    	System.out.printf("%2d %7.3f %7.3f %7.3f %7.3f\n", + iterations,start,next,errorT,errorA);

			start = next;
			++iterations;
		}
	}

	public static void falsePosition(double a, double b, ArrayList<Double> equation, double trueRoot, boolean partB){
		if(!partB){ System.out.println("False Position for Equation 1"); }
		else { System.out.println("False Position for Equation 2");	}
		System.out.printf("%2s %5s %7s %7s %8s %7s %7s %7s %9s\n", "n","a","b","c","f(a)","f(b)","f(c)","true %","error %");
		
		int iterations = 0;
		double c, prevC = Double.NaN, fa, fb, fc = Double.NaN, errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;

		while (errorA > 0.01 && iterations < 100 && fc != 0){
			if (!partB){
				fa = calcEquation(a, equation);
				fb = calcEquation(b, equation);
				c = ((a*fb)-(b*fa))/(fb-fa);
				fc = calcEquation(c, equation);
			}
			else{
				fa = calcEquation2(a);
				fb = calcEquation2(b);
				c = ((a*fb)-(b*fa))/(fb-fa);
				fc = calcEquation2(c);
			}
			if (iterations == 0){
				prevC = c;
			}
			else{
				errorA = Math.abs(c - prevC)/c;
				prevC = c;
			}
			errorT = (Math.abs(trueRoot - c))/trueRoot;

			System.out.printf("%2d %7.3f %7.3f %7.3f %7.3f %7.3f% 7.3f %7.3f %7.3f\n", 
			    	+ iterations,a,b,c,fa,fb,fc,errorT,errorA);
/*			System.out.println("n: " + iterations
					+ "\na: " + a + ",\tb: " + b + ",\tc: " + c
					+"\nfa:\t" + fa + ", fb:\t" + fb + ", fc:\t" + fc
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");*/
			if ((fc < 0 && fa >= 0) || (fc > 0 && fa <= 0)){
				b = c;
			}
			else if (fc < 0 && fb >= 0 || fc > 0 && fb <= 0){
				a = c;
			}
			++iterations;
		}
	}

	public static double calcEquation(double variable, ArrayList<Double> equation){
		double sum = 0;
		if (equation.size() != 0){
			if (equation.size() > 1){// at least 1 var, x^1
				for (int i = 0; i < equation.size()-1; ++i){
					sum += (Math.pow(variable, (equation.size()-1-i)) * equation.get(i));
				}
			}
			sum += equation.get(equation.size()-1);	
		}
		return sum;
	}
	public static double calcEquation2(double variable){
		return (variable + 10 - (variable * Math.cosh(50/variable)));
	}
	public static double calcEquation2Deriv(double variable){
		return (50*Math.sinh(50/variable)/variable - Math.cosh(50/variable) + 1);
	}
}