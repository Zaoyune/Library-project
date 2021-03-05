package org.sid.demo.WebController;

import org.sid.demo.Repositories.BookRepository;
import org.sid.demo.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class WebController {
    @Autowired
    private BookRepository bookRepository;


    @GetMapping(path = "/photoBook/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception {
        Book b = bookRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Library/books/" + b.getPhotoName()));

    }


    @RequestMapping("/user")
    public Principal user(Principal user,HttpServletResponse response) {
        System.out.println("Im here");
        return user;
    }


    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {

        SecurityContextLogoutHandler securityContextLogoutHandler =
                new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);

    }


    @GetMapping("/books")
    List<Book> getBooks() {
        return  bookRepository.findAll();
    }


    @RequestMapping("/userA")
    public String authentication() {
        return "Successful Authentication";
    }







    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException {
        Book b = bookRepository.findById(id).get();
        //enregistrer le nom de laphoto en database par son id
        //p.setPhotoName(id+".png");
        b.setPhotoName(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/Library/books/" + b.getPhotoName()), file.getBytes());
        bookRepository.save(b);
    }
}
