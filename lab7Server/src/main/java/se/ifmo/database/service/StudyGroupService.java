package se.ifmo.database.service;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import se.ifmo.database.models.*;
import se.ifmo.database.repository.StudyGroupRepository;
import se.ifmo.dbutils.DatabaseManager;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudyGroupService implements StudyGroupRepository {
    static StudyGroupService instance;
    final String FIND_ALL_STUDYGROUPS = "SELECT * FROM studygroups";
    final String FIND_ALL_STUDYGROUPS_BY_USER = "SELECT * FROM studygroups WHERE owner_id = ?";
    final String FIND_STUDYGROUP_BY_ID = "SELECT * FROM studygroups WHERE id = ?";
    final String SAVE_GROUP = "INSERT INTO studygroups (owner_id, name, coordinates_x, coordinates_y, creation_date, students_count, expelled_students, should_be_expelled, semester, admin_name, birthday, eye_color, location_x, location_y, location_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String UPDATE_GROUP = "UPDATE studygroups SET name = ?, coordinates_x = ?, coordinates_y = ?, creation_date = ?, students_count = ?, expelled_students = ?, should_be_expelled = ?, semester = ?, admin_name = ?, birthday = ?, eye_color = ?, location_x = ?, location_y = ?, location_name = ? WHERE id = ?";
    final String REMOVE_STUDYGROUP_BY_ID = "DELETE FROM studygroups WHERE id = ?";
    final String REMOVE_ALL_STUDYGROUPS_BY_USER = "DELETE FROM studygroups WHERE owner_id = ?";

    private StudyGroupService() {
    }

    public static StudyGroupService getInstance() {
        return instance == null ? instance = new StudyGroupService() : instance;
    }

    @Override
    @SneakyThrows
    public synchronized NavigableSet<StudyGroup> findAllByOwner(User owner) {
        NavigableSet<StudyGroup> studyGroups = new TreeSet<>();
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(FIND_ALL_STUDYGROUPS_BY_USER)) {
            query.setLong(1, owner.getId());
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    studyGroups.add(map(resultSet));
                }
            }
        }
        return studyGroups;
    }

    @Override
    @SneakyThrows
    public NavigableSet<StudyGroup> findAll() {
        NavigableSet<StudyGroup> studyGroups = new TreeSet<>();
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(FIND_ALL_STUDYGROUPS);
        ResultSet resultSet = query.executeQuery()) {
            while (resultSet.next()) {
                studyGroups.add(map(resultSet));
            }
        }
        return studyGroups;
    }

    @Override
    @SneakyThrows
    public synchronized void deleteAllByOwner(User owner) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(REMOVE_ALL_STUDYGROUPS_BY_USER)) {
            query.setLong(1, owner.getId());
            query.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public synchronized StudyGroup save(StudyGroup studyGroup, User user) {
        try (PreparedStatement query = DatabaseManager.getInstance().getConnection().prepareStatement(SAVE_GROUP, PreparedStatement.RETURN_GENERATED_KEYS)){
            query.setLong(1, user.getId());
            query.setString(2, studyGroup.getName());
            query.setLong(3, studyGroup.getCoordinates().getX());
            query.setFloat(4, studyGroup.getCoordinates().getY());
            query.setTimestamp(5, Timestamp.valueOf(studyGroup.getCreationDate()));
            query.setInt(6, studyGroup.getStudentsCount());
            query.setInt(7, studyGroup.getExpelledStudents());
            query.setLong(8, studyGroup.getShouldBeExpelled());
            query.setString(9, studyGroup.getSemesterEnum().name());
            query.setString(10, studyGroup.getGroupAdmin().getName());
            query.setDate(11, Date.valueOf(studyGroup.getGroupAdmin().getBirthday()));
            query.setString(12, studyGroup.getGroupAdmin().getEyeColor().name());
            query.setLong(13, studyGroup.getGroupAdmin().getLocation().getX());
            query.setInt(14, studyGroup.getGroupAdmin().getLocation().getY());
            query.setString(15, studyGroup.getGroupAdmin().getLocation().getName());

            query.executeUpdate();

            try (ResultSet generatedKeys = query.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    studyGroup.setId(generatedKeys.getLong(1));
                }
            }
        }
        return studyGroup;
    }

    @Override
    @SneakyThrows
    public synchronized StudyGroup update(StudyGroup studyGroup, User user) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(UPDATE_GROUP)) {
            query.setString(1, studyGroup.getName());
            query.setLong(2, studyGroup.getCoordinates().getX());
            query.setFloat(3, studyGroup.getCoordinates().getY());
            query.setTimestamp(4, Timestamp.valueOf(studyGroup.getCreationDate()));
            query.setInt(5, studyGroup.getStudentsCount());
            query.setInt(6, studyGroup.getExpelledStudents());
            query.setLong(7, studyGroup.getShouldBeExpelled());
            query.setString(8, studyGroup.getSemesterEnum().name());
            query.setString(9, studyGroup.getGroupAdmin().getName());
            query.setDate(10, Date.valueOf(studyGroup.getGroupAdmin().getBirthday()));
            query.setString(11, studyGroup.getGroupAdmin().getEyeColor().name());
            query.setLong(12, studyGroup.getGroupAdmin().getLocation().getX());
            query.setInt(13, studyGroup.getGroupAdmin().getLocation().getY());
            query.setString(14, studyGroup.getGroupAdmin().getLocation().getName());
            query.setLong(15, studyGroup.getId());

            query.executeUpdate();
        }
        return studyGroup;
    }

    @Override
    @SneakyThrows
    public void deleteById(Long id) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(REMOVE_STUDYGROUP_BY_ID)) {
            query.setLong(1, id);
            query.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<StudyGroup> findById(Long id) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(FIND_STUDYGROUP_BY_ID)) {
            query.setLong(1, id);
            try (ResultSet result = query.executeQuery()) {
                return result.next() ? Optional.of(map(result)) : Optional.empty();
            }
        }
    }

    @SneakyThrows
    private StudyGroup map(ResultSet resultSet) {
        return StudyGroup.builder()
                .id(resultSet.getLong(1))
                .user(new User(resultSet.getLong(2)))
                .name(resultSet.getString(3))
                .coordinates(new Coordinates(resultSet.getLong(4), resultSet.getFloat(5)))
                .creationDate(resultSet.getObject(6, LocalDateTime.class))
                .studentsCount(resultSet.getInt(7))
                .expelledStudents(resultSet.getInt(8))
                .shouldBeExpelled(resultSet.getLong(9))
                .semesterEnum(Semester.valueOf(resultSet.getString(10)))
                .groupAdmin(new Person(resultSet.getString(11),
                        resultSet.getObject(12, LocalDate.class),
                        Color.valueOf(resultSet.getString(13)),
                        new Location(resultSet.getLong(14),
                                resultSet.getInt(15),
                                resultSet.getString(16))))
                .build();
    }
}
