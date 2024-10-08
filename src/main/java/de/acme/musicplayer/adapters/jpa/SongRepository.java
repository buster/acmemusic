package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;

public class SongRepository implements LiedPort {

    private final SongJpaRepository jpaRepo;
    private final JpaEntityToSongMapper mapper = new JpaEntityToSongMapper();

    public SongRepository(SongJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Lied ladeLied(String songId) {
        SongJpaEntity jpaEntity = jpaRepo.findById(songId).orElseThrow();
        return mapper.toDomain(jpaEntity);
    }

    @Override
    public long zähleLieder() {
        return 0L;
    }

    @Override
    public Lied.LiedId fügeLiedHinzu(Lied lied) {
        return null;
    }
}
