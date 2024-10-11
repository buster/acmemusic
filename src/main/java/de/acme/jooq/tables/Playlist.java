/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq.tables;


import de.acme.jooq.Keys;
import de.acme.jooq.Public;
import de.acme.jooq.tables.PlaylistSong.PlaylistSongPath;
import de.acme.jooq.tables.Song.SongPath;
import de.acme.jooq.tables.records.PlaylistRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.util.Collection;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Playlist extends TableImpl<PlaylistRecord> {

    /**
     * The reference instance of <code>public.playlist</code>
     */
    public static final Playlist PLAYLIST = new Playlist();
    private static final long serialVersionUID = 1L;
    /**
     * The column <code>public.playlist.id</code>.
     */
    public final TableField<PlaylistRecord, String> ID = createField(DSL.name("id"), SQLDataType.VARCHAR.nullable(false), this, "");
    /**
     * The column <code>public.playlist.name</code>.
     */
    public final TableField<PlaylistRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR, this, "");
    /**
     * The column <code>public.playlist.besitzer</code>.
     */
    public final TableField<PlaylistRecord, String> BESITZER = createField(DSL.name("besitzer"), SQLDataType.VARCHAR, this, "");
    private transient PlaylistSongPath _playlistSong;

    private Playlist(Name alias, Table<PlaylistRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Playlist(Name alias, Table<PlaylistRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.playlist</code> table reference
     */
    public Playlist(String alias) {
        this(DSL.name(alias), PLAYLIST);
    }

    /**
     * Create an aliased <code>public.playlist</code> table reference
     */
    public Playlist(Name alias) {
        this(alias, PLAYLIST);
    }

    /**
     * Create a <code>public.playlist</code> table reference
     */
    public Playlist() {
        this(DSL.name("playlist"), null);
    }

    public <O extends Record> Playlist(Table<O> path, ForeignKey<O, PlaylistRecord> childPath, InverseForeignKey<O, PlaylistRecord> parentPath) {
        super(path, childPath, parentPath, PLAYLIST);
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlaylistRecord> getRecordType() {
        return PlaylistRecord.class;
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<PlaylistRecord> getPrimaryKey() {
        return Keys.PLAYLIST_PKEY;
    }

    /**
     * Get the implicit to-many join path to the
     * <code>public.playlist_song</code> table
     */
    public PlaylistSongPath playlistSong() {
        if (_playlistSong == null)
            _playlistSong = new PlaylistSongPath(this, null, Keys.PLAYLIST_SONG__FK_PLAYLIST_ID.getInverseKey());

        return _playlistSong;
    }

    /**
     * Get the implicit many-to-many join path to the <code>public.song</code>
     * table
     */
    public SongPath song() {
        return playlistSong().song();
    }

    @Override
    public Playlist as(String alias) {
        return new Playlist(DSL.name(alias), this);
    }

    @Override
    public Playlist as(Name alias) {
        return new Playlist(alias, this);
    }

    @Override
    public Playlist as(Table<?> alias) {
        return new Playlist(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Playlist rename(String name) {
        return new Playlist(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Playlist rename(Name name) {
        return new Playlist(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Playlist rename(Table<?> name) {
        return new Playlist(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Playlist where(Condition condition) {
        return new Playlist(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Playlist where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Playlist where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Playlist where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Playlist where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Playlist where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Playlist where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Playlist where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Playlist whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Playlist whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class PlaylistPath extends Playlist implements Path<PlaylistRecord> {

        private static final long serialVersionUID = 1L;

        public <O extends Record> PlaylistPath(Table<O> path, ForeignKey<O, PlaylistRecord> childPath, InverseForeignKey<O, PlaylistRecord> parentPath) {
            super(path, childPath, parentPath);
        }

        private PlaylistPath(Name alias, Table<PlaylistRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public PlaylistPath as(String alias) {
            return new PlaylistPath(DSL.name(alias), this);
        }

        @Override
        public PlaylistPath as(Name alias) {
            return new PlaylistPath(alias, this);
        }

        @Override
        public PlaylistPath as(Table<?> alias) {
            return new PlaylistPath(alias.getQualifiedName(), this);
        }
    }
}
