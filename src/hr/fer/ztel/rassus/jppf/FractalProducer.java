package hr.fer.ztel.rassus.jppf;

import java.util.List;

import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.server.protocol.JPPFTask;

import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;
import hr.fer.ztel.rassus.complex.ComplexPolynomial;
import hr.fer.ztel.rassus.complex.ComplexRootedPolynomial;

public class FractalProducer implements IFractalProducer {
	
	private final ComplexRootedPolynomial rootedPolynom;
	private final ComplexPolynomial polynom;

	public FractalProducer(ComplexRootedPolynomial rootedPolynom) {
		this.rootedPolynom = rootedPolynom;
		this.polynom = rootedPolynom.toComplexPolynom();
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer) {		
		JPPFClient client = null;
		
		try {
			client = new JPPFClient();
			
			final JPPFJob job = FractalJob.createJob(reMin, reMax, imMin, imMax, width, height, rootedPolynom, polynom);			
			final List<JPPFTask> results = client.submit(job);
			
			final short data[] = new short[width * height];
			
			for (final JPPFTask result : results) {
				if (result.getException() != null) {
					System.err.println("Exception occured while getting result: " + result.getException().getMessage());
					result.getException().printStackTrace();
				} else {					
					final FractalIndexArray fia = (FractalIndexArray) result.getResult();
					
					int offset = width * fia.getYMin();
					
					for (int index = 0; index < fia.getDataSize(); ++index) {
						data[offset++] = fia.getDataAt(index);
					}				
				}
			}
			
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) client.close();
		}		
	}

}
