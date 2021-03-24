package repository;

import java.util.Map;

public class Queries {
    public static String CREATE_QUERY = "insert into %s (%s,%s,%s) values(?,?,?)";
    public static String CREATE_START = "insert into %s (";
    public static String CREATE_END = ") values()";
    public static String UPDATE_QUERY = "update %s set name = ?, phone = ?, email = ? where \"id\" = ?";
    public static String UPDATE_START = "update %s set ";
    public static String UPDATE_END = "where \"id\" = ?";
    public static String DELETE_QUERY = "delete from %s where \"id\" = ?";
    public static String GET_ALL_QUERY = "select * from %s";
    public static String GET_ONE_QUERY = "select * from %s where \"id\" = ?";

    public Queries(String entityName, Map<Boolean, String> fields) {
        String fieldType = "";
        for (Map.Entry<Boolean, String> field : fields.entrySet()) {
            if (field.getKey())
                fieldType = "%s, ";
            else fieldType = "%d ";
            CREATE_START = CREATE_START + fieldType;
            UPDATE_START = UPDATE_START + fieldType + " = ";
        }
    }
}