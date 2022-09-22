package seguridadRegistraduriaGP2.Seguridad.Modelos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class PermisosRoles {

  @Id
  private String _id;
  @DBRef
  private Rol rol;
  @DBRef
  private Permiso permiso;

  public PermisosRoles() {
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  public Permiso getPermiso() {
    return permiso;
  }

  public void setPermiso(Permiso permiso) {
    this.permiso = permiso;
  }

}
