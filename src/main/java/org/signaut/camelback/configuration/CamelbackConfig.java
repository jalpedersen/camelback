package org.signaut.camelback.configuration;

import org.codehaus.jackson.annotate.JsonAnySetter;

public class CamelbackConfig {

    public static class DeployerConfig {
        private String databaseUrl;
        private String username;
        private String password;
        private String filter;
        private String designDocument;

        public String getDatabaseUrl() {
            return databaseUrl;
        }

        public void setDatabaseUrl(String databaseUrl) {
            this.databaseUrl = databaseUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

        public String getDesignDocument() {
            return designDocument;
        }

        public void setDesignDocument(String designDocument) {
            this.designDocument = designDocument;
        }

    }

    public static class LoginConfig {
        private String authenticationUrl = "http://localhost:5984/_session";
        // Hazelcast configuration file
        private String clusterConfig = null;

        public String getAuthenticationUrl() {
            return authenticationUrl;
        }

        public void setAuthenticationUrl(String authenticationUrl) {
            this.authenticationUrl = authenticationUrl;
        }

        public String getClusterConfig() {
            return clusterConfig;
        }

        public void setClusterConfig(String clusterConfig) {
            this.clusterConfig = clusterConfig;
        }

    }

    public static class SessionManagerConfig {
        // Hazelcast configuration file
        private String sessionConfig = "/properties/cluster.xml";

        public String getSessionConfig() {
            return sessionConfig;
        }

        public void setSessionConfig(String sessionConfig) {
            this.sessionConfig = sessionConfig;
        }
    }

    public static class SslConfig {
        private String keystore;
        private String keystorePassword;

        public String getKeystore() {
            return keystore;
        }

        public void setKeystore(String keystore) {
            this.keystore = keystore;
        }

        public String getKeystorePassword() {
            return keystorePassword;
        }

        public void setKeystorePassword(String keystorePassword) {
            this.keystorePassword = keystorePassword;
        }

    }

    private String name = "Camelback";
    // Https port for admin part
    private int adminPort = 9009;
    // Http port
    private int port = 8080;
    // Https port
    private int securePort = 8443;

    private int threadPoolSize = 50;

    private String tempDirectory = "/tmp/";

    private SslConfig sslConfig;

    private String hazelcastConfig = "/properties/cluster.xml";

    private LoginConfig loginConfig = new LoginConfig();

    private SessionManagerConfig sessionManagerConfig = new SessionManagerConfig();

    private DeployerConfig deployerConfig = new DeployerConfig();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(int adminPort) {
        this.adminPort = adminPort;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSecurePort() {
        return securePort;
    }

    public void setSecurePort(int securePort) {
        this.securePort = securePort;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public String getTempDirectory() {
        return tempDirectory;
    }

    public void setTempDirectory(String tempDirectory) {
        this.tempDirectory = tempDirectory;
    }

    public SslConfig getSslConfig() {
        return sslConfig;
    }

    public void setSslConfig(SslConfig sslConfig) {
        this.sslConfig = sslConfig;
    }

    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public SessionManagerConfig getSessionManagerConfig() {
        return sessionManagerConfig;
    }

    public void setSessionManagerConfig(SessionManagerConfig sessionManagerConfig) {
        this.sessionManagerConfig = sessionManagerConfig;
    }

    public DeployerConfig getDeployerConfig() {
        return deployerConfig;
    }

    public void setDeployerConfig(DeployerConfig deployerConfig) {
        this.deployerConfig = deployerConfig;
    }

    public String getHazelcastConfig() {
        return hazelcastConfig;
    }

    public void setHazelcastConfig(String hazelcastConfig) {
        this.hazelcastConfig = hazelcastConfig;
    }

    @JsonAnySetter
    public void setOptional(String key, Object value) {
        // Ignore
    }
}
