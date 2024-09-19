package com.morpembot.MorphemBot.dataBase;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    @Query(value = "select * from users_data_table where user_id = ?1", nativeQuery = true)
    User findByUserId(long userId);
}
