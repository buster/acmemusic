/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq.tables.records;


import de.acme.jooq.tables.PlaylistSong;
import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class PlaylistSongRecord extends UpdatableRecordImpl<PlaylistSongRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a detached PlaylistSongRecord
     */
    public PlaylistSongRecord() {
        super(PlaylistSong.PLAYLIST_SONG);
    }

    /**
     * Getter for <code>public.playlist_song.song_id</code>.
     */
    public String getSongId() {
        return (String) get(0);
    }

    /**
     * Create a detached, initialised PlaylistSongRecord
     */
    public PlaylistSongRecord(String songId, String playlistId) {
        super(PlaylistSong.PLAYLIST_SONG);

        setSongId(songId);
        setPlaylistId(playlistId);
        resetChangedOnNotNull();
    }

    /**
     * Getter for <code>public.playlist_song.playlist_id</code>.
     */
    public String getPlaylistId() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>public.playlist_song.song_id</code>.
     */
    public void setSongId(String value) {
        set(0, value);
    }

    /**
     * Setter for <code>public.playlist_song.playlist_id</code>.
     */
    public void setPlaylistId(String value) {
        set(1, value);
    }
}