package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.ElementValue;
import org.obicere.bytecode.core.objects.ElementValuePair;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ElementValuePairModeler implements Modeler<ElementValuePair> {
    @Override
    public void model(final ElementValuePair element, final DocumentBuilder builder) {
        final int elementNameIndex = element.getElementNameIndex();
        final ElementValue value = element.getValue();

        final ConstantPool constantPool = builder.getConstantPool();
        builder.add(constantPool.getAsString(elementNameIndex));
        builder.add(" = ");
        builder.model(value);
    }
}
