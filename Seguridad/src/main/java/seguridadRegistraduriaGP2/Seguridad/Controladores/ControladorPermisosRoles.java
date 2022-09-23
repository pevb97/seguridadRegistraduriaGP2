package seguridadRegistraduriaGP2.Seguridad.Controladores;

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
import seguridadRegistraduriaGP2.Seguridad.Modelos.Permiso;
import seguridadRegistraduriaGP2.Seguridad.Modelos.PermisosRoles;
import seguridadRegistraduriaGP2.Seguridad.Modelos.Rol;
import seguridadRegistraduriaGP2.Seguridad.Repositorios.RepositorioPermiso;
import seguridadRegistraduriaGP2.Seguridad.Repositorios.RepositorioPermisosRoles;
import seguridadRegistraduriaGP2.Seguridad.Repositorios.RepositorioRol;

@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisosRoles {
  @Autowired
  private RepositorioPermisosRoles miRepositorioPermisoRoles;
  @Autowired
  private RepositorioPermiso miRepositorioPermiso;
  @Autowired
  private RepositorioRol miRepositorioRol;

  @GetMapping("")
  public List<PermisosRoles> index(){
    return this.miRepositorioPermisoRoles.findAll();
  }

  /**   Metodo crear   **/
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
  public PermisosRoles create(@PathVariable String id_rol,@PathVariable String id_permiso){
    PermisosRoles nuevo=new PermisosRoles();
    Rol elRol=this.miRepositorioRol.findById(id_rol).get();
    Permiso elPermiso=this.miRepositorioPermiso.findById(id_permiso).get();
    if (elRol!=null && elPermiso!=null){
      nuevo.setPermiso(elPermiso);
      nuevo.setRol(elRol);
      return this.miRepositorioPermisoRoles.save(nuevo);
    }else{
      return null;
    }
  }

  /**   Metodo mostrar   **/
  @GetMapping("{id}")
  public PermisosRoles show(@PathVariable String id){
    PermisosRoles permisosRolesActual=this.miRepositorioPermisoRoles
        .findById(id)
        .orElse(null);
    return permisosRolesActual;
  }

  /**   Metodo actualizar   **/
  @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
  public PermisosRoles update(@PathVariable String id,@PathVariable String id_rol,@PathVariable String id_permiso){
    PermisosRoles permisosRolesActual=this.miRepositorioPermisoRoles.findById(id).orElse(null);
    Rol elRol=this.miRepositorioRol.findById(id_rol).get();
    Permiso elPermiso=this.miRepositorioPermiso.findById(id_permiso).get();
    if(permisosRolesActual!=null && elPermiso!=null && elRol!=null){
      permisosRolesActual.setPermiso(elPermiso);
      permisosRolesActual.setRol(elRol);
      return
          this.miRepositorioPermisoRoles.save(permisosRolesActual);
    }else{
      return null;
    }
  }

  /**   Metodo eliminar   **/
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("{id}")
  public void delete(@PathVariable String id){
    PermisosRoles permisosRolesActual=this.miRepositorioPermisoRoles.findById(id).orElse(null);
    if (permisosRolesActual!=null){
      this.miRepositorioPermisoRoles.delete(permisosRolesActual);
    }
  }

  /** Metodo de validacion permisos **/
  @GetMapping("validar-permiso/rol/{id_rol}")
  public PermisosRoles getPermiso(@PathVariable String id_rol,@RequestBody Permiso infoPermiso){
    Permiso elPermiso=this.miRepositorioPermiso.getPermiso(infoPermiso.getUrl(),infoPermiso.getMetodo());
    Rol elRol=this.miRepositorioRol.findById(id_rol).get();
    if (elPermiso!=null && elRol!=null){
      return this.miRepositorioPermisoRoles.getPermisoRol(elRol.get_id(),elPermiso.get_id());
    }else{
      return null;
    }
  }

}
