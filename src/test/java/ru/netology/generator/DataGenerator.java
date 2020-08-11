package ru.netology.generator;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    final DateTimeFormatter FORMATEDATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private Faker faker = new Faker(new Locale("ru"));

    public String dateGenerate(){
        final int random_num = (int) (Math.random() * 15);
        LocalDate date = LocalDate.now();
        LocalDate bookingDate = date.plusDays(3 + random_num);
        return FORMATEDATE.format(bookingDate);
    }

    public String nameGenerate(){
        return faker.name().lastName() + " " + faker.name().firstName() ;
    }
    public String wrongNameGenerate(){
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName() + " " + faker.name().firstName() ;
    }

    public String cityGenerate(){
        String[] cityList = new String[]{"Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Владикавказ", "Горно-Алтайск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Краснодар", "Магас", "Махачкала", "Нарьян-Мар", "Салехард", "Самара", "Саранск", "Саратов", "Хабаровск", "Ханты-Мансийск", "Южно-Сахалинск", "Великий Новгород", "Владивосток", "Владимир", "Вологда", "Рязань", "Биробиджан", "Чебоксары", "Москва", "Санкт-Петербург", "Ульяновск", "Симферополь", "Ростов-на-Дону"};
        int city =  (int) (Math.random() * cityList.length);
        return cityList[city];
    }

    public String phone(){
        return faker.phoneNumber().phoneNumber();
    }

    public String wrongCityGenerator(){
        Faker faker = new Faker(new Locale("en"));
        return faker.address().city();
    }

    public String todaysDate(){
       LocalDate date = LocalDate.now();
       return FORMATEDATE.format(date);
    }

}
