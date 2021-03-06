package org.obicere.bytecode.viewer.gui.swing;

import org.obicere.bytecode.viewer.configuration.OS;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.gui.EditorPanelManager;
import org.obicere.bytecode.viewer.gui.FrameManager;
import org.obicere.bytecode.viewer.gui.SettingsManager;
import org.obicere.bytecode.viewer.gui.swing.menu.MainMenuBar;
import org.obicere.bytecode.viewer.gui.swing.settings.SwingSettingsManager;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Obicere
 */
public class SwingFrameManager implements FrameManager {

    private final JFrame frame;

    private final SettingsManager<JComponent> settings;

    private final SwingEditorPanelManager editorPanels;

    private final Domain domain;

    public SwingFrameManager(final Domain domain) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.domain = domain;
        this.frame = new JFrame(domain.getApplicationName());
        this.settings = new SwingSettingsManager(frame, domain);
        this.editorPanels = new SwingEditorPanelManager(domain);
    }

    @Override
    public void open() {
        if (frame.isVisible()) {
            return;
        }
        addComponents();
        settings.initialize();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void addComponents() {
        final MainMenuBar menuBar = new MainMenuBar(domain);


        frame.setJMenuBar(menuBar);
        frame.add(editorPanels.getContent());

        frame.setDropTarget(getDropTarget());
        frame.setIconImages(Arrays.asList(domain.getIcons().getDarkApplicationImages()));
    }

    private DropTarget getDropTarget() {
        switch (OS.getOS()) {
            case WINDOWS:
                return new DropTarget() {
                    public synchronized void drop(final DropTargetDropEvent event) {
                        try {
                            event.acceptDrop(DnDConstants.ACTION_COPY);

                            final Object dropped = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                            if (dropped instanceof List) {

                                // we ca suppress this warning as this data
                                // flavor returns a List<File>
                                @SuppressWarnings("unchecked")
                                final List<File> droppedFiles = (List<File>) dropped;
                                final File[] files = droppedFiles.toArray(new File[droppedFiles.size()]);

                                domain.getClassLoader().load(files);
                            }
                        } catch (final Exception e) {
                            domain.getLogger().log(Level.WARNING, e.getMessage(), e);
                        }
                    }
                };
            default:
                return null;
        }
    }

    @Override
    public void close() {
        if (!frame.isVisible()) {
            return;
        }
        frame.dispose();
    }

    @Override
    public void requestFocus() {
        frame.requestFocus();
    }

    @Override
    public String getTitle() {
        return frame.getTitle();
    }

    @Override
    public void setTitle(final String name) {
        frame.setTitle(name);
    }

    @Override
    public void setSize(final int width, final int height) {
        frame.setSize(width, height);
    }

    @Override
    public void paint() {
        frame.repaint();
    }

    @Override
    public void validate() {
        frame.revalidate();
    }

    @Override
    public void pack() {
        frame.pack();
    }

    @Override
    public EditorPanelManager getEditorManager() {
        return editorPanels;
    }

    @Override
    public File[] promptForFiles(final String... extensions) {

        final String description = String.join(", ", extensions);
        final List<String> extensionList = Arrays.asList(extensions);

        final JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(final File f) {
                if (f.isDirectory()) {
                    return true;
                }
                final String name = f.getName();
                final int lastIndex = name.lastIndexOf('.');
                final String extension;
                if (lastIndex > 0) {
                    extension = name.substring(lastIndex);
                } else {
                    return false;
                }
                return extensionList.contains(extension);
            }

            @Override
            public String getDescription() {
                return description;
            }
        });

        final int response = chooser.showOpenDialog(frame);

        if (response == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFiles();
        } else {
            return null;
        }
    }

    @Override
    public SettingsManager<?> getSettingsManager() {
        return settings;
    }

}
