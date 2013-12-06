package hr.fer.ztel.rassus.jppf;

import hr.fer.ztel.rassus.complex.ComplexPolynomial;
import hr.fer.ztel.rassus.complex.ComplexRootedPolynomial;

import org.jppf.client.JPPFJob;

/**
 * Helper class which helps produce the fractal in a parallel way.
 * 
 * @author josko
 * 
 */

public class FractalJob {
	public static JPPFJob createJob(double reMin, double reMax, double imMin, double imMax, int width, int height, ComplexRootedPolynomial rootedPolynom, ComplexPolynomial polynom) throws Exception {
		JPPFJob job = new JPPFJob();
		job.setName("RASSUS - distribuiran izracun fraktala");
		job.setBlocking(true);
		
		final short limit = 1024;

		for (int heightInterval = 0; heightInterval < height; heightInterval += 64) {
			final int yMin = heightInterval;
			int yMax = heightInterval + 64;

			if (height - heightInterval < 64) {
				yMax = height - 1;
			}

			final FractalTask task = new FractalTask(reMin, reMax, imMin, imMax, yMin, yMax, width, height, limit, rootedPolynom, polynom);
			
			job.addTask(task);
		}
		
		return job;
	}
}
