package br.com.sadock.isibank.service.facerecognition;

import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.DatatypeConverter;

@Service
public class FaceRecognitionImpl implements IFaceRecognition {
	
	@Value("${io.isiflix.tempfolder}")
	private String tmpFolder;
	
	@Value("${io.isiflix.cropfaces}")
	private String cropFaces;
	

	@Override
	public String toImage(String base64Content) {
		try {
			String fileName = tmpFolder + File.separator + UUID.randomUUID().toString() + ".png";
			byte[] imageData = DatatypeConverter.parseBase64Binary(base64Content);
			File newImage = new File(fileName);
			OutputStream output = new BufferedOutputStream(new FileOutputStream(newImage));
			output.write(imageData);
			output.close();
			return fileName;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean cropFace(String filename) {
		try {
			CascadeClassifier classifier = new CascadeClassifier();
			classifier.load("haarcascade_frontalface_default.xml");
			
			// vou ler a imagem
			Mat image = imread(filename, IMREAD_COLOR);
			if (image != null) {
				RectVector faces = new RectVector();
				classifier.detectMultiScale(image, faces);
				System.out.println("Faces detected = " + faces.size());
				if (faces.size() > 0) {
					Mat croppedFace = new Mat(image, faces.get(0));
					String newFile = filename.replace(tmpFolder, cropFaces);
					imwrite(newFile+"-crop.png", croppedFace);
					return true;
				}
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	

}
