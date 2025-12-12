import com.intellij.openapi.ui.GraphicsConfig;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.GraphicsUtil;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class SantaClausProgressBarUi extends BasicProgressBarUI {
    private static final float ONE_OVER_SEVEN = 1f / 7;
    private static TexturePaint backgroundTexture = null;
    
    private static Paint getCandyCaneGradient(int width, int height) {
        return new LinearGradientPaint(0, JBUI.scale(2), 0, height - JBUI.scale(6),
                new float[]{0f, 0.5f, 1f},
                new Color[]{new Color(0x7A1F1A), new Color(0xA12823), new Color(0xC8322C)});
    }


    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
    public static ComponentUI createUI(JComponent c) {
        c.setBorder(JBUI.Borders.empty().asUIResource());
        return new SantaClausProgressBarUi();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(super.getPreferredSize(c).width, JBUI.scale(20));
   }

    @Override
    protected void installListeners() {
        super.installListeners();
        progressBar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
            }
        });
    }

    private volatile int offset = 0;
    private volatile int offset2 = 0;
    private volatile int velocity = 1;
    @Override
    protected void paintIndeterminate(Graphics g2d, JComponent c) {

        if (!(g2d instanceof Graphics2D)) {
            return;
        }
        Graphics2D g = (Graphics2D)g2d;


        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        //boxRect = getBox(boxRect);
        g.setColor(new JBColor(Gray._240.withAlpha(50), Gray._128.withAlpha(50)));
        int w = c.getWidth();
        int h = c.getPreferredSize().height;
        if (!isEven(c.getHeight() - h)) h++;

        Paint backgroundPaint = getCandyCaneGradient(w, h);
        g.setPaint(backgroundPaint);

        if (c.isOpaque()) {
            g.fillRect(0, (c.getHeight() - h)/2, w, h);
        }
        g.setColor(new JBColor(Gray._165.withAlpha(50), Gray._88.withAlpha(50)));
        final GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        g.translate(0, (c.getHeight() - h) / 2);
        int x = -offset;


//        LinearGradientPaint baseRainbowPaint = new LinearGradientPaint(0, JBUI.scale(2), 0, h - JBUI.scale(6),
//                new float[]{ONE_OVER_SEVEN * 1, ONE_OVER_SEVEN * 2, ONE_OVER_SEVEN * 3, ONE_OVER_SEVEN * 4, ONE_OVER_SEVEN * 5, ONE_OVER_SEVEN * 6, ONE_OVER_SEVEN * 7},
//                new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.cyan, Color.blue, VIOLET});

        Paint old = g.getPaint();
        Paint bgPaint = getCandyCaneGradient(w, h);
        g.setPaint(bgPaint);

        final float R = JBUI.scale(8f);
        final float R2 = JBUI.scale(9f);
        final Area containingRoundRect = new Area(new RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R));
//        while (x < Math.max(c.getWidth(), c.getHeight())) {
//            Path2D.Double path = new Path2D.Double();
//            float ww = getPeriodLength() / 2f;
//            path.moveTo(x, 0);
//            path.lineTo(x+ww, 0);
//            path.lineTo(x+ww - h / 2, h);
//            path.lineTo(x-h / 2, h);
//            path.lineTo(x, 0);
//            path.closePath();
//
//            final Area area = new Area(path);
//            area.intersect(containingRoundRect);
            g.fill(containingRoundRect);
//            x+= getPeriodLength();
//        }
        g.setPaint(old);
        offset = (offset + 1) % getPeriodLength();
        
        int iconWidth = SantaClausIcons.SANTA_ICON.getIconWidth();
        int iconHeight = SantaClausIcons.SANTA_ICON.getIconHeight();
        int targetHeight = (int) (h * 0.8);
        double scale = (double) targetHeight / iconHeight;
        int scaledIconWidth = (int) (iconWidth * scale);
        int minLeftBoundary = scaledIconWidth + JBUI.scale(2);
        
        offset2 += velocity;
        if (offset2 <= minLeftBoundary) {
            offset2 = minLeftBoundary;
            velocity = 1;
        } else if (offset2 >= w - JBUI.scale(15)) {
            offset2 = w - JBUI.scale(15);
            velocity = -1;
        }
//        offset2 = (offset2 + 1) % (w - 3);
        Area area = new Area(new Rectangle2D.Float(0, 0, w, h));
        area.subtract(new Area(new RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R)));
        g.setPaint(Gray._128);
//        g.setPaint(baseRainbowPaint);
        if (c.isOpaque()) {
            g.fill(area);
        }

        area.subtract(new Area(new RoundRectangle2D.Float(0, 0, w, h, R2, R2)));

        Container parent = c.getParent();
        Color background = parent != null ? parent.getBackground() : UIUtil.getPanelBackground();
        g.setPaint(background);
//        g.setPaint(baseRainbowPaint);
        if (c.isOpaque()) {
            g.fill(area);
        }

