package org.sid.demo;

import org.sid.demo.Repositories.AffectationRepository;
import org.sid.demo.Repositories.BookRepository;
import org.sid.demo.Repositories.ReservationRepository;
import org.sid.demo.Repositories.Services.service;
import org.sid.demo.Repositories.Services.serviceIMP;
import org.sid.demo.Repositories.StudentRepository;
import org.sid.demo.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

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
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    CommandLineRunner start(){
        return args -> {
            service.initBook();
            service.initStudent();
            service.initAffectation();
            service.initReservation();
            System.out.println(bookRepository.findAll());
            System.out.println(studentRepository.findAll());
            System.out.println(affectationRepository.findAll());
            System.out.println(reservationRepository.findAll());
        };
    }
}
