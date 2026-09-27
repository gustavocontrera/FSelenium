package varios2.clase14.fakers;

import com.github.javafaker.Faker;

public class Fakers {

    //https://github.com/DiUS/java-faker
    //http://dius.github.io/java-faker/apidocs/index.html

    public static void main (String args[]){

        Faker faker = new Faker();
        System.out.println(faker.job().title());
        System.out.println(faker.job().seniority());
        System.out.println(faker.job().keySkills());
        System.out.println(faker.job().position());
        System.out.println(faker.name().prefix());
        System.out.println(faker.name().suffix());
        System.out.println(faker.name().lastName());
        System.out.println(faker.internet().emailAddress());
        System.out.println(faker.address().fullAddress());

        System.out.println();

        System.out.println(faker.lorem().sentence(20));
    }
}
