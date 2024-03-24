package Fo.Suzip.service.OAuth2Service;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.domain.Member;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.CustomOAuth2User;
import Fo.Suzip.web.dto.SecurityUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 클라이언트 등록 ID(google, naver, kakao)와 사용자 이름 속성을 가져온다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 사용하여 가져온 OAuth2User 정보로 OAuth2Attribute 객체를 만든다.
        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // OAuth2Attribute의 속성값들을 Map으로 반환 받는다.
//        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();

        String username = oAuth2Attribute.getProvider() + " " + oAuth2Attribute.getProviderId();
        Member existData = memberRepository.findByUserName(username);

        if (existData == null) {
            System.out.println("CustomOAuth2UserService.loadUser");
            Member member = Member.builder()
                    .userName(username)
                    .email(oAuth2Attribute.getEmail())
                    .name(oAuth2Attribute.getName())
                    .profileImage(oAuth2Attribute.getPicture())
                    .userRole("ROLE_USER")
                    .provider(oAuth2Attribute.getProvider())
                    .build();

            memberRepository.save(member);

            SecurityUserDto userDTO = SecurityUserDto.builder()
                    .userName(username)
                    .name(oAuth2Attribute.getName())
                    .role("ROLE_USER")
                    .email(oAuth2Attribute.getEmail())
                    .build();


            return new CustomOAuth2User(userDTO);
        }
        else {

            existData.updateEmail(oAuth2Attribute.getEmail());
            existData.updateName(oAuth2Attribute.getName());

            memberRepository.save(existData);

            SecurityUserDto userDTO = SecurityUserDto.builder()
                    .userName(username)
                    .name(existData.getName())
                    .role(existData.getUserRole())
                    .build();

            return new CustomOAuth2User(userDTO);
        }

//        // 사용자 email(또는 id) 정보를 가져온다.
//        String email = (String) memberAttribute.get("email");
//        // 이메일로 가입된 회원인지 조회한다.
//        Optional<Member> findMember = memberService.findByEmail(email);
//
//        if (findMember.isEmpty()) {
//            System.out.println("CustomOAuth2UserService.loadUser.isEmpty");
//            // 회원이 존재하지 않을경우, memberAttribute의 exist 값을 false로 넣어준다.
//            memberAttribute.put("exist", false);
//            System.out.println("userRequest = " + userRequest.getAccessToken().getTokenValue());
//            // 회원의 권한(회원이 존재하지 않으므로 기본권한인 ROLE_USER를 넣어준다), 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
//            return new DefaultOAuth2User(
//                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                    memberAttribute, "email");
//        }
//
//        // 회원이 존재할경우, memberAttribute의 exist 값을 true로 넣어준다.
//        memberAttribute.put("exist", true);
//        // 회원의 권한과, 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_".concat(findMember.get().getUserRole()))),
//                memberAttribute, "email");
//
//    }
    }
}