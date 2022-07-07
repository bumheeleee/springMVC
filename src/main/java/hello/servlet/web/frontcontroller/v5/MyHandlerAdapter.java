package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//핸들러가 맞는지 확인하고 핸들러를 실행시키는 역할을 한다.
public interface MyHandlerAdapter  {

    //컨트롤러가 넘어왔을때 지원할 수 있는 컨트롤러인지 확인한다.
    boolean supports(Object handler);

    //지원되는거면 적용한다, 다룬다.
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;

}
