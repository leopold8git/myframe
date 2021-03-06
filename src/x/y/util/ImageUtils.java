package x.y.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImageUtils {

    private static BufferedImage image;

    public static void getImage(Object objResponse, Object objSession)
            throws Exception {
        HttpServletResponse response = (HttpServletResponse) objResponse;
        HttpSession session = (HttpSession) objSession;
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        String sRand = getRandomRand();

        session.setAttribute("easy_web_rand", sRand);


        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    public static String getRandomRand() {
        int width = 60;
        int height = 20;
        image = new BufferedImage(width, height, 1);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Comic Sans MS", 0, 20));

        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand = sRand + rand;

            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return sRand;
    }

    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public static void main(String[] args) throws IOException {
       String sRand =  getRandomRand();
        ImageIO.write(image, "JPEG", new FileOutputStream("E:/"+sRand+".jpg"));
    }

}
