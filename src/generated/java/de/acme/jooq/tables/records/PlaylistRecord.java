/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq.tables.records;


import de.acme.jooq.tables.Playlist;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class PlaylistRecord extends UpdatableRecordImpl<PlaylistRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.playlist.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.playlist.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.playlist.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.playlist.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.playlist.besitzer</code>.
     */
    public void setBesitzer(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.playlist.besitzer</code>.
     */
    public String getBesitzer() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlaylistRecord
     */
    public PlaylistRecord() {
        super(Playlist.PLAYLIST);
    }

    /**
     * Create a detached, initialised PlaylistRecord
     */
    public PlaylistRecord(String id, String name, String besitzer) {
        super(Playlist.PLAYLIST);

        setId(id);
        setName(name);
        setBesitzer(besitzer);
        resetChangedOnNotNull();
    }
}