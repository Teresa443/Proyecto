package com.example.demo;

import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.RepositorioProducto;
import com.example.demo.repositorio.RepositorioUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@SpringBootApplication
public class DemoApplication {

	private final RepositorioUsuario repositorioUsuario;
	private final RepositorioProducto repositorioProducto;
	private static final String IMAGE_BASE_PATH = "C:\\Users\\luiso\\Desktop\\MIS PROYECTOS\\proyecto\\imagenesProducto\\";

	@Autowired
	public DemoApplication(RepositorioUsuario repositorioUsuario, RepositorioProducto repositorioProducto) {
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioProducto = repositorioProducto;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			repositorioUsuario.save(new Usuario("admin@site.org", "admin", encryptPassword("admin"), "Cr 5 # 1-10", "302185465" ));

			// Agrega productos de ejemplo
			repositorioProducto.save(new Producto("Tasa Día de la mujer", "10000", "data:image/jpeg;base64," + encodeImage(IMAGE_BASE_PATH + "tasa1.png")));
			repositorioProducto.save(new Producto("Tasa Día de la Madre", "10000", "data:image/jpeg;base64," + encodeImage(IMAGE_BASE_PATH + "tasa2.png")));
			repositorioProducto.save(new Producto("Tasa cromada", "15000", "data:image/jpeg;base64," + encodeImage(IMAGE_BASE_PATH + "tasa3.png")));
			repositorioProducto.save(new Producto("Tasa con mensaje", "10000", "data:image/jpeg;base64," + encodeImage(IMAGE_BASE_PATH + "tasa4.png")));
			repositorioProducto.save(new Producto("Rompecabezas", "25000", "data:image/jpeg;base64," + encodeImage(IMAGE_BASE_PATH + "rompeCabezas.png")));
			repositorioProducto.save(new Producto("botilito", "20000", "data:image/jpeg;base64," + encodeImage(IMAGE_BASE_PATH + "botilito.png")));
		};
	}

	private String encryptPassword(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			return Base64.getEncoder().encodeToString(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error al encriptar la contraseña", e);
		}
	}

	private String encodeImage(String imagePath) {
		// Lee la imagen desde el sistema de archivos y la convierte en base64
		try {
			byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}


