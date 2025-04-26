package br.com.sadock.isibank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sadock.isibank.dto.FaceDTO;
import br.com.sadock.isibank.service.facerecognition.IFaceRecognition;

@RestController
@CrossOrigin("*")
public class FaceController {
	
	@Autowired
	private IFaceRecognition handler;
	
	@PostMapping("/face/register")
	private ResponseEntity<?> registerFace(@RequestBody FaceDTO faceDto) {
		String filename = handler.toImage(faceDto.getData());
		
		if (handler.cropFace(filename)) {
			return ResponseEntity.ok(filename);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	
	
	

}
