package org.sid.demo.Repositories;

import org.sid.demo.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


//@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*")
@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findBookByDisponiblityIsTrue();

    @Query("select b from Book b where b.name like :x")
    public Page<Book> Search(@Param("x") String mc, Pageable pageable);


    @RestResource(path="/ByBookPage")
    public Page<Book> findByNameContains(@Param("mc") String mc, Pageable pageable);


}
