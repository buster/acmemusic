/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq.tables.records;


import de.acme.jooq.tables.PlaylistLied;

import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class PlaylistLiedRecord extends UpdatableRecordImpl<PlaylistLiedRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.playlist_lied.lied_id</code>.
     */
    public void setLiedId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.playlist_lied.lied_id</code>.
     */
    public String getLiedId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.playlist_lied.playlist_id</code>.
     */
    public void setPlaylistId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.playlist_lied.playlist_id</code>.
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
     * Create a detached PlaylistLiedRecord
     */
    public PlaylistLiedRecord() {
        super(PlaylistLied.PLAYLIST_LIED);
    }

    /**
     * Create a detached, initialised PlaylistLiedRecord
     */
    public PlaylistLiedRecord(String liedId, String playlistId) {
        super(PlaylistLied.PLAYLIST_LIED);

        setLiedId(liedId);
        setPlaylistId(playlistId);
        resetChangedOnNotNull();
    }
}