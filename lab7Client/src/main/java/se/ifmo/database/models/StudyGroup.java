package se.ifmo.database.models;


import lombok.*;
import se.ifmo.database.exceptions.FieldIsNullException;
import se.ifmo.database.exceptions.InvalidArgumentException;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private User user;

    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Integer studentsCount; //Значение поля должно быть больше 0, Поле не может быть null

    private Integer expelledStudents; //Значение поля должно быть больше 0, Поле не может быть null

    private Long shouldBeExpelled; //Значение поля должно быть больше 0, Поле может быть null

    private Semester semesterEnum; //Поле не может быть null

    private Person groupAdmin; //Поле не может быть null

    public void setId(long id) {
        if (id <= 0) throw new InvalidArgumentException("id", "id must be greater than 0");
        this.id = id;
    }

    public void setName(String name) {
        if (name == null) throw new FieldIsNullException("name");
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) throw new FieldIsNullException("coordinates");
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate == null) throw new FieldIsNullException("creationDate");
        this.creationDate = creationDate;
    }

    public void setStudentsCount(Integer studentsCount) {
        if (studentsCount <= 0)
            throw new InvalidArgumentException("studentsCount", "studentsCount must be greater than 0");
        if (studentsCount == null) throw new FieldIsNullException("studentsCount");
        this.studentsCount = studentsCount;
    }

    public void setExpelledStudents(Integer expelledStudents) {
        if (expelledStudents <= 0) throw new InvalidArgumentException("expelledStudents",
                "expelledStudents must be greater than 0");
        if (expelledStudents == null) throw new FieldIsNullException("expelledStudents");
        this.expelledStudents = expelledStudents;
    }

    public void setShouldBeExpelled(Long shouldBeExpelled) {
        if (shouldBeExpelled <= 0) throw new InvalidArgumentException("shouldBeExpelled",
                "shouldBeExpelled must be greater than 0");
        this.shouldBeExpelled = shouldBeExpelled;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        if (semesterEnum == null) throw new FieldIsNullException("semesterEnum");
        this.semesterEnum = semesterEnum;
    }

    public void setGroupAdmin(Person groupAdmin) {
        if (groupAdmin == null) throw new FieldIsNullException("groupAdmin");
        this.groupAdmin = groupAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroup that = (StudyGroup) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(studentsCount, that.studentsCount) && Objects.equals(expelledStudents, that.expelledStudents) && Objects.equals(shouldBeExpelled, that.shouldBeExpelled) && semesterEnum == that.semesterEnum && Objects.equals(groupAdmin, that.groupAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, expelledStudents, shouldBeExpelled, semesterEnum, groupAdmin);
    }

    @Override
    public String toString() {
        return id +
                "," + name + "," + "," + coordinates + "," +
                creationDate +
                "," + studentsCount +
                "," + expelledStudents +
                "," + shouldBeExpelled +
                "," + semesterEnum +  ","
                + groupAdmin;
    }

    @Override
    public int compareTo(StudyGroup o) {
        return this.getName().compareTo(o.getName());
    }
}