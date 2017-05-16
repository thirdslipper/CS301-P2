/**
 * Author: Colin Koo
 * Professor: Raheja
 * Program: Implement methods to find a root of an equation including
 * Bisection, Secant, Newton-Raphson, False-Position, and Modified Secant.
 */
import java.util.ArrayList;

public class FindingRoots {

	public static void main(String[] args) {
		ArrayList<Double> equation = new ArrayList<Double>(); //roots = 0.365, 1.92, 3.56
		equation.add(2.0);	//2x3 � 11.7x2 + 17.7x � 5
		equation.add(-11.7);
		equation.add(17.7);
		equation.add(-5.0);
		//bisection(0, 0.5, equation, 0.365098);
		//secant(0, 0.5, equation, 0.365098);
		//newton(0.5, equation, 0.365098);
		modSecant(1, equation, 0.365098);
	}
	// no special checks, assumes root exists between brackets
	public static void bisection(double a, double b, ArrayList<Double> equation, double trueRoot){

		double prevC = Double.NaN, c, errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;
		double fa, fb, fc = Double.NaN;
		int iterations = 0; 

		while (errorA > 0.01 && iterations < 100){
			fa = calcEquation(a, equation);
			fb = calcEquation(b, equation);

			c = (a+b)/2;
			fc = calcEquation(c, equation);

			if (iterations == 0){
				prevC = c;
			}
			else{
				errorA = Math.abs(c - prevC)/c;
				prevC = c;
			}
			errorT = (Math.abs(trueRoot - c))/trueRoot;

			System.out.println("n: " + iterations
					+ "\na: " + a + ",\tb: " + b + ",\tc: " + c
					+"\nfa:\t" + fa + ", fb:\t" + fb + ", fc:\t" + fc
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");
			if ((fc < 0 && fa >= 0) || (fc >= 0 && fa < 0))
				b = c;
			else if ((fc < 0 && fb >= 0) || (fc >= 0 && fb < 0)){
				a = c;
			}
			++iterations;
		}
	}
	public static void newton(double a,  ArrayList<Double> equation, double trueRoot){
		double start = a, prevStart = Double.NaN, next, fderiv, fstart, errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;
		int iterations = 0;
		
		while (iterations < 100 && errorA > 0.01){
			fstart = calcEquation(start, equation);
			if (iterations == 0){
				prevStart = start;
			}
			else{
				errorA = Math.abs(start - prevStart)/start;
				prevStart = start;
			}
			errorT = Math.abs(trueRoot - start)/trueRoot;
			ArrayList<Double> derivative = derivative(equation);
			fderiv = calcEquation(start, derivative);
			next = start - ((fstart)/(fderiv));	//no need?
			
			System.out.println("n: " + iterations
					+ "\nstart: " + start + ",\tnext: " + next
					+"\nfstart:\t" + fstart + ", fderiv:\t" + fderiv
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");
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

	public static void secant(double a, double b, ArrayList<Double> equation, double trueRoot){
		double start = 0, end = 0, next, prevNext = Double.NaN;
		double fstart, fend, fnext;
		double errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;

		if (Math.abs(calcEquation(a, equation)) < Math.abs(calcEquation(b, equation))){
			start = a;
			end = b;
		}
		else{
			start = b;
			end = a;
		}

		int iterations = 0;
		while (iterations < 100 && errorA > 0.01){
			fstart = calcEquation(start, equation);
			fend = calcEquation(end, equation);

			next = end - ( (fend*(end-start)) / (fend - fstart) );
			fnext = calcEquation(next, equation);

			if (iterations == 0){
				prevNext = next;
			}
			else{
				errorA = Math.abs(next  - prevNext)/next;
				prevNext = next;
			}
			errorT = (Math.abs(trueRoot - next))/trueRoot;
			System.out.println("n: " + iterations
					+ "\nstart: " + start + ",\tend: " + end + ",\tnext: " + next
					+"\nfstart:\t" + fstart + ", fend:\t" + fend + ", fnext:\t" + fnext
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");

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

	public static void modSecant(double a, ArrayList<Double> equation, double trueRoot){
		int iterations = 0;
		final double delta = 0.01;
		double start = a, prevStart = Double.NaN, next, fstart, fstartdelta, errorA = Double.POSITIVE_INFINITY, errorT = Double.POSITIVE_INFINITY;
		while (iterations < 100 && Math.abs(errorA) > 0.01){
			fstart = calcEquation(start, equation);
			fstartdelta = calcEquation(start + start*delta, equation);
			next = start - ((fstart*delta*start)/(fstartdelta-fstart));
			errorT = Math.abs(trueRoot - start)/trueRoot;
			if (iterations == 0){
				prevStart = start;
			}
			else{
				errorA = Math.abs(start - prevStart)/start;
				prevStart = start;
			}
			System.out.println("n: " + iterations
					+ "\nstart: " + start + ",\tnext: " + next
					+"\nfstart:\t" + fstart + ", fstartdelta:\t" + fstartdelta
					+ "\nErrorA:\t" + errorA + ", ErrorT:\t" + errorT + "\n");
			
			start = next;
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
}