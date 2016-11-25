/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.api.dto;

import java.util.Objects;

/**
 * Data Transfer Object for Song
 *
 * @author Martin Kulisek
 */
public class SongDTO {

    private Long id;

    private String title;

    private AlbumDTO album;

    private MusicianDTO musician;

    private int position;

    private GenreDTO genre;

    private int bitrate;

    private String commentary;

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
     * Set title of song
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
    public AlbumDTO getAlbum() {
        return album;
    }

    /**
     * Set album of song
     *
     * @param album album
     */
    public void setAlbum(AlbumDTO album) {
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
    public GenreDTO getGenre() {
        return genre;
    }

    /**
     * Set genre of song
     *
     * @param genre genre
     */
    public void setGenre(GenreDTO genre) {
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
     * Set commentary of song.
     *
     * @param commentary commentary
     */
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public MusicianDTO getMusician() {
        return musician;
    }

    public void setMusician(MusicianDTO musician) {
        this.musician = musician;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.album);
        hash = 97 * hash + Objects.hashCode(this.musician);
        hash = 97 * hash + this.position;
        hash = 97 * hash + Objects.hashCode(this.genre);
        hash = 97 * hash + this.bitrate;
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
        final SongDTO other = (SongDTO) obj;
        if (this.position != other.position) {
            return false;
        }
        if (this.bitrate != other.bitrate) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.album, other.album)) {
            return false;
        }
        if (!Objects.equals(this.musician, other.musician)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String albumId = "null";
        if (album.getId() != null) {
            albumId = album.getId().toString();
        }
        return "SongDTO{" + "id=" + id + ", title=" + title + ", album=" + albumId
                + ", musician=" + musician + ", position=" + position + ", genre="
                + genre + ", bitrate=" + bitrate + ", commentary=" + commentary + '}';
    }

}
