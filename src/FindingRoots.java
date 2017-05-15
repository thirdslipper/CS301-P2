import java.util.ArrayList;


public class FindingRoots {

	public static void main(String[] args) {
		ArrayList<Double> equation = new ArrayList<Double>(); //roots = 0.365, 1.92, 3.56
		equation.add(2.0);	//2x3 – 11.7x2 + 17.7x – 5
		equation.add(-11.7);
		equation.add(17.7);
		equation.add(-5.0);
		bisection(0, 0.5, equation, 0.365098);

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
	public static void secant(double a, double b, ArrayList<Double> equation, double trueRoot){
		double start = 0, end = 0;
		if (a < b){
			start = a;
			end = b;
		}
		else{
			start = b;
			end = a;
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
