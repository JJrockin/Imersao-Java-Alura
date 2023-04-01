package br.com.jjrockin.languagesapi.controllers;

import br.com.jjrockin.languagesapi.entities.Language;
import br.com.jjrockin.languagesapi.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping
    public List<Language> getLanguages() {
        return languageRepository.findByOrderByRanking();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Language>> getLanguageById(@PathVariable String id){
        return new ResponseEntity<Optional<Language>>(languageRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Language> addLanguages(@RequestBody Language language){
        return new ResponseEntity<Language>(languageRepository.save(language), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguageById(@PathVariable String id, @RequestBody Language language) {
        if(!languageRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        language.setId(id);
        return new ResponseEntity<Language>(languageRepository.save(language), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteLanguageById(@PathVariable String id){
        languageRepository.deleteById(id);
    }

}
