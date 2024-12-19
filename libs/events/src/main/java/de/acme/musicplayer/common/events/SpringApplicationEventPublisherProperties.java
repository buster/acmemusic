package de.acme.musicplayer.common.events;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "events.publisher")
@Component
@Data
public class SpringApplicationEventPublisherProperties {
    private boolean publishEventsToApplication;
    private boolean saveToOutbox;
}
