package Fo.Suzip.service.contentService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.ContentHandler;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.repository.ContentRepository;
import Fo.Suzip.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentQueryServiceImpl implements ContentQueryService{
    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;
    @Override
    public Book findBook(Long bookId) {

        return contentRepository.findBookById(bookId)
                .orElseThrow(() -> new ContentHandler(ErrorStatus._BOOK_NOT_FOUND));
    }

    @Override
    public Movie findMovie(Long movieId) {
        return contentRepository.findMovieById(movieId)
                .orElseThrow(() -> new ContentHandler(ErrorStatus._MOVIE_NOT_FOUND));
    }

    @Override
    public Music findMusic(Long musicId) {
        return contentRepository.findMusicById(musicId)
                .orElseThrow(() -> new ContentHandler(ErrorStatus._MUSIC_NOT_FOUND));
    }

    @Override
    public Page<Book> getBookList(String userName, Integer page) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 10);
        return contentRepository.findAllBookByMember(member.getId(), pageRequest, "book");
    }
}
