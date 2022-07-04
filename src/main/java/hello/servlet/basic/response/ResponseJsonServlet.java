package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Content-Type: application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        HelloData helloData = new HelloData();

        helloData.setUsername("dlqjagml");
        helloData.setAge(55);

        //{"username":"dlqjagml", "age":55}이런식으로 바꾸기 (객체 -> JSON형식)
        //objectMapper.writeValueAsString() 를 사용하면 객체를 JSON 문자로 변경할 수 있다.
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);

    }
}
