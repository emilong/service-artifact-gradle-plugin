package rack;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.jruby.rack.RackFilter;
import org.jruby.rack.RackServletContextListener;

import java.util.EnumSet;
import javax.servlet.DispatcherType;

public class RackApplication extends Application<RackConfiguration> {
    public static void main(String[] args) throws Exception {
        new RackApplication().run(args);
    }

    @Override
    public String getName() {
        return "my-rack-app";
    }

    @Override
    public void initialize(Bootstrap<RackConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(RackConfiguration configuration, Environment environment) {
        // We probably won't use jersey in our jruby rack app...
        environment.jersey().disable();

        // The RackFilter actually serves the request
        environment.servlets().addFilter("RackFilter", RackFilter.class)
            .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                                      /*isMatchAfter=*/true, "/*");

        // Looks for the config.ru in the classpath
        environment.getApplicationContext()
            .setInitParameter("jruby.rack.layout_class", "ClassPathLayout");

        // Does shared setup on context initialization
        environment.getApplicationContext().addEventListener(new RackServletContextListener());
    }
}

