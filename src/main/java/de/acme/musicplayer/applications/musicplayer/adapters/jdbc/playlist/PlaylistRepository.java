package de.acme.musicplayer.applications.musicplayer.adapters.jdbc.playlist;

import de.acme.jooq.tables.records.PlaylistRecord;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.PlaylistId;
import de.acme.musicplayer.common.TenantId;
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
    public Playlist lade(BenutzerId benutzerId, Playlist.Name playlistName, TenantId tenantId) {
        return lade(new PlaylistId(benutzerId.Id(), playlistName.name()), tenantId);
    }

    @Override
    public Playlist lade(PlaylistId playlistId, TenantId tenantId) {
        PlaylistRecord record = fetchPlaylist(playlistId, tenantId);
        Playlist playlist = new Playlist(new BenutzerId(record.getBesitzer()), new Playlist.Name(record.getName()));

        dslContext.selectFrom(PLAYLIST_LIED)
                .where(PLAYLIST_LIED.PLAYLIST_ID.eq(playlistId.id()))
                .and(PLAYLIST_LIED.TENANT.eq(tenantId.value()))
                .fetch()
                .forEach(playlistSongRecord -> {
                    String liedId = playlistSongRecord.getLiedId();
                    playlist.liedHinzufügen(new LiedId(liedId), playlist.getBesitzer());
                });
        return playlist;
    }

    @Override
    public PlaylistId erstellePlaylist(BenutzerId benutzername, Playlist.Name name, TenantId tenantId) {
        PlaylistRecord record = fetchPlaylist(new PlaylistId(benutzername.Id(), name.name()), tenantId);
        if (record == null) {
            createPlaylist(benutzername, name, tenantId);
            record = fetchPlaylist(new PlaylistId(benutzername.Id(), name.name()), tenantId);
        }
        return new PlaylistId(record.getId());
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(PLAYLIST_LIED
                .where(PLAYLIST_LIED.TENANT.eq(tenantId.value()))).execute();
        dslContext.deleteFrom(PLAYLIST
                .where(PLAYLIST.TENANT.eq(tenantId.value()))).execute();
    }

    @Override
    public void speichere(Playlist playlist, TenantId tenantId) {
        dslContext.update(PLAYLIST)
                .set(PLAYLIST.NAME, playlist.getName())
                .where(PLAYLIST.ID.eq(playlist.getId().id()))
                .and(PLAYLIST.TENANT.eq(tenantId.value()))
                .execute();

        dslContext.deleteFrom(PLAYLIST_LIED)
                .where(PLAYLIST_LIED.PLAYLIST_ID.eq(playlist.getId().id()))
                .and(PLAYLIST_LIED.TENANT.eq(tenantId.value()))
                .execute();

        playlist.getLieder().forEach(liedId -> {
            dslContext.insertInto(PLAYLIST_LIED)
                    .set(PLAYLIST_LIED.PLAYLIST_ID, playlist.getId().id())
                    .set(PLAYLIST_LIED.LIED_ID, liedId.id())
                    .set(PLAYLIST_LIED.TENANT, tenantId.value())
                    .execute();
        });
    }

    private PlaylistRecord fetchPlaylist(PlaylistId playlistId, TenantId tenantId) {
        return dslContext
                .selectFrom(PLAYLIST)
                .where(PLAYLIST.ID.eq(playlistId.id()))
                .and(PLAYLIST.TENANT.eq(tenantId.value()))
                .fetchOne();
    }

    private void createPlaylist(BenutzerId benutzer, Playlist.Name playlistName, TenantId tenantId) {
        dslContext.insertInto(PLAYLIST, PLAYLIST.ID, PLAYLIST.BESITZER, PLAYLIST.NAME, PLAYLIST.TENANT)
                .values(new PlaylistId(benutzer.Id(), playlistName.name()).id(), benutzer.Id(), playlistName.name(), tenantId.value())
                .execute();
    }
}
