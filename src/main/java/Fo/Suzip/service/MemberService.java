package Fo.Suzip.service;

import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberProvider;
import Fo.Suzip.domain.MemberRole;
import Fo.Suzip.jwt.JwtIssuer;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.web.dto.JwtDto;
import Fo.Suzip.web.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtIssuer jwtIssuer;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getMemberByEmail(email);
    }

    public Member signUp(SignUpForm form) {
        if(memberRepository.existsByEmail(form.getEmail())){
            throw new RuntimeException("사용중인 이메일입니다.");
        }
        return memberRepository.save(Member.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .memberRole(MemberRole.USER)
                .provider(MemberProvider.LOCAL)
                .build());
    }

    public JwtDto signIn(SignUpForm form) {
        Member member = getMemberByEmail(form.getEmail());

        if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("일치하는 정보가 없습니다.");
        }

        return jwtIssuer.createToken(member.getEmail(), member.getMemberRole().name());
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 정보가 없습니다."));
    }
}
