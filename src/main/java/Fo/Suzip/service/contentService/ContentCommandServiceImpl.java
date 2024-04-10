package Fo.Suzip.service.contentService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.ContentHandler;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.repository.ContentRepository;
import Fo.Suzip.repository.MemberItemRepository;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.web.dto.contentDTO.ContentRequestDTO;
import Fo.Suzip.web.dto.scrapDTO.ScrapRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentCommandServiceImpl implements ContentCommandService{

    private final MemberRepository memberRepository;
    private final MemberItemRepository memberItemRepository;
    private final ContentRepository contentRepository;

    @Override
    @Transactional
    public MemberItem addScrapContent(String userName, ScrapRequestDTO.scrapContentsRequestDto request) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        ContentItem content = contentRepository.findContentById(request.getContentId())
                        .orElseThrow(()-> new ContentHandler(ErrorStatus._CONTENT_NOT_FOUND));

        MemberItem memberItem = ContentConverter.toMemberItem(member, content);

        return memberItemRepository.save(memberItem);
    }

    @Override
    @Transactional
    public void deleteScrapContent(String userName, Long id) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        ContentItem content = contentRepository.findContentById(id)
                .orElseThrow(()-> new ContentHandler(ErrorStatus._CONTENT_NOT_FOUND));

        memberItemRepository.deleteByContentItemAndMember(content, member);
    }
}
