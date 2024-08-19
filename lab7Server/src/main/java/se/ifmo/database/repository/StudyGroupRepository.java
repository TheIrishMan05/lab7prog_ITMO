package se.ifmo.database.repository;


import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.models.User;

import java.util.NavigableSet;
import java.util.Optional;

public interface StudyGroupRepository{
    NavigableSet<StudyGroup> findAllByOwner(User owner);

    NavigableSet<StudyGroup> findAll();

    void deleteAllByOwner(User owner);

    Optional<StudyGroup> findById(Long id);

    StudyGroup save(StudyGroup studyGroup, User user);
    StudyGroup update(StudyGroup studyGroup, User user);



    void deleteById(Long id);

}
