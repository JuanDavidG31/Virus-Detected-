package co.edu.unbosque.virusdetectedback.service;

import java.io.File;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.virusdetectedback.dto.VirusTotalDTO;
import co.edu.unbosque.virusdetectedback.model.VirusTotal;
import co.edu.unbosque.virusdetectedback.repository.VirusTotalRepository;

@Service
public class VirusTotalService {
	@Autowired
	private VirusTotalRepository virusRepo;
	@Autowired
	private ModelMapper modelMapper;

	private String url = "https://www.virustotal.com/api/v3/";

	public VirusTotalService() {
		// TODO Auto-generated constructor stub
	}

	public VirusTotalDTO uploadfile(File file) {

		String id = ExternalHTTPRequestHandler.toPostFileAndConvertToDTOVirus(url + "files",
				"2c45c2507d4362d2881e6780f5aeff2147a290f13141f3b522b6494f8b979588", file);
		VirusTotalDTO virus = ExternalHTTPRequestHandler.toGetAndConvertToDTOVirus(url + "analyses/" + id,
				"2c45c2507d4362d2881e6780f5aeff2147a290f13141f3b522b6494f8b979588");
		return virus;

	}

	public int create(VirusTotalDTO data) {
		VirusTotal entity = modelMapper.map(data, VirusTotal.class);
		try {
			virusRepo.save(entity);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public ArrayList<VirusTotalDTO> findAll() {
		ArrayList<VirusTotal> entityList = (ArrayList<VirusTotal>) virusRepo.findAll();
		ArrayList<VirusTotalDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {

			VirusTotalDTO dto = modelMapper.map(entity, VirusTotalDTO.class);
			dtoList.add(dto);

		});

		return dtoList;
	}

	public VirusTotalRepository getVirusRepo() {
		return virusRepo;
	}

	public void setVirusRepo(VirusTotalRepository virusRepo) {
		this.virusRepo = virusRepo;
	}

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
