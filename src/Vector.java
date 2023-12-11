package vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;


public class Vector extends JFrame implements KeyListener {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    private int puntoDePerspectiva = 700; // Declarar la variable puntoDePerspectiva

    private int xCabeza = 0;
    private int yCabeza = 0;
    private int zCabeza = 0;

    private double anguloX = 0; // Angulo de rotación en el eje X
    private double anguloY = 0; // Angulo de rotación en el eje Y
    private double anguloZ = 0; // Angulo de rotación en el eje Z

    private boolean rotarX = false;
    private boolean rotarY = false;
    private boolean rotarZ = false;

    // Definir los vértices de la cara en 3D
    private int[][] verticesCabeza = {
            {75, -300, 75},
            {75, -300, -75},
            {75, -175, 75},
            {75, -175, -75},
            {-75, -300, 75},
            {-75, -300, -75},
            {-75, -175, 75},
            {-75, -175, -75}
    };

    // Definir los vértices de el cuerpo en 3D
    private int[][] verticesCuerpo = {
            {75, 75, 50},
            {75, 75, -50},
            {75, -175, 50},
            {75, -175, -50},
            {-75, 75, 50},
            {-75, 75, -50},
            {-75, -175, 50},
            {-75, -175, -50}
    };

    // Definir los vértices de la pata 1 en 3D
    private int[][] verticesPata1 = {
            {-5, 150, -25},
            {-5, 150, -75},
            {-5, 75, -25},
            {-5, 75, -75},
            {-75, 150, -25},
            {-75, 150, -75},
            {-75, 75, -25},
            {-75, 75, -75}
    };

    // Definir los vértices de la pata 2 en 3D
    private int[][] verticesPata2 = {
            {75, 150, -25},
            {75, 150, -75},
            {75, 75, -25},
            {75, 75, -75},
            {5, 150, -25},
            {5, 150, -75},
            {5, 75, -25},
            {5, 75, -75}
    };

    // Definir los vértices de la pata 3 en 3D
    private int[][] verticesPata3 = {
            {-5, 150, 25},
            {-5, 150, 75},
            {-5, 75, 25},
            {-5, 75, 75},
            {-75, 150, 25},
            {-75, 150, 75},
            {-75, 75, 25},
            {-75, 75, 75}
    };

    // Definir los vértices de la pata 4 en 3D
    private int[][] verticesPata4 = {
            {75, 150, 25},
            {75, 150, 75},
            {75, 75, 25},
            {75, 75, 75},
            {5, 150, 25},
            {5, 150, 75},
            {5, 75, 25},
            {5, 75, 75}
    };

    // Definir los vértices de la cara
    // Definir los vértices del ojo 1 en 3D
    private int[][] verticesOjo1 = {
            {-20, -275, -70},
            {-20, -275, -80},
            {-20, -245, -70},
            {-20, -245, -80},
            {-50, -275, -70},
            {-50, -275, -80},
            {-50, -245, -70},
            {-50, -245, -80}
    };

    // Definir los vértices del ojo 2 en 3D
    private int[][] verticesOjo2 = {
            {20, -275, -70},
            {20, -275, -80},
            {20, -245, -70},
            {20, -245, -80},
            {50, -275, -70},
            {50, -275, -80},
            {50, -245, -70},
            {50, -245, -80}
    };

    // Definir los vértices de la boca 1 en 3D
    private int[][] verticesBoca1 = {
            {-20, -230, -70},
            {-20, -230, -80},
            {-20, -200, -70},
            {-20, -200, -80},
            {20, -230, -70},
            {20, -230, -80},
            {20, -200, -70},
            {20, -200, -80}
    };

    // Definir los vértices de la boca 2 en 3D
    private int[][] verticesBoca2 = {
            {-35, -220, -70},
            {-35, -220, -80},
            {-35, -190, -70},
            {-35, -190, -80},
            {-20, -220, -70},
            {-20, -220, -80},
            {-20, -190, -70},
            {-20, -190, -80}
    };

    // Definir los vértices de la boca 3 en 3D
    private int[][] verticesBoca3 = {
            {35, -220, -70},
            {35, -220, -80},
            {35, -190, -70},
            {35, -190, -80},
            {20, -220, -70},
            {20, -220, -80},
            {20, -190, -70},
            {20, -190, -80}
    };

    private int[][] caras = {
            {0, 1, 2, 3},  // Cara frontal
            {4, 6, 7, 5},  // Cara trasera
            {0, 4, 5, 1},  // Cara superior
            {2, 6, 7, 3},  // Cara inferior
            {0, 2, 6, 4},  // Cara izquierda
            {1, 3, 7, 5}   // Cara derecha
    };


