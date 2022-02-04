package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterImpl implements TextGraphicsConverter {

    private int width;
    private int height;
    private double ratio;
    public TextColorSchema schema = new TextColorSchemaImpl("#$@%*+-");  // схема по умолчанию



    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));

        double tempRatio = img.getWidth() >= img.getHeight() ? img.getWidth() / img.getHeight() : img.getHeight() / img.getWidth();

        if (getRatio() != 0 && tempRatio > getRatio()) {
            throw new BadImageSizeException(tempRatio, getRatio());
        }

        int localWidth = img.getWidth();
        int localHeight = img.getHeight();

        int newWidth = 0;
        int newHeight = 0;

        if (((localWidth > getWidth()) || (localHeight > getHeight())) || ((localWidth > getWidth()) && (localHeight > getHeight()))) { // хотя бы один или оба из текущих параметров больше заданных занчений
            if (getWidth() != 300 || getHeight() != 300) {  // хотя бы 1 параметр задан, сценарий ИЛИ, пропорционально

                if (localWidth > localHeight) {
                    int temp = localWidth / getWidth();
                    newWidth = localWidth / temp;
                    newHeight = localHeight / temp;
                } else {
                    int temp = localHeight / getHeight();
                    newWidth = localWidth / temp;
                    newHeight = localHeight / temp;
                }

            } else if (getWidth() != 300 && getHeight() != 300) { // оба параметра заданы, сценарий И
                if (localWidth > localHeight) {
                    int temp = localWidth / getWidth();
                    newWidth = localWidth / temp;
                    int temp1 = localHeight / getHeight();
                    newHeight = localHeight / temp1;
                } else {
                    int temp = localHeight / getHeight();
                    newHeight = localHeight / temp;
                    int temp1 = localWidth / getWidth();
                    newWidth = localWidth / temp1;
                }

            } else {  // не заданы оба
                newWidth = getWidth();
                newHeight = getHeight();
            }
        } else { // (localWidth < getWidth()) && (localHeight < getHeight()) оба параметра текущей картинки меньше заданных параметров.

            if (getWidth() != 300 || getHeight() != 300) {  // хотя бы 1 параметр задан, сценарий ИЛИ, пропорционально

                if (localWidth > localHeight) {
                    int temp = getWidth() / localWidth;
                    newWidth = localWidth * temp;
                    newHeight = localHeight * temp;
                } else {
                    int temp = getHeight() / localHeight;
                    newWidth = localWidth * temp;
                    newHeight = localHeight * temp;
                }

            } else if (getWidth() != 300 && getHeight() != 300) { // оба параметра заданы, сценарий И
                if (localWidth > localHeight) {
                    int temp = getWidth() / localWidth;
                    newWidth = localWidth * temp;
                    int temp1 = getHeight() / localHeight;
                    newHeight = localHeight * temp1;
                } else {
                    int temp = getHeight() / localHeight;
                    newHeight = localHeight * temp;
                    int temp1 = getWidth() / localWidth;
                    newWidth = localWidth * temp1;
                }

            }

        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();

        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();

        StringBuilder sb1 = new StringBuilder();
        char[][] arrayChar = new char[newHeight][newWidth];

        char c = (char) 0;
        String result = null;

        sb1.append("<div style=\"white-space: nowrap;\">");
        for (int i = 0; i < arrayChar.length; i++) {
            for (int j = 0; j < arrayChar[i].length; j++) {
                int color = bwRaster.getPixel(j, i, new int[3])[0];
                c = schema.convert(color);

                sb1
                        .append(c)
                        .append(c);
            }
            sb1
                    .append("\n");

            result = sb1.toString();
        }
        sb1.append("</div>");
        return result;

    }




    public void setMaxWidth(int width) {
        this.width = width;
    }

    public void setMaxHeight(int height) {
        this.height = height;
    }

    public void setMaxRatio(double maxRatio) {
        this.ratio = maxRatio;
    }

    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getRatio() {
        return ratio;
    }
}
