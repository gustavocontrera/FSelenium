package varios2.clase104;

import com.github.javafaker.Faker;

public class Constants {

    public static final String PASSWORD = "helloworld";
    public static final String BIRTH_DAY = "3";
    public static final String BIRTH_MONTH = "10";
    public static final String BIRTH_YEAR = "1990";
    public static final String CITY = "NEW YORK";

    private static Faker FAKER = new Faker();
    public static String FAKER_FIRST_NAME = FAKER.name().firstName();
    public static String FAKER_LAST_NAME = FAKER.name().lastName();

    public static String EMAIL_ADDRESS = "seleniumintermedio" + Math.random() + "test@gmail.com";

}
