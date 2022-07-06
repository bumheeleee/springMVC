package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) {

        //paramMap는 request.getParameter()의 정보를 HashMap()으로 받아옴!
        //key,value로 값을 꺼냄
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        //viewName : 상대 경로 지정
        ModelView mv = new ModelView("save-result");

        //나중에 frontControllerServlet에서 model에 담은 값 -> request.setAttribute(key, value))으로
        //나눠주기 위해 model에 저장
        mv.getModel().put("member",member);

        return  mv;
    }
}
