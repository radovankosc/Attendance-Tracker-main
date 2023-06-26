package com.ctrlaltelite.backend.repositories;

import com.ctrlaltelite.backend.models.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
//public interface UserRepository extends JpaRepository<AppUser, Long> {
public interface UserRepository extends CrudRepository<AppUser,Long> { //CRUDRepository Spring Data interface for generic CRUD operations on a repository of a specific type

  Iterable<AppUser> findAll();
  Optional<AppUser> findByUsername(String username); //query method
  Optional<AppUser> findByEmail(String username);

  Optional<AppUser> findByPwResetCode(String username);

  Iterable<AppUser> findAllByActive(boolean active);

  Optional<AppUser> getAppUserByIdIs(Long userId);

}

