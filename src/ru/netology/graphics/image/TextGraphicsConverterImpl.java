package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;


public class TextGraphicsConverterImpl implements TextGraphicsConverter {

    int maxWidth = 0;
    int maxHeight = 0;
    double ratio = 0.0; //соотношение ИСХОДНОГО изображения, меняется на MAX если вводили соотношение.
    //TextColorSchema schema;


    public String convert(String url) throws IOException, BadImageSizeException {
        TextColorSchema schema = new TextColorSchemaImpl();
        BufferedImage img = ImageIO.read(new URL(url));
        int localWidth = img.getWidth(); //ширина ИСХОДНОГО по URL изображения!!!
        int localHeight = img.getHeight(); //высота ИСХОДНОГО по URL изображения!!!

        //сначала проверяем соотношение сторон
        int localRatio = localWidth > localHeight ? localWidth / localHeight : localHeight / localWidth;

        String result = null;
        if (localRatio > ratio) { //если исходное соотношение больше заданного, то ошибка
            throw new BadImageSizeException(localRatio, ratio);
        } else { // если меньше проверяем стороны.
            int newWidth = 0;
            int newHeight = 0;
            //checkSides(localWidth, localHeight, newWidth, newHeight); // сразу для двух сторон
            newWidth = checkWidth(localWidth, localHeight, newWidth);
            newHeight = checkHeight(localWidth, localHeight, newHeight);

            //сам процесс конвертирования

            //Сужение до новых размеров:
            Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

            // Теперь сделаем её чёрно-белой.
            BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
            // Попросим у этой картинки инструмент для рисования на ней:
            Graphics2D graphics = bwImg.createGraphics();
            // А этому инструменту скажем, чтобы он скопировал содержимое из нашей суженной картинки:
            graphics.drawImage(scaledImage, 0, 0, null);

            //сохранить промежуточную картинку в файл через:
            //ImageIO.write(imageObject, "png", new File("out.png"));
            // После вызова этой инструкции у вас в проекте появится файл картинки out.png

            WritableRaster bwRaster = bwImg.getRaster();
            //int color = bwRaster.getPixel(newWidth, newHeight, new int[3])[0];

            
            char [][] arrayChar = new char [newWidth][newHeight];

            for (int i = 0; i < newWidth; i++) {
                for (int j = 0; j < newHeight; j++) {
                    int color = bwRaster.getPixel(newWidth, newHeight, new int[3])[0];
                    char c = schema.convert(color);
                    c = arrayChar [i][j]; //запоминаем символ c, например, в двумерном массиве или как-то ещё на ваше усмотрение
                }
            }

            //цикл на печать изображения символами
            for (int i = 0; i < arrayChar.length; i++) {
                for (int j = 0; j < arrayChar[i].length; j++) {
                    System.out.print(arrayChar[i][j]);
                    System.out.print(arrayChar[i][j]); //дублирование пикселей
                }
                System.out.println();
            }


            StringBuilder sb = new StringBuilder();



            for (int i = 0; i < arrayChar.length; i++) {
                for (int j = 0; j < arrayChar[i].length; j++) {
                    sb
                            .append(arrayChar[i][j])
                            .append(arrayChar[i][j]);
                    result = sb.toString();
                }
                sb
                        .append("\n");

                result = sb.toString();

            }
           // System.out.println(result);
           // return result;


        }




        return result;
    }



    public void setMaxWidth(int width) {
        maxWidth = width;
    }


    public void setMaxHeight(int height) {
        maxHeight = height;
    }


    public void setMaxRatio(double maxRatio) {
        ratio = maxRatio; // максимальное соотношение сторон, пользовательский ввод
    }

    public void setTextColorSchema(TextColorSchema schema) {
        
    }
    
    



//    public void checkSides(int localWidth, int localHeight, int newWidth, int newHeight) {
//
//        if ((maxWidth != 0) && (maxHeight != 0)) {
//            if (localWidth > localHeight) {
//                int temp = localWidth / maxWidth;
//                newWidth = localWidth / temp;
//                newHeight = localHeight / temp;
//            } else if (localWidth < localHeight) {
//                int temp = localHeight / maxHeight;
//                newWidth = localWidth / temp;
//                newHeight = localHeight / temp;
//            } else if (localWidth == localHeight) {
//                int temp = localWidth / maxWidth;
//                newWidth = localWidth / temp;
//                newHeight = localHeight / temp;
//            }
//
//        }
//    }

    public int checkWidth(int localWidth, int localHeight, int newWidth) {

        if (maxWidth != 0 && (localWidth != maxWidth)) {
            if (localWidth > localHeight) {
                int temp = localWidth / maxWidth;
                if(temp ==0) {
                    newWidth = 0;
                } else {
                newWidth = localWidth / temp; }

            } else if (localWidth < localHeight) {
                int temp = localHeight / maxHeight;
                if(temp ==0) {
                    newWidth = 0;
                } else {
                newWidth = localWidth / temp; }
            }

        }
        return newWidth;
    }

    public int checkHeight(int localWidth, int localHeight, int newHeight) {

        if (maxHeight != 0 && (localHeight != maxHeight)) {
            if (localHeight > localWidth) {
                int temp = localHeight / maxHeight;
                newHeight = localHeight / temp;

            } else if (localHeight < localWidth) {
                int temp = localWidth / localWidth;
                newHeight = localHeight / temp;
            }

        }
        return newHeight;
    }


//    public void checkRatio(int localWidth, int localHeight, double validRatio) {
//        double tempRatio = localWidth / localHeight;
//        double tempRatio1 = localHeight/ localWidth; //добавить точность сравнения для double!!!!
//        if ((Math.abs(tempRatio - validRatio) < 0.01) || (Math.abs(tempRatio1 - validRatio) < 0.01)) { //если хотя бы одно совпадает, значит соотношение такое же
//            //все ок, конвертируем
//        } else if ((tempRatio != validRatio) && (tempRatio1 != validRatio)) { //если оба не совпали, тогда ошибка
//            //ошибка BadImageSizeException  для ratio
//        }
//    }


}
