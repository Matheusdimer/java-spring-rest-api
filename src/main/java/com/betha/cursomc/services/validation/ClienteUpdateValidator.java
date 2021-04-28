package com.betha.cursomc.services.validation;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.enums.TipoCliente;
import com.betha.cursomc.dto.ClienteDTO;
import com.betha.cursomc.dto.ClienteNewDTO;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.resources.exceptions.FieldMessage;
import com.betha.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Integer urlId = Integer.parseInt(map.get("id"));

        Optional<Cliente> aux = clienteRepository.findByEmail(objDto.getEmail());

        if (aux.isPresent() && !aux.get().getId().equals(urlId)) {
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