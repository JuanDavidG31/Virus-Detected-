package co.edu.unbosque.virusdetectedback.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.virusdetectedback.dto.UserDTO;
import co.edu.unbosque.virusdetectedback.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
public class UserController {
	@Autowired
	private UserService userServ;

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping("/crearjson")
	public ResponseEntity<String> crearConJson(@RequestBody UserDTO nuevo) {

		UserDTO newUser = nuevo;
		int status = userServ.create(newUser);

		if (status == 0) {
			return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el Usuario", HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@GetMapping("/showAll")
	public ResponseEntity<ArrayList<UserDTO>> showAll() {
		ArrayList<UserDTO> users = userServ.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
		}
	}

	@DeleteMapping("/eliminarId/{id}")

	public ResponseEntity<String> deleteById(@PathVariable Integer id) {
		int status = userServ.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado con exito", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error al eliminar el usuario", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/eliminar/user/{user}")
	public ResponseEntity<String> eliminarPorUser(@PathVariable String user) {
		int estado = userServ.deleteByUser(user);
		return estado == 0 ? new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK)
				: new ResponseEntity<>("No encontrado", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/actualizarjson")
	public ResponseEntity<String> actualizar(@RequestBody UserDTO nuevo) {

		UserDTO userUpdate = nuevo;

		int estado = userServ.update(userUpdate);
		if (estado == 0) {
			return new ResponseEntity<>("Usuario actualizado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al actualizar el usuario", HttpStatus.NOT_FOUND);
		}
	}

	public UserService getUserServ() {
		return userServ;
	}

	public void setUserServ(UserService userServ) {
		this.userServ = userServ;
	}

}
