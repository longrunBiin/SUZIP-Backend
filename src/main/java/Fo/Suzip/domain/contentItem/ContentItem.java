package Fo.Suzip.domain.contentItem;

import Fo.Suzip.domain.BaseEntity;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.MemberItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public class ContentItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_item_id")
    private Long id;

    private String name;

    private String content;

    private String image;

    private String genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @OneToMany(mappedBy = "serviceItem")
    private List<MemberItem> memberItemList = new ArrayList<>();

    public void setDiary(Diary diary) {
        if (diary != null) {
            this.diary.getContentItemList().remove(this);
        }
        this.diary = diary;
    }
}
