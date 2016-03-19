package utilities.configuration;

import java.util.Properties;

public class DatasourceConfiguration extends Properties {
    public DatasourceConfiguration() {
        String username = System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_POSTGRESQL_DB_PASSWORD");
        String host = System.getenv("OPENSHIFT_POSTGRESQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_POSTGRESQL_DB_PORT");

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        
        if (host == null) {
            host = "";
        }

        if (port == null) {
            port = "";
        }

        super.put("user", username);
        super.put("password", password);
        super.put("serverName", host);
        super.put("portNumber", port);
    }
}
