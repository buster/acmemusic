package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardRepository;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;

public class ScoreBoardAdministrationService implements ScoreBoardAdministrationUsecase {

    private final UserScoreBoardRepository userScoreBoardRepository;

    public ScoreBoardAdministrationService(UserScoreBoardRepository userScoreBoardRepository) {
        this.userScoreBoardRepository = userScoreBoardRepository;
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        userScoreBoardRepository.löscheDatenbank(tenantId);
    }
}
