package org.server.onlinelearningserver.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryProgressDto {
    private String category;
    private int level;

    public CategoryProgressDto(String category, int level) {
        this.category = category;
        this.level = level;
    }
}
