import java.awt.*;
import java.awt.image.BufferedImage;

public class Poligon3D extends Screen {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    private int xCenter;
    private int yCenter;
    private int zCenter;

    private int[][] xPoligon3D;
    private int[][] yPoligon3D;
    private int[][] zPoligon3D;

    private int[] poligonFaces;
    private int[][] cubeFace;
    private Color[] colors;

    public Poligon3D(int xC, int yC, int zC) {
        super();

        this.xCenter = xC;
        this.yCenter = yC;
        this.zCenter = zC;

        this.xPoligon3D = new int[][] {{-50, 50, 50, -50}};
        this.yPoligon3D = new int[][] {{-50, 50, 50, -50}};
        this.zPoligon3D = new int[][] {{-50, 50, 50, -50}};

        this.cubeFace = new int[][] {
            {0, 1, 2, 3},  // Cara frontal
            {4, 5, 6, 7},  // Cara trasera
            {0, 4, 5, 1},  // Cara izquierda
            {2, 6, 7, 3},  // Cara derecha
            {0, 4, 7, 3},  // Cara superior
            {1, 5, 6, 2}   // Cara inferior
        };     
        
        this.colors = new Color[] {
            Color.RED,      // Color de la cara frontal
            Color.BLUE,     // Color de la cara trasera
            Color.GREEN,    // Color de la cara izquierda
            Color.YELLOW,   // Color de la cara derecha
            Color.WHITE,    // Color de la cara superior
            Color.ORANGE    // Color de la cara inferior
        };

        initBuffer();
    }

    public void initBuffer() {
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void clearScreen() {
        graPixel.clearRect(0, 0, getWidth(), getHeight());
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    public void drawLineBresenham(int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, c);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }

    public void drawPoligon3D() {
        
    }
    
    
      

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }
}