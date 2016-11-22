package cz.muni.fi.pa165.api.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by olda on 20.11.2016.
 */
public class AlbumDTO {

    private  Long id;

    private String title;

    private LocalDate releaseDate;

    private String commentary;

    private Set<SongDTO> songs = new HashSet<>();

    private ArtDTO art;


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

    public Set<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(Set<SongDTO> songs) {
        this.songs = songs;
    }

    public ArtDTO getArt() {
        return art;
    }

    public void setArt(ArtDTO art) {
        this.art = art;
    }

    public void addSong(SongDTO songDTO) {
        this.songs.add(songDTO);
    }

    public void addSong(Collection<SongDTO> songs) {
        this.songs.addAll(songs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumDTO albumDTO = (AlbumDTO) o;

        if (title != null ? !title.equals(albumDTO.title) : albumDTO.title != null) return false;
        return releaseDate != null ? releaseDate.equals(albumDTO.releaseDate) : albumDTO.releaseDate == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", commentary='" + commentary + '\'' +
                ", songs=" + songs +
                ", art=" + art +
                '}';
    }
}
