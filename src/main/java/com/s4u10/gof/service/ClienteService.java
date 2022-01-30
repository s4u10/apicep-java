package com.s4u10.gof.service;

import com.s4u10.gof.model.Cliente;

/**
 * Interface that defines the <b>Strategy</b> pattern in the client domain. With
 * this, if necessary, we can have multiple implementations of the same
 * interface.
 * 
 * @author s4u10
 */

public interface ClienteService {

	Iterable<Cliente> buscarTodos();

	Cliente buscarPorId(Long id);

	void inserir(Cliente cliente);

	void atualizar(Long id, Cliente cliente);

	void deletar(Long id);

}