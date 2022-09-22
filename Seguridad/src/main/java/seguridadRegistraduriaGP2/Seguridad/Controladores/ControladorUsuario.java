package seguridadRegistraduriaGP2.Seguridad.Controladores;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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

import seguridadRegistraduriaGP2.Seguridad.Modelos.Rol;
import seguridadRegistraduriaGP2.Seguridad.Modelos.Usuario;
import seguridadRegistraduriaGP2.Seguridad.Repositorios.RepositorioRol;
import seguridadRegistraduriaGP2.Seguridad.Repositorios.RepositorioUsuario;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {

  @Autowired
  private RepositorioUsuario miRepositorioUsuario;

  @Autowired
  private RepositorioRol miRepositorioRol;


  /**
   * Función de LISTAR a todos los usuarios
   */
  @GetMapping("")
  public List<Usuario> index() {
    return this.miRepositorioUsuario.findAll();
  }

  /**
   * Función de CREAR usuario
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Usuario create(@RequestBody Usuario infoUsuario) {
    infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
    return this.miRepositorioUsuario.save(infoUsuario);
  }

  /**
   * Función de MOSTRAR usuario con el id
   */
  @GetMapping("{id}")
  public Usuario show(@PathVariable String id) {
    Usuario usuarioActual = this.miRepositorioUsuario
        .findById(id)
        .orElse(null);
    return usuarioActual;
  }

  /**
   * Función de ACTUALIZAR usuario con el id
   */
  @PutMapping("{id}")
  public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario) {
    Usuario usuarioActual = this.miRepositorioUsuario
        .findById(id)
        .orElse(null);
    if (usuarioActual != null) {
      usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
      usuarioActual.setCorreo(infoUsuario.getCorreo());
      usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
      return this.miRepositorioUsuario.save(usuarioActual);
    } else {
      return null;
    }
  }

  /**
   * Función de ELIMINAR usuario
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("{id}")
  public void delete(@PathVariable String id) {
    Usuario usuarioActual = this.miRepositorioUsuario
        .findById(id)
        .orElse(null);
    if (usuarioActual != null) {
      this.miRepositorioUsuario.delete(usuarioActual);
    }
  }

  /**
   * Encriptacion
   */
  public String convertirSHA256(String password) {
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
    byte[] hash = md.digest(password.getBytes());
    StringBuffer sb = new StringBuffer();
    for (byte b : hash) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  @PutMapping("{id}/rol/{id_rol}")
  public Usuario asignarRolAUsuario(@PathVariable String id, @PathVariable
  String id_rol) {
    Usuario usuarioActual = this.miRepositorioUsuario.findById(id)
        .orElseThrow(RuntimeException::new);
    Rol rolActual = this.miRepositorioRol.findById(id_rol).orElseThrow(RuntimeException::new);
    usuarioActual.setRol(rolActual);
    return this.miRepositorioUsuario.save(usuarioActual);
  }

  /**
   * Metodo de validacion
   */
  @PostMapping("/validar")
  public Usuario validate(@RequestBody Usuario infoUsuario, final HttpServletResponse response)
      throws IOException {
    Usuario usuarioActual = this.miRepositorioUsuario.getUserByEmail(infoUsuario.getCorreo());
    if (usuarioActual != null && usuarioActual.getContrasena().equals(convertirSHA256(infoUsuario.getContrasena()))) {
      usuarioActual.setContrasena("");
      return usuarioActual;
    } else {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return null;
    }


  }

}
