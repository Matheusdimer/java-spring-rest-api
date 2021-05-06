package com.betha.cursomc.services;

import com.betha.cursomc.domain.Cidade;
import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.Endereco;
import com.betha.cursomc.domain.dto.ClienteDTO;
import com.betha.cursomc.domain.dto.ClienteNewDTO;
import com.betha.cursomc.domain.enums.Perfil;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.repositories.EnderecoRepository;
import com.betha.cursomc.security.UserSS;
import com.betha.cursomc.services.exceptions.AuthorizationException;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private S3Service s3Service;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ClienteDTO> getAll() {
        return clienteRepository.findAll().stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Cliente getOne(Integer id) {
        UserSS user = UserService.authenticated();

        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        return clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente id " + id + " não encontrado."));
    }

    public Cliente add(ClienteNewDTO clienteDTO) {
        Cliente cliente = this.fromDTO(clienteDTO);
        enderecoRepository.saveAll(cliente.getEnderecos());

        return clienteRepository.save(cliente);
    }


    public Cliente save(Cliente cliente) {
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    public Cliente edit(Integer id, Cliente cli) {
        Cliente cliente = this.getOne(id);

        cliente.setId(id);
        this.updateData(cliente, cli);
        return clienteRepository.save(cliente);
    }

    public Cliente delete(Integer id) {
        Cliente cliente = this.getOne(id);

        clienteRepository.delete(cliente);
        cliente.setEnderecos(Collections.emptyList());
        cliente.setPedidos(Collections.emptyList());
        cliente.setTelefones(Collections.emptySet());

        return cliente;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Endereco> getEnderecos(Integer clienteId) {
        return enderecoRepository.findByClienteId(clienteId);
    }

    public Endereco addEndereco(Integer clienteId, Endereco endereco) {
        Cliente cliente = this.getOne(clienteId);

        endereco.setCliente(cliente);

        cliente.getEnderecos().add(endereco);
        clienteRepository.save(cliente);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        entityManager.refresh(enderecoSalvo);
        return enderecoSalvo;
    }

    public Endereco editEndereco(Integer clienteId, Integer enderecoId, Endereco endereco) {
        Cliente cliente = this.getOne(clienteId);
        Endereco enderecoSalvo = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ObjectNotFoundException("Endereco relacionado ao cliente id " + clienteId + " não encontrado."));

        endereco.setId(enderecoId);
        endereco.setCliente(cliente);

        return enderecoRepository.save(endereco);
    }

    public Endereco removeEndereco(Integer clienteId, Integer enderecoId) {
        Cliente cliente = this.getOne(clienteId);
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ObjectNotFoundException("Endereco relacionado ao cliente id " + clienteId + " não encontrado."));

        cliente.getEnderecos().remove(endereco);
        clienteRepository.save(cliente);

        enderecoRepository.delete(endereco);
        endereco.setCliente(null);
        return endereco;
    }

    public Page<Cliente> findPerPages(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO cli) {
        Cliente cliente = new Cliente();
        cliente.setId(cli.getId());
        cliente.setNome(cli.getNome());
        cliente.setEmail(cli.getEmail());

        return cliente;
    }

    public Cliente fromDTO(ClienteNewDTO cliDTO) {
        Cliente cliente = new Cliente(cliDTO.getNome(), cliDTO.getEmail(), cliDTO.getCpf_cnpj(),
                cliDTO.getTipo(), passwordEncoder.encode(cliDTO.getSenha()));
        Cidade cidade = new Cidade(cliDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(cliDTO.getLogradouro(), cliDTO.getNumero(),
                cliDTO.getComplemento(), cliDTO.getBairro(), cliDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(cliDTO.getTelefone1());

        if (cliDTO.getTelefone2() != null) {
            cliente.getTelefones().add(cliDTO.getTelefone2());
        }

        if (cliDTO.getTelefone3() != null) {
            cliente.getTelefones().add(cliDTO.getTelefone3());
        }

        return cliente;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = UserService.authenticated();

        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        URI uri = s3Service.uploadFile(multipartFile);

        Cliente cliente = this.getOne(user.getId());
        cliente.setImageUrl(uri.toString());
        clienteRepository.save(cliente);
        
        return uri;
    }
}
