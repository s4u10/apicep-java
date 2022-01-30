package com.s4u10.gof.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.s4u10.gof.model.Endereco;

/**
* HTTP client, created via <b>OpenFeign</b>, to consume the API of the
* <b>ViaCEP</b>.
* 
* @see <a href="https://spring.io/projects/spring-cloud-openfeign">Spring Cloud OpenFeign</a>
* @see <a href="https://viacep.com.br">ViaCEP</a>
* 
** @author s4u10
*/
	
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

	@GetMapping("/{cep}/json/")
	Endereco consultarCep(@PathVariable("cep") String cep);
}