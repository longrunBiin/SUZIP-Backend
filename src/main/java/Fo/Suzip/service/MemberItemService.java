package Fo.Suzip.service;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.repository.MemberItemRepository;
import Fo.Suzip.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberItemService {

    private final MemberItemRepository memberItemRepository;
    private final MemberRepository memberRepository;

    public List<MemberItem> getScrapItemsByUserName(String userName) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));
        return memberItemRepository.findAllByMember(member);
    }

    public boolean isContentScrapped(String userName, Long contentItemId) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Optional<MemberItem> memberItem = memberItemRepository.findByMemberAndContentItem_Id(member, contentItemId);
        return memberItem.isPresent();
    }
}
