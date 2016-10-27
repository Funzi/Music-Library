package cz.muni.fi.pa165.entity;



import cz.muni.fi.pa165.utils.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.invoke.ConstantCallSite;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by olda on 26.10.2016.
 */
@Entity
@Table(name = "ALBUM")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private Long id;

    @NotNull
    @Column(nullable = false, length = Constants.INT_LENGTH_SMALL)
    private String title;

    @ManyToOne
    private Musician musician;

    @NotNull
    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(length = Constants.INT_LENGTH_HUGE)
    private String commentary;


    @ManyToOne
    private Genre genre;

    @OneToMany
    private List<Song> songs;


    public Album() {

    }

    public Album(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Musician getMusician() {
        return musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (!title.equals(album.title)) return false;
        if (!musician.equals(album.musician)) return false;
        if (!releaseDate.equals(album.releaseDate)) return false;
        return genre.equals(album.genre);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + musician.hashCode();
        result = 31 * result + releaseDate.hashCode();
        result = 31 * result + genre.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", musician=" + musician +
                ", releaseDate=" + releaseDate +
                ", commentary='" + commentary + '\'' +
                ", genre=" + genre +
                ", songs=" + songs +
                '}';
    }
}
