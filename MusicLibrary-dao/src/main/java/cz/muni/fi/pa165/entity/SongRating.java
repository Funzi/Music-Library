/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author Jan Stourac
 */
@Entity
@Table(name = "SONG_RATING",
        uniqueConstraints = {
            @UniqueConstraint(columnNames
                    = {"song_id", "user_id"})})
public class SongRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(nullable = false)
    private double rvalue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true, length = Constants.INT_LENGTH_LONG)
    private String comment;

    @Column(nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date added;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        setSong(song, true);
    }

    public void setSong(Song song, boolean add) {
        this.song = song;
        if (song != null && add) {
            song.addRating(this, false);
        }
    }

    public double getRvalue() {
        return rvalue;
    }

    public void setRvalue(double rvalue) {
        this.rvalue = rvalue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    // Necessary to handle bi-directional relations
    @PreRemove
    private void removeFromSong() {
        song.removeRating(this);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.song);
        hash = 97 * hash + Objects.hashCode(this.user);
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
        final SongRating other = (SongRating) obj;
        if (!Objects.equals(this.song, other.song)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

}
