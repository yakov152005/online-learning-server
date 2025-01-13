package org.server.onlinelearningserver.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySuccessStreakDto {
    private String category;
    private int successStreak;

    public CategorySuccessStreakDto(String category, int successStreak) {
        this.category = category;
        this.successStreak = successStreak;
    }
}
