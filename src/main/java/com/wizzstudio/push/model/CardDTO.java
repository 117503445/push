package com.wizzstudio.push.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CardDTO {
    private String name;
    private String title;
    private String description;
    private String url;
    private String btnTxt;
}
