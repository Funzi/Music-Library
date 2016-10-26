package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by olda on 26.10.2016.
 */
@Entity
@Table(name = "ALBUM")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 128)
    private String title;

    @NotNull
    @Column(nullable = false)
    private Musician musician;

    @NotNull
    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(length = 2048)
    private String commentary;





}
