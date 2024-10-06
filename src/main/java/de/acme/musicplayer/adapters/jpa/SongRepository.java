package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.ports.LoadSongPort;
import de.acme.musicplayer.application.domain.model.Song;

public class SongRepository implements LoadSongPort {

    private SongJpaRepository jpaRepo;
    private JpaEntityToSongMapper mapper;

    @Override
    public Song loadSong(String songId) {
        SongJpaEntity jpaEntity = jpaRepo.findById(songId);
        return mapper.toDomain(jpaEntity);
    }

    @Override
    public void updateSong(Song song) {

    }

    @Override
    public Song findSongByArtist(Artist artist) {
        return null;
    }

    @Override
    public Long getPlayedSecondsByArtist(Artist artist) {
        return 0L;
    }
}
