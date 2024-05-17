package Fo.Suzip.domain;

import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
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

    @Column(columnDefinition = "varchar(5000)")
    private String content;

    @Column(columnDefinition = "varchar(5000)")
    private String image;

    private LocalDate date;//작성한 일기의 날짜

    private String sentence;

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
        this.image = request.getPreviewSrc();
        if (url!=null)
            this.image = url;
    }
}
