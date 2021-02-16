package studia.isa.monikowski.file.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.file.entity.Details;
import studia.isa.monikowski.file.repository.DetailsRepository;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DetailsService {

    private DetailsRepository detailsRepository;

    @Autowired
    public DetailsService(DetailsRepository repository) {
        this.detailsRepository = repository;
    }

    public List<Details> findAll() {return detailsRepository.findAll();}

    public Optional<Details> find(Long id) {return detailsRepository.findById(id);}

    @Transactional
    public Details create(Details details) {return detailsRepository.save(details);}

}

