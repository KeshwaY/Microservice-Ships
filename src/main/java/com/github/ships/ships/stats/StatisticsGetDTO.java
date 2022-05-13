package com.github.ships.ships.stats;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
class StatisticsGetDTO {

    @NonNull
    private final List<String> statistics;
}
