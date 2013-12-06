package hr.fer.ztel.rassus.dz2;

import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.ztel.rassus.complex.Complex;
import hr.fer.ztel.rassus.complex.ComplexRootedPolynomial;
import hr.fer.ztel.rassus.jppf.FractalProducer;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		final Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");

		int rootCount = 0;
		final List<Complex> roots = new LinkedList<Complex>();

		while (true) {
			System.out.print("Root " + (rootCount + 1) + "> ");
			String line = scanner.nextLine();
			line = line.trim();

			if (line.length() == 0) {
				System.out.println("Empty string is not legal complex number.");
				System.exit(-1);
			}

			if (line.equals("done")) {
				if (rootCount < 2) {
					System.out.println("You must enter at least two roots, one root per line.");
					System.exit(-1);
				}

				break;
			}

			final String[] tokens = line.split(" ");

			double re = 0;
			double im = 0;

			if (tokens.length == 1) {
				if (tokens[0].charAt(0) == 'i') {
					im = tokenToRootPart(tokens[0]);
				} else {
					re = tokenToRootPart(tokens[0]);
				}
			} else if (tokens.length == 3) {
				re = tokenToRootPart(tokens[0]);
				im = tokenToRootPart(tokens[2]);

				if (tokens[1].equals("-")) {
					im *= -1.;
				}
			} else {
				System.out.println("Invalid complex number.");
				System.exit(-1);
			}

			roots.add(new Complex(re, im));
			++rootCount;
		}
		
		scanner.close();

		int offset = 0;
		final Complex[] rootsArray = new Complex[roots.size()];

		for (final Complex root : roots) {
			rootsArray[offset++] = root;
		}
		
		FractalViewer.show(new FractalProducer(new ComplexRootedPolynomial(rootsArray)));
	}
	
	/**
	 * Helper method which turns a string token into a number. The string token
	 * should represent a real or imaginary part of a {@link Complex} number. If
	 * it encounters an illegal token or invalid complex value it will exit with
	 * the error code -1.
	 * 
	 * @param token
	 *            Real or imaginary part of a {@link Complex} number.
	 * @return Value as {@link Double}.
	 */

	private static double tokenToRootPart(String token) {
		double value = 0;

		if (token.charAt(0) == 'i') {
			if (token.length() == 1) {
				return 1.;
			}

			token = token.substring(1);
		}

		try {
			value = Double.parseDouble(token);
		} catch (NumberFormatException e) {
			System.out.println("Not a valid complex number.");
			System.exit(-1);
		}

		return value;
	}
}
