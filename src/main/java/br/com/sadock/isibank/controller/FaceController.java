package br.com.sadock.isibank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sadock.isibank.dto.FaceDTO;
import br.com.sadock.isibank.dto.RecognitionDTO;
import br.com.sadock.isibank.security.IsiToken;
import br.com.sadock.isibank.service.auth.IAuthService;
import br.com.sadock.isibank.service.facerecognition.IFaceRecognition;
import br.com.sadock.isibank.util.FaceMode;

@RestController
@CrossOrigin("*")
public class FaceController {

	@Autowired
	private IFaceRecognition handler;
	
	@Autowired
	private IAuthService authService;

	@PostMapping("/face/register")
	public ResponseEntity<?> registerFace(@RequestBody FaceDTO faceDto) {
		String filename = handler.toImage(faceDto);

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
	public ResponseEntity<IsiToken> recognizeFace(@RequestBody FaceDTO faceDto) {

		RecognitionDTO confidence = handler.performRecognition(faceDto);
		IsiToken token = authService.authenticateByFace(confidence);
		
		if (token != null) {
			return ResponseEntity.ok(token);
		}

		return ResponseEntity.status(403).build();
	}

}
