package org.sid.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String Author;
    private LocalDate date_entree;
    private Boolean disponiblity;
//    private Category category;
    private String photoName;
    @Column(length = 100000)
    private String description;
    private String category ;

}
