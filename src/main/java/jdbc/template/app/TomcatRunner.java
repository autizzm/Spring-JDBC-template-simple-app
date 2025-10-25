package jdbc.template.app;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class TomcatRunner {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);
        tomcat.setHostname("localhost");
        tomcat.setBaseDir("tomcat-base-dir");
        tomcat.getConnector();
        Host host = tomcat.getHost();

        Context context = tomcat.addWebapp(host, "/myapp", new File("src/main/webapp").getAbsolutePath());


        File classesDir = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(
                resources,
                "/WEB-INF/classes",
                classesDir.getAbsolutePath(),
                "/"
        ));
        context.setResources(resources);


        tomcat.start();

        tomcat.getServer().await();
    }
}
