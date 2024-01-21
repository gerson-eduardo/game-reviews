package datadives.io.msreviews.service;

import datadives.io.msreviews.mapper.UsuarioMapper;
import datadives.io.msreviews.model.Usuario;
import datadives.io.msreviews.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private UsuarioRepository repository;
    private UsuarioMapper mapper;

    @Autowired
    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado")
        );
    }

    public List<String> findAll() {
        return repository.findAll().stream()
                .map(Usuario::getEmail)
                .collect(Collectors.toList());
    }

    public void create(Usuario user) {
        try {
            repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario com mesmo email já existe");
        }
    }

    public void update(Usuario user) {
        Usuario usuario = repository.findById(user.getUsuarioId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado")
        );
        repository.save(mapper.updateUsuarioFromUsuario(user, usuario));
    }

    public void deleteByEmail(String email){
        try {
            repository.deleteByEmail(email);
        }catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }
    }
}
