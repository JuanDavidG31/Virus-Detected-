package co.edu.unbosque.virusdetectedback.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import co.edu.unbosque.virusdetectedback.dto.VirusTotalDTO;
import co.edu.unbosque.virusdetectedback.service.VirusTotalService;

@RestController
@RequestMapping("/virus")
@CrossOrigin(origins = { "*" })
public class VirusTotalController {
	@Autowired
	private VirusTotalService virusServ;

	public VirusTotalController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping(value = "/showVirus", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ArrayList<VirusTotalDTO>> showVirus(
			@Parameter(description = "Archivo a analizar", required = true, content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(type = "string", format = "binary"))) @RequestParam("file") MultipartFile multipartFile, @RequestParam String apikey) {

		ArrayList<VirusTotalDTO> virus = new ArrayList<>();

		try {
			File file = File.createTempFile("upload-", multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			VirusTotalDTO tVirus = virusServ.uploadfile(file,apikey);
			virus.add(tVirus);
			virusServ.create(tVirus);

			file.delete();

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (virus.isEmpty()) {
			return new ResponseEntity<>(virus, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(virus, HttpStatus.ACCEPTED);
		}
	}

	@PostMapping("/crearjson")
	public ResponseEntity<String> crearConJson(@RequestBody VirusTotalDTO nuevo) {

		VirusTotalDTO newVirus = nuevo;
		int status = virusServ.create(newVirus);

		if (status == 0) {
			return new ResponseEntity<>("Virus creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el Virus", HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@GetMapping("/showAll")
	public ResponseEntity<ArrayList<VirusTotalDTO>> showAll() {
		ArrayList<VirusTotalDTO> virus = virusServ.findAll();
		if (virus.isEmpty()) {
			return new ResponseEntity<>(virus, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(virus, HttpStatus.ACCEPTED);
		}
	}

	public VirusTotalService getVirusServ() {
		return virusServ;
	}

	public void setVirusServ(VirusTotalService virusServ) {
		this.virusServ = virusServ;
	}

}
