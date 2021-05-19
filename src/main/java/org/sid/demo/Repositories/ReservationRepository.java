package org.sid.demo.Repositories;

import org.sid.demo.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Reservation findReservationByBook_Id(Long id);

    @Query("select r from Reservation r where r.student.last like :x")
    public Page<Reservation> SearchReser(@Param("x") String mc, Pageable pageable);
    public Page<Reservation> findReservationByStudent_Last(@Param("mc") String mc, Pageable pageable);
}
