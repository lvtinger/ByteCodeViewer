package org.obicere.bytecode.viewer.gui.swing.tree;

import org.obicere.bytecode.core.objects.DefaultJCClass;
import org.obicere.bytecode.viewer.configuration.Icons;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

/**
 */
public class ByteCodeTree extends JTree {

    private ByteCodeTreeNode root;

    private final DefaultTreeModel model;

    private final Domain domain;

    public ByteCodeTree(final Domain domain) {
        this.domain = domain;
        this.model = (DefaultTreeModel) getModel();

        setCellRenderer(new ByteCodeTreeCellRenderer());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (getSelectionPath() == null) {
                        final TreePath path = getPathForLocation(e.getX(), e.getY());
                        setSelectionPath(path);
                    }
                }
            }

            @Override
            public void mouseClicked(final MouseEvent e) {
                // to simulate double-click getInputStream for a file explorer
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {

                    final TreePath path = getSelectionPath();
                    if (path == null) {
                        return;
                    }
                    final Object[] userPath = path.getPath();
                    final StringBuilder className = new StringBuilder();

                    boolean first = true;
                    // we start at 1 to avoid the empty root node
                    for (int i = 1; i < userPath.length; i++) {
                        final Object userObject = userPath[i];
                        if (!first) {
                            className.append('/');
                        }
                        className.append(userObject);
                        first = false;
                    }

                    domain.getGUIManager().getFrameManager().getEditorManager().displayEditorPanel(className.toString());
                }
            }
        });
    }

    public void addClass(final DefaultJCClass file) {
        addClassToTree(file.getName(), file.getAccessFlags());
    }

    public void addClass(final DefaultJCClass file, final int accessFlags) {
        addClassToTree(file.getName(), accessFlags);
    }

    private void addClassToTree(final String name, final int accessFlags) {
        final String className = ByteCodeUtils.getClassName(name);
        final String packageName = ByteCodeUtils.getPackage(name);

        synchronized (getTreeLock()) {
            if (root == null) {
                root = createPackagePart("Classes");

                model.setRoot(root);
            }

            final ByteCodeTreeNode possiblePackage = getPackage(packageName);
            final ByteCodeTreeNode absolutePackage;
            if (possiblePackage == null) {
                absolutePackage = addPackage(packageName);
            } else {
                absolutePackage = possiblePackage;
            }

            if (hasChildByName(absolutePackage, className)) {
                return;
            }
            final ByteCodeTreeNode node = ByteCodeTreeNode.buildNode(domain, name, accessFlags);

            model.insertNodeInto(node, absolutePackage, absolutePackage.getIndexFor(node));
            expandPath(new TreePath(absolutePackage.getPath()));
        }
    }

    public void removeClass(final String name) {
        final String packageName = ByteCodeUtils.getPackage(name);
        final String className = ByteCodeUtils.getQualifiedName(name);

        synchronized (getTreeLock()) {
            final ByteCodeTreeNode node = getPackage(packageName);
            if (hasChildByName(node, name)) {
                final Enumeration enumeration = node.children();
                while (enumeration.hasMoreElements()) {
                    final ByteCodeTreeNode next = (ByteCodeTreeNode) enumeration.nextElement();
                    if (next.getUserObject().equals(className)) {
                        node.remove(next);
                    }
                }
                collapseIfEmpty(node);
            }
        }
    }

    private void collapseIfEmpty(final ByteCodeTreeNode node) {
        if (node.isLeaf()) {
            final ByteCodeTreeNode parent = (ByteCodeTreeNode) node.getParent();
            parent.remove(node);
            collapseIfEmpty(node);
        }
    }

    private ByteCodeTreeNode addPackage(final String name) {
        final String[] split = name.split("\\.");
        ByteCodeTreeNode root = this.root;

        int i = 0;
        while (i < split.length) {
            final ByteCodeTreeNode node = getPackagePart(root, split[i]);
            if (node == null) {
                break;
            }
            root = node;
            i++;
        }
        while (i < split.length) {
            root = addPackagePart(root, split[i++]);
        }
        return root;
    }

    private ByteCodeTreeNode addPackagePart(final ByteCodeTreeNode node, final String name) {
        if (hasChildByName(node, name)) {
            return null;
        }
        final ByteCodeTreeNode packageNode = createPackagePart(name);

        model.insertNodeInto(packageNode, node, node.getIndexFor(packageNode));

        return packageNode;
    }

    private ByteCodeTreeNode createPackagePart(final String name) {
        final Icons icons = domain.getIcons();
        return new ByteCodeTreeNode(icons.getIcon(Icons.ICON_PACKAGE), icons.getIcon(Icons.ICON_PACKAGE_DISABLED), name, true);
    }

    private boolean hasChildByName(final ByteCodeTreeNode node, final String name) {
        final Enumeration children = node.children();
        while (children.hasMoreElements()) {
            final ByteCodeTreeNode next = (ByteCodeTreeNode) children.nextElement();
            if (name.equals(next.getUserObject())) {
                return true;
            }
        }
        return false;
    }

    public ByteCodeTreeNode getPackage(final String name) {
        synchronized (getTreeLock()) {
            if (root == null) {
                return null;
            }
            if (name == null || name.isEmpty()) {
                return root;
            }
            final String[] split = name.split("\\.");
            ByteCodeTreeNode root = this.root;
            for (final String item : split) {
                final ByteCodeTreeNode node = getPackagePart(root, item);
                if (node == null) {
                    return null;
                }
                root = node;
            }
            return root;
        }
    }

    private ByteCodeTreeNode getPackagePart(final ByteCodeTreeNode node, final String name) {
        final Enumeration children = node.children();
        while (children.hasMoreElements()) {
            final ByteCodeTreeNode next = (ByteCodeTreeNode) children.nextElement();
            if (name.equals(next.getUserObject())) {
                return next;
            }
        }
        return null;
    }
}
