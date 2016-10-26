package cz.muni.fi.pa165.entity;

import javax.persistence.*;

/**
 * Created by olda on 26.10.2016.
 */
@Entity
@Table(name = "GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private Long id;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
