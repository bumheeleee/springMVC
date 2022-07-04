package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="requestBodyStringServlet",urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //inputStream은 byte코드를 반환한다.
        ServletInputStream inputStream = request.getInputStream();

        //StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); inputStream 문자열로 바꿈
        String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("message = " + message);

        response.getWriter().write("ok");
    }
}
