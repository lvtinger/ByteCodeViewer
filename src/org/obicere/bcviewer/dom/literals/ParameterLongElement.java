package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class ParameterLongElement extends TextElement {

    public ParameterLongElement(final String name, final long value, final DocumentBuilder builder) {
        super(name, String.valueOf(value));

        // ensure at least 1 whitespace up the a full tab to the left
        setLeftPad(builder.getTabSize());

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_NUMBER));
    }
}
