package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.tables.records.PlaylistRecord;
import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.domain.model.TenantId;
import de.acme.musicplayer.application.ports.PlaylistPort;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static de.acme.jooq.Tables.PLAYLIST;
import static de.acme.jooq.Tables.PLAYLIST_LIED;

@Component
public class PlaylistRepository implements PlaylistPort {

    private final DSLContext dslContext;

    public PlaylistRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void fügeLiedHinzu(Lied.Id liedId, Playlist.Id playlistId, TenantId tenantId) {
        dslContext.insertInto(PLAYLIST_LIED, PLAYLIST_LIED.PLAYLIST_ID, PLAYLIST_LIED.LIED_ID, PLAYLIST_LIED.TENANT)
                .values(playlistId.id(), liedId.id(), tenantId.value())
                .execute();
    }

    @Override
    public Playlist lade(Benutzer.Id benutzer, Playlist.Name playlistName, TenantId tenantId) {
        return lade(new Playlist.Id(benutzer.Id(), playlistName.name()), tenantId);
    }

    @Override
    public Playlist lade(Playlist.Id playlistId, TenantId tenantId) {
        PlaylistRecord record = fetchPlaylist(playlistId, tenantId);
        Playlist playlist = new Playlist(new Benutzer.Id(record.getBesitzer()), new Playlist.Name(record.getName()));

        dslContext.selectFrom(PLAYLIST_LIED)
                .where(PLAYLIST_LIED.PLAYLIST_ID.eq(playlistId.id()))
                .and(PLAYLIST_LIED.TENANT.eq(tenantId.value()))
                .fetch()
                .forEach(playlistSongRecord -> {
                    String liedId = playlistSongRecord.getLiedId();
                    playlist.liedHinzufügen(new Lied.Id(liedId));
                });
        return playlist;
    }

    @Override
    public Playlist.Id erstellePlaylist(Benutzer.Id benutzername, Playlist.Name name, TenantId tenantId) {
        PlaylistRecord record = fetchPlaylist(new Playlist.Id(benutzername.Id(), name.name()), tenantId);
        if (record == null) {
            createPlaylist(benutzername, name, tenantId);
            record = fetchPlaylist(new Playlist.Id(benutzername.Id(), name.name()), tenantId);
        }
        return new Playlist.Id(record.getId());
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(PLAYLIST_LIED
                .where(PLAYLIST_LIED.TENANT.eq(tenantId.value()))).execute();
        dslContext.deleteFrom(PLAYLIST
                .where(PLAYLIST.TENANT.eq(tenantId.value()))).execute();
    }

    private PlaylistRecord fetchPlaylist(Playlist.Id id, TenantId tenantId) {
        return dslContext
                .selectFrom(PLAYLIST)
                .where(PLAYLIST.ID.eq(id.id()))
                .and(PLAYLIST.TENANT.eq(tenantId.value()))
                .fetchOne();
    }

    private void createPlaylist(Benutzer.Id benutzer, Playlist.Name playlistName, TenantId tenantId) {
        dslContext.insertInto(PLAYLIST, PLAYLIST.ID, PLAYLIST.BESITZER, PLAYLIST.NAME, PLAYLIST.TENANT)
                .values(new Playlist.Id(benutzer.Id(), playlistName.name()).id(), benutzer.Id(), playlistName.name(), tenantId.value())
                .execute();
    }
}
