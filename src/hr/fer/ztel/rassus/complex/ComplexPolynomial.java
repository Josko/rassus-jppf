package hr.fer.ztel.rassus.complex;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ComplexPolynomial class is used to represent polynomial functions with
 * complex coefficients. Class provides methods for multiplication, division,
 * addition, negation, subtraction and module. There are a few static methods as
 * well.
 * 
 * @author josko
 * 
 */

public class ComplexPolynomial implements Serializable {
	private static final long serialVersionUID = -4820768264534260293L;
	
	private final ArrayList<Complex> factors;

	/**
	 * {@link ComplexPolynomial} constructor which takes an arbitrary number of
	 * {@link Complex} factors. Factors must be given in descending order by the
	 * magnitude of the variable they are at.
	 * 
	 * @param factors
	 */

	public ComplexPolynomial(Complex... factors) {
		this.factors = new ArrayList<Complex>(factors.length);

		for (int factorsIterator = factors.length - 1; factorsIterator >= 0; --factorsIterator) {
			this.factors.add(factors[factorsIterator]);
		}
	}

	/**
	 * Method returns the list {@link Complex} factors of this
	 * {@link ComplexPolynomial}.
	 * 
	 * @return {@link ArrayList} of complex factors.
	 */

	public ArrayList<Complex> getFactors() {
		return factors;
	}

	/**
	 * Method returns the order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1
	 * returns 3
	 * 
	 * @return order of this polynom.
	 */

	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Method computes a new polynomial of this {@link ComplexPolynomial}
	 * multiplied by the specified polynomial.
	 * 
	 * @param p
	 *            Multiplier {@link ComplexPolynomial}
	 * @return New polynomial this * p.
	 */

	public ComplexPolynomial multiply(ComplexPolynomial p) {
		final Complex[] multiplyFactors = new Complex[p.order() + this.order() + 1];
		final ArrayList<Complex> pFactors = p.getFactors();

		for (int index = 0; index < multiplyFactors.length; ++index) {
			multiplyFactors[index] = Complex.ZERO;
		}

		for (int firstPolyFactorIndex = p.order(); firstPolyFactorIndex >= 0; --firstPolyFactorIndex) {
			for (int secondPolyFactorIndex = this.order(); secondPolyFactorIndex >= 0; --secondPolyFactorIndex) {
				multiplyFactors[multiplyFactors.length - secondPolyFactorIndex
						- firstPolyFactorIndex - 1] = factors.get(secondPolyFactorIndex).multiply(pFactors.get(firstPolyFactorIndex));
			}
		}

		return new ComplexPolynomial(multiplyFactors);
	}

	/**
	 * Method computes the first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return First derivative of this polynomial.
	 */

	public ComplexPolynomial derive() {
		int offset = 0;
		Complex[] derivedFactors = new Complex[factors.size() - 1];

		for (int position = factors.size() - 1; position >= 1; --position) {
			derivedFactors[offset++] = factors.get(position).multiply(new Complex(position, 0.));
		}

		return new ComplexPolynomial(derivedFactors);
	}

	/**
	 * Method applies the specified {@link Complex} value to this function and
	 * returns the value computed.
	 * 
	 * @param z
	 *            Point to calculate at.
	 * @return Value of the this {@link ComplexPolynomial} in z.
	 */

	public Complex apply(Complex z) {
		Complex polyValue = Complex.ZERO;

		for (int position = 0; position < factors.size(); ++position) {
			Complex currentFactor = Complex.ONE;

			for (int numOfMultiplies = 0; numOfMultiplies < position; ++numOfMultiplies) {
				currentFactor = currentFactor.multiply(z);
			}

			polyValue = polyValue.add(factors.get(position).multiply(currentFactor));
		}

		return polyValue;
	}

	/**
	 * Method returns this {@link ComplexPolynomial} function in {@link String}
	 * representation.
	 */

	@Override
	public String toString() {
		String polyAsString = new String();

		for (int position = factors.size() - 1; position >= 0; --position) {
			if (position == 1) {
				polyAsString += "(" + factors.get(position) + ")z+";
			} else if (position != 0) {
				polyAsString += "(" + factors.get(position) + ")z^" + position + "+";
			} else {
				polyAsString += "(" + factors.get(position) + ")";
			}
		}

		return polyAsString;
	}
}