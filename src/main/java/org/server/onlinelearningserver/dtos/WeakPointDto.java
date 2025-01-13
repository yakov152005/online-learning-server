package org.server.onlinelearningserver.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeakPointDto {
    private String category;
    private int errorCount;

    public WeakPointDto(String category, int errorCount) {
        this.category = category;
        this.errorCount = errorCount;
    }
}
