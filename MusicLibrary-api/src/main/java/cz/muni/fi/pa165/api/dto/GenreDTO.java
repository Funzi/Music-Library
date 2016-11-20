package cz.muni.fi.pa165.api.dto;

/**
 * An DTO class representing a music genre. Genre has its id, name and a short
 * description.
 *
 * @author David Pribula
 */
public class GenreDTO {

    private Long id;
    private String name;
    private String description;

    public GenreDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
