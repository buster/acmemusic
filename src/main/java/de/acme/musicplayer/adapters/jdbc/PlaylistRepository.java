package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.Tables;
import de.acme.jooq.tables.records.PlaylistRecord;
import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static de.acme.jooq.tables.Playlist.PLAYLIST;

@Component
public class PlaylistRepository implements PlaylistPort {

    private final DSLContext dslContext;

    public PlaylistRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void fügeLiedHinzu(Lied.Id liedId, Playlist.Id playlistId) {
        dslContext.insertInto(Tables.PLAYLIST_LIED, Tables.PLAYLIST_LIED.PLAYLIST_ID, Tables.PLAYLIST_LIED.LIED_ID)
                .values(playlistId.id(), liedId.id())
                .execute();
    }

    @Override
    public Playlist lade(Benutzer.Id benutzer, Playlist.Name playlistName) {
        return lade(new Playlist.Id(benutzer.Id(), playlistName.name()));
    }

    @Override
    public Playlist lade(Playlist.Id playlistId) {
        PlaylistRecord record = fetchPlaylist(playlistId);
        Playlist playlist = new Playlist(new Benutzer.Id(record.getBesitzer()), new Playlist.Name(record.getName()));

        dslContext.selectFrom(Tables.PLAYLIST_LIED)
                .where(Tables.PLAYLIST_LIED.PLAYLIST_ID.eq(playlistId.id()))
                .fetch()
                .forEach(playlistSongRecord -> {
                    String liedId = playlistSongRecord.getLiedId();
                    playlist.liedHinzufügen(new Lied.Id(liedId));
                });
        return playlist;
    }

    @Override
    public Playlist.Id erstellePlaylist(Benutzer.Id benutzername, Playlist.Name name) {
        PlaylistRecord record = fetchPlaylist(new Playlist.Id(benutzername.Id(), name.name()));
        if (record == null) {
            createPlaylist(benutzername, name);
            record = fetchPlaylist(new Playlist.Id(benutzername.Id(), name.name()));
        }
        return new Playlist.Id(record.getId());
    }

    @Override
    public void löscheDatenbank() {
        dslContext.truncate(Tables.PLAYLIST, Tables.PLAYLIST_LIED).cascade().execute();
    }

    private PlaylistRecord fetchPlaylist(Playlist.Id id) {
        return dslContext
                .selectFrom(Tables.PLAYLIST)
                .where(PLAYLIST.ID
                        .eq(id.id()))
                .fetchOne();
    }

    private void createPlaylist(Benutzer.Id benutzer, Playlist.Name playlistName) {
        dslContext.insertInto(Tables.PLAYLIST, PLAYLIST.ID, PLAYLIST.BESITZER, PLAYLIST.NAME)
                .values(new Playlist.Id(benutzer.Id(), playlistName.name()).id(), benutzer.Id(), playlistName.name())
                .execute();
    }
}
