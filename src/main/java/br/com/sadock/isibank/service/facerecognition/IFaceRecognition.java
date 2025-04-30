package br.com.sadock.isibank.service.facerecognition;

import br.com.sadock.isibank.dto.FaceDTO;

public interface IFaceRecognition {
	public String toImage(String base64);
	public boolean cropFace(String filename, String extension, int mode);
	public void performTraining();
	public Double performRecognition(FaceDTO data);

}
