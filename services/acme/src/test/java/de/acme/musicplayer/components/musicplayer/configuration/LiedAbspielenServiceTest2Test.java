package de.acme.musicplayer.components.musicplayer.configuration;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.adapters.jdbc.lied.LiedPortFake;
import de.acme.musicplayer.components.musicplayer.domain.LiedAbspielenService;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAbspielenUsecase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LiedAbspielenServiceTest2Test {

    private final LiedPort liedPortFake = new LiedPortFake();
    LiedAbspielenUsecase liedAbspielenUsecase = new LiedAbspielenService(liedPortFake);

    @SneakyThrows
    @Test
    void shouldReturnSongWhenStreaming() {
        // Given
        LiedId liedId = new LiedId("testid");
        TenantId tenantId = new TenantId("testtenant");
        byte[] givenBytesOfSong = new byte[10];
        liedPortFake.fügeLiedHinzu(new Lied(liedId,
                        new Lied.Titel("Firestarter"),
                        new BenutzerId(UUID.randomUUID().toString()), tenantId),
                new ByteArrayInputStream(givenBytesOfSong));

        // When
        try (InputStream inputStream = liedAbspielenUsecase.liedStreamen(liedId, tenantId)) {

            // Then
            assertThat(inputStream).hasBinaryContent(givenBytesOfSong);
        }
    }
}