package org.sid.demo.Repositories;

import org.sid.demo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book,Long> {

    @RestResource(path = "/Disponible")
    Collection<Book> findBookByDisponiblityIsTrue();


}
