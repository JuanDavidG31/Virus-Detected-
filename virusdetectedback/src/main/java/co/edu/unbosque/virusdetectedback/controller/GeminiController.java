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
	public ResponseEntity<ArrayList<GeminiDTO>> getText(@RequestParam String hash, @RequestParam String apikey) {
		GeminiDTO gemini = new GeminiDTO();
		gemini = geminiServ.getText(hash, apikey);
		ArrayList<GeminiDTO> geminiList = new ArrayList<>();
		geminiList.add(gemini);
		boolean acepted = true;

		String name = null;
		ArrayList<GeminiDTO> tGemini = geminiServ.findAll();

		main: for (GeminiDTO tG : tGemini) {
			name = tG.getName();
			for (GeminiDTO g : geminiList) {

				if (g.getName().equals(name)) {
					acepted = false;
					break main;
				}
			}
			name = null;
		}

		if (acepted) {
			geminiServ.create(gemini);

		}

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
		ArrayList<GeminiDTO> gemini = geminiServ.findAll();
		if (gemini.isEmpty()) {
			return new ResponseEntity<>(gemini, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(gemini, HttpStatus.ACCEPTED);
		}
	}

	public GeminiService getGeminiServ() {
		return geminiServ;
	}

	public void setGeminiServ(GeminiService geminiServ) {
		this.geminiServ = geminiServ;
	}

}
