package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

/**
 * Class representing song entity.
 *
 * @author David Pribula
 */
@Entity
@Table(name = "SONG")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, length = Constants.INT_LENGTH_SMALL)
    private String title;

    @ManyToOne
    private Album album;

    @ManyToOne
    private Musician musician;

    @Column(nullable = false)
    private int position;

    @ManyToOne
    private Genre genre;

    private int bitrate;

    @Column(length = Constants.INT_LENGTH_HUGE)
    private String commentary;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SongRating> ratings = new ArrayList<>();

    @Formula("(select coalesce(avg(r.rvalue), 0) from Song_Rating r where r.song_id = id)")
    private double avgRating;

    public Song() {

    }

    /**
     * Returns unique identifier of song. <strong>Warning:</strong> relevant
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
     * @param id unique identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns title of song
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
     * Returns album of song
     *
     * @return album
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * Set album of song
     *
     * @param album album
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Returns position of song in album
     *
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set position of song in album.
     *
     * @param position position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Returns genre of song.
     *
     * @return genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Set genre of song
     *
     * @param genre genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Returns bitrate of song
     *
     * @return bitrate
     */
    public int getBitrate() {
        return bitrate;
    }

    /**
     * Set bitrate of song
     *
     * @param bitrate bitrate
     */
    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
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
     * {@link Constants#INT_LENGTH_HUGE}.
     *
     * @param commentary commentary
     */
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Musician getMusician() {
        return musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }

    public void setRatings(List<SongRating> ratings) {
        this.ratings = ratings;
    }

    public List<SongRating> getRatings() {
        return ratings;
    }

    public void addRating(SongRating rating) {
        addRating(rating, true);
    }

    public void addRating(SongRating rating, boolean set) {
        if (rating == null) {
            return;
        }

        this.ratings.add(rating);

        if (ratings.contains(rating)) {
            ratings.remove(rating);
        }

        ratings.add(rating);

        if (set) {
            rating.setSong(this, false);
        }

    }

    public boolean removeRating(SongRating rating) {
        return this.ratings.remove(rating);
    }

    public double getAvgRating() {
        return avgRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Song song = (Song) o;

        if (position != song.position) {
            return false;
        }
        if (bitrate != song.bitrate) {
            return false;
        }
        if (title != null ? !title.equals(song.title) : song.title != null) {
            return false;
        }
        if (album != null ? !album.equals(song.album) : song.album != null) {
            return false;
        }
        if (musician != null ? !musician.equals(song.musician) : song.musician != null) {
            return false;
        }
        if (genre != null ? !genre.equals(song.genre) : song.genre != null) {
            return false;
        }
        return commentary != null ? commentary.equals(song.commentary) : song.commentary == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (musician != null ? musician.hashCode() : 0);
        result = 31 * result + position;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + bitrate;
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Song{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", album=" + (album != null ? album.getId() : null)
                + ", musician=" + (musician != null ? musician.getId() : null)
                + ", position=" + position
                + ", genre=" + (genre != null ? genre.getId() : null)
                + ", bitrate=" + bitrate
                + ", commentary='" + commentary + '\''
                + '}';
    }
}
