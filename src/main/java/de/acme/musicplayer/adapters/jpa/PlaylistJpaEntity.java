package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Table("playlist")
public class PlaylistJpaEntity {

    @Id
    private PlaylistId id;
    private String name;
    private List<Lied.LiedId> lieder = new ArrayList<>();
    private String besitzer;

    public record PlaylistId(String id) {
        public PlaylistId(String benutzername, String playlistName) {
            this(String.format("%s-%s", benutzername, playlistName));
        }

    }

    public Playlist toPlaylistEntity()   {
        Playlist playlist = new Playlist(besitzer, name);
        for (Lied.LiedId liedId : lieder) {
            playlist.addLied(new Lied.LiedId(liedId.id()));
        }

        return playlist;
    }
}
