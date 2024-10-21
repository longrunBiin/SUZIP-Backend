package Fo.Suzip.memberService;


import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.domain.Member;
import Fo.Suzip.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

//    @BeforeEach
    public void init() {
        for (int i = 0; i < 10000; i++) {

            Member mem = Member.builder()
                    .userName("username" + i)
                    .email("Email" + i)
                    .name("Name" + i)
                    .profileImage("Picture" + i)
                    .userRole("ROLE_USER")
                    .provider("Provider")
                    .build();
            memberRepository.save(mem);
        }
    }

    @Test
    public void findByUserNameIndexTest() {
        long startTime = System.currentTimeMillis();
        Member findMember = memberRepository.findMemberByUserName("username9990")
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));
        Assertions.assertThat(findMember.getUserName()).isEqualTo("username9990");
        long endTime = System.currentTimeMillis();
        System.out.println("인덱스 o 실행시간 측정 : " + (endTime - startTime) + "ms");
    }
}
