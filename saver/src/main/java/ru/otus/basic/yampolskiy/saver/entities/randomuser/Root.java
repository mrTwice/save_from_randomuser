package ru.otus.basic.yampolskiy.saver.entities.randomuser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;

@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Root {
    private List<User> results;
    private Info info;
}
