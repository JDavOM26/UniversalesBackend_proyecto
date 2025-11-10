package com.universales.gestionseguros.service;

import java.util.Collections;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.universales.gestionseguros.entity.Usuario;
import com.universales.gestionseguros.repository.UsuarioRepository;




@Service
public class JwtUserDetailsService  implements UserDetailsService {
  
    private final UsuarioRepository userRepository;
   
   public JwtUserDetailsService(UsuarioRepository userRepository) {
	   this.userRepository = userRepository;
   }
    
    @Override
    public UserDetails loadUserByUsername(String username){
        Usuario user = userRepository.findByUsername(username);
        
     
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
        }
        	
        
    
}
