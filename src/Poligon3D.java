import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Poligon3D extends Screen {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    private int xCenter;
    private int yCenter;
    private int zCenter;

    private int[] proyectionCenter;

    private int[][] xPoligon3D;
    private int[][] yPoligon3D;
    private int[][] zPoligon3D;

    private double scale;

    private int[] poligonFaces;
    private int[][] cubeFace;
    private Color[] colors;

    private ArrayList<int[]> polygonPoints = new ArrayList<>();

    public Poligon3D(int[] C, int[] pC, int[][] xP, int[][] yP, int[][] zP, double scale) {
        super();

        this.xCenter = C[0];
        this.yCenter = C[1];
        this.zCenter = C[2];

        this.proyectionCenter = pC;

        this.xPoligon3D = xP;
        this.yPoligon3D = yP;
        this.zPoligon3D = zP;

        this.scale = scale;

        this.cubeFace = new int[][] {
            {0, 1, 2, 3},  // Cara frontal
            {4, 5, 6, 7},  // Cara trasera
            {0, 4, 5, 1},  // Cara izquierda
            {2, 6, 7, 3},  // Cara derecha
            {0, 4, 7, 3},  // Cara superior
            {1, 5, 6, 2}   // Cara inferior
        };     
        
        this.colors = new Color[] {
            Color.RED,      // 0 Abajo -> 2
            Color.BLUE,     // 1 Arriba -> 2
            Color.RED,      // 2 Abajo -> 1
            Color.BLUE,     // 3 Arriba -> 1
            Color.BLUE,     // 4 Arriba -> 0
            Color.RED,      // 5 Abajo -> 0
            Color.BLUE,     // 6 Arriba -> 3
            Color.RED       // 7 Abajo -> 3
        };

        initBuffer();
    }

    public void setProyectionCenter(int[] proyectionCenter) {
        this.proyectionCenter = proyectionCenter;
    }

    public void initBuffer() {
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void drawCenterPoigon3D() {
        int x, y, z;
        double u;

        u = this.proyectionCenter[2];
        u /= (zCenter - this.proyectionCenter[2]);

        // Cálculo de las coordenadas x e y
        x = (int) ((getWidth() / 2) + (this.proyectionCenter[0] + (xCenter - this.proyectionCenter[0]) * u));
        y = (int) ((getHeight() / 2) + (this.proyectionCenter[1] + (yCenter - this.proyectionCenter[1]) * u));
        z = (int) ((getWidth() / 2) + (this.proyectionCenter[2] + (zCenter - this.proyectionCenter[2]) * u));

        xCenter = x;
        yCenter = y;
        zCenter = z;

        fillEllipseScanLine(xCenter, yCenter, 5, 5, Color.GREEN);
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

    public void fillEllipseScanLine(int centerX, int centerY, int a, int b, Color fillColor) {
        
        double a2 = a * a;
        double b2 = b * b;
    
        for (int y = -b; y <= b; y++) {
            int xLimit = (int) (a * Math.sqrt(1 - (y * y) / b2));
            for (int x = -xLimit; x <= xLimit; x++) {
                int pixelX = centerX + x;
                int pixelY = centerY + y;
                putPixel(pixelX, pixelY, fillColor);
            }
        }

        repaint();
    }

    public void drawPoligon3D() {
    
        for (int i = 0; i < this.xPoligon3D.length; i++) {
            for (int j = 0; j < this.xPoligon3D[i].length; j++) {
                int x, y;
                double u;
    
                u = this.proyectionCenter[2];
                u /= (zPoligon3D[i][j] - this.proyectionCenter[2]);
    
                // Cálculo de las coordenadas x e y
                x = (int) ((getWidth() / 2) + ((this.proyectionCenter[0] + (xPoligon3D[i][j] - this.proyectionCenter[0]) * u) * scale));
                y = (int) ((getHeight() / 2) + ((this.proyectionCenter[1] + (yPoligon3D[i][j] - this.proyectionCenter[1]) * u) * scale));

                // Guarda las coordenadas en un array para conectar los vértices posteriormente
                int[] coordinates = new int[]{x, y};
                // Agrega las coordenadas al array de puntos del polígono
                polygonPoints.add(coordinates);
    
                fillEllipseScanLine(x, y, 5, 5, this.colors[j]);
            }

            // Une los vértices para formar el polígono completo
            connectPolygonVertices(this.colors[i]);
            polygonPoints.clear();
        }

        repaint();
    }
    
    private void connectPolygonVertices(Color color) {
        int numVertices = polygonPoints.size();
    
        for (int i = 0; i < numVertices; i++) {
            int[] p1 = polygonPoints.get(i);
            for (int j = 0; j < numVertices; j++) {
                if (i != j) {
                    int[] p2 = polygonPoints.get(j);
                    drawLineBresenham(p1[0], p1[1], p2[0], p2[1], color);
                }
            }
        }
    
        repaint();
    }

    public void rotateX(double angle) {
        double[][] matrixX = 
            {{1,  0,               0,               0},
             {0,  Math.cos(angle), Math.sin(angle), 0},
             {0, -Math.sin(angle), Math.cos(angle), 0},
             {0,  0,               0,               1}}; 
    }
    
    public void rotateY(double angle) {
        double[][] matrixY = 
            {{Math.cos(angle), 0, -Math.sin(angle), 0},
             {0,               1,  0,               0},
             {Math.sin(angle), 0,  Math.cos(angle), 0},
             {0,               0,  0,               1}}; 
    }
    
    public void rotateZ(double angle) {
        double[][] matrixZ = 
            {{ Math.cos(angle), Math.sin(angle), 0, 0},
             {-Math.sin(angle), Math.cos(angle), 0, 0},
             {0,                0,               1, 0},
             {0,                0,               0, 1}}; 
    }
    
    
    // Método para rotar y mostrar los polígonos en cada eje
    public void rotateAndShow(double angleX, double angleY, double angleZ, int delay) {
        for (int i = 0; i < 360; i ++) {
            clearScreen();
            
            // Aplicar rotaciones en cada eje
            rotateX(angleX);
            rotateY(angleY);
            rotateZ(angleZ);
            
            // Dibujar el polígono 3D
            drawPoligon3D();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }
}