package org.example.jsfclient;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBServiceLocator {

    private static Context context;

    static {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080"); // URL du serveur WildFly

            context = new InitialContext(props);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static <T> T lookup(String jndiName, Class<T> beanClass) throws NamingException {
        return (T) context.lookup(jndiName);
    }
}
