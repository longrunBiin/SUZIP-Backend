package Fo.Suzip.repository;

import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.contentItem.ContentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

    void deleteByContentItemAndMember(ContentItem content, Member member);


}
