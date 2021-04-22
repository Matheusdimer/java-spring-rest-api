package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.Endereco;
import com.betha.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
    @Autowired
    ClienteService clienteService;

    @GetMapping
    public List<Cliente> getCidades() {
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable Integer id) {
        return clienteService.getOne(id);
    }

    @PostMapping
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        cliente = clienteService.save(cliente);
        return ResponseEntity.created(URI.create("/clientes/"+ cliente.getId())).body(cliente);
    }

    @PutMapping("/{id}")
    public Cliente editCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteService.edit(id, cliente);
    }

    @DeleteMapping("/{id}")
    public Cliente deleteCliente(@PathVariable Integer id) {
        return clienteService.delete(id);
    }

    @GetMapping("/{id}/enderecos")
    public List<Endereco> buscarEnderecos(@PathVariable(name = "id") Integer clienteId) {
        return clienteService.getEnderecos(clienteId);
    }

    @PostMapping("/{id}/enderecos")
    public Endereco adicionarEndereco(@PathVariable(name = "id") Integer clienteId, @RequestBody Endereco endereco) {
        return clienteService.addEndereco(clienteId, endereco);
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
