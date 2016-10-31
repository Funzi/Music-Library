package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.Objects;
import javax.persistence.*;

/**
 * An entity class representing a music genre. Genre has its name and a short
 * description.
 *
 * @author Martin Kulisek
 */
@Entity
@Table(name = "GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, unique = true, length = Constants.INT_LENGTH_SMALL)
    private String name;

    @Column(length = Constants.INT_LENGTH_LONG)
    private String description;

    /**
     * Returns unique identifier of genre. <strong>Warning:</strong> relevant
     * identifier is provided only when the object is persisted!
     *
     * @return unique identifier
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns genre name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns genre description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets genre name. Maximal name length is limited to
     * {@link Constants#INT_LENGTH_SMALL}.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets genre description. Maximal description length is limited to
     * {@link Constants#INT_LENGTH_LONG}.
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Genre other = (Genre) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Genre{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }

}
