package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.Tables;
import de.acme.jooq.tables.records.PlaylistRecord;
import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import org.jooq.DSLContext;

import static de.acme.jooq.tables.Playlist.PLAYLIST;

public class PlaylistRepository implements PlaylistPort {

    private final DSLContext dslContext;

    public PlaylistRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void addSongToPlaylist(Lied.LiedId liedId, Playlist.PlaylistId playlistId) {
        dslContext.insertInto(Tables.PLAYLIST_SONG, Tables.PLAYLIST_SONG.PLAYLIST_ID, Tables.PLAYLIST_SONG.SONG_ID)
                .values(playlistId.id(), liedId.id())
                .execute();
    }

    @Override
    public Playlist lade(Benutzer.Id benutzer, Playlist.Name playlistName) {
        return lade(new Playlist.PlaylistId(benutzer.Id(), playlistName.name()));
    }

    @Override
    public Playlist lade(Playlist.PlaylistId playlistId) {
        PlaylistRecord record = fetchPlaylist(playlistId);
        Playlist playlist = new Playlist(new Benutzer.Id(record.getBesitzer()), new Playlist.Name(record.getName()));

        dslContext.selectFrom(Tables.PLAYLIST_SONG)
                .where(Tables.PLAYLIST_SONG.PLAYLIST_ID.eq(playlistId.id()))
                .fetch()
                .forEach(playlistSongRecord -> {
                    String songId = playlistSongRecord.getSongId();
                    playlist.liedHinzufügen(new Lied.LiedId(songId));
                });
        return playlist;
    }

    @Override
    public Playlist.PlaylistId erstellePlaylist(Benutzer.Id benutzername, Playlist.Name name) {
        PlaylistRecord record = fetchPlaylist(new Playlist.PlaylistId(benutzername.Id(), name.name()));
        if (record == null) {
            createPlaylist(benutzername, name);
            record = fetchPlaylist(new Playlist.PlaylistId(benutzername.Id(), name.name()));
        }
        return new Playlist.PlaylistId(record.getId());
    }

    @Override
    public void löscheDatenbank() {
        dslContext.truncate(Tables.PLAYLIST, Tables.PLAYLIST_SONG).cascade().execute();
    }

    private PlaylistRecord fetchPlaylist(Playlist.PlaylistId id) {
        return dslContext
                .selectFrom(Tables.PLAYLIST)
                .where(PLAYLIST.ID
                        .eq(id.id()))
                .fetchOne();
    }

    private void createPlaylist(Benutzer.Id benutzer, Playlist.Name playlistName) {
        dslContext.insertInto(Tables.PLAYLIST, PLAYLIST.ID, PLAYLIST.BESITZER, PLAYLIST.NAME)
                .values(new Playlist.PlaylistId(benutzer.Id(), playlistName.name()).id(), benutzer.Id(), playlistName.name())
                .execute();
    }
}
