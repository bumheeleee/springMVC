package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);
        response.getWriter().write("ok");
    }


    /**
     *  class 레벨에서 @Controller로 지정해줘서 return값이 view로 가는데,
     *  @ResponseBody의 역할은 return 값을 그냥 responseBody로 보내버림!
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ){
        log.info("username = {}, age = {}", memberName, memberAge);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }


    /**
     * 인간의 욕심은 끝이 없다.... 다 없애줌(요청 파라미터랑 이름만 같으면!)
     * 근데 이건 좀 헷갈릴 수 도 있기때문에 사용을 자제하자...
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }


    /**
     * required = true라고 하면 request-parameter로 반드시 와야된다. 안그럼 에러발생
     * required = false라고 하면 안와도 문제는 없음
     * Interger타입으로 age를 만든 이유는 int 타입에는 null이 들어갈 수 없기 때문이다.
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * defaultValue를 사용함으로써 requset-parameter로 값을 받지 못했을 경우,
     * 그냥 default로 값을 설정해 주는 기능
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * 파라미터를 Map으로 조회하기
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap){
        log.info("username={}, age={}", paramMap.get("username"),paramMap.get("age"));
        return "ok";
    }


    /**
     * @ModelAttribute 사용
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     *스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
     * String , int , Integer 같은 단순 타입 = @RequestParam
     * 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}
