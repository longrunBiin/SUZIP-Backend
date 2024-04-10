package Fo.Suzip.domain;

import Fo.Suzip.domain.contentItem.ContentItem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberItem extends BaseEntity {//사용자가 저장한 컨텐츠 아이템

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meber_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_item_id")
    private ContentItem contentItem;

}
