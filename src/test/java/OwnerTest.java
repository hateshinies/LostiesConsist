import org.junit.Test;

public class OwnerTest {
    @Test
    public void simpleQuery(){
        String SQL_QUERY = "select * from owners";
        DataSource dataSource = new DataSource();
        dataSource.simpleQuery(SQL_QUERY);
        //как проверить результат???
    }
}
