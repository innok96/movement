package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.*;

public class GUI extends JFrame {
    private JLabel imageLabel;
    private int imageIndex = 1;
    private BufferedImage image, previousImage, imageCopy;
    private JButton startButton, resetButton;

    public GUI() {
        super("Движение");
        setProperties(null, false, JFrame.EXIT_ON_CLOSE, new Rectangle(0, 0, 800, 620));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(imageIndex>8) return;
                for(int i=2; i<=8; i++) {
                    previousImage = imageCopy;
                    changePicture();
                    showDirection(image.getGraphics());
                    imageLabel.updateUI();
                    JOptionPane.showMessageDialog(null,"ok");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageIndex=1;
                changePicture();
            }
        });
    }

    private void showDirection(Graphics graphics) {
        int width = image.getWidth(), height = image.getHeight();
        graphics.setColor(Color.RED);
        for (int x = 0; x < width; x += 5) {
            for (int y = 0; y < height; y += 5) {
                if (!PixelRGB.isCloseEnough(new PixelRGB(image.getRGB(x, y)), new PixelRGB(previousImage.getRGB(x, y)))) {
                    PixelRGB pixel = new PixelRGB(previousImage.getRGB(x, y));
                    int nearestX = -100, nearestY = -100;
                    for (int i = -20; i <= 20; i++) {
                        for (int j = -20; j <= 20; j++) {
                            try {
                                if (PixelRGB.isCloseEnough(new PixelRGB(image.getRGB(x + i, y + j)), pixel)) {
                                    if (abs(x - nearestX) + abs(y - nearestY) > abs(i) + abs(j)) {
                                        nearestX = x + i;
                                        nearestY = y + j;
                                    }
                                }
                            } catch (IndexOutOfBoundsException ex) {
                            }
                        }
                    }
                    if (nearestX != -100) {
                        drawArrow(graphics, x, y, nearestX + 6 * (nearestX - x), nearestY + 6 * (nearestY - y));
                        x+=3; y+=3;
                    }
                    if(x>=width) break;
                }
            }
        }
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2){
        try {
            if (abs((double) (y2 - y1) / (double) (x2 - x1)) > 0.4) return;
        } catch(Exception ex){
            return;
        }
        int d=3, h=3;
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    private void changePicture() {
        try {
            BufferedImage helpImage = ImageIO.read(new File("C:\\Users\\Админ\\Downloads\\Университет\\Компьютерное_зрение\\Dumptruck\\frame" + (imageIndex++) + ".png"));
            image = new BufferedImage(helpImage.getWidth(), helpImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            image.getGraphics().drawImage(helpImage, 0, 0, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageCopy=new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        imageCopy.getGraphics().drawImage(image, 0, 0, null);
        imageLabel.setIcon(new ImageIcon(image));
    }

    private void setProperties(LayoutManager layout, boolean isResizable, int defaultCloseOperation, Rectangle rectangle) {
        setLayout(layout);
        setResizable(isResizable);
        setDefaultCloseOperation(defaultCloseOperation);
        setBounds(rectangle);
        imageLabel = new JLabel();
        addElement(imageLabel, 10, 10, 780, 480);
        changePicture();
        startButton = new JButton("Старт");
        addElement(startButton, 10, 500, 130, 30);
        resetButton = new JButton("Сбросить");
        addElement(resetButton, 150, 500, 130, 30);
    }

    private void addElement(JComponent component, int x, int y, int width, int height) {
        component.setBounds(x, y, width, height);
        add(component);
    }
}
