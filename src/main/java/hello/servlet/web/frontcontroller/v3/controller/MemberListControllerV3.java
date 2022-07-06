package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.ArrayList;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        //paramMap는 request.getParameter()의 정보를 HashMap()으로 받아옴!
        //key,value로 값을 꺼냄(여기서는 사용하지 않음)
        ArrayList<Member> members = memberRepository.findAll();

        //viewName : 상대 경로 지정
        ModelView mv = new ModelView("members");

        //나중에 frontControllerServlet에서 model에 담은값 -> request.setAttribute(key, value))으로
        //나눠주기 위해 model에 저장
        mv.getModel().put("members",members);

        return mv;
    }
}
