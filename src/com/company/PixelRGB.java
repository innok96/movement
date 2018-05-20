package com.company;

public class PixelRGB {
    private int R, G, B;

    public PixelRGB(){
        R=G=B=0;
    }

    public PixelRGB(int R, int G, int B){
        this.R=R;
        this.G=G;
        this.B=B;
    }

    public PixelRGB(int rgb){
        R = (rgb>>16) & 0xff;
        G = (rgb>>8) & 0xff;
        B = rgb & 0xff;
    }

    public int getR() {
        return R;
    }

    public int getG() {
        return G;
    }

    public int getB() {
        return B;
    }

    public PixelLab getPixelLab(){
        return new PixelLab(this);
    }

    public static boolean isCloseEnough(PixelRGB pixel1, PixelRGB pixel2){
        return PixelLab.getDist(new PixelLab(pixel1), new PixelLab(pixel2))<=2;
    }
}
