/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.com.biometria;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;

/**
 *
 * @author GerenteTI
 */
public class Biometria {

	public static Boolean compararDigitais(DPFPTemplate digitalPrevia,
			DPFPSample digitalAtual) {

		Boolean encontrou = false;

		try {
			DPFPFeatureExtraction featureExtractor = DPFPGlobal
					.getFeatureExtractionFactory().createFeatureExtraction();
			DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(
					digitalAtual, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

			DPFPVerification matcher = DPFPGlobal.getVerificationFactory()
					.createVerification();
			matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);

			DPFPVerificationResult result = matcher.verify(featureSet,
					digitalPrevia);

			encontrou = result.isVerified();
		} catch (Exception e) {
			System.out.printf("Erro!!! " + e.getMessage() + e.getCause());
		}

		return encontrou;
	}

	/* Classe do exemplo da DigitalPersona */
	public static DPFPSample getDigital() throws InterruptedException {
		final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
		DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
                
		capture.setReaderSerialNumber(null);
		capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
		capture.addDataListener(new DPFPDataListener() {
			public void dataAcquired(DPFPDataEvent e) {
				if (e != null && e.getSample() != null) {
					try {
						samples.put(e.getSample());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		capture.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;

			public void readerConnected(DPFPReaderStatusEvent e) {
				if (lastStatus != e.getReaderStatus())
					System.out.println("Leitor conectado!");
				lastStatus = e.getReaderStatus();
			}

			public void readerDisconnected(DPFPReaderStatusEvent e) {
				if (lastStatus != e.getReaderStatus())
					System.out.println("O leitor esta desconectado!");
				lastStatus = e.getReaderStatus();
			}

		});
		try {
                        JOptionPane.showMessageDialog(null, "Registre a digital de um dos seus dedos para verificacao.");
			capture.startCapture();
                                                
                       
			return samples.take();
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null, "Falha ao iniciar captura. Verifique se o leitor nao esta sendo usado por outra applicacao.");
			throw e;
		} finally {
			capture.stopCapture();
		}
	}    
}
