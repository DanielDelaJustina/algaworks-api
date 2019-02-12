package com.algaworks.algaworksapi.algaworksapi.resource;

import com.algaworks.algaworksapi.algaworksapi.config.property.AlgaworksApiProperty;
import com.algaworks.algaworksapi.algaworksapi.enumeration.TokenName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

    @Autowired
    private AlgaworksApiProperty algaworksApiProperty;

    @DeleteMapping("/revoke")
    public void revoke(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = new Cookie(TokenName.refreshToken.name(),null);
        cookie.setHttpOnly(true);
        cookie.setSecure(algaworksApiProperty.getSeguranca().isEnableHttps());//TODO mudar em produção
        cookie.setPath(request.getContextPath() + "/oauth/token");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());

    }
}
