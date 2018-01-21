package FixieGui;

import java.awt.Component;
import javax.swing.plaf.ComponentUI;
import javax.swing.*;

/**
 * Created by lmancini on 1/15/18.
 */
public class NoWrapTextPane extends JTextPane
{
    public NoWrapTextPane()
    {
        super();
        this.noWrap_ = true;
    }

    public void setNoWrap_(boolean noWrap) {
        this.noWrap_ = noWrap;
    }

    @Override
    public boolean getScrollableTracksViewportWidth()
    {
        if (false == noWrap_)
        {
            // NoWrap is disabled so always return true
            return true;
        }

        Component parent = getParent();
        ComponentUI ui = getUI();

        if (null == parent) {
            return true;
        }
        return (ui.getPreferredSize(this).width <= parent.getSize().width);
    }

    private boolean noWrap_;
}
