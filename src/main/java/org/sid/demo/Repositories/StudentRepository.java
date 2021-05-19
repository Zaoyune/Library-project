package org.sid.demo.Repositories;

import org.sid.demo.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query("select s from Student s where s.name like :x")
    public Page<Student> Search(@Param("x") String mc, Pageable pageable);


    Student findByName(String nom);

    @RestResource(path = "/StudentByKeyword")
    public Page<Student> findByLastContains(@Param("mc") String mc, Pageable pageable);
}
