package Fo.Suzip.domain.contentItem;

import Fo.Suzip.domain.BaseEntity;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.MemberRecommendedItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "dtype")
public class ContentItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_item_id")
    private Long id;

    private String name;

    @Column(columnDefinition = "varchar(500)")
    private String content;

    private String image;

    private String genre;

    private String dType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @OneToMany(mappedBy = "contentItem", cascade = CascadeType.ALL)
    private List<MemberItem> memberItemList = new ArrayList<>();

}
