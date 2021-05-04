package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.Endereco;
import com.betha.cursomc.domain.dto.ClienteDTO;
import com.betha.cursomc.domain.dto.ClienteNewDTO;
import com.betha.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<ClienteDTO> getCidades() {
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable Integer id) {
        return clienteService.getOne(id);
    }

    @PostMapping
    public ResponseEntity<Cliente> addCliente(@Valid @RequestBody ClienteNewDTO clienteNovo) {
        Cliente cliente = clienteService.add(clienteNovo);
        return ResponseEntity.created(URI.create("/clientes/"+ cliente.getId())).body(cliente);
    }

    @PutMapping("/{id}")
    public Cliente editCliente(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.fromDTO(clienteDTO);
        return clienteService.edit(id, cliente);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Cliente deleteCliente(@PathVariable Integer id) {
        return clienteService.delete(id);
    }

    @GetMapping("/{id}/enderecos")
    public List<Endereco> buscarEnderecos(@PathVariable(name = "id") Integer clienteId) {
        return clienteService.getEnderecos(clienteId);
    }

    @PostMapping("/{id}/enderecos")
    public ResponseEntity<Endereco> adicionarEndereco(@PathVariable(name = "id") Integer clienteId, @RequestBody Endereco endereco) {
        Endereco enderecoSalvo = clienteService.addEndereco(clienteId, endereco);
        return ResponseEntity.created(URI.create("/enderecos/" + enderecoSalvo.getId())).body(enderecoSalvo);
    }

    @PutMapping("/{clienteId}/enderecos/{enderecoId}")
    public Endereco editEndereco(@PathVariable Integer clienteId,
                                 @PathVariable Integer enderecoId, @RequestBody Endereco endereco) {
        return clienteService.editEndereco(clienteId, enderecoId, endereco);
    }

    @DeleteMapping("/{clienteId}/enderecos/{enderecoId}")
    public Endereco removeEndereco(@PathVariable Integer clienteId, @PathVariable Integer enderecoId) {
        return clienteService.removeEndereco(clienteId, enderecoId);
    }
}
