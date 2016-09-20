package com.vm62.diary.frontend.client.common.components;


import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import gwt.material.design.client.ui.MaterialIcon;

import static com.google.gwt.dom.client.BrowserEvents.CLICK;

/**
 * Use this class for creating clickable icon cell in data grid.
 *
 */

public class MaterialIconCell extends AbstractCell<MaterialIcon>  {

    public MaterialIconCell(){
        super("click");
    }

    @Override
    public void render(Context context, MaterialIcon widgets, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.appendHtmlConstant(DOM.toString(widgets.getElement()));
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, MaterialIcon value, NativeEvent event, ValueUpdater<MaterialIcon> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);

        if (CLICK.equals(event.getType())) {
            EventTarget eventTarget = event.getEventTarget();
            if (!Element.is(eventTarget)) {
                return;
            }
            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
                // Ignore clicks that occur outside of the main element.
                onEnterKeyDown(context, parent, value, event, valueUpdater);
            }
        }
    }

    @Override
    protected void onEnterKeyDown(Context context, Element parent, MaterialIcon value, NativeEvent event, ValueUpdater<MaterialIcon> valueUpdater) {
        if(valueUpdater != null){
            valueUpdater.update(value);
        }
    }
}
