package co.edu.unbosque.virusdetectedback.service;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.virusdetectedback.dto.GeminiDTO;
import co.edu.unbosque.virusdetectedback.dto.VirusTotalDTO;
import co.edu.unbosque.virusdetectedback.model.Gemini;
import co.edu.unbosque.virusdetectedback.model.VirusTotal;
import co.edu.unbosque.virusdetectedback.repository.GeminiRespository;

@Service
public class GeminiService {

	@Autowired
	private GeminiRespository geminiRepo;

	@Autowired
	private ModelMapper modelMapper;

	private final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyBOVU2UUEkshLPd7r0pf6Gn1p8a-vt9mn8";

	public GeminiDTO getText(String hash) {

		String prompt = """
				{
				  "contents": [
				    {
				      "parts": [
				        {
				          "text": "Te voy a dar un hash SHA256 que corresponde a un archivo. Quiero que me digas el nombre del posible malware o virus informático asociado a ese hash (si es que es conocido públicamente), y luego proporciona una breve descripción de cómo funciona o qué hace ese virus. Sé específico y claro en la descripción, sin exceder un párrafo.\\n\\nHash: %s"
				        }
				      ]
				    }
				  ]
				}
				"""
				.formatted(hash);

		return ExternalHTTPRequestHandler.postAndConvertToDTOGemini(URL, prompt);
	}

	public int create(GeminiDTO data) {
		Gemini entity = modelMapper.map(data, Gemini.class);
		try {
			geminiRepo.save(entity);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public ArrayList<GeminiDTO> findAll() {
		ArrayList<Gemini> entityList = (ArrayList<Gemini>) geminiRepo.findAll();
		ArrayList<GeminiDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {

			GeminiDTO dto = modelMapper.map(entity, GeminiDTO.class);
			dtoList.add(dto);

		});

		return dtoList;
	}

	public GeminiService() {
		// TODO Auto-generated constructor stub
	}

	public GeminiRespository getGeminiRepo() {
		return geminiRepo;
	}

	public void setGeminiRepo(GeminiRespository geminiRepo) {
		this.geminiRepo = geminiRepo;
	}

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public String getURL() {
		return URL;
	}

}
