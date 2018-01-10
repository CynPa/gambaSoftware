package com.cynpa.main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    List<Usuario> findByApellido(String apellido);

    List<Usuario> findByEdadLessThan(Integer edad);

}
