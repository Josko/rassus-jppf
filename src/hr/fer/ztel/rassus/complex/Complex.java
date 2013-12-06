package hr.fer.ztel.rassus.complex;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Complex class is used to represent complex numbers using floating point
 * values in double precision. Class provides methods for multiplication,
 * division, addition, negation, subtraction and module or complex numbers.
 * There are a few static methods as well.
 * 
 * @author josko
 * 
 */

public class Complex implements Serializable {
	private static final long serialVersionUID = 2131831278200895307L;
	
	private final double re;
	private final double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * {@link Complex} class constructor setting both values to zero.
	 */

	public Complex() {
		this.re = 0;
		this.im = 0;
	}

	/**
	 * {@link Complex} class constructor with both imaginary and real values
	 * passed as parameters.
	 * 
	 * @param re
	 *            Real part value.
	 * @param im
	 *            Real part value.
	 */

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Method returns the module of the complex number.
	 * 
	 * @return Module.
	 */
	
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Method returns this {@link Complex} number multiplied by the specified
	 * {@link Complex} number.
	 * 
	 * @param c
	 *            Multiplier.
	 * @return New {@link Complex} equal to this * c.
	 */

	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, this.im * c.re + this.re * c.im);
	}

	/**
	 * Method returns this {@link Complex} number divided by the specified
	 * {@link Complex} number.
	 * 
	 * @param c
	 *            Divisor.
	 * @return New {@link Complex} equal to this / c.
	 */

	public Complex divide(Complex c) {
		final double denominator = c.re * c.re + c.im * c.im;
		
		return new Complex((re * c.re + im * c.im) / denominator,
				(im * c.re - re * c.im) / denominator);
	}

	/**
	 * Method returns this {@link Complex} number plus the specified
	 * {@link Complex} number.
	 * 
	 * @param c
	 *            Summand.
	 * @return New {@link Complex} equal to this + c.
	 */

	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * Method returns this {@link Complex} number minus the specified
	 * {@link Complex} number.
	 * 
	 * @param c
	 *            Subtrahend.
	 * @return New {@link Complex} equal to this - c.
	 */

	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * Method returns this {@link Complex} number with negated values.
	 * 
	 * @return New {@link Complex} equal to this * (-1, -1).
	 */
	
	public Complex negate() {
		return new Complex(re * -1., im * -1.);
	}

	/**
	 * Method returns this {@link Complex} number in {@link String}
	 * representation.
	 */
	
	@Override
	public String toString() {
		String realAsString = new String();
		String imaginaryAsString = new String();
		DecimalFormat decimalFormat = new DecimalFormat("#.###");

		if (Math.abs(re) > 10e-6) {
			realAsString = decimalFormat.format(re);
		}

		if (Math.abs(im) > 10e-6) {
			imaginaryAsString = (im >= 0. ? "+" : "") + decimalFormat.format(im) + "i";
		}

		if (realAsString.length() == 0 && imaginaryAsString.length() == 0) {
			realAsString = "0";
		}

		return String.format("%s%s", realAsString, imaginaryAsString);
	}

	/**
	 * Method returns the real value of this {@link Complex} number.
	 * 
	 * @return Real part.
	 */

	public double getReal() {
		return re;
	}

	/**
	 * Method returns the imaginary value of this {@link Complex} number.
	 * 
	 * @return Imaginary part.
	 */

	public double getImaginary() {
		return im;
	}
}
