package Fo.Suzip.domain;

import Fo.Suzip.domain.serviceItem.ServiceItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String title;

    private String content;

    private String image;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "diary_emotion_id")
    private DiaryEmotion diaryEmotion;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<ServiceItem> serviceItemList = new ArrayList<>();
}
