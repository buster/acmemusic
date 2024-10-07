package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.ports.LiedLadenPort;
import de.acme.musicplayer.application.domain.model.Song;

public class SongRepository implements LiedLadenPort {

    private SongJpaRepository jpaRepo;
    private JpaEntityToSongMapper mapper;

    @Override
    public Song ladeLied(String songId) {
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

    @Override
    public Long zähleLieder() {
        return 0L;
    }
}
