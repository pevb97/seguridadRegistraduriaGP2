package tutorialMisionTIC.seguridad.Controladores;

import tutorialMisionTIC.seguridad.Modelos.Rol;
import tutorialMisionTIC.seguridad.Repositorios.RepositorioRol;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/roles")
public class ControladorRol {

  @Autowired
  private RepositorioRol miRepositorioRol;


  @GetMapping("")
  public List<Rol> index(){
    return this.miRepositorioRol.findAll();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Rol create(@RequestBody Rol infoRol){
    return this.miRepositorioRol.save(infoRol);
  }

  @GetMapping("{id}")
  public Rol show(@PathVariable String id){
    Rol rolActual=this.miRepositorioRol
        .findById(id)
        .orElse(null);
    return rolActual;
  }

  @PutMapping("{id}")
  public Rol update(@PathVariable String id,@RequestBody  Rol infoRol){
    Rol rolActual=this.miRepositorioRol
        .findById(id)
        .orElse(null);
    if (rolActual!=null){
      rolActual.setNombre(infoRol.getNombre());
      return this.miRepositorioRol.save(rolActual);
    }else{
      return  null;
    }
  }
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("{id}")
  public void delete(@PathVariable String id){
    Rol rolActual=this.miRepositorioRol
        .findById(id)
        .orElse(null);
    if (rolActual!=null){
      this.miRepositorioRol.delete(rolActual);
    }
  }

}
