package ru.wordle.infrastructure.repository.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.wordle.infrastructure.entity.AttemptEntity;
import org.springframework.data.repository.query.Param;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttemptJpaRepository
        extends JpaRepository<AttemptEntity, UUID> {

    @EntityGraph(attributePaths = "letters")
    @Query("from AttemptEntity where id in :ids")
    List<AttemptEntity> findByIdWithLetters(@Param("ids") Collection<UUID> ids);
}
