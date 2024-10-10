/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq;


import de.acme.jooq.tables.Benutzer;
import de.acme.jooq.tables.Playlist;
import de.acme.jooq.tables.PlaylistSong;
import de.acme.jooq.tables.Song;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.benutzer</code>.
     */
    public final Benutzer BENUTZER = Benutzer.BENUTZER;

    /**
     * The table <code>public.playlist</code>.
     */
    public final Playlist PLAYLIST = Playlist.PLAYLIST;

    /**
     * The table <code>public.playlist_song</code>.
     */
    public final PlaylistSong PLAYLIST_SONG = PlaylistSong.PLAYLIST_SONG;

    /**
     * The table <code>public.song</code>.
     */
    public final Song SONG = Song.SONG;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
                Benutzer.BENUTZER,
                Playlist.PLAYLIST,
                PlaylistSong.PLAYLIST_SONG,
                Song.SONG
        );
    }
}