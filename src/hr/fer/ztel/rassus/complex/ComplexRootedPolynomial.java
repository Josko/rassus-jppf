package hr.fer.ztel.rassus.complex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComplexRootedPolynomial implements Serializable {
	private static final long serialVersionUID = -6901670583538291061L;
	
	private final List<Complex> roots;

	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = new ArrayList<Complex>(roots.length);

		for (int position = roots.length - 1; position >= 0; --position) {
			this.roots.add(roots[position]);
		}
	}

	// computes polynomial value at given point z
	
	public Complex apply(Complex z) {
		Complex complexPolyValue = Complex.ONE;

		for (int position = 0; position < roots.size(); ++position) {
			complexPolyValue = complexPolyValue.multiply(roots.get(position).add(z));
		}

		return complexPolyValue;
	}

	// converts this representation to ComplexPolynomial type
	
	public ComplexPolynomial toComplexPolynom() {
		if(roots.size() == 0)
			return null;
		
		final Complex[] coeffcients = new Complex[roots.size() + 1];
		coeffcients[0] = Complex.ONE;
		coeffcients[1] = roots.get(0).negate();
		
		for(int i = 1; i < roots.size(); ++i) {
			coeffcients[i+1] = coeffcients[i].negate().multiply(roots.get(i));
			
			for(int j = i; j > 0; --j) {
				coeffcients[j] = coeffcients[j].sub(coeffcients[j-1].multiply(roots.get(i)));
			}
		}
		
		return new ComplexPolynomial(coeffcients);
	}	

	// finds index of closest root for given complex number z that is within
	// threshold
	// if there is no such root, returns -1
	
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int closestRootIndex = -1;
		final double distance = treshold + 1.;
		
		for(int index = 0; index < roots.size(); ++index) {
			final double currentDistance = roots.get(index).sub(z).module();
			
			 if(currentDistance < treshold && currentDistance < distance) {
				 closestRootIndex = index + 1; 
			 }
		}
		
		return closestRootIndex;
	}
	
	/**
	 * Method returns this {@link ComplexRootedPolynomial} function in {@link String}
	 * representation.
	 */

	@Override
	public String toString() {
		String complexPolyAsString = new String();

		for (int position = roots.size() - 1; position >= 0; --position) {
			String sign = "-";
			String complexAsString = roots.get(position).toString();
			
			if(complexAsString.charAt(0) == '-') {
				sign = "+";				
				complexAsString = complexAsString.substring(1);
			} else if (complexAsString.charAt(0) == '+') {
				complexAsString = complexAsString.substring(1);
			}
			
			complexPolyAsString += String.format("(z%s%s)", sign, complexAsString);
		}

		return complexPolyAsString;
	}
}
