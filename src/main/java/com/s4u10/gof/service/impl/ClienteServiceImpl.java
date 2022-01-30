package com.s4u10.gof.service.impl;

/**
 * Implementation of the <b>Strategy</b> {@link ClienteService}, which can be
 * injected by Spring (via {@link Autowired}). With that, as this class is a
 * {@link Service}, it will be treated as a <b>Singleton</b>.
 *
 *
 *  @author s4u10
 */


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s4u10.gof.model.Cliente;
import com.s4u10.gof.model.ClienteRepository;
import com.s4u10.gof.model.Endereco;
import com.s4u10.gof.model.EnderecoRepository;
import com.s4u10.gof.service.ClienteService;
import com.s4u10.gof.service.ViaCepService;


@Service
public class ClienteServiceImpl implements ClienteService {

	// Singleton: Inject Spring components with @Autowired.
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implement the methods defined in the interface.
	// Facade: Abstract integrations with subsystems, providing a simple interface.

	@Override
	public Iterable<Cliente> buscarTodos() {
		// Search all customers.
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// Search Customer by ID.
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		// Search for Customer by ID, if any:
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		// Delete Customer by ID.
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(Cliente cliente) {
		// Check if the Customer Address already exists (by zip code).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// If it does not exist, integrate with ViaCEP and the return persists.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		// Insert Customer, linking Address (new or existing).
		clienteRepository.save(cliente);
	}

}