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
 * Entity class representing one Music Album
 *
 * @author Oldrich Konecny
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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Musician> musicians = new HashSet<>();

    @Column(nullable = true)
    private LocalDate releaseDate;

    @Column(length = Constants.INT_LENGTH_HUGE)
    private String commentary;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Song> songs = new HashSet<>();

    @OneToOne
    private Art art;

    public Album() {

    }

    public Album(Long id) {
        this.id = id;
    }

    /**
     * Returns unique identifier of album. <strong>Warning:</strong> relevant
     * identifier is provided only when the object is persisted!
     *
     * @return unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id of song
     *
     * @return unique identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns title of album
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title of song Maximal title length is limited to
     * {@link Constants#INT_LENGTH_SMALL}.
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns set of musicians
     *
     * @return Set<Musician>
     */
    public Set<Musician> getMusicians() {
        return musicians;
    }

    /**
     * Add musician to album
     *
     * @param musician musician
     */
    public void addMusician(Musician musician) {
        this.musicians.add(musician);
    }

    /**
     * Add musicians to album and not delete current one
     *
     * @param musicians musicians
     */
    public void addMusicians(Collection<Musician> musicians) {
        this.musicians.addAll(musicians);
    }

    /**
     * Returns releaseDate of album
     *
     * @return releaseDate
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Set releaseDate of album
     *
     * @param releaseDate Localdate
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Returns commentary of song.
     *
     * @return commentary
     */
    public String getCommentary() {
        return commentary;
    }

    /**
     * Set commentary of song. Maximal commentary length is limited to
     * {@link Constants#INT_LENGTH_Huge}.
     *
     * @param commentary commentary
     */
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    /**
     * Set songs to album
     *
     * @param Set<song> songs
     */
    public Set<Song> getSongs() {
        return songs;
    }

    /**
     * Set musicians to album and rewrite current one
     *
     * @param Set<musician> musicians
     */
    public void setMusicians(Set<Musician> musicians) {
        this.musicians = musicians;
    }

    /**
     * Set songs to album and delete current ones
     *
     * @param Set<song> songs
     */
    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    /**
     * Set song to album
     *
     * @param song song
     */
    public void addSong(Song song) {
        this.songs.add(song);
    }

    /**
     * Add songs to album and NOT delete current ones
     *
     * @param Set<song> songs
     */
    public void addSongs(Collection<Song> songs) {
        this.songs.addAll(songs);
    }

    /**
     * get picture of album
     *
     * @return Art art
     */
    public Art getArt() {
        return art;
    }

    /**
     * Set picture of album
     *
     * @param Art art
     */
    public void setArt(Art art) {
        this.art = art;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Album album = (Album) o;

        if (title != null ? !title.equals(album.title) : album.title != null) {
            return false;
        }
        if (musicians != null ? !musicians.equals(album.musicians) : album.musicians != null) {
            return false;
        }
        if (releaseDate != null ? !releaseDate.equals(album.releaseDate) : album.releaseDate != null) {
            return false;
        }
        if (commentary != null ? !commentary.equals(album.commentary) : album.commentary != null) {
            return false;
        }
        return songs != null ? songs.equals(album.songs) : album.songs == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (musicians != null ? musicians.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        result = 31 * result + (songs != null ? songs.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Album{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", musicians=" + musicians
                + ", releaseDate=" + releaseDate
                + ", commentary='" + commentary + '\''
                + ", songs=" + songs
                + '}';
    }
}
