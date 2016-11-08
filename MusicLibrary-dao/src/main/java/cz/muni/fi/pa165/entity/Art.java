package cz.muni.fi.pa165.entity;

import javax.persistence.*;

/**
 * Entity Class representing one Album Image Created by olda on 30.10.2016.
 */
@Entity
@Table(name = "ART")
public class Art {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private Long id;

    //TODO find out best way to represent and save Images and implement that shit!
    public Art() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Art art = (Art) o;

        return id != null ? id.equals(art.id) : art.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
