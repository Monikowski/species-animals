package studia.isa.monikowski.file.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.util.UriComponentsBuilder;
import studia.isa.monikowski.animal.dto.GetAnimalsResponse;
import studia.isa.monikowski.animal.entity.Animal;
import studia.isa.monikowski.file.dto.GetFilesResponse;
import studia.isa.monikowski.file.entity.Details;
import studia.isa.monikowski.file.service.DetailsService;
import studia.isa.monikowski.file.storage.StorageService;

@RestController
@RequestMapping("api/files")
public class FileUploadController {

    private final StorageService storageService;
    private final DetailsService detailsService;

    @Autowired
    public FileUploadController(StorageService storageService, DetailsService detailsService) {
        this.storageService = storageService;
        this.detailsService = detailsService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<GetFilesResponse> getFiles()
    {
        return ResponseEntity.ok(GetFilesResponse.entityToDtoMapper().apply(detailsService.findAll()));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Void> handleFileUpload(@RequestPart("file") MultipartFile file,
                                                 @RequestParam("info") String some_text,
                                                 @RequestParam("email") String email,
                                                 UriComponentsBuilder builder) {
        Details details = Details.builder()
                .some_text(some_text)
                .email(email)
                .file_name(file.getOriginalFilename())
                .build();
        storageService.store(file);
        detailsService.create(details);
        return ResponseEntity.created(builder.pathSegment("api", "file", "{id}")
                .buildAndExpand(details.getId()).toUri()).build();
    }

    @CrossOrigin
    @GetMapping("{id}")
    public ResponseEntity<Resource> serveFile(@PathVariable Long id) {
        return detailsService.find(id)
                .map(fileInfo -> {
                    Resource file = storageService.loadAsResource(fileInfo.getFile_name());
                    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}