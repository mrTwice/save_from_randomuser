package ru.otus.basic.yampolskiy.saver.entities.randomuser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Picture {
    private String large;
    private String medium;
    private String thumbnail;
}
