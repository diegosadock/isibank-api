package br.com.sadock.isibank.service.auth;

import br.com.sadock.isibank.dto.RecognitionDTO;
import br.com.sadock.isibank.security.IsiToken;

public interface IAuthService {
	public IsiToken authenticate(String key, String password);
	public IsiToken authenticateByFace(RecognitionDTO faceData);

}
