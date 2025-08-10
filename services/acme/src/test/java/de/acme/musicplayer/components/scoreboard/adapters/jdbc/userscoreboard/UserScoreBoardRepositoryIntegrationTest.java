package de.acme.musicplayer.components.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.TestJooqConfiguration;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {UserScoreBoardRepository.class, TestJooqConfiguration.class})
@TestPropertySource(properties = {
    "spring.liquibase.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserScoreBoardRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserScoreBoardPort userScoreBoardPort;

    private TenantId tenantId;
    private BenutzerId benutzerId;
    private BenutzerId andereBenutzerId;

    @BeforeEach
    void setUp() {
        tenantId = new TenantId(UUID.randomUUID().toString());
        benutzerId = new BenutzerId(UUID.randomUUID().toString());
        andereBenutzerId = new BenutzerId(UUID.randomUUID().toString());
    }

    @Test
    void sollteNeuesLiedZaehlenKoennen() {
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);

        BenutzerId topScorer = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId);

        assertThat(topScorer).isEqualTo(benutzerId);
    }

    @Test
    void sollteMehrereliederFuerGleichenBenutzerZaehlenKoennen() {
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);

        BenutzerId topScorer = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId);

        assertThat(topScorer).isEqualTo(benutzerId);
    }

    @Test
    void sollteBenutzerMitHoechsterPunktzahlFindenKoennen() {
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);
        userScoreBoardPort.zähleNeuesLied(andereBenutzerId, tenantId);
        userScoreBoardPort.zähleNeuesLied(andereBenutzerId, tenantId);

        BenutzerId topScorer = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId);

        assertThat(topScorer).isEqualTo(andereBenutzerId);
    }

    @Test
    void sollteNullZurueckgebenWennKeineBenutzerVorhanden() {
        BenutzerId topScorer = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId);

        assertThat(topScorer).isNull();
    }

    @Test
    void sollteDatenbankLoeschenKoennen() {
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);
        userScoreBoardPort.zähleNeuesLied(andereBenutzerId, tenantId);

        assertThat(userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId)).isNotNull();

        userScoreBoardPort.löscheDatenbank(tenantId);

        assertThat(userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId)).isNull();
    }

    @Test
    void sollteNurDatenDesRichtigenTenantsBeruecksichtigen() {
        TenantId andereTenantId = new TenantId(UUID.randomUUID().toString());
        
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);
        userScoreBoardPort.zähleNeuesLied(andereBenutzerId, andereTenantId);

        BenutzerId topScorerTenant1 = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId);
        BenutzerId topScorerTenant2 = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(andereTenantId);

        assertThat(topScorerTenant1).isEqualTo(benutzerId);
        assertThat(topScorerTenant2).isEqualTo(andereBenutzerId);
    }

    @Test
    void sollteDatenbankNurFuerSpezifischenTenantLoeschen() {
        TenantId andereTenantId = new TenantId(UUID.randomUUID().toString());
        
        userScoreBoardPort.zähleNeuesLied(benutzerId, tenantId);
        userScoreBoardPort.zähleNeuesLied(andereBenutzerId, andereTenantId);

        userScoreBoardPort.löscheDatenbank(tenantId);

        assertThat(userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(tenantId)).isNull();
        assertThat(userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(andereTenantId)).isEqualTo(andereBenutzerId);
    }
}