package ru.wordle.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private UUID id;

    private String login;

    private String password;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}