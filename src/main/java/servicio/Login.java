
package servicio;

import com.yoprogramo.mavenproject1.Usuario;
import dao.UsuarioDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.awt.RenderingHints.Key;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class Login {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    
    public Response validar(Usuario usuario){
    
        boolean status=UsuarioDAO.validar(usuario.getUsername(), usuario.getPassword());
        
        if(status){
           
            Key key = (Key) Keys.secretKeyFor(SignatureAlgorithm.HS256);
            long tiempo=System.currentTimeMillis();
            String jwt=Jwts.builder().signWith((java.security.Key) key)
                    .setSubject("Enzo")
                    .setIssuedAt(new Date(tiempo))
                    .setExpiration(new Date(tiempo +900000))
                    .compact();
            JsonObject json=Json.createObjectBuilder().add("JWT", jwt).build();
            return Response.status(Response.Status.CREATED).entity(json).build();
            
        }
        
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
}
