package pl.edu.pg.eti.kask.component;

import pl.edu.pg.eti.kask.user.mapper.UserToResponseFunction;
import pl.edu.pg.eti.kask.user.mapper.UsersToResponseFunction;

public class DtoMapperFactory {

    public UserToResponseFunction userToResponse(){return new UserToResponseFunction();}

    public UsersToResponseFunction usersToResponse(){return new UsersToResponseFunction();}
}
