package co.edu.unbosque.virusdetectedback.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.virusdetectedback.dto.GeminiDTO;
import co.edu.unbosque.virusdetectedback.dto.VirusTotalDTO;
import co.edu.unbosque.virusdetectedback.service.GeminiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/gemini")
@CrossOrigin(origins = { "*" })
public class GeminiController {
	@Autowired
	private GeminiService geminiServ;

	public GeminiController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping("/traerTexto")
	public ResponseEntity<ArrayList<GeminiDTO>> getText(@RequestParam String hash) {

		ArrayList<GeminiDTO> geminiList = new ArrayList<>();
		geminiList.add(geminiServ.getText(hash));

		if (geminiList.isEmpty()) {
			return new ResponseEntity<>(geminiList, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(geminiList, HttpStatus.ACCEPTED);
		}

	}

	@PostMapping("/crearjson")
	public ResponseEntity<String> crearConJson(@RequestBody GeminiDTO nuevo) {

		GeminiDTO newGemini = nuevo;
		int status = geminiServ.create(newGemini);

		if (status == 0) {
			return new ResponseEntity<>("Texto creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el Texto", HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@GetMapping("/showAll")
	public ResponseEntity<ArrayList<GeminiDTO>> showAll() {
		ArrayList<GeminiDTO> virus = geminiServ.findAll();
		if (virus.isEmpty()) {
			return new ResponseEntity<>(virus, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(virus, HttpStatus.ACCEPTED);
		}
	}

	public GeminiService getGeminiServ() {
		return geminiServ;
	}

	public void setGeminiServ(GeminiService geminiServ) {
		this.geminiServ = geminiServ;
	}

}
