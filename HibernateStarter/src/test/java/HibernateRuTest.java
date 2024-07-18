import lombok.Cleanup;
import lombok.val;
import org.checkerframework.checker.units.qual.C;
import org.example.starter.entity.Birthday;
import org.example.starter.entity.Company;
import org.example.starter.entity.Users;
import org.example.starter.entity.enumerat.Role;
import org.example.starter.entity.parametrs.PersonalInfo;
import org.example.starter.entity.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class HibernateRuTest {
    @Test
    public void testAddNewUserCompany(){
        var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = Company.builder()
                .name("Googlesss")
                .build();
        //TRANSIENT
        Users user = Users.builder()
                .username("Samara526@gmail.com")
                .build();
        company.addUsers(user);
        session.save(company);
        session.getTransaction().commit();
    }
    @Test
    public void testOneToMany(){
        var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession();
        session.beginTransaction();
        var company = session.get(Company.class,2);
        System.out.print(company.getUsers());
        session.getTransaction().commit();
    }
    @Test
    public void testHibernateApi() throws SQLException, IllegalAccessException {
       Users user = Users.builder()
                .username("Samara63@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Chuhmanov")
                        .birthDate(new Birthday(LocalDate.of(2002,6,21)))
                        .build())
                .build();
       // Получение значений через рефлексию
       // 1) %s какая таблица 2) %s какие поля 3) %s какие значения.
       var sql = """
               insert into 
               %s
               (%s) 
               values 
               (%s)
               """;
       // 1 этап: получаем анатации для проверки нужной нам таблицы, получаем table name, schema парамеьоы
        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(table -> table.schema() + "." + table.name())
                .orElse(user.getClass().getName());
        // 2 этап: получаем, нужные нам поля для данного запроса
        Field[] fields = user.getClass().getDeclaredFields();
        // 3 этап: поиск анатаций для наших полей таблицы
        var columName = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name).orElse(field.getName()))
                .collect(Collectors.joining(", "));
        // 4 этап: поиск значений values, которые пойду в BD
        var columValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        Connection connection = null;
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres" ,
                    "postgres" ,"11");

            PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName,columName,columValues));

        for(int i = 0; i < fields.length; i++){
            fields[i].setAccessible(true); // позволяет получать нам доступ к private полям
            preparedStatement.setObject(i+1,fields[i].get(user)); // мы получаем значение полей и устанавили их.
        }
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}
