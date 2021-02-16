package studia.isa.monikowski.animal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studia.isa.monikowski.animal.entity.Species;
import studia.isa.monikowski.animal.repository.SpeciesRepository;


import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class SpeciesService {

    private SpeciesRepository repository;


    @Autowired
    public SpeciesService(SpeciesRepository repository) {
        this.repository = repository;
    }

    public Optional<Species> find(String name) {return repository.findById(name);}

    public List<Species> findAll() {return repository.findAll();}

    @Transactional
    public void create(Species species) {
        repository.save(species);
    }

    @Transactional
    public void delete(Species species) {
        repository.delete(species);
    }

    @Transactional
    public void update(Species species) {repository.save(species);}
}