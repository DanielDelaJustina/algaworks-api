package com.algaworks.algaworksapi.algaworksapi.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        //o parametro (messagem.invalida) é o nome definido no arquivo messages.properties
        String msgUser = messageSource.getMessage("messagem.invalida",null, LocaleContextHolder.getLocale());
        String msgDev = ex.getCause().toString();
        return handleExceptionInternal(ex, new Erro(msgUser, msgDev) , headers, HttpStatus.BAD_REQUEST, request);
    }

    public static class Erro {
        private String msgUser;
        private String msgDev;

        public Erro(String msgUser, String msgDev) {
            this.msgUser = msgUser;
            this.msgDev = msgDev;
        }

        public String getMsgUser() {
            return msgUser;
        }

        public void setMsgUser(String msgUser) {
            this.msgUser = msgUser;
        }

        public String getMsgDev() {
            return msgDev;
        }

        public void setMsgDev(String msgDev) {
            this.msgDev = msgDev;
        }
    }
}
