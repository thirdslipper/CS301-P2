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

	public static void bisection(double a, double b, ArrayList<Double> equation, double trueRoot){

		double c, error = 1;
		double fa, fb, fc;
		for (int i = 0; i < 5; ++i){
			//loop or change parameters and loop whole
			fa = calcEquation(a, equation);
			fb = calcEquation(b, equation);

			c = (a+b)/2;
			fc = calcEquation(c, equation);

			System.out.println(fa + " " + fb + " " + fc + " " + c);
			if ((fc < 0 && fa >= 0) || (fc >= 0 && fa < 0))
				b = c;
			else if ((fc < 0 && fb >= 0) || (fc >= 0 && fb < 0)){
				a = c;
			}
			
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
