package com.morpembot.MorphemBot.dataBase;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {
    @Query(value = "select * from words_data_table where entered = ?1", nativeQuery = true)
    Word findByEntered(String entered);
}
