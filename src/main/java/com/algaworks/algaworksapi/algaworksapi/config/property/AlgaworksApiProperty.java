package com.algaworks.algaworksapi.algaworksapi.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("algaworks")
public class AlgaworksApiProperty {

    private String origemPermitida = "http://localhost:8000";
    private final Seguranca seguranca = new Seguranca();

    @Getter
    @Setter
    public static class Seguranca {

        private boolean enableHttps;
    }
}
