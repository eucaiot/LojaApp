package br.com.eucaiot.lojaapp.utils.validation;

import br.com.eucaiot.lojaapp.exception.CampoComErro;
import br.com.eucaiot.lojaapp.dto.ClienteNewDto;
import br.com.eucaiot.lojaapp.model.Cliente;
import br.com.eucaiot.lojaapp.repository.ClienteRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class InserirClienteValidator implements ConstraintValidator<InserirCliente, ClienteNewDto> {

    private final ClienteRepository clienteRepository;

    public InserirClienteValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void initialize(InserirCliente constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteNewDto cliente, ConstraintValidatorContext context) {

        List<CampoComErro> erros = new ArrayList<>();

        Cliente clienteEmail = clienteRepository.findByEmail(cliente.email());

        if(clienteEmail != null){
            erros.add(new CampoComErro("email", "E-mail já existente"));
        }

        for (CampoComErro e : erros) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getCampo())
                    .addConstraintViolation();
        }

        return erros.isEmpty();
    }
}
