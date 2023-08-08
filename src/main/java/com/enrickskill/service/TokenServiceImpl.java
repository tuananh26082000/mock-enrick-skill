package com.enrickskill.service;

import com.enrickskill.repository.TokenRepository;
import com.enrickskill.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TokenServiceImpl implements TokenService{
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void delete(Integer id) {
        tokenRepository.deleteById(id);
    }
}
