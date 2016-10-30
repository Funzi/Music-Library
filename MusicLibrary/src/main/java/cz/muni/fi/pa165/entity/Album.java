package cz.muni.fi.pa165.entity;



import cz.muni.fi.pa165.utils.Constants;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Set<Musician> musicians = new HashSet<>();

    @Column(nullable = true)
    private LocalDate releaseDate;

    @Column(length = Constants.INT_LENGTH_HUGE)
    private String commentary;

    @OneToMany
    private Set<Song> songs = new HashSet<>();


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

    public Set<Musician> getMusicians() {
        return musicians;
    }

    public void addMusician(Musician musician) {
        this.musicians.add(musician);
    }

	public void addMusicians(Collection<Musician> musicians) {
        this.musicians.addAll(musicians);
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

    public Set<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

	public void addSongs(Collection<Song> songs) {
        this.songs.addAll(songs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

		return title.equals(album.title)
				&& Objects.equals(musicians, album.musicians)
				&& Objects.equals(releaseDate, album.releaseDate)
				&& Objects.equals(commentary, album.commentary)
				&& Objects.equals(songs, album.songs);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + Objects.hashCode(musicians);
        result = 31 * result + Objects.hashCode(releaseDate);
        result = 31 * result + Objects.hashCode(commentary);
        result = 31 * result + Objects.hashCode(songs);
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
