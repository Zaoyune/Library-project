package org.sid.demo.Repositories;

import org.sid.demo.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;


//@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*")
@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {

    @RestResource(path = "/BookByKeyword")
    Page<Book> findByNameContains(@Param("des") String des, Pageable pageable);


    @RestResource(path = "/Disponible")
    Collection<Book> findBookByDisponiblityIsTrue();




}