//        g.setPaint(baseRainbowPaint);

        Icon icon = SantaClausIcons.SANTA_ICON;
        paintScaledIcon(g, icon, offset2 - JBUI.scale(10), 0, h, velocity < 0);

        g.draw(new RoundRectangle2D.Float(1f, 1f, w - 2f - 1f, h - 2f -1f, R, R));
        g.translate(0, -(c.getHeight() - h) / 2);

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, boxRect.x, boxRect.width);
            }
            else {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, boxRect.y, boxRect.height);
            }
        }
        config.restore();
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        if (progressBar.getOrientation() != SwingConstants.HORIZONTAL || !c.getComponentOrientation().isLeftToRight()) {
            super.paintDeterminate(g, c);
            return;
        }
        final GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        Insets b = progressBar.getInsets(); // area for border
        int w = progressBar.getWidth();
        int h = progressBar.getPreferredSize().height;
        if (!isEven(c.getHeight() - h)) h++;

        int barRectWidth = w - (b.right + b.left);
        int barRectHeight = h - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

        Container parent = c.getParent();
        Color background = parent != null ? parent.getBackground() : UIUtil.getPanelBackground();

        g.setColor(background);
        Graphics2D g2 = (Graphics2D)g;
        if (c.isOpaque()) {
            g.fillRect(0, 0, w, h);
        }

        final float R = JBUI.scale(8f);
        final float R2 = JBUI.scale(9f);
        final float off = JBUI.scale(1f);

        g2.translate(0, (c.getHeight() - h)/2);
        g2.setColor(progressBar.getForeground());
        g2.fill(new RoundRectangle2D.Float(0, 0, w - off, h - off, R2, R2));
        g2.setColor(background);
        g2.fill(new RoundRectangle2D.Float(off, off, w - 2f*off - off, h - 2f*off - off, R, R));
//        g2.setColor(progressBar.getForeground());
        Paint bgPaint = getCandyCaneGradient(w, h);
        g2.setPaint(bgPaint);

        paintScaledIcon(g2, SantaClausIcons.SANTA_ICON, amountFull + JBUI.scale(2), 0, h, false);
        g2.fill(new RoundRectangle2D.Float(2f*off,2f*off, amountFull - JBUI.scale(5f), h - JBUI.scale(5f), JBUI.scale(7f), JBUI.scale(7f)));
        g2.translate(0, -(c.getHeight() - h)/2);

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            paintString(g, b.left, b.top,
                    barRectWidth, barRectHeight,
                    amountFull, b);
        }
        config.restore();
    }

    private void paintString(Graphics g, int x, int y, int w, int h, int fillStart, int amountFull) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Graphics2D g2 = (Graphics2D)g;
        String progressString = progressBar.getString();
        g2.setFont(progressBar.getFont());
        Point renderLocation = getStringPlacement(g2, progressString,
                x, y, w, h);
        Rectangle oldClip = g2.getClipBounds();

        if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
            g2.setColor(getSelectionBackground());
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(fillStart, y, amountFull, h);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
        } else { // VERTICAL
            g2.setColor(getSelectionBackground());
            AffineTransform rotate =
                    AffineTransform.getRotateInstance(Math.PI/2);
            g2.setFont(progressBar.getFont().deriveFont(rotate));
            renderLocation = getStringPlacement(g2, progressString,
                    x, y, w, h);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(x, fillStart, w, amountFull);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
        }
        g2.setClip(oldClip);
    }

    @Override
    protected int getBoxLength(int availableLength, int otherDimension) {
        return availableLength;
    }

    private int getPeriodLength() {
        return JBUI.scale(16);
    }

    private static boolean isEven(int value) {
        return value % 2 == 0;
    }

    private void paintScaledIcon(Graphics2D g, Icon icon, int x, int y, int barHeight, boolean flipHorizontal) {
        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();
        
        int targetHeight = (int) (barHeight * 0.8);
        double scale = (double) targetHeight / iconHeight;
        int scaledWidth = (int) (iconWidth * scale);
        int scaledHeight = (int) (iconHeight * scale);
        
        int centeredY = (barHeight - scaledHeight) / 2;
        
        if (icon instanceof ImageIcon) {
            ImageIcon imageIcon = (ImageIcon) icon;
            AffineTransform oldTransform = g.getTransform();
            g.translate(x, y + centeredY);
            if (flipHorizontal) {
                g.translate(scaledWidth, 0);
                g.scale(-scale, scale);
            } else {
                g.scale(scale, scale);
            }
            imageIcon.paintIcon(progressBar, g, 0, 0);
            g.setTransform(oldTransform);
        } else {
            Image image = iconToImage(icon);
            if (image != null) {
                if (flipHorizontal) {
                    AffineTransform oldTransform = g.getTransform();
                    g.translate(x + scaledWidth, y + centeredY);
                    g.scale(-1, 1);
                    g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
                    g.setTransform(oldTransform);
                } else {
                    g.drawImage(image, x, y + centeredY, scaledWidth, scaledHeight, null);
                }
            } else {
                icon.paintIcon(progressBar, g, x, y + centeredY);
            }
        }
    }

    private Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
}

