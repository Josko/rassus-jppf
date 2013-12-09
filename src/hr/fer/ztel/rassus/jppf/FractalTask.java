package hr.fer.ztel.rassus.jppf;

import hr.fer.ztel.rassus.complex.Complex;
import hr.fer.ztel.rassus.complex.ComplexPolynomial;
import hr.fer.ztel.rassus.complex.ComplexRootedPolynomial;

import org.jppf.server.protocol.JPPFTask;

/**
 * Helper class which represents one task of calculation of a fractal.
 * 
 * @author josko
 * 
 */

public class FractalTask extends JPPFTask {
	private static final long serialVersionUID = 8806142183797955533L;
	
	private double reMin;
	private double reMax;
	private double imMin;
	private double imMax;
	private int yMin;
	private int yMax;
	private int width;
	private int height;
	private short limit;
	private ComplexPolynomial polynom;
	private ComplexRootedPolynomial rootedPolynom;

	/**
	 * {@link MyFractalProducer} constructor.
	 * 
	 * @param reMin
	 * @param reMax
	 * @param imMin
	 * @param imMax
	 * @param yMin
	 * @param yMax
	 * @param width
	 * @param height
	 * @param m
	 * @param data
	 * @param rootedPolynom
	 * @param polynom
	 */

	public FractalTask(double reMin, double reMax, double imMin, double imMax, int yMin, int yMax, int width, int height, short m, ComplexRootedPolynomial rootedPolynom, ComplexPolynomial polynom) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.width = width;
		this.height = height;
		this.limit = m;
		this.rootedPolynom = rootedPolynom;
		this.polynom = polynom;
	}

	/**
	 * Method which called upon does the calculating.
	 */

	@Override
	public void run() {
		final short[] data = new short[width * (yMax - yMin)];
		
		int offset = 0;
		final double creFactor = (reMax - reMin) / (width - 1.0);
		final double cimFactor = (imMax - imMin) / (height - 1.0);
		final ComplexPolynomial derived = polynom.derive();

		for (int y = yMin; y < yMax; ++y) {
			for (int x = 0; x < width; ++x) {
				final double cre = x * creFactor + reMin;
				final double cim = (height - 1.0 - y) * cimFactor + imMin;

				int iter = 0;
				Complex zn = new Complex(cre, cim);
				Complex zn1 = new Complex(cre, cim);

				do {
					zn = new Complex(zn1.getReal(), zn1.getImaginary());
					final Complex numerator = polynom.apply(zn);
					final Complex denominator = derived.apply(zn);
					final Complex fraction = numerator.divide(denominator);

					zn1 = zn.sub(fraction);
					++iter;
				} while (zn1.sub(zn).module() > 0.001 && iter < limit);

				final short index = (short) rootedPolynom.indexOfClosestRootFor(zn1, 0.002);

				data[offset++] = (index == -1) ? 0 : index;
			}
		}
		
		this.setResult(new FractalIndexArray(yMin, yMax, data));
	}
}
