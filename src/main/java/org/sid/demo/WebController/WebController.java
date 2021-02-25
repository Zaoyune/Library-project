package org.sid.demo.WebController;

import org.sid.demo.Repositories.BookRepository;
import org.sid.demo.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class WebController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping(path = "/photoBook/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Book b = bookRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/Library/books/"+b.getPhotoName()));

    }
    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException {
        Book b = bookRepository.findById(id).get();
        //enregistrer le nom de laphoto en database par son id
        //p.setPhotoName(id+".png");
        b.setPhotoName(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home")+"/Library/books/"+b.getPhotoName()), file.getBytes());
        bookRepository.save(b);
    }
}
