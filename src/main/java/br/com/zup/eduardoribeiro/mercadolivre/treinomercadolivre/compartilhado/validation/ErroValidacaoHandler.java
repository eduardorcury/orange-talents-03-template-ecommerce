package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroValidacaoHandler {

    private final MessageSource messageSource;

    public ErroValidacaoHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDto>> handleErroValidacao(MethodArgumentNotValidException exception) {

        List<FieldError> erros = exception.getFieldErrors();

        List<ErroDto> response = new ArrayList<>();
        erros.forEach(fieldError -> {
            String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            ErroDto erroDto = new ErroDto(fieldError.getField(), mensagem);
            response.add(erroDto);
        });

        return ResponseEntity.badRequest().body(response);

    }

}
