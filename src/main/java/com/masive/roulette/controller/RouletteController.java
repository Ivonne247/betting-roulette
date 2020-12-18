package com.masive.roulette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masive.roulette.model.Bet;
import com.masive.roulette.model.Roulette;
import com.masive.roulette.repository.RouletteRepository;

@RestController
public class RouletteController {
	
	public static final String COLOR_RED= "rojo";
	public static final String COLOR_BLACK = "negro";
	public static final int NUM_MAX = 36;
	public static final int NUM_MIN = 0;
	public static final double TOP = 10000;
	public static final double WIN_COLOR = 1.8;
	public static final double WIN_NUMERO = 4;

	@Autowired
	private RouletteRepository rouletteRepository;
	
	public RouletteController(RouletteRepository rouletteRepository) {
		this.rouletteRepository = rouletteRepository;
	}
	
	@GetMapping
	public ResponseEntity<?> readAll(){
		return ResponseEntity.ok().body(rouletteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable int id){
		Roulette roulette= rouletteRepository.findById(id);
		return ResponseEntity.ok(roulette);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Roulette roulette){
		roulette.setStatus(false);
		rouletteRepository.save(roulette);
		return ResponseEntity.status(HttpStatus.CREATED).body(roulette.getId());
	}
	
	@PutMapping("change-status/{id}")
	public ResponseEntity<?> changeStatus(@PathVariable int id){
		Roulette roulette= rouletteRepository.findById(id);
		roulette.setStatus(true);
		rouletteRepository.save(roulette);
		String text = "Operación denegada";
		if(roulette != null && roulette.isStatus()) {
			text = "Operación exitosa";
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(text);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id){
		rouletteRepository.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@PostMapping("bet/{idApuesta}/{idUsuario}")
	public ResponseEntity<?> bet(@PathVariable int idApuesta, @PathVariable int idUsuario, @RequestBody Bet bet){
		Roulette roulette= rouletteRepository.findById(idApuesta);
		if(!roulette.isStatus()) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Ruleta no disponible");
		}
		if(bet.getColor()!=null && !(bet.getColor().equals(COLOR_RED) ||  bet.getColor().equals(COLOR_BLACK))) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Color no válido");
		}
		if((Integer)bet.getNumber()!=null && (bet.getNumber()<NUM_MIN || bet.getNumber()>NUM_MAX)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Número no válido");
		}
		if((bet.getAmount()==null) && ((Integer)bet.getNumber()==null || bet.getColor()==null)){
			return ResponseEntity.status(HttpStatus.CREATED).body("Petición no válida");
		}
		if(bet.getAmount()>TOP) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Cantidad de dinero a apostar no válida");
		}
		bet.setIdUser(idUsuario);
		roulette.addBet(bet);
		rouletteRepository.save(roulette);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(roulette);
	}
	
	
	@PutMapping("close/{id}")
	public ResponseEntity<?> closeRoulette(@PathVariable int id){
		Roulette roulette= rouletteRepository.findById(id);
		roulette.setStatus(false);
		int winNumber = (int) Math.floor(Math.random()*(NUM_MAX-NUM_MIN+1)+NUM_MIN);
		String winColor = COLOR_BLACK;
		roulette.setWnumber(winNumber);
		if(winNumber%2==0) {
			winColor = COLOR_RED;
		}
		for(int i=0; i<roulette.getBets().size(); i++) {
			if(roulette.getBets().get(i).getColor()!=null && roulette.getBets().get(i).getColor().equals(winColor)) {
				roulette.getBets().get(i).setWMoney(roulette.getBets().get(i).getAmount()*WIN_COLOR);
			}
			if((Integer)roulette.getBets().get(i).getNumber()!=null && roulette.getBets().get(i).getNumber()==winNumber) {
				roulette.getBets().get(i).setWMoney(roulette.getBets().get(i).getAmount()*WIN_NUMERO);
			}
		}
		rouletteRepository.save(roulette);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(roulette);
	}
}
