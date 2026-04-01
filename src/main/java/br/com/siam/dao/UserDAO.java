package br.com.siam.dao;

import br.com.siam.model.User;
import java.util.List;
import java.util.Optional;

// Interface de Data Access Object para o Usuário utilizando CRUD
public interface UserDAO extends GenericDAO<User> {

    Optional<User> findByLogin(String login); // Método de procura de usuário por login

}
