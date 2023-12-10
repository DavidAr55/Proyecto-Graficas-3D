public class App {
    public static void main(String[] args) throws Exception {
        int[] center = { 10, -100, 10 };
        int[] proyectionCenter = { 1, -5, 5 };

        int[][] pX = { 
            { -100, -100, 100, 100, -100, -100, 100, 100 },
            { -100, -100, 100, 100, -100, -100, 100, 100 } };

        int[][] pY = { 
            { -200, 200, -200, 200, -200, 200, -200, 200 },
            { 200, 450, 200, 450, -450, -200, -450, -200 } };

        int[][] pZ = { 
            { -100, -100, -100, -100, 100, 100, 100, 100 },
            { -100, -100, -100, -100, 100, 100, 100, 100 } };
        double scale =10;

        Poligon3D poligon3D = new Poligon3D(center, proyectionCenter, pX, pY, pZ, scale);
        poligon3D.drawCenterPoigon3D();
        poligon3D.drawPoligon3D();
        poligon3D.rotateAndShow(0, 0.1, 0, 50);
        /*for(int i = 0; i < 360; i++) {
            try {
                
                int[] auxPC = {1 + i, -5, 5};

                poligon3D.drawPoligon3D();
                poligon3D.setProyectionCenter(auxPC);
                Thread.sleep(100);
                poligon3D.clearScreen();

            } catch (Exception e) {
                // TODO: handle exception
            }
        }*/
    }
}
