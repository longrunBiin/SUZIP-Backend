package Fo.Suzip.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryEmotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_emotion_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Emotions emotion;

    private String color;

    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "diary_id")
//    private Diary diary;
}
