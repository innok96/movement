package com.company;

import static java.lang.Math.*;

public class PixelLab {
    private int L, a, b;

    public PixelLab(){
        L=a=b=0;
    }

    public PixelLab(int L, int a, int b){
        this.L=L;
        this.a=a;
        this.b=b;
    }

    public PixelLab(PixelRGB pixel){
        double var_R = ( pixel.getR() / 255. );
        double var_G = ( pixel.getG() / 255. );
        double var_B = ( pixel.getB() / 255. );

        if ( var_R > 0.04045 ) var_R = pow(( ( var_R + 0.055 ) / 1.055 ) , 2.4);
        else var_R = var_R / 12.92;
        if ( var_G > 0.04045 ) var_G = pow(( ( var_G + 0.055 ) / 1.055 ) , 2.4);
        else var_G = var_G / 12.92;
        if ( var_B > 0.04045 ) var_B = pow(( ( var_B + 0.055 ) / 1.055 ) , 2.4);
        else var_B = var_B / 12.92;

        var_R = var_R * 100.;
        var_G = var_G * 100.;
        var_B = var_B * 100.;

        double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
        double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
        double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;
        double var_X = X / 95.047, var_Y = Y / 100.000, var_Z = Z / 108.883;

        if ( var_X > 0.008856 ) var_X = pow(var_X , ( 1./3. ));
        else                    var_X = ( 7.787 * var_X ) + ( 16. / 116. );
        if ( var_Y > 0.008856 ) var_Y = pow(var_Y , ( 1./3. ));
        else                    var_Y = ( 7.787 * var_Y ) + ( 16. / 116. );
        if ( var_Z > 0.008856 ) var_Z = pow(var_Z , ( 1./3. ));
        else                    var_Z = ( 7.787 * var_Z ) + ( 16. / 116. );

        double L = ( 116. * var_Y ) - 16.,
                a = 500. * ( var_X - var_Y ),
                b = 200. * ( var_Y - var_Z );

        this.L=(int)L; this.a=(int)a; this.b=(int)b;
    }

    public static double getDist(PixelLab pixel1, PixelLab pixel2){
        return sqrt(pow(pixel1.L-pixel2.L, 2.)+pow(pixel1.a-pixel2.a, 2.)+pow(pixel1.b-pixel2.b, 2.));
    }
}
