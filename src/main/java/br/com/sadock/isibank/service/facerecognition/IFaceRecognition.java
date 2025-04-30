package br.com.sadock.isibank.service.facerecognition;

import br.com.sadock.isibank.dto.FaceDTO;
import br.com.sadock.isibank.dto.RecognitionDTO;

public interface IFaceRecognition {
	public String toImage(FaceDTO faceData);
	public boolean cropFace(String filename, String extension, int mode);
	public void performTraining();
	public RecognitionDTO performRecognition(FaceDTO data);

}
