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
package org.signaut.camelback.start;

import javax.servlet.ServletContext;

import org.eclipse.jetty.deploy.DeploymentManager;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.Authenticator.AuthConfiguration;
import org.eclipse.jetty.security.Authenticator.Factory;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.signaut.camelback.configuration.CamelbackConfig;
import org.signaut.common.hazelcast.HazelcastFactory;
import org.signaut.couchdb.impl.CouchDbAuthenticatorImpl;
import org.signaut.jetty.deploy.providers.couchdb.CouchDbAppProvider;
import org.signaut.jetty.deploy.providers.couchdb.CouchDbAppProvider.SessionManagerProvider;
import org.signaut.jetty.server.security.CouchDbLoginService;
import org.signaut.jetty.server.security.authentication.CouchDbSSOAuthenticator;
import org.signaut.jetty.server.session.ClusterSessionIdManager;
import org.signaut.jetty.server.session.ClusterSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

class JettyInstance {
    private final CamelbackConfig config;
    private final Server server = new Server();;
    private final HazelcastFactory hazelcastFactory = new HazelcastFactory();;
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public JettyInstance(CamelbackConfig config) {
        this.config = config;
    }

    public void start() {
        final long startingTime = System.currentTimeMillis();
        setConnectors(server);

        final HandlerCollection handlers = new HandlerCollection();
        final ContextHandlerCollection contextHandlers = new ContextHandlerCollection();
        final Handler defaultHandler = new DefaultHandler();
        handlers.setHandlers(new Handler[] { contextHandlers, defaultHandler });
        server.setHandler(handlers);
        
        final HazelcastInstance hazelcastInstance = hazelcastFactory.loadHazelcastInstance(config.getHazelcastConfig(), getClass()); 
        
        // Authentication
        final CouchDbLoginService couchDbLoginService = new CouchDbLoginService("couchdb_realm",
                new CouchDbAuthenticatorImpl(config.getLoginConfig().getAuthenticationUrl()),
                hazelcastInstance);
                
        server.addBean(couchDbLoginService);
        server.addBean(authenticatorFactory);
        
        // Session manager
        final ClusterSessionIdManager clusterSessionIdManager = 
            new ClusterSessionIdManager(null,
                                        hazelcastInstance);
        server.setSessionIdManager(clusterSessionIdManager);
        final SessionManagerProvider sessionManagerProvider = new SessionManagerProvider() {
            @Override
            public SessionManager get() {
                return new ClusterSessionManager(clusterSessionIdManager);
            }
        };

        // Deployment handling
        final DeploymentManager deploymentManager = new DeploymentManager();
        deploymentManager.setContexts(contextHandlers);
        server.addBean(deploymentManager);
        deploymentManager.addAppProvider(new CouchDbAppProvider(config.getDeployerConfig(), 
                                                                authenticatorFactory, 
                                                                sessionManagerProvider));
        

        server.setStopAtShutdown(true);
        server.setGracefulShutdown(5000);
        // Now start server
        try {
            server.start();
            log.info(String.format("Started camelback in %d milliseconds", System.currentTimeMillis()-startingTime));
        } catch (Exception e) {
            throw new IllegalStateException("While starting jetty", e);
        }

    }

    private final Authenticator.Factory authenticatorFactory = new Factory() {

        @Override
        public Authenticator getAuthenticator(Server server, ServletContext context, AuthConfiguration configuration,
                IdentityService identityService, LoginService loginService) {
            return new CouchDbSSOAuthenticator(new CouchDbAuthenticatorImpl(config.getLoginConfig()
                    .getAuthenticationUrl()));
        }
    };

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new IllegalStateException("While stopping server", e);
        }
    }

    private void setConnectors(Server server) {
        server.addConnector(new SelectChannelConnector() {
            {
                setPort(config.getPort());
                setName("http");
                setConfidentialPort(config.getSecurePort());
                setThreadPool(new QueuedThreadPool(config.getThreadPoolSize()));
            }
        });

        if (config.getSslConfig() != null) {
            server.addConnector(new SslSelectChannelConnector() {
                {
                    setPort(config.getSecurePort());
                    setName("https");
                    setConfidentialPort(config.getSecurePort());
                    setKeystore(config.getSslConfig().getKeystore());
                    setKeyPassword(config.getSslConfig().getKeystorePassword());
                    setThreadPool(new QueuedThreadPool(config.getThreadPoolSize()));
                }
            });
        }

    }

}
