package ru.netology.graphics.image;

public class TextColorSchemaImpl implements TextColorSchema{

    private String defSchema ="#$@%*+-'";

    public TextColorSchemaImpl(String defSchema) {
        this.defSchema = defSchema;
    }

    public char convert(int color) {

        char[] charArray = defSchema.toCharArray();
        int range = 256/charArray.length;
        char pixel = (char) 0;
        for (int i =0; i < 256; i++) {
            double temp = color / range; // ищем диапазон в который попадает color
            long cutTemp = (long) temp; //отсекаем дробную часть
            for(int j = 0; j <= charArray.length; j++) {
                if (cutTemp == j) {
                    pixel = charArray[j];
                }
            }
        }
        return pixel;
    }

}
