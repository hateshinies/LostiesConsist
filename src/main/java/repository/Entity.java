package repository;

public class Entity {
    public String CREATE_QUERY;
    public String UPDATE_QUERY;
    public String DELETE_QUERY;
    public String GET_ONE_QUERY;
    public String GET_ALL_QUERY;

    public Entity() {
    }

    public void prepareQueries(String entityName, String[] fields) {
        String preparedCreateQuery;
        String preparedUpdateQuery;
        String preparedDeleteQuery = "delete from %s where \"id\" = ?";
        String preparedGetAllQuery = "select * from %s";
        String preparedGetOneQuery = "select * from %s where \"id\" = ?";

        String createStartQuery = "insert into %s (";
        String createMiddleQuery = ") values(";
        String createEndQuery = ")";
        String updateStart = "update %s set ";
        String updateEnd = "where \"id\" = ?";

        for (String field : fields) {
            updateStart += field + " = ?,";
            createStartQuery += field + ",";
            createMiddleQuery += "?,";
        }

        createStartQuery = createStartQuery.substring(0, createStartQuery.length() - 1);
        createMiddleQuery = createMiddleQuery.substring(0, createMiddleQuery.length() - 1);
        updateStart = updateStart.substring(0, updateStart.length() - 1) + " ";
        preparedCreateQuery = createStartQuery + createMiddleQuery + createEndQuery;
        preparedUpdateQuery = updateStart + updateEnd;
        CREATE_QUERY = String.format(preparedCreateQuery, entityName);
        UPDATE_QUERY = String.format(preparedUpdateQuery, entityName);
        DELETE_QUERY = String.format(preparedDeleteQuery, entityName);
        GET_ONE_QUERY = String.format(preparedGetOneQuery, entityName);
        GET_ALL_QUERY = String.format(preparedGetAllQuery, entityName);
    }
}
