package com.example.springmvc.web.frontcontroller.v5.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springmvc.web.frontcontroller.ModelView;
import com.example.springmvc.web.frontcontroller.MyView;
import com.example.springmvc.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.example.springmvc.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.example.springmvc.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.example.springmvc.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.example.springmvc.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.example.springmvc.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import com.example.springmvc.web.frontcontroller.v5.MyHandlerAdapter;

/**
 * 메인 로직을 거의 건드리지 않고 V4로 확장을 함.
 *  -> 어댑터만 확장시킴. -> OCP (설정을 밖에서 주입한다면)
 */
@WebServlet(name = "frontControllerServiceV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServiceV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServiceV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {

        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
            .filter(adapter -> adapter.supports(handler))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler));
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // v4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }
}
