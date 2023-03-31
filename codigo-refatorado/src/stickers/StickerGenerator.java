package stickers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class StickerGenerator {

    public void createSticker(InputStream inputStream, String nameArchive, String phrase) throws Exception{
        BufferedImage originalImage = ImageIO.read(inputStream);
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int newHeight = (int) (height * 1.1);
        BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.drawImage(originalImage,0,0,null);

        var font = new Font("Comic Sans",Font.BOLD, (width / 10));
        graphics.setColor(Color.YELLOW);
        graphics.setFont(font);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rectangle2D = fontMetrics.getStringBounds(phrase, graphics);
        int textWidth = (int) rectangle2D.getWidth();
        int textPositionX = (width - textWidth)/2;
        int textPositionY = (int) (newHeight * .985);
        graphics.drawString(phrase, textPositionX, textPositionY);

        // outline
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(phrase, font, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(textPositionX, textPositionY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(((int) (width / 216)));
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        ImageIO.write(newImage,"png", new File(nameArchive));

    }
}
