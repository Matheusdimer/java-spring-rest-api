package com.betha.cursomc.services.validation;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.enums.TipoCliente;
import com.betha.cursomc.domain.dto.ClienteNewDTO;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.resources.exceptions.FieldMessage;
import com.betha.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        // inclua os testes aqui, inserindo erros na lista
        if (objDto.getTipo() == TipoCliente.PESSOA_FISICA.getCod()
                && !BR.isValidCPF(objDto.getCpf_cnpj())) {
            list.add(new FieldMessage("cpf_cnpj", "CPF inválido"));
        } else if (objDto.getTipo() == TipoCliente.PESSOA_JURIDICA.getCod()
                && !BR.isValidCNPJ(objDto.getCpf_cnpj())) {
            list.add(new FieldMessage("cpf_cnpj", "CNPJ inválido"));
        }

        Optional<Cliente> aux = clienteRepository.findByEmail(objDto.getEmail());

        if (aux.isPresent()) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}