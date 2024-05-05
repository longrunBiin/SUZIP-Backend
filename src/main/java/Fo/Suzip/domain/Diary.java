package Fo.Suzip.domain;

import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "varchar(1000)")
    private String content;

    private String image;

    @Enumerated(EnumType.STRING)
    private Emotions emotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    @OneToMany(mappedBy = "diary")
//    private List<DiaryEmotion> diaryEmotion = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<ContentItem> contentItemList = new ArrayList<>();

    public void updateDiary(DiaryRequestDTO.UpdateRequestDTO request, String url){
        this.title = request.getTitle();
        this.content = request.getContent();
        this.image = url;
    }
}
