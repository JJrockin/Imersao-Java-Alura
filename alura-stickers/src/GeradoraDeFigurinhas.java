import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class GeradoraDeFigurinhas {

    public void cria(InputStream inputStream, String nomeArquivo, String frase) throws Exception {

//        InputStream inputStream = new FileInputStream(new File("entrada/filme1.jpg"));
//        InputStream inputStream = new URL("https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@.jpg")
//                                .openStream();
//        BufferedImage imagemOriginal = ImageIO.read(new File("entrada/filme1.jpg"));
        BufferedImage imagemOriginal = ImageIO.read(inputStream);
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = (int) (altura * 1.1);
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal,0,0,null);

        var fonte = new Font("Comic Sans",Font.BOLD, (largura / 10));
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(frase, graphics);
        int larguraDoTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguraDoTexto)/2;
        int posicaoTextoY = (int) (novaAltura * .985);
        graphics.drawString(frase, posicaoTextoX, posicaoTextoY);
        
        // outline
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(frase, fonte, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(((int) (largura / 216)));
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

//        ImageIO.write(novaImagem,"png", new File("saida/figurinha.png"));
        ImageIO.write(novaImagem,"png", new File(nomeArquivo));

    }

//    public static void main(String[] args) throws Exception {
//        var geradora = new GeradoraDeFigurinhas();
//        geradora.cria();
//    }
}
