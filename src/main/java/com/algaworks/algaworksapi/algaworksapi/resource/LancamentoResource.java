package com.algaworks.algaworksapi.algaworksapi.resource;

import com.algaworks.algaworksapi.algaworksapi.event.RecursoCriadoEvent;
import com.algaworks.algaworksapi.algaworksapi.exceptionhandler.AlgamoneyExceptionHandler;
import com.algaworks.algaworksapi.algaworksapi.model.Lancamento;
import com.algaworks.algaworksapi.algaworksapi.repository.LancamentoRepository;
import com.algaworks.algaworksapi.algaworksapi.repository.filter.LancamentoFilter;
import com.algaworks.algaworksapi.algaworksapi.repository.projection.Resumolancamento;
import com.algaworks.algaworksapi.algaworksapi.service.LancamentoService;
import com.algaworks.algaworksapi.algaworksapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/lancamentos")
@RestController
public class LancamentoResource {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {

        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Resumolancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {

        return lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<Lancamento> buscarLancamento(@PathVariable Long id) {

        Lancamento lancamento = lancamentoRepository.findOne(id);
        return lancamento == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(lancamento);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> criarLancamento(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {

        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }


    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String msgUser = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String msgDev = ex.toString();

        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(msgUser, msgDev));
        return ResponseEntity.badRequest().body(erros);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    public void excluirRegistro(@PathVariable Long id) {

        lancamentoRepository.delete(id);
    }
}




