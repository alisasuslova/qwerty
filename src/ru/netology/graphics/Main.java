package ru.netology.graphics;

import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextColorSchemaImpl;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterImpl;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TextGraphicsConverterImpl(); // Создайте тут объект вашего класса конвертера
        TextGraphicsConverterImpl converterImpl = new TextGraphicsConverterImpl();
        TextColorSchema schema = new TextColorSchemaImpl();

        Scanner scanner = new Scanner(System.in);
        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с сохранением в файл:
        String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);



        //Запрашиваем допустимые значения высоты, ширины и соотношения пропускаем.
        //спрашиваем проверить или нет(если пропустили, то не спрашиваем). Если да, проверка, нет - converterImpl.convert();
        //если проверка не пройдена - ошибка, все ок - converterImpl.convert();
        // ошибка если картинка превышает соотношение  или слишком большая, или (ширина и/или высота меньше допустимой)
        //converterImpl.setMaxHeight(600);
       // converterImpl.setMaxWidth(600);
       // converterImpl.setMaxRatio(2);

        System.out.println("Проверить на максимально допустимое соотношение сторон изображения? да/нет");
        String input = scanner.nextLine();
        if (input.equals("нет")) {
            converterImpl.convert(url); // конвертируем как есть.

        }
        if(input.equals("да")) {
            System.out.println("Введите максимальную ширину результирующего изображения в \"текстовых пикселях\":");
            int width = Integer.parseInt(scanner.nextLine());
            converterImpl.setMaxWidth(width);
            System.out.println("Введите максимальную высоту результирующего изображения в \"текстовых пикселях\":");
            int height = Integer.parseInt(scanner.nextLine());
            converterImpl.setMaxWidth(height);
            System.out.println("максимально допустимое соотношение сторон исходного изображения:");
            double maxRatio = Double.parseDouble(scanner.nextLine());
            converterImpl.setMaxRatio(maxRatio);
            converterImpl.convert(url);
            //далее проверка в методе!!!

        }



    }
}
