package dev.harakki.comics.recommendations.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tag_profiles",
        indexes = {
                @Index(name = "idx_user_tag_profiles_user_id", columnList = "user_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class UserTagProfile {

    @EmbeddedId
    private UserTagProfileId id;

    @Column(nullable = false)
    private double score;

    @Column(nullable = false)
    private int count;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private Instant updatedAt;

}
