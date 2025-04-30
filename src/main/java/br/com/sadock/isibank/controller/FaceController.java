package br.com.sadock.isibank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sadock.isibank.dto.FaceDTO;
import br.com.sadock.isibank.service.facerecognition.IFaceRecognition;
import br.com.sadock.isibank.util.FaceMode;

@RestController
@CrossOrigin("*")
public class FaceController {

	@Autowired
	private IFaceRecognition handler;

	@PostMapping("/face/register")
	public ResponseEntity<?> registerFace(@RequestBody FaceDTO faceDto) {
		String filename = handler.toImage(faceDto.getData());

		if (handler.cropFace(filename, "-crop.png", FaceMode.REGISTER)) {
			handler.performTraining();
			return ResponseEntity.ok(filename);
		}

		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/face/training")
	public String performFaceTraining() {
		handler.performTraining();

		return "Ok";
	}

	@PostMapping("/face/recognize")
	public String recognizeFace(@RequestBody FaceDTO faceDto) {
		Double confidence = 0.0;
		String filename = handler.toImage(faceDto.getData());
		
		if (handler.cropFace(filename, "-recog.png", FaceMode.RECOGNITION)) {
			confidence = handler.performRecognition(faceDto);
		}

		return String.format("Similarity = %.5f", confidence);
	}

}
