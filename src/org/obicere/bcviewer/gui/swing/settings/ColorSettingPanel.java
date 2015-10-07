package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.settings.Settings;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 */
public class ColorSettingPanel extends SettingPanel<Color> {

    private final JLabel descriptor;

    private final JButton colorChooser;

    public ColorSettingPanel(final Domain domain, final Setting<Color> setting) {
        super(setting);

        this.descriptor = new JLabel(setting.getDescriptor());
        this.colorChooser = new JButton();

        final Color selected;
        final Color color = setting.getValue();
        if (color == null) {
            selected = setting.getDefaultValue();
        } else {
            selected = color;
        }

        colorChooser.addActionListener(e -> {
            final Color newColor = JColorChooser.showDialog(ColorSettingPanel.this, "Pick new color", selected);
            if (newColor == null) {
                return;
            }
            final Settings settings = domain.getSettingsController().getSettings();
            final String name = setting.getName();

            settings.set(name, newColor);

            setValue(newColor);
        });
        final Dimension size = new Dimension(16, 16);
        colorChooser.setMinimumSize(size);
        colorChooser.setMaximumSize(size);
        colorChooser.setPreferredSize(size);
        colorChooser.setSize(size);

        final BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);

        add(descriptor);
        add(Box.createHorizontalGlue());
        add(colorChooser);
    }

    @Override
    public void setValue(final Object value) {
        final Color color = (Color) value;
        final Icon icon = new ImageIcon(buildImage(color));

        colorChooser.setIcon(icon);
    }

    private Image buildImage(final Color color) {
        final BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        final Graphics g = image.getGraphics();

        g.setColor(color);
        g.fillRect(0, 0, 15, 15);
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, 15, 15);

        g.dispose();

        return image;
    }
}