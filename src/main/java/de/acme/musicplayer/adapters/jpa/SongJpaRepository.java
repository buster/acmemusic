package de.acme.musicplayer.adapters.jpa;


import org.springframework.data.repository.CrudRepository;

public interface SongJpaRepository extends CrudRepository<SongJpaEntity, String> {
//    SongJpaEntity findById(String songId);
}
