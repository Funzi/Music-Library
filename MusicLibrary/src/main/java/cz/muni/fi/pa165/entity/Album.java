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

    @ManyToMany
    private List<Musician> musicians;


    @Column(nullable = true)
    private LocalDate releaseDate;

    @Column(length = Constants.INT_LENGTH_HUGE)
    private String commentary;

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

    public List<Musician> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<Musician> musicians) {
        this.musicians = musicians;
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
        if (musicians != null ? !musicians.equals(album.musicians) : album.musicians != null) return false;
        if (!releaseDate.equals(album.releaseDate)) return false;
        if (commentary != null ? !commentary.equals(album.commentary) : album.commentary != null) return false;
        return songs != null ? songs.equals(album.songs) : album.songs == null;

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (musicians != null ? musicians.hashCode() : 0);
        result = 31 * result + releaseDate.hashCode();
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        result = 31 * result + (songs != null ? songs.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", musicians=" + musicians +
                ", releaseDate=" + releaseDate +
                ", commentary='" + commentary + '\'' +
                ", songs=" + songs +
                '}';
    }
}
