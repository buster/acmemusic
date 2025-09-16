package de.acme.musicplayer.components.users.adapters.jdbc.benutzer;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.TestJooqConfiguration;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Auszeichnung;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {BenutzerRepository.class, TestJooqConfiguration.class})
@TestPropertySource(properties = {
    "spring.liquibase.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class BenutzerRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private BenutzerPort benutzerPort;

    private TenantId tenantId;
    private Benutzer testBenutzer;

    @BeforeEach
    void setUp() {
        tenantId = new TenantId(UUID.randomUUID().toString());
        testBenutzer = new Benutzer(
                new BenutzerId(UUID.randomUUID().toString()),
                new Benutzer.Name("testuser"),
                new Benutzer.Passwort("testpass"),
                new Benutzer.Email("test@example.com")
        );
    }

    @Test
    void sollteBenutzerHinzufuegenUndZaehlenKoennen() {
        BenutzerId benutzerId = benutzerPort.benutzerHinzufügen(testBenutzer, tenantId);

        assertThat(benutzerId).isEqualTo(testBenutzer.getId());
        assertThat(benutzerPort.zaehleBenutzer(tenantId)).isEqualTo(1);
    }

    @Test
    void sollteBenutzerLesenKoennen() {
        benutzerPort.benutzerHinzufügen(testBenutzer, tenantId);

        Benutzer gelesenerBenutzer = benutzerPort.leseBenutzer(testBenutzer.getId(), tenantId);

        assertThat(gelesenerBenutzer.getId()).isEqualTo(testBenutzer.getId());
        assertThat(gelesenerBenutzer.getName().benutzername()).isEqualTo("testuser");
        assertThat(gelesenerBenutzer.getPasswort().passwort()).isEqualTo("testpass");
        assertThat(gelesenerBenutzer.getEmail().email()).isEqualTo("test@example.com");
        assertThat(gelesenerBenutzer.getAuszeichnungen()).isEmpty();
    }

    @Test
    void sollteBenutzerMitAuszeichnungenSpeichernUndLesenKoennen() {
        benutzerPort.benutzerHinzufügen(testBenutzer, tenantId);
        
        testBenutzer.setAuszeichnungen(Set.of(Auszeichnung.MUSIC_LOVER_LOVER));
        benutzerPort.speichereBenutzer(testBenutzer, tenantId);

        Benutzer gelesenerBenutzer = benutzerPort.leseBenutzer(testBenutzer.getId(), tenantId);

        assertThat(gelesenerBenutzer.getAuszeichnungen())
                .containsExactly(Auszeichnung.MUSIC_LOVER_LOVER);
    }

    @Test
    void sollteAuszeichnungenAktualisierenKoennen() {
        benutzerPort.benutzerHinzufügen(testBenutzer, tenantId);
        testBenutzer.setAuszeichnungen(Set.of(Auszeichnung.MUSIC_LOVER_LOVER));
        benutzerPort.speichereBenutzer(testBenutzer, tenantId);

        testBenutzer.setAuszeichnungen(Set.of());
        benutzerPort.speichereBenutzer(testBenutzer, tenantId);

        Benutzer gelesenerBenutzer = benutzerPort.leseBenutzer(testBenutzer.getId(), tenantId);
        assertThat(gelesenerBenutzer.getAuszeichnungen()).isEmpty();
    }

    @Test
    void sollteDatenbankLoeschenKoennen() {
        benutzerPort.benutzerHinzufügen(testBenutzer, tenantId);
        assertThat(benutzerPort.zaehleBenutzer(tenantId)).isEqualTo(1);

        benutzerPort.loescheDatenbank(tenantId);

        assertThat(benutzerPort.zaehleBenutzer(tenantId)).isEqualTo(0);
    }

    @Test
    void sollteNurBenutzerDesRichtigenTenantsBerücksichtigen() {
        TenantId andereTenantId = new TenantId(UUID.randomUUID().toString());
        
        benutzerPort.benutzerHinzufügen(testBenutzer, tenantId);

        assertThat(benutzerPort.zaehleBenutzer(tenantId)).isEqualTo(1);
        assertThat(benutzerPort.zaehleBenutzer(andereTenantId)).isEqualTo(0);
    }

    @Test
    void sollteExceptionWerfenWennBenutzerNichtGefunden() {
        BenutzerId nichtExistierendeBenutzerId = new BenutzerId(UUID.randomUUID().toString());

        assertThatThrownBy(() -> benutzerPort.leseBenutzer(nichtExistierendeBenutzerId, tenantId))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Benutzer " + nichtExistierendeBenutzerId + " in tenant " + tenantId + " nicht gefunden");
    }

    @Test
    void sollteExceptionWerfenWennBenutzerUpdateFehlschlaegt() {
        assertThatThrownBy(() -> benutzerPort.speichereBenutzer(testBenutzer, tenantId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Benutzer " + testBenutzer.getId() + " in tenant " + tenantId + " nicht gefunden");
    }
}