package com.qnowapp.web.rest;

import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.User;
import com.qnowapp.repository.ImEmployeeRepository;
import com.qnowapp.repository.QnowUserRepository;
import com.qnowapp.repository.UserRepository;
import com.qnowapp.security.jwt.JWTFilter;
import com.qnowapp.security.jwt.TokenProvider;
import com.qnowapp.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class UserJWTController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	QnowUserRepository qnowUserRepository;
	
	@Autowired
	ImEmployeeRepository imEmployeeRepository;
	
    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        
        User user = new User();
        user = userRepository.findBylogin(loginVM.getUsername());
        
        QnowUser qnowUser = qnowUserRepository.findByuser(user);
        
        ImEmployee imEmployee = imEmployeeRepository.findByqnowUser(qnowUser);
        
        
        
 
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
       
        return new ResponseEntity<>(new JWTToken(jwt , imEmployee), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        
        private ImEmployee imemployee;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        JWTToken(String jwt, ImEmployee imemployee) {
        	this.idToken = jwt;
        	this.imemployee = imemployee;
		}

		@JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

		public ImEmployee getImemployee() {
			return imemployee;
		}

		public void setImemployee(ImEmployee imemployee) {
			this.imemployee = imemployee;
		}

	
        
        
    }
}
