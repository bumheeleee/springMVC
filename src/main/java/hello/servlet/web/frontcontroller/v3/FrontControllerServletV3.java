package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;

import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//frontController가 하는 역할이 uri 정보에 따라 controller를 매핑시켜주는 역할을 한다.
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    //key : url, value : Controller
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        //클라이언트로 부터 입력된 uri 정보를 받아온다.
        String requestURI = request.getRequestURI();

        //controllerMap으로 클라이언트로 부터 입력된 uri 정보를 통해 controller를 매핑.
        ControllerV3 controller = controllerMap.get(requestURI);

        //controller가 매핑되지 못했을 경우를 대비해서 만들어줌
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //HttpServletRequest, HttpServletResponse를 controller에서 사용하지 않고
        //Map<> paramMap을 통해 넘겨주기 위해서 사용 (request정보 -> paramMap으로 넣음)
        //request.getParameterNames()를 통해 모든 param값을 paramMap에 실어서 보냄
        Map<String, String> paramMap = createParamMap(request);

        //paramMap값을 주고 ModelView객체를 return 받음
        //mv객체에는 viewName에 상대경로가 입력되어있고, model에 값이 들어가 있음(없을 수도 있음)
        ModelView mv = controller.process(paramMap);

        //mv를 통해 논리적주소(new-form)등을 얻어냄
        String viewName = mv.getViewName();

        //viewResolver를 통해 절대 경로를 얻어냄
        MyView view = viewResolver(viewName);

        //jsp로 render할때 model, request, response를 다 넘겨줘야한다.
        view.render(mv.getModel(), request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


    private Map<String, String> createParamMap(HttpServletRequest request) {
        //paramMap
        //process에 paramMap을 넘겨줘야된다.
        //따라서 HttpServletRequest request와 매핑을 다 시켜줘야한다.
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }


}
