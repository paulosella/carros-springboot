package com.example.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.carros.api.domain.Carro;
import com.example.carros.api.domain.CarroService;
import com.example.carros.api.domain.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosServiceTests {

	@Autowired
	private CarroService service; 
	
	@Test
	public void testInsert() {
		
		Carro carro = new Carro();
		
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");
		
		CarroDTO c = service.insert(carro);
		
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		//Buscar objeto
		Optional<CarroDTO> op = service.getCarroById(id);
		assertTrue(op.isPresent());
		
		c = op.get();
		
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		//Deletar o objeto
		service.delete(id);
		
		//Verificar se deletou
		assertFalse(service.getCarroById(id).isPresent());
	}
	
	@Test
	public void testList() {
		
		List<CarroDTO> lista = service.getCarros();
		
		assertEquals(30, lista.size());
	}
	
	@Test
	public void testGet() {
		
		Optional<CarroDTO> op = service.getCarroById(11L);
		assertTrue(op.isPresent());
		
		CarroDTO c = op.get();
		assertEquals("Ferrari FF", c.getNome());
		
	}
	
	@Test
	public void testListaPorTipo() {
		
		assertEquals(10, service.getCarrosByTipo("classicos").size());
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());
		
		assertEquals(0, service.getCarrosByTipo("x").size());
		
	}

}
