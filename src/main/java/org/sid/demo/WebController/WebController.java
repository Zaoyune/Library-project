package org.sid.demo.WebController;

import lombok.Data;
import org.sid.demo.Repositories.BookRepository;
import org.sid.demo.Repositories.ReservationRepository;
import org.sid.demo.Repositories.StudentRepository;
import org.sid.demo.entities.Book;
import org.sid.demo.entities.Reservation;
import org.sid.demo.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class WebController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(path = "/photoBook/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception {
        Book b = bookRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Library/books/" + b.getPhotoName()));

    }


    @RequestMapping("/user")
    public Principal user(Principal user, HttpServletResponse response) {
        System.out.println("Im here");
        return user;
    }


    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {

        SecurityContextLogoutHandler securityContextLogoutHandler =
                new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);

    }


    @GetMapping("/bookss")
    List<Book> getBookss() {
        return bookRepository.findAll();
    }


    @RequestMapping("/userA")
    public String authentication() {
        return "Successful Authentication";
    }






    /*********************************************!Books**************************/
    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException {
        System.out.println("seeeeeeeeeee me");
        Book b = bookRepository.findById(id).get();

        //enregistrer le nom de laphoto en database par son id
        //p.setPhotoName(id+".png");
        b.setPhotoName(file.getOriginalFilename());System.out.println(b);
        Files.write(Paths.get(System.getProperty("user.home") + "/Library/books/" + b.getPhotoName()), file.getBytes());
        bookRepository.save(b);
    }

  @GetMapping("/getBook/{id}")
  Book Go(@PathVariable  Long id)
  {
      return bookRepository.findById(id).get();
  }



    @GetMapping("/Dbooks/{id}")
    public Map<String, Boolean> dB(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + id));
        System.out.println("im heeeeeeeeeeeere" +book);
        bookRepository.delete(book);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Book Deleted", Boolean.TRUE);
        return response;
    }

    @RequestMapping(value = "/Sbooks", method = RequestMethod.GET)
    public Page<Book> FinBooks(@RequestParam(name = "mc", defaultValue = "") String mc,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "5") int size) {

        return bookRepository.findByNameContains(mc, PageRequest.of(page, size))
                ;
    }

    @PostMapping(value = "/AddBook")
    public Book save(@RequestBody Book b) {
        return bookRepository.save(b);
    }

    @GetMapping("/books")
    List<Book> getBooks() {


        return bookRepository.findAll();
    }
    @GetMapping("/res")
    List<Reservation> getRes() {


        return reservationRepository.findAll();
    }
    @GetMapping("/booksDis")
    List<Book> getBooksDis() {

        return bookRepository.findBookByDisponiblityIsTrue();
    }

    @GetMapping("/studentss")
    List<Student> getstudents() {
        return studentRepository.findAll();
    }

    @PatchMapping("/editB/{id}")
    public Book update(@PathVariable("id") Long id, @RequestBody Book book) {
        book.setId(id);
        return bookRepository.save(book);
    }

    /**************************************students*************/
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getAbsence(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
        return ResponseEntity.ok().body(student);
    }

    @RequestMapping(value = "/addS", method = RequestMethod.POST)
    public Student save(@RequestBody Student s) {

        s.setPassword(passwordEncoder.encode(s.getPassword()));
        return studentRepository.save(s);
    }



    @PutMapping("/editS/{id}")
    public Student update(@PathVariable("id") Long id, @RequestBody Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    @GetMapping("/Dstudent/{id}")
    public Map<String, Boolean> dS(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Student Deleted", Boolean.TRUE);
        return response;
    }

    @RequestMapping(value = "/Sstudents", method = RequestMethod.GET)

    public Page<Student> Find(@RequestParam(name = "mc", defaultValue = "") String mc,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size) {

        return studentRepository.findByLastContains(mc, PageRequest.of(page, size))
                ;
    }
    @RequestMapping(value = "/Sres", method = RequestMethod.GET)

    public Page<Reservation> FindRes(@RequestParam(name = "mc", defaultValue = "") String mc,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size) {

        return reservationRepository.findReservationByStudent_Last(mc, PageRequest.of(page, size))
                ;
    }

    /******************************* Reservation**********************/
    @PostMapping(path = "/saveReservation")
    public Reservation saveReservation(@RequestBody FromReservation reservation) {
        if (reservation != null) {
            Student student = studentRepository.findByName(reservation.nameStd);

            Book book = bookRepository.findById(reservation.idBook).get();

            book.setDisponiblity(false);
            bookRepository.save(book);
            Reservation p = reservationRepository.save(new Reservation(null, reservation.StratRes, book, student, reservation.EndRes, false));
            return p;
        } else
            return null;

    }
    @GetMapping("/getDate/{id}")
    LocalDate getDate(@PathVariable Long id){

        Reservation reservation = reservationRepository.findReservationByBook_Id(id);
        return reservation.getDate_limit();
    }

    @GetMapping("/reservations")
    Page<Reservation> getReservations(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        return reservationRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/Dreservation/{id}")
    public Map<String, Boolean> deleteReservation(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found for this id :: " + id));
        System.out.println(reservation);
        reservationRepository.delete(reservation);

        Book b = bookRepository.findById(reservation.getBook().getId()).get();
        b.setDisponiblity(true);
        bookRepository.save(b);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Reservation Deleted", Boolean.TRUE);
        return response;
    }

    @PatchMapping("/editR/{id}")
    public Reservation updateR(@PathVariable( "id")Long id, @RequestBody Reservation reservation ) {
        reservation.setId(id);
        return reservationRepository.save(reservation);
    }
    @PostMapping(value = "/AddReservation")
    public Reservation save(@RequestBody Reservation r) {
        return reservationRepository.save(r);
    }


}

@Data
class FromReservation {

    public Long idBook;
    public String nameStd;
    public LocalDate StratRes;
    public LocalDate EndRes;

}
