package Fo.Suzip.service.OAuth2Service;

import Fo.Suzip.domain.Member;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.CustomOAuth2User;
import Fo.Suzip.web.dto.SecurityUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


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

        String username = oAuth2Attribute.getProvider() + " " + oAuth2Attribute.getProviderId();
        Member existData = memberRepository.findByUserName(username);

        if (existData == null) {
            Member member = Member.builder()
                    .userName(username)
                    .email(oAuth2Attribute.getEmail())
                    .name(oAuth2Attribute.getName())
                    .userName(username)
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
    }
}