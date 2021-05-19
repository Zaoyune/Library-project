package org.sid.demo;

import org.sid.demo.Repositories.AffectationRepository;
import org.sid.demo.Repositories.BookRepository;
import org.sid.demo.Repositories.ReservationRepository;
import org.sid.demo.Repositories.StudentRepository;
import org.sid.demo.Services.serviceIMP;
import org.sid.demo.entities.Book;
import org.sid.demo.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryApplication {
    @Autowired
    PasswordEncoder passwordEncoder ;
    @Autowired
    serviceIMP service ;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StudentRepository studentRepository ;
    @Autowired
    private AffectationRepository affectationRepository ;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(){


        studentRepository.save(new Student(null,"admin","admin","s1","0999999","gg@gg.wp",passwordEncoder.encode("1234"),"admin"));
        repositoryRestConfiguration.exposeIdsFor(Book.class);
        return args -> {

           // service.initBook();
            //service.initStudent();
            //service.initAffectation();
            //service.initReservation();
            System.out.println(bookRepository.findAll());
            System.out.println(studentRepository.findAll());
            System.out.println(affectationRepository.findAll());
            System.out.println(reservationRepository.findAll());
        };
    }
}
