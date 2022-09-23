package seguridadRegistraduriaGP2.Seguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import seguridadRegistraduriaGP2.Seguridad.Modelos.Rol;

public interface RepositorioRol extends MongoRepository<Rol,String> {

}
