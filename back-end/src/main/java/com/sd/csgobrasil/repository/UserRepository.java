package com.sd.csgobrasil.repository;

import com.sd.csgobrasil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmailOrNome(String email, String nome);

    boolean existsUserByEmailAndSenha(String email, String senha);

    User findUsersByEmail(String email);

}
