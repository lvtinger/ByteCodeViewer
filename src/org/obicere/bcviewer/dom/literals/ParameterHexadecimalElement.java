package org.obicere.bcviewer.dom.literals;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.HexadecimalElement;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;

/**
 */
public class ParameterHexadecimalElement extends HexadecimalElement {

    public ParameterHexadecimalElement(final String name, final int number, final DocumentBuilder builder) {
        super(name, number);

        // ensure at least 1 whitespace up the a full tab to the left
        setLeftPad(builder.getTabbedPaddingSize(getText().length(), builder.getTabSize()));

        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_PARAMETER_NUMBER));
    }
}