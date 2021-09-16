import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, String> users;

    public Database() {
        users = new HashMap<>();
        users.put("phamjason", "123");
        users.put("jasonpham", "234");
    }

    public Map<String, String> getUsers() {
        return users;
    }
}
