package obss.project.finalproject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import obss.project.finalproject.model.Role;
import obss.project.finalproject.model.RoleType;
import obss.project.finalproject.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;

@SpringBootApplication
public class FinalProjectApplication {

    public static void main(String[] args) throws JsonProcessingException {
        /*HashSet<Role> h = new HashSet();
        Role r = new Role();
        r.setName(RoleType.ROLE_ADMIN);
        h.add(r);
        User u = new User(1l,"aasd","sdgfs","2343142343",h,null,null);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(u);

        System.out.println(json);*/

        SpringApplication.run(FinalProjectApplication.class, args);
    }

}
