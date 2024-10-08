package de.acme.musicplayer.adapters.jpa;

import de.acme.jooq.Tables;
import de.acme.jooq.tables.records.PlaylistRecord;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import org.jooq.DSLContext;

import static de.acme.jooq.tables.Playlist.PLAYLIST;

public class PlaylistRepository implements PlaylistPort {

    private final PlaylistJpaRepository playlistJpaRepository;
    private final DSLContext dslContext;

    public PlaylistRepository(PlaylistJpaRepository playlistJpaRepository, DSLContext dslContext) {
        this.playlistJpaRepository = playlistJpaRepository;
        this.dslContext = dslContext;
    }

    @Override
    public void addSongToPlaylist(Lied.LiedId liedId, Playlist.PlaylistId playlistId) {
        dslContext.insertInto(Tables.PLAYLIST_SONG, Tables.PLAYLIST_SONG.PLAYLIST_ID, Tables.PLAYLIST_SONG.SONG_ID)
                .values(playlistId.id(), liedId.id())
                .execute();
    }

    @Override
    public Playlist lade(String benutzername, String playlistName) {
        String id = new PlaylistJpaEntity.PlaylistId(benutzername, playlistName).id();
        PlaylistRecord record = fetchPlaylist(id);
        if (record == null) {
            createPlaylist(benutzername, playlistName);
            record = fetchPlaylist(id);
        }
        Playlist playlist = new Playlist(record.getBesitzer(), record.getName());

        dslContext.selectFrom(Tables.PLAYLIST_SONG)
                .where(Tables.PLAYLIST_SONG.PLAYLIST_ID.eq(id))
                .fetch()
                .forEach(playlistSongRecord -> {
                    String songId = playlistSongRecord.getSongId();
                    playlist.addLied(new Lied.LiedId(songId));
                });
        return playlist;
    }

    private PlaylistRecord fetchPlaylist(String id) {
        return dslContext
                .selectFrom(Tables.PLAYLIST)
                .where(PLAYLIST.ID
                        .eq(id))
                .fetchOne();
    }

    private void createPlaylist(String benutzername, String playlistName) {
        dslContext.insertInto(Tables.PLAYLIST, PLAYLIST.ID, PLAYLIST.BESITZER, PLAYLIST.NAME)
                .values(new Playlist.PlaylistId(benutzername, playlistName).id(), benutzername, playlistName)
                .execute();
    }
}
