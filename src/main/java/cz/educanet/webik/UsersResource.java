package cz.educanet.webik;
import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    private static final List<User> names = new ArrayList<>();
    private static final Gson gson = new Gson();

    private Integer findUser(String username){
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getUsername().equals(username)){
                return i;
            }
        } return -1;
    }
    /*
    private boolean userExists(String username) {
        return findUser(username) != -1;
    }*/

    @GET
    public Response getAll() {
        return Response.ok(gson.toJson(names)).build();
    }

    @POST
    public Response createUser(@QueryParam("username") String user) {
            if (user == null) {
                return Response.status(400, "No user sent").build();
            }
            /*
            if (!userExists(user)) {
                names.add(new User(user));
                return Response.ok("User created").build();
            } else
                return Response.status(400, "User already exists").build();
            */
        if (findUser(user) == -1) {
            names.add(new User(user));
            return Response.ok("User created").build();
        } else
            return Response.status(400, "User already exists").build();
    }

    @DELETE //delete user
    public Response deleteUser(@QueryParam("username") String user) {
        int index = findUser(user);
        if (index != -1) {
            names.remove(index);
            return Response.ok("User deleted").build();
        } else {
            return  Response.notModified("User doesn't exist").build();
        }
    }

    @Path("/{username}" )
    @PUT
    public Response editUser(@QueryParam("username") String userNew, @PathParam("username") String userOld) {
        int index = findUser(userOld);
        if (index != -1) {
            names.get(index).setUsername(userNew);
            return Response.ok("User Edited to " + userNew).build();
        } else {
            return  Response.notModified("User doesn't exist").build();
        }
    }



}
