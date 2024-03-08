package Fo.Suzip.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryEmotion extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "diary_emotion_id")
    private Long id;

    private String emotion;

    private String color;

    private String content;
}
