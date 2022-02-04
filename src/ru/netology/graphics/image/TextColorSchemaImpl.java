package ru.netology.graphics.image;

public class TextColorSchemaImpl implements TextColorSchema{

    public char convert(int color) {
        // 0 - черное, 255 - светлое. Всего 256/8 = 32 - ширина диапазона? 8 - количество диапазонов.

        char pixel = (char) 0;
        if(color % 32 == 0) {
            pixel = '#';
        } else if (color % 32 == 1) {
            pixel = '$';
        } else if (color % 32 == 2) {
            pixel = '@';
        } else if (color % 32 == 3) {
            pixel = '%';
        } else if (color % 32 == 4) {
            pixel = '*';
        } else if (color % 32 == 5) {
            pixel = '+';
        } else if (color % 32 == 6) {
            pixel = '-';
        } else if (color % 32 == 7) {
            pixel = ' ';
        }
        return pixel;
    }
}
