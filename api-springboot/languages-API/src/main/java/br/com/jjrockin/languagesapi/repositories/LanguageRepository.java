package br.com.jjrockin.languagesapi.repositories;

import br.com.jjrockin.languagesapi.entities.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends MongoRepository<Language, String> {
    List<Language> findByOrderByRanking();
}
