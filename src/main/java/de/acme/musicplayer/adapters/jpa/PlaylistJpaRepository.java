package de.acme.musicplayer.adapters.jpa;

import org.springframework.data.repository.CrudRepository;

public interface  PlaylistJpaRepository extends CrudRepository<PlaylistJpaEntity, PlaylistJpaEntity.PlaylistId> {
}
