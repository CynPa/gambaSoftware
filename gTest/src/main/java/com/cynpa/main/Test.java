package com.cynpa.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
@EnableAutoConfiguration
public class Test {
	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
      //  SpringApplication.run(Test.class, args);
    	ConfigurableApplicationContext context = SpringApplication.run(Test.class);
        UsuarioRepository repository = context.getBean(UsuarioRepository.class);

        // Guardar un conjunto de usuarios
        Usuario usuario=new Usuario();
        usuario.setApellido("Perez");
        usuario.setNombre("Juna");
        usuario.setEdad(25);
        repository.save(usuario);
      /*  repository.save(new Usuario("Maria", "Lozz", 15));
        repository.save(new Usuario("Lina", "Ruixs", 23));
        repository.save(new Usuario("Dania", "Mars", 20));
*/
        // findAll heredado de la interface CrudRepository
        Iterable<Usuario> todos = repository.findAll();
        System.out.println("Listar todos los Usuarios:");
        for (Usuario usr : todos) {
            System.out.println("\t" + usr);
        }
        System.out.println();

        // findByEdadLessThan devuelve todos los usuarios con edades menores a la indicada
        Iterable<Usuario> edades = repository.findByEdadLessThan(21);
        System.out.println("Usuarios con edad menor a 21");
        for (Usuario usr : edades) {
            System.out.println("\t" + usr);
        }
        System.out.println();

        context.close();

    }
}
