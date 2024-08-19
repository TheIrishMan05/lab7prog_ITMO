package se.ifmo.database.models;

import lombok.*;
import se.ifmo.database.exceptions.FieldIsNullException;
import se.ifmo.database.exceptions.InvalidArgumentException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long x;  //Максимальное значение поля: 901, Поле не может быть null

    @Setter
    private float y;


    @Override
    public String toString() {
        return x +
                "," + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.y, y) == 0 && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void setX(Long x) {
        if (x > 901) throw new InvalidArgumentException("x", "x has to be less than 901");
        if (x == null) throw new FieldIsNullException("x");
        this.x = x;
    }

}
