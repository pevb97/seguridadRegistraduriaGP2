package tutorialMisionTIC.seguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import tutorialMisionTIC.seguridad.Modelos.Rol;

public interface RepositorioRol extends MongoRepository<Rol,String> {

}