    public Vector() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.CYAN);
        g2d.clearRect(0, 0, getWidth(), getHeight());

        addKeyListener(this);  // Agregar el escuchador de teclas

        // Configurar temporizador para la rotación continua
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rotarX) {
                    anguloX += 0.01; // Ajusta la velocidad de rotación según sea necesario
                }
                if (rotarY) {
                    anguloY += 0.01;
                }
                if (rotarZ) {
                    anguloZ += 0.01;
                }
                drawPoligon3D();
            }
        });
        timer.start();
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    public void clearMyScreen() {
        graPixel.clearRect(0, 0, getWidth(), getHeight());
    }

    // Algoritmo de Bresenham para dibujar una línea
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

    private int[][] aplicarTransformacion(int[][] vertices, double[][] matrizTransformacion) {
        int[][] verticesTransformados = new int[vertices.length][3];
    
        for (int i = 0; i < vertices.length; i++) {
            int[] resultadoRotacion = vertices[i];
    
            // Aplicar la traslación antes de la rotación
            resultadoRotacion[0] += xCabeza;
            resultadoRotacion[1] += yCabeza;
            resultadoRotacion[2] += zCabeza;
    
            // Aplicar la transformación utilizando la matriz
            for (int j = 0; j < 3; j++) {
                double suma = 0;
                for (int k = 0; k < 3; k++) {
                    suma += resultadoRotacion[k] * matrizTransformacion[k][j];
                }
                verticesTransformados[i][j] = (int) suma;
            }
        }
    
        return verticesTransformados;
    }
    

    // Función para dibujar un conjunto de vértices proyectados en 2D
    private void dibujarVertices(int[][] vertices, int puntoDePerspectiva) {
        for (int i = 0; i < vertices.length; i++) {
            int x = (vertices[i][0] * puntoDePerspectiva) / (vertices[i][2] + puntoDePerspectiva) + getWidth() / 2;
            int y = (vertices[i][1] * puntoDePerspectiva) / (vertices[i][2] + puntoDePerspectiva) + getHeight() / 2;

            // Dibujar los vértices
            graPixel.setColor(Color.BLACK);
            graPixel.fillRect(x, y, 3, 3);
        }
    }

    // Función para dibujar un conjunto de líneas entre vértices
    private void dibujarLineas(int[][] vertices, Color fill) {
        for (int i = 0; i < vertices.length; i++) {
            int x1 = (vertices[i][0] * puntoDePerspectiva) / (vertices[i][2] + puntoDePerspectiva) + getWidth() / 2;
            int y1 = (vertices[i][1] * puntoDePerspectiva) / (vertices[i][2] + puntoDePerspectiva) + getHeight() / 2;

            // Conectar los vértices para formar el objeto
            for (int j = i + 1; j < vertices.length; j++) {
                int x2 = (vertices[j][0] * puntoDePerspectiva) / (vertices[j][2] + puntoDePerspectiva) + getWidth() / 2;
                int y2 = (vertices[j][1] * puntoDePerspectiva) / (vertices[j][2] + puntoDePerspectiva) + getHeight() / 2;

                drawLineBresenham(x1, y1, x2, y2, fill);
            }
        }
    }

    // Función para dibujar el cubo y la pirámide
    private void drawPoligon3D() {
        // Crear la matriz de rotación
        double[][] matrizRotacion = {
                {Math.cos(anguloY) * Math.cos(anguloZ), -Math.cos(anguloY) * Math.sin(anguloZ), Math.sin(anguloY)},
                {Math.cos(anguloX) * Math.sin(anguloZ) + Math.sin(anguloX) * Math.sin(anguloY) * Math.cos(anguloZ), Math.cos(anguloX) * Math.cos(anguloZ) - Math.sin(anguloX) * Math.sin(anguloY) * Math.sin(anguloZ), -Math.sin(anguloX) * Math.cos(anguloY)},
                {Math.sin(anguloX) * Math.sin(anguloZ) - Math.cos(anguloX) * Math.sin(anguloY) * Math.cos(anguloZ), Math.sin(anguloX) * Math.cos(anguloZ) + Math.cos(anguloX) * Math.sin(anguloY) * Math.sin(anguloZ), Math.cos(anguloX) * Math.cos(anguloY)}
        };

        int[][] verticesCabezaRotados = aplicarTransformacion(verticesCabeza, matrizRotacion);      // Aplicar la rotación a los vértices de la cabesa
        int[][] verticesCuerpoRotados = aplicarTransformacion(verticesCuerpo, matrizRotacion);      // Aplicar la rotación a los vértices del cuerpo

        int[][] verticesPata1Rotados = aplicarTransformacion(verticesPata1, matrizRotacion);        // Aplicar la rotación a los vértices de la pata numero 1
        int[][] verticesPata2Rotados = aplicarTransformacion(verticesPata2, matrizRotacion);        // Aplicar la rotación a los vértices de la pata numero 2
        int[][] verticesPata3Rotados = aplicarTransformacion(verticesPata3, matrizRotacion);        // Aplicar la rotación a los vértices de la pata numero 3
        int[][] verticesPata4Rotados = aplicarTransformacion(verticesPata4, matrizRotacion);        // Aplicar la rotación a los vértices de la pata numero 4
        
        int[][] verticesOjo1Rotados = aplicarTransformacion(verticesOjo1, matrizRotacion);          // Aplicar la rotación a los vértices del ojo numero 1
        int[][] verticesOjo2Rotados = aplicarTransformacion(verticesOjo2, matrizRotacion);          // Aplicar la rotación a los vértices del ojo numero 2

        int[][] verticesBoca1Rotados = aplicarTransformacion(verticesBoca1, matrizRotacion);        // Aplicar la rotación a los vértices de la boca numero 1
        int[][] verticesBoca2Rotados = aplicarTransformacion(verticesBoca2, matrizRotacion);        // Aplicar la rotación a los vértices de la boca numero 2
        int[][] verticesBoca3Rotados = aplicarTransformacion(verticesBoca3, matrizRotacion);        // Aplicar la rotación a los vértices de la boca numero 3

        clearMyScreen();

        fillPoligon3D(caras, verticesCabezaRotados, Color.GREEN);
        fillPoligon3D(caras, verticesCuerpoRotados, Color.GREEN);

        fillPoligon3D(caras, verticesPata1Rotados, Color.GREEN);
        fillPoligon3D(caras, verticesPata2Rotados, Color.GREEN);
        fillPoligon3D(caras, verticesPata3Rotados, Color.GREEN);
        fillPoligon3D(caras, verticesPata4Rotados, Color.GREEN);
        
        fillPoligon3D(caras, verticesOjo1Rotados, Color.BLACK);
        fillPoligon3D(caras, verticesOjo2Rotados, Color.BLACK);
        
        fillPoligon3D(caras, verticesBoca1Rotados, Color.BLACK);
        fillPoligon3D(caras, verticesBoca2Rotados, Color.BLACK);
        fillPoligon3D(caras, verticesBoca3Rotados, Color.BLACK);


        // Dibujar los vértices
        // dibujarVertices(verticesCabezaRotados, puntoDePerspectiva);
        // dibujarVertices(verticesCuerpoRotados, puntoDePerspectiva);

        // dibujarVertices(verticesPata1Rotados, puntoDePerspectiva);
        // dibujarVertices(verticesPata2Rotados, puntoDePerspectiva);
        // dibujarVertices(verticesPata3Rotados, puntoDePerspectiva);
        // dibujarVertices(verticesPata4Rotados, puntoDePerspectiva);

        // dibujarVertices(verticesOjo1Rotados, puntoDePerspectiva);
        // dibujarVertices(verticesOjo2Rotados, puntoDePerspectiva);
   
        // dibujarVertices(verticesBoca1Rotados, puntoDePerspectiva);
        // dibujarVertices(verticesBoca2Rotados, puntoDePerspectiva);
        // dibujarVertices(verticesBoca3Rotados, puntoDePerspectiva);

        
        // dibujarLineas(verticesCabezaRotados, Color.GREEN);          // Dibujar las líneas de la cabeza
        // dibujarLineas(verticesCuerpoRotados, Color.GREEN);          // Dibujar las líneas del cueroi

        // dibujarLineas(verticesPata1Rotados, Color.GREEN);           // Dibujar las líneas de la pata numero 1
        // dibujarLineas(verticesPata2Rotados, Color.GREEN);           // Dibujar las líneas de la pata numero 2
        // dibujarLineas(verticesPata3Rotados, Color.GREEN);           // Dibujar las líneas de la pata numero 3
        // dibujarLineas(verticesPata4Rotados, Color.GREEN);           // Dibujar las líneas de la pata numero 4

        // dibujarLineas(verticesOjo1Rotados, Color.BLACK);            // Dibujar las líneas del ojo numero 1
        // dibujarLineas(verticesOjo2Rotados, Color.BLACK);            // Dibujar las líneas del ojo numero 2

        // dibujarLineas(verticesBoca1Rotados, Color.BLACK);           // Dibujar las líneas de la boca numero 1
        // dibujarLineas(verticesBoca2Rotados, Color.BLACK);           // Dibujar las líneas de la boca numero 2
        // dibujarLineas(verticesBoca3Rotados, Color.BLACK);           // Dibujar las líneas de la boca numero 3

        // Repintar el JFrame para mostrar el cubo y la pirámide
        repaint();
    }

    public void fillPoligon3D(int[][] caras, int[][] vertices, Color fill) {
        // Crear un array para almacenar las distancias de las caras al espectador
        double[] distancias = new double[caras.length];

        // Calcular la distancia al espectador para cada cara
        for (int i = 0; i < caras.length; i++) {
            int[] cara = caras[i];
            int[] centro = {0, 0, 0};  // Puedes usar el centroide u otro punto representativo de la cara

            // Calcular la distancia euclidiana
            distancias[i] = Math.sqrt(Math.pow(centro[0] - xCabeza, 2) + Math.pow(centro[1] - yCabeza, 2) + Math.pow(centro[2] - zCabeza, 2));
        }

        // Ordenar las caras por distancia (de cercano a lejano)
        int[] ordenCaras = IntStream.range(0, caras.length)
                .boxed()
                .sorted(Comparator.comparingDouble(i -> -distancias[i]))
                .mapToInt(ele -> ele)
                .toArray();

        // Dibujar las caras en el orden correcto
        for (int i = 0; i < ordenCaras.length; i++) {
            int[] cara = caras[ordenCaras[i]];
            int[] puntosX = new int[cara.length];
            int[] puntosY = new int[cara.length];

            for (int j = 0; j < cara.length; j++) {
                int x = (vertices[cara[j]][0] * puntoDePerspectiva) / (vertices[cara[j]][2] + puntoDePerspectiva)
                        + getWidth() / 2;
                int y = (vertices[cara[j]][1] * puntoDePerspectiva) / (vertices[cara[j]][2] + puntoDePerspectiva)
                        + getHeight() / 2;
                puntosX[j] = x;
                puntosY[j] = y;
            }

            // Rellenar la cara con un color
            fillPolygonScanLine(puntosX, puntosY, fill);
        }

        // Repintar el JFrame para mostrar el cubo
        repaint();
    }


    public void fillPolygonScanLine(int[] xPoints, int[] yPoints, Color c) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;

        // Encontrar el rango horizontal del polígono
        for (int x : xPoints) {
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
        }

        List<Integer> intersections = new ArrayList<>();

        // Escanear cada línea vertical dentro del rango horizontal
        for (int x = minX; x <= maxX; x++) {
            intersections.clear();

            for (int i = 0; i < xPoints.length; i++) {
                int x1 = xPoints[i];
                int y1 = yPoints[i];
                int x2 = xPoints[(i + 1) % xPoints.length];
                int y2 = yPoints[(i + 1) % xPoints.length];

                if ((x1 <= x && x2 > x) || (x2 <= x && x1 > x)) {
                    // Calcula la intersección vertical con la línea
                    double y = y1 + (double) (x - x1) * (y2 - y1) / (x2 - x1);
                    intersections.add((int) y);
                }
            }

            // Ordena las intersecciones de arriba a abajo
            intersections.sort(Integer::compareTo);

            // Rellena el espacio entre las intersecciones
            for (int i = 0; i < intersections.size(); i += 2) {
                int startY = intersections.get(i);
                int endY = intersections.get(i + 1);
                for (int y = startY; y < endY; y++) {
                    putPixel(x, y, c);
                }

                repaint();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    // Métodos de la interfaz KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
        // No necesitamos implementar este método en este caso
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Capturar teclas presionadas y ajustar los estados de rotación y traslación
        if (e.getKeyChar() == 'x' && !rotarX) {
            rotarX = true;
        } else if (e.getKeyChar() == 'y' && !rotarY) {
            rotarY = true;
        } else if (e.getKeyChar() == 'z' && !rotarZ) {
            rotarZ = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            xCabeza -= 10; // Ajusta la velocidad de traslación según sea necesario
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            xCabeza += 10;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            zCabeza -= 10;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            zCabeza += 10;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Restaurar el cubo y la pirámide a su posición estática
            anguloX = 0;
            anguloY = 0;
            anguloZ = 0;
            xCabeza = 0;
            yCabeza = 0;
            zCabeza = 0;
            rotarX = false;
            rotarY = false;
            rotarZ = false;
            drawPoligon3D();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No necesitamos implementar este método en este caso
    }

    public static void main(String[] args) throws Exception {
        Vector vector = new Vector();
        vector.setVisible(true);
    }
}
