package br.com.sadock.isibank.service.facerecognition;

public interface IFaceRecognition {
	public String toImage(String base64);
	public boolean cropFace(String filename);

}
