package co.edu.unbosque.virusdetectedback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.virusdetectedback.dto.UserDTO;
import co.edu.unbosque.virusdetectedback.model.User;
import co.edu.unbosque.virusdetectedback.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper modelMapper;

	public UserService() {
		// TODO Auto-generated constructor stub
	}

	public int create(UserDTO data) {
		User entity = modelMapper.map(data, User.class);
		try {
			userRepo.save(entity);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public ArrayList<UserDTO> findAll() {
		ArrayList<User> entityList = (ArrayList<User>) userRepo.findAll();
		ArrayList<UserDTO> dtoList = new ArrayList<>();

		entityList.forEach((entity) -> {

			UserDTO dto = modelMapper.map(entity, UserDTO.class);
			dtoList.add(dto);

		});

		return dtoList;
	}

	public int deleteById(Integer id) {
		Optional<User> found = userRepo.findById(id);
		if (found.isPresent()) {

			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public int deleteByUser(String user) {
		List<User> found = userRepo.findByUser(user);

		if (!found.isEmpty()) {
			userRepo.deleteAll(found);
			return 0;
		} else {
			return 1;
		}
	}

	public int update(UserDTO data) {
		Optional<User> existingUser = userRepo.findByGmail(data.getGmail());

		if (existingUser.isPresent()) {
			User entity = existingUser.get();

			if (data.getName() != null && !data.getName().isEmpty()) {
				entity.setName(data.getName());
			}

			if (data.getPassword() != null && !data.getPassword().isEmpty()) {
				entity.setPassword(data.getPassword());
			}
			if (data.getUser() != null && !data.getUser().isEmpty()) {
				entity.setUser(data.getUser());
			}
			if (data.getGmail() != null && !data.getGmail().isEmpty()) {
				entity.setGmail(data.getGmail());
			}

			try {
				userRepo.save(entity);
				return 0;
			} catch (Exception e) {
				return 1;
			}
		} else {
			return 1;
		}
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}
