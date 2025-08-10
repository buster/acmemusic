package de.acme.musicplayer.components.musicplayer.adapters.jdbc.lied;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.TestJooqConfiguration;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = {
    @ComponentScan.Filter(Repository.class),
    @ComponentScan.Filter(Component.class)
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class LiedRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private LiedPort liedRepository;

    @Test
    void sollteLiedSpeichernUndFinden() throws IOException {
        // Arrange
        TenantId tenantId = new TenantId(UUID.randomUUID().toString());
        BenutzerId benutzerId = new BenutzerId(UUID.randomUUID().toString());
        Lied.Titel titel = new Lied.Titel("Testlied");
        Lied lied = Lied.neuesLied(titel, benutzerId, tenantId);
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());

        // Act
        liedRepository.fügeLiedHinzu(lied, inputStream);
        Collection<Lied> gefundeneLieder = liedRepository.listeLiederAuf(benutzerId, tenantId);

        // Assert
        assertThat(gefundeneLieder).hasSize(1);
        Lied gefundenesLied = gefundeneLieder.iterator().next();
        assertThat(gefundenesLied.getTitel()).isEqualTo(titel.titel());
        assertThat(gefundenesLied.getBesitzer()).isEqualTo(benutzerId);
        assertThat(gefundenesLied.getTenantId()).isEqualTo(tenantId);
        assertThat(gefundenesLied.getId()).isNotNull();
    }
}