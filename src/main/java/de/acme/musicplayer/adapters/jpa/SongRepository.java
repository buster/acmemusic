package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;

public class SongRepository implements LiedPort {

    private SongJpaRepository jpaRepo;
    private JpaEntityToSongMapper mapper;

    @Override
    public Lied ladeLied(String songId) {
        SongJpaEntity jpaEntity = jpaRepo.findById(songId);
        return mapper.toDomain(jpaEntity);
    }

    @Override
    public void updateSong(Lied lied) {

    }

    @Override
    public Lied findSongByArtist(Artist artist) {
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

    @Override
    public void fügeLiedHinzu(Lied lied) {

    }
}
