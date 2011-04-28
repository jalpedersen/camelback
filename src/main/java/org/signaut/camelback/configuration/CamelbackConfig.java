/*
Copyright (c) 2010, Jesper André Lyngesen Pedersen
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 - Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.signaut.camelback.configuration;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.signaut.jetty.deploy.providers.couchdb.CouchDbDeployerProperties;

public class CamelbackConfig {
    
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
        private String keyManagerPassword;
        private String truststore;
        private String truststorePassword;

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

        public String getTruststore() {
            return truststore!=null?truststore:keystore;
        }

        public void setTruststore(String truststore) {
            this.truststore = truststore;
        }

        public String getTruststorePassword() {
            return truststorePassword!=null?truststorePassword:keystorePassword;
        }

        public void setTruststorePassword(String truststorePassword) {
            this.truststorePassword = truststorePassword;
        }

        public String getKeyManagerPassword() {
            return keyManagerPassword!=null?keyManagerPassword:keystorePassword;
        }

        public void setKeyManagerPassword(String keyManagerPassword) {
            this.keyManagerPassword = keyManagerPassword;
        }
    }

    private String name = "Camelback";
    // Https port for admin part
    private int adminPort = 9009;
    // Http port
    private int port = 8080;
    // Https port
    private int securePort = 8443;

    private int threadPoolSize = 8;

    private String tempDirectory = "/tmp/";

    private SslConfig sslConfig;

    private String hazelcastConfig = "/properties/cluster.xml";

    private LoginConfig loginConfig = new LoginConfig();

    private SessionManagerConfig sessionManagerConfig = new SessionManagerConfig();

    private CouchDbDeployerProperties deployerConfig = new CouchDbDeployerProperties();

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

    public CouchDbDeployerProperties getDeployerConfig() {
        return deployerConfig;
    }

    public void setDeployerConfig(CouchDbDeployerProperties deployerConfig) {
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
