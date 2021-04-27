import utils.BasicConnectionPool;
import utils.ConnectionPool;
import utils.DbProperties;

import java.sql.Connection;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties props = DbProperties.getProps();
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        ConnectionPool connectionPool = BasicConnectionPool.create(url, username, password);
        Connection connection = connectionPool.getConnection();


/*        UserDao userDao = new UserDao(connection);
        String userEmail = "st@mymail.ru";
        User requestingExistedUser = new User(userEmail);
        Optional<User> opUser = userDao.getOne(requestingExistedUser);
        if (opUser.isPresent())
            requestingExistedUser = userDao.getOne(requestingExistedUser).get();*/

        //если авторизован - то есть token
/*        Actor actor = new Actor(userEmail, UserRole.OWNER, null);
        if (!actor.isAuthed())
            actor = requestingExistedUser.auth("");

        String photo = "";
        String description = "";
        Publication publicationFromWeb = new Publication(actor, photo, description);
        Publication publication = new Publication();
        publication.create(publicationFromWeb);

        //confirmation stuff
        String code;
        if (publication.state == PublicationState.CONFIRM_WAIT)
            code = requestingExistedUser.startTwoStepRegistration(actor);
        //redirect with code
        //some code entered
        User updatedUser = new User();
        if (requestingExistedUser.finishTwoStepRegistration(updatedUser))
            publication.state = PublicationState.CONFIRMED;

        userDao.update(requestingExistedUser);*/


        connectionPool.releaseConnection(connection);
    }
}
/**
 * @startuml title Use case diagram
 * left to right direction
 * rectangle App {
 * usecase "Add Losty" as uc1
 * usecase "Add Foundy" as uc2
 * usecase "Manage current ad" as uc3
 * usecase "Get all ads" as uc4
 * usecase "Add response" as uc5
 * }
 * User --> uc1
 * User --> uc2
 * User --> uc3
 * User --> uc4
 * User --> uc5
 * @enduml
 */