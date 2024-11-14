package es.deusto.sd.strava;

import java.util.ArrayList;
import java.util.HashMap;

public class UsuarioDTO {
	//Atributos
		int id;
		String token;
		String username;
		String email;
		String nombre;
		float peso;
		float altura;
		double fNacimiento;
		float fecCMax;
		float fecCReposo;
		ArrayList<Entrenamiento> entrenamientos;
		HashMap<Reto, String> retos;
	
}
