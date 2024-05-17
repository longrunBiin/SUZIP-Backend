package Fo.Suzip.service.contentService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.ContentHandler;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.MemberRecommendedItem;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.repository.ContentRepository;
import Fo.Suzip.repository.MemberItemRepository;
import Fo.Suzip.repository.MemberRecommendedItemRepository;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
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
    private final MemberRecommendedItemRepository memberRecommendedItemRepository;

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

    @Override
    @Transactional
    public void addContent(String userName, DiaryResponseDTO.EmotionResponseDto emotionResponse, Diary diary) {

        DiaryResponseDTO.MovieDto movie = emotionResponse.getRecommendations().getMovie();
        DiaryResponseDTO.BookDto book = emotionResponse.getRecommendations().getBook();
        DiaryResponseDTO.MusicDto music = emotionResponse.getRecommendations().getMusic();

        ContentItem newMovie = ContentConverter.toMovie(movie, diary);
        ContentItem newMusic = ContentConverter.toMusic(music, diary);
        ContentItem newBook = ContentConverter.toBook((book), diary);

        Optional<ContentItem> item;
        item = contentRepository.findMovieByNameAndContent(newMovie.getName(), newMovie.getContent(), "movie");
        if(item.isEmpty())  {
            ContentItem savedMovie = contentRepository.save(newMovie);
            addRecommendedContent(userName, savedMovie);
        }

        item = contentRepository.findMusicByNameAndContent(newMovie.getName(), newMovie.getContent(), "music");
        if(item.isEmpty())  {
            ContentItem savedMusic = contentRepository.save(newMusic);
            addRecommendedContent(userName, savedMusic);
        }

        item = contentRepository.findBookByNameAndContent(newMovie.getName(), newMovie.getContent(), "book");
        if(item.isEmpty()) {
            ContentItem savedBook = contentRepository.save(newBook);
           addRecommendedContent(userName, savedBook);
        }
    }

    @Override
    @Transactional
    public MemberRecommendedItem addRecommendedContent(String userName, ContentItem item) {

        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        MemberRecommendedItem recommendedItem = ContentConverter.toRecommendedItem(member, item);
        System.out.println("recommendedItem = " + recommendedItem.getContentItem().getName());
        return memberRecommendedItemRepository.save(recommendedItem);
    }


}
