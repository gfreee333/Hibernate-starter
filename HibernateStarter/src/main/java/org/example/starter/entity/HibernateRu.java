package org.example.starter.entity;

import lombok.extern.slf4j.Slf4j;
import org.example.starter.entity.enumerat.Role;
import org.example.starter.entity.parametrs.PersonalInfo;
import org.example.starter.entity.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import java.time.LocalDate;

@Slf4j
public class HibernateRu {
    public static void main(String[] args){
        // Configuration configuration = new Configuration();
        // configuration.configure();
        // configuration.addAttributeConverter(new ConvertorBirthday(), true);
        // configuration.addAnnotatedClass(Users.class);
        Company company = Company.builder()
                .name("Googles")
                .build();
             //TRANSIENT
             Users user = Users.builder()
                     .username("Samara56@gmail.com")
                     .personalInfo(PersonalInfo.builder()
                     .firstname("Vano")
                     .lastname("Chuhmanov")
                     .birthDate(new Birthday(LocalDate.of(2002,6,21)))
                             .build())
                     .role(Role.ADMIN)
                     .company(company)
                     .build();
            //session.save(user);
            //session.saveOrUpdate(user);
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                //PERSISTENT к session и TRANSIENT к session2
                //session.saveOrUpdate(user);
                //session.save(company);
                session.save(user);
                //log.info("Info: {}", user);
                session.getTransaction().commit();
            }
            try(Session session2 = sessionFactory.openSession()) {
                session2.getTransaction();
                //Сначала GET потом DELETE: PERSISTENT к session2 DETACHED к session
                //session2.delete(user);
                //REMOVED к session2
                session2.getTransaction().commit();
            }
        }
        catch (SessionException e){
            e.printStackTrace();
        }
    }
}
