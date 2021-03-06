package org.sid.demo.Services;

import net.bytebuddy.utility.RandomString;
import org.sid.demo.Repositories.AffectationRepository;
import org.sid.demo.Repositories.BookRepository;
import org.sid.demo.Repositories.ReservationRepository;
import org.sid.demo.Repositories.StudentRepository;
import org.sid.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class    serviceIMP implements service {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ReservationRepository reservationRepository ;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private AffectationRepository affectationRepository;

    @Autowired
    PasswordEncoder passwordEncoder ;

    @Override
    public void initStudent() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Student student = new Student(null, "user"+i, RandomString.make(10), RandomString.make(5), RandomString.make(5), RandomString.make(15),passwordEncoder.encode("1234"),"user");
            studentRepository.save(student);
        }
        Student student = new Student(null, "admin", RandomString.make(10), RandomString.make(5), RandomString.make(5), RandomString.make(15),passwordEncoder.encode("admin"),"admin");
        studentRepository.save(student);
    }

    @Override
    public void initBook() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Book book = new Book(null, RandomString.make(10), RandomString.make(12), LocalDate.now(), random.nextBoolean(),"test.png",RandomString.make(50),RandomString.make(12));
            bookRepository.save(book);
        }
    }


    @Override
    public void initReservation() {
        Random random = new Random();
        long minDay = LocalDate.of(2021, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        for (int i = 0; i < 20; i++) {
            Reservation reserv = new Reservation(null, randomDate,null,null,null,false);
            reservationRepository.save(reserv);
        }
    }

    @Override
    public void initAffectation() {
        Random random = new Random();
        long minDay = LocalDate.of(2021, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        for (int i = 0; i < 20; i++) {
            Affectation affect = new Affectation(null, LocalDate.now(),randomDate,null,null);
            affectationRepository.save(affect);
        }
    }
}
