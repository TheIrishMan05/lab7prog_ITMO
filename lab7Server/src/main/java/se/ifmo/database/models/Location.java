package se.ifmo.database.models;


import lombok.*;
import se.ifmo.database.exceptions.InvalidArgumentException;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter

public class Location implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Setter
    private long x;
    @Setter
    private int y;

    private String name; //Длина строки не должна быть больше 992, Поле может быть null


    @Override
    public String toString() {
        return x +
                "," + y +
                "," + name;
    }

    public void setName(String name) {
        if (name.length() > 992)
            throw new InvalidArgumentException("name", "name length has to be less than 992 characters");
        this.name = name;
    }

}