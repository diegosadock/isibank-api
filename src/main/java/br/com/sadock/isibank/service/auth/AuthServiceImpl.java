package br.com.sadock.isibank.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sadock.isibank.dto.RecognitionDTO;
import br.com.sadock.isibank.model.Cliente;
import br.com.sadock.isibank.repo.ClienteRepo;
import br.com.sadock.isibank.security.IsiToken;
import br.com.sadock.isibank.security.TokenUtil;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private ClienteRepo repo;

	@Override
	public IsiToken authenticate(String key, String password) {
		// TODO Auto-generated method stub
		Cliente c = repo.findByEmailOrCpfOrTelefone(key, key, key);

		if (c != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(password, c.getSenha())) {
				IsiToken token = TokenUtil.encode(c);

				return token;
			}
		}

		return null;
	}

	@Override
	public IsiToken authenticateByFace(RecognitionDTO faceData) {
		// TODO Auto-generated method stub
		
		if (faceData.confidence() > 50.0) {
			Cliente c = repo.findById(faceData.label()).get();
			
			if (c != null) {
				return TokenUtil.encode(c);
			}
		}
		
		return null;
	}

}
