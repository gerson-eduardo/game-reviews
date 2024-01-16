package datadives.io.msreviews.service;

import datadives.io.msreviews.model.Usuario;
import datadives.io.msreviews.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public Usuario findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado")
        );
    }
    public List<String> findAll(){
    return repository.findAll().stream()
            .map(Usuario::getEmail)
            .collect(Collectors.toList());
    }
}
