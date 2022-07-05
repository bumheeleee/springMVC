package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MemberRepositoryTest {

    //싱글톤으로 만들었기 때문에 객체를 생성하는 것이 불가는 하다.
    //MemberRepository memberRepository = new MemberRepository(); x

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }
    @Test
    void save(){
        //given : ~이 주어졌을 때
        Member member = new Member("dlqjagml",20);

        //when : ~이 실행할 때
        Member savedMember = memberRepository.save(member);

        //then : ~이 이렇게 동작해야됨
        Member findMember = memberRepository.findById(member.getId());
        Assertions.assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll(){
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 21);

        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        ArrayList<Member> result = memberRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(member1,member2);

    }





}
