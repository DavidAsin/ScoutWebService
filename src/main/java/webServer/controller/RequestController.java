package webServer.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webServer.DataBase.HibernateUtil;
import webServer.base.Greeting;
import webServer.base.Usuario;

@RestController
public class RequestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }


    @RequestMapping("/login")
    public Boolean login(@RequestParam("email") String email, @RequestParam("pass") String pass) {
        boolean login = false;

        String stringQuery = "FROM Usuario WHERE email = :email";
        HibernateUtil hu = new HibernateUtil<Usuario>(Usuario.class);
        hu.openCurrentSession();
        Query query =  hu.getCurrentSession().createQuery(stringQuery);
        query.setString("email", email);
        Usuario user = (Usuario) query.uniqueResult();
        hu.closeCurrentSession();

        if ( user != null){
            if (user.getPass().equals(pass)){
                login = true;
            }
            System.out.println(user.getEmail());
            System.out.println(user.getPass());
        }


        System.out.println(login);

        return login;
    }
}
