package utils;

import java.util.Map;

/**
 * Класс, который создает каркас базовых sql-запросов, на основе имени таблицы и полей.
 * Его паблик поля *Query - это по сути PreparedStatement, куда остается подставить конкретные значения.
 */
public class SqlCreator {
    private static String entityName;

    public String createQuery;
    public String updateQuery;
    public String deleteQuery;
    public String softDeleteQuery;
    public String getOneQuery;
    public String getAllQuery;
    public String findBySpecification;
    public String getLock;

    public SqlCreator(Map<String, String> map) {
        prepareQueries(map);
    }

    private void prepareQueries(Map<String, String> map) {
        entityName = map.get("entityName");
        map.remove("entityName");

        createQuery = prepareCreateQuery(map);
        updateQuery = prepareUpdateQuery(map);
        deleteQuery = prepareDeleteQuery(map);
        softDeleteQuery = prepareSoftDeleteQuery(map);
        getOneQuery = prepareGetOneQuery(map);
        getAllQuery = prepareGetAllQuery();
        getLock = prepareLock(map);
    }

    private String prepareCreateQuery(Map<String, String> map) {
        String startQuery = "insert into %s (";
        String middleQuery = ") values (";
        String endQuery = ")";

        for (Map.Entry<String, String> entry : map.entrySet()) {
            //тут ID еще не должно быть в мапе
            if (entry.getKey().equals("id"))
                continue;
            startQuery = startQuery + "\"" + entry.getKey() + "\",";
            middleQuery = middleQuery + "'" + entry.getValue() + "',";
        }

        String preparedQuery = startQuery.replaceAll(".$", "") +
                middleQuery.replaceAll(".$", "") +
                endQuery;

        return String.format(preparedQuery, entityName);
    }

    private String prepareUpdateQuery(Map<String, String> map) {
        String startQuery = "update %s set ";
        String endQuery = " where \"id\" = ";

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().contains("id"))
                endQuery += entry.getValue();
            else if (entry.getKey().contains("email") && !map.containsKey("id"))
                endQuery = endQuery.replace("\"id\"", "\"email\"") + "'" + entry.getValue() + "'";
            else
                startQuery = startQuery + "\"" + entry.getKey() + "\" = '" + entry.getValue() + "',";
        }
        String preparedQuery = startQuery.replaceAll(".$", "") + endQuery;
        return String.format(preparedQuery, entityName);

    }

    private String prepareDeleteQuery(Map<String, String> map) {
        String query = "delete from %s where \"id\" = ";
        if (map.containsKey("id"))
            query = query + "'" + map.get("id") + "'";
        else if (map.containsKey("email"))
            query = query.replace("\"id\"", "\"email\"") + "'" + map.get("email") + "'";
        else
//            throw new IllegalStateException("pk not found");
            return null;
        return String.format(query, entityName);
    }

    private String prepareGetOneQuery(Map<String, String> map) {
        String query = "select * from %s where \"id\" = ";
        if (map.containsKey("id"))
            query += map.get("id");
        else if (map.containsKey("email"))
            query = query.replace("\"id\"", "\"email\"") + "'" + map.get("email") + "'";
        else
//            throw new IllegalStateException("pk not found");
            return null;
        return String.format(query, entityName);
    }

    private String prepareSoftDeleteQuery(Map<String, String> map) {
        String query = "update %s set isDeleted = true where \"id\" = ";
        if (map.containsKey("id"))
            query += map.get("id");
        else if (map.containsKey("email"))
            query = query.replace("\"id\"", "\"email\"") + "'" + map.get("email") + "'";
        else
//            throw new IllegalStateException("pk not found");
            return null;
        return String.format(query, entityName);
    }

    private String prepareGetAllQuery() {
        String getAllQuery = "select * from %s";
        return String.format(getAllQuery, entityName);
    }

    public void findBy(String entity, String expression) {
        String startQuery = "select * from ";
        String middleQuery = " where ";
        findBySpecification = startQuery + entity + middleQuery + expression;
    }

    private String prepareLock(Map<String, String> map) {
        String query = "select \"value\" from %s where \"id\" = ";
        return String.format(query, map.get("entity")) + map.get("id");
    }
}
