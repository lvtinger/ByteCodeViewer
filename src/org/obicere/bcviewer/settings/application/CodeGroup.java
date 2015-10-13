package org.obicere.bcviewer.settings.application;

import org.obicere.bcviewer.settings.Group;
import org.obicere.bcviewer.settings.target.BooleanSetting;
import org.obicere.bcviewer.settings.target.IntegerSetting;
import org.obicere.bcviewer.settings.target.Setting;

/**
 */
public class CodeGroup implements Group {

    private static final String NAME = "Code";

    private static final String SUFFIX = "code.";

    private static final IntegerSetting TAB_SIZE = new IntegerSetting(SUFFIX + "tabSize", "Tab size", 4, 1, Integer.MAX_VALUE);

    private static final BooleanSetting INCLUDE_CONSTANT_POOL = new BooleanSetting(SUFFIX + "includeConstantPool", "Include Constant Pool", false);

    private static final Setting<?>[] SETTINGS = new Setting[]{
            TAB_SIZE,
            INCLUDE_CONSTANT_POOL
    };

    @Override
    public String getGroupName() {
        return NAME;
    }

    @Override
    public Setting<?>[] getSettings() {
        return SETTINGS;
    }
}
