package com.betha.cursomc.services;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.Endereco;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.repositories.EnderecoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Cliente getOne(Integer id) {
        Optional<Cliente> possivelCliente = clienteRepository.findById(id);

        if (!possivelCliente.isPresent()) {
            throw new ObjectNotFoundException("Cliente id " + id + " não encontrado.");
        }

        return possivelCliente.get();
    }

    public Cliente save(Cliente cliente) {
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    public Cliente edit(Integer id, Cliente cliente) {
        Optional<Cliente> possivelCliente = clienteRepository.findById(id);

        if (!possivelCliente.isPresent()) {
            throw new ObjectNotFoundException("Cliente id " + id + " não encontrado.");
        }

        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public Cliente delete(Integer id) {
        Optional<Cliente> possivelCliente = clienteRepository.findById(id);

        if (!possivelCliente.isPresent()) {
            throw new ObjectNotFoundException("Cliente id " + id + " não encontrado.");
        }

        Cliente cliente = possivelCliente.get();

        clienteRepository.delete(cliente);
        cliente.setEnderecos(Collections.emptyList());

        return cliente;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Endereco> getEnderecos(Integer clienteId) {
        return enderecoRepository.findByClienteId(clienteId);
    }

    public Endereco addEndereco(Integer clienteId, Endereco endereco) {
        Optional<Cliente> possivelCliente = clienteRepository.findById(clienteId);

        if (!possivelCliente.isPresent()) {
            throw new ObjectNotFoundException("Cliente id " + clienteId + " não encontrado.");
        }

        Cliente cliente = possivelCliente.get();

        endereco.setCliente(cliente);

        cliente.getEnderecos().add(endereco);
        clienteRepository.save(cliente);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        entityManager.refresh(enderecoSalvo);
        return enderecoSalvo;
    }

    public Endereco editEndereco(Integer clienteId, Integer enderecoId, Endereco endereco) {
        Optional<Cliente> possivelCliente = clienteRepository.findById(clienteId);
        Optional<Endereco> possivelEndereco = enderecoRepository.findById(enderecoId);

        if (!possivelCliente.isPresent()) {
            throw new ObjectNotFoundException("Cliente id " + clienteId + " não encontrado.");
        }
        if (!possivelEndereco.isPresent()) {
            throw new ObjectNotFoundException("Endereco relacionado ao cliente id "
                    + clienteId + " não encontrado.");
        }

        Cliente cliente = possivelCliente.get();

        endereco.setId(enderecoId);
        endereco.setCliente(cliente);

        return enderecoRepository.save(endereco);
    }

    public Endereco removeEndereco(Integer clienteId, Integer enderecoId) {
        Optional<Cliente> possivelCliente = clienteRepository.findById(clienteId);
        Optional<Endereco> possivelEndereco = enderecoRepository.findById(enderecoId);

        if (!possivelCliente.isPresent()) {
            throw new ObjectNotFoundException("Cliente id " + clienteId + " não encontrado.");
        }
        if (!possivelEndereco.isPresent()) {
            throw new ObjectNotFoundException("Endereco relacionado ao cliente id "
                    + clienteId + " não encontrado.");
        }

        Cliente cliente = possivelCliente.get();
        Endereco endereco = possivelEndereco.get();

        cliente.getEnderecos().remove(endereco);
        clienteRepository.save(cliente);

        enderecoRepository.delete(endereco);
        endereco.setCliente(null);
        return endereco;
    }
}
