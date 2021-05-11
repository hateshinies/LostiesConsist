package domain;

import utils.SqlCreator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEntity {
    private final Map<String, String> cargo = new HashMap<>();
    public SqlCreator queries;

    public SqlCreator getQueries() {
        return queries;
    }

    /**
     * Кладет в мапу cargo все поля и значения объекта
     */
    public Map<String, String> pack() throws IllegalAccessException {
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.get(this) == null)
                continue;
            cargo.put(field.getName(), String.valueOf(field.get(this)));
        }
        String entityName;
        char[] letters = clazz.getSimpleName().toCharArray();
        letters[0] = Character.toLowerCase(letters[0]);
        entityName = "\"" + new String(letters) + "\"";
        cargo.put("entityName", entityName);
        queries = new SqlCreator(cargo);
        return cargo;
    }

    /**
     * Возвращает экземпляр класса, построенного из мапы cargo
     */
    public Object unpack(Map<String, String> cargo) throws Exception {
//        Field[] fields = clazz.getDeclaredFields();
        Field[] fields = this.getClass().getDeclaredFields();
//        Field[] fields = this.getClass().getDeclaredFields();
        Object classInstance = this.getClass().getConstructor().newInstance();
        cargo.remove("version");
        cargo.remove("isDeleted");
        for (Map.Entry<String, String> entry : cargo.entrySet()) {
            for (Field field : fields) {
                String fieldName = field.getName();
                if (entry.getKey().equals(fieldName)) {
                    if (long.class.isAssignableFrom(field.getType())) {
                        long value = Long.parseLong(cargo.get(fieldName));
                        classInstance.getClass().getDeclaredField(fieldName)
                                .set(classInstance, value);
                        break;
                    }
                    if (int.class.isAssignableFrom(field.getType())) {
                        int value = Integer.parseInt(cargo.get(fieldName));
                        classInstance.getClass().getDeclaredField(fieldName)
                                .set(classInstance, value);
                        break;
                    }
                    if (String.class.isAssignableFrom(field.getType())) {
                        classInstance.getClass().getDeclaredField(fieldName)
                                .set(classInstance, cargo.get(fieldName));
                        break;
                    }
                    if (Enum.class.isAssignableFrom(field.getType())) {
                        classInstance.getClass().getDeclaredField(fieldName)
                                .set(classInstance, Enum.valueOf((Class<Enum>) field.getType(), cargo.get(fieldName)));
                        break;
                    }
                }
            }
        }
        return classInstance;
    }
}
