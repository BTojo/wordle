package ru.wordle.api.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UiController {

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/**")
    public ResponseEntity<?> any(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addCookie(new Cookie("servlet-path", request.getContextPath()));
        byte[] body = request.getInputStream().readAllBytes();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(k -> headers.add(k, request.getHeader(k)));
        headers.set("content-length", Integer.toString(body.length));
        try {
            return restTemplate.exchange(
                    "https://wordle-ui.internship.regiuss.space/" + request.getServletPath() + "?" + request.getQueryString(),
                    HttpMethod.valueOf(request.getMethod()),
                    new HttpEntity<>(body, headers),
                    byte[].class,
                    request.getParameterMap());
        } catch (final HttpClientErrorException | HttpServerErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getResponseHeaders(), e.getStatusCode());
        }
    }
}