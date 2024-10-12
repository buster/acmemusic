/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq.tables;


import de.acme.jooq.Keys;
import de.acme.jooq.Public;
import de.acme.jooq.tables.Playlist.PlaylistPath;
import de.acme.jooq.tables.Song.SongPath;
import de.acme.jooq.tables.records.PlaylistSongRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class PlaylistSong extends TableImpl<PlaylistSongRecord> {

    /**
     * The reference instance of <code>public.playlist_song</code>
     */
    public static final PlaylistSong PLAYLIST_SONG = new PlaylistSong();
    private static final long serialVersionUID = 1L;
    /**
     * The column <code>public.playlist_song.song_id</code>.
     */
    public final TableField<PlaylistSongRecord, String> SONG_ID = createField(DSL.name("song_id"), SQLDataType.VARCHAR.nullable(false), this, "");
    /**
     * The column <code>public.playlist_song.playlist_id</code>.
     */
    public final TableField<PlaylistSongRecord, String> PLAYLIST_ID = createField(DSL.name("playlist_id"), SQLDataType.VARCHAR.nullable(false), this, "");
    private transient PlaylistPath _playlist;
    private transient SongPath _song;

    private PlaylistSong(Name alias, Table<PlaylistSongRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private PlaylistSong(Name alias, Table<PlaylistSongRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.playlist_song</code> table reference
     */
    public PlaylistSong(String alias) {
        this(DSL.name(alias), PLAYLIST_SONG);
    }

    /**
     * Create an aliased <code>public.playlist_song</code> table reference
     */
    public PlaylistSong(Name alias) {
        this(alias, PLAYLIST_SONG);
    }

    /**
     * Create a <code>public.playlist_song</code> table reference
     */
    public PlaylistSong() {
        this(DSL.name("playlist_song"), null);
    }

    public <O extends Record> PlaylistSong(Table<O> path, ForeignKey<O, PlaylistSongRecord> childPath, InverseForeignKey<O, PlaylistSongRecord> parentPath) {
        super(path, childPath, parentPath, PLAYLIST_SONG);
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlaylistSongRecord> getRecordType() {
        return PlaylistSongRecord.class;
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<PlaylistSongRecord> getPrimaryKey() {
        return Keys.PK_PLAYLIST_SONG;
    }

    @Override
    public List<ForeignKey<PlaylistSongRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PLAYLIST_SONG__FK_PLAYLIST_ID, Keys.PLAYLIST_SONG__FK_SONG_ID);
    }

    /**
     * Get the implicit join path to the <code>public.playlist</code> table.
     */
    public PlaylistPath playlist() {
        if (_playlist == null)
            _playlist = new PlaylistPath(this, Keys.PLAYLIST_SONG__FK_PLAYLIST_ID, null);

        return _playlist;
    }

    /**
     * Get the implicit join path to the <code>public.song</code> table.
     */
    public SongPath song() {
        if (_song == null)
            _song = new SongPath(this, Keys.PLAYLIST_SONG__FK_SONG_ID, null);

        return _song;
    }

    @Override
    public PlaylistSong as(String alias) {
        return new PlaylistSong(DSL.name(alias), this);
    }

    @Override
    public PlaylistSong as(Name alias) {
        return new PlaylistSong(alias, this);
    }

    @Override
    public PlaylistSong as(Table<?> alias) {
        return new PlaylistSong(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public PlaylistSong rename(String name) {
        return new PlaylistSong(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PlaylistSong rename(Name name) {
        return new PlaylistSong(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public PlaylistSong rename(Table<?> name) {
        return new PlaylistSong(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public PlaylistSong where(Condition condition) {
        return new PlaylistSong(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public PlaylistSong where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public PlaylistSong where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public PlaylistSong where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public PlaylistSong where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public PlaylistSong where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public PlaylistSong where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public PlaylistSong where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public PlaylistSong whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public PlaylistSong whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class PlaylistSongPath extends PlaylistSong implements Path<PlaylistSongRecord> {

        private static final long serialVersionUID = 1L;

        public <O extends Record> PlaylistSongPath(Table<O> path, ForeignKey<O, PlaylistSongRecord> childPath, InverseForeignKey<O, PlaylistSongRecord> parentPath) {
            super(path, childPath, parentPath);
        }

        private PlaylistSongPath(Name alias, Table<PlaylistSongRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public PlaylistSongPath as(String alias) {
            return new PlaylistSongPath(DSL.name(alias), this);
        }

        @Override
        public PlaylistSongPath as(Name alias) {
            return new PlaylistSongPath(alias, this);
        }

        @Override
        public PlaylistSongPath as(Table<?> alias) {
            return new PlaylistSongPath(alias.getQualifiedName(), this);
        }
    }
}