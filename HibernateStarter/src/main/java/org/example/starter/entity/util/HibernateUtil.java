package org.example.starter.entity.util;

import org.example.starter.entity.Convert.ConvertorBirthday;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration().configure();
        configuration.configure();
        configuration.addAttributeConverter(new ConvertorBirthday());
        return configuration.buildSessionFactory();
    }
}
