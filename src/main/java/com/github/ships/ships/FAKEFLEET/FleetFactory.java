package com.github.ships.ships.FAKEFLEET;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FleetFactory {

    @Bean
    public FleetService fleetService() {
        return new FleetService();
    }
}
