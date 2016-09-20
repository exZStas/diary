package com.vm62.diary.frontend.client.common.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.constants.CenterOn;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTitle;

/**
 * inheriting you dialog box from this class helps you modal dialog to close on CloseDialogBoxEvent fired
 */
public abstract class CDialogBox extends Composite {

    private final MaterialModal root;

    private static CDialogBox dialogOnTop = null;

    private CDialogBox dialogOnTopLast = null;

    private HandlerRegistration handlerNativePreviewRegistration = null;

    interface CDialogBoxUiBinder extends UiBinder<MaterialModal, CDialogBox> {}

    private static CDialogBoxUiBinder uiBinder = GWT.create(CDialogBoxUiBinder.class);

    public CDialogBox() {

        root = uiBinder.createAndBindUi(this);
        super.initWidget(root);

        root.addCloseHandler(new CloseHandler<MaterialModal>() {
            @Override
            public void onClose(CloseEvent<MaterialModal> event) {
                hideIfVisible();
            }
        });

        //remove margin defined by MaterialTitle constructor
        getTitleElement().getWidget(0).getElement().getStyle().clearMarginTop();
        getTitleElement().setPaddingTop(24);
        getTitleElement().setPaddingBottom(10);
        getTitleElement().setPaddingLeft(24);

    }

    protected void registerKeyEvents() {
        if (null == handlerNativePreviewRegistration) {
            handlerNativePreviewRegistration = Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
                @Override
                public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
                    if (event.isCanceled() || event.isConsumed()) {
                        event.cancel();
                        return;
                    }
                    if (isVisible(root.getElement()) && CDialogBox.this == dialogOnTop) {
                        NativeEvent nativeEvent = event.getNativeEvent();
                        if (nativeEvent.getKeyCode() > 0) {
                            if (!isCheckEventTarget(nativeEvent)) {
                                event.cancel();
                            } else {
                                switch (event.getTypeInt()) {
                                    case Event.ONKEYUP:
                                        doOnKeyUp(nativeEvent);
                                        break;

                                    case Event.ONKEYPRESS:
                                        doOnKeyPress(nativeEvent);
                                        break;

                                }
                                if (CDialogBox.this != dialogOnTop) {
                                    nativeEvent.preventDefault();
                                    nativeEvent.stopPropagation();
                                    event.cancel();
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private boolean isCheckEventTarget(NativeEvent event) {
        EventTarget target = event.getEventTarget();
        if (Element.is(target)) {
            return getElement().isOrHasChild(Element.as(target));
        }
        return false;
    }

    protected void unregisterKeyEvents() {
        if (null != handlerNativePreviewRegistration) {
            handlerNativePreviewRegistration.removeHandler();
            handlerNativePreviewRegistration = null;
        }
    }

    public void doOnKeyUp(NativeEvent event) {
        event.stopPropagation();
    }

    public void doOnKeyPress(NativeEvent event) {
        event.stopPropagation();
    }

    private MaterialModalContent getContentElement() {
        return (MaterialModalContent) root.getWidget(2);
    }

    private MaterialTitle getTitleElement() {
        return (MaterialTitle) root.getWidget(0);
    }

    private MaterialRow getContainerElement() {
        return (MaterialRow) getContentElement().getWidget(0);
    }

    public void setCaptionHtml(String text) {
        getTitleElement().setTitle(text);
    }

    @Override
    public void setWidget(final Widget widget) {
        getContainerElement().add(widget);
    }

    public void center() {
        show();
    }

    public void show() {
        RootPanel.get().add(this);
        root.openModal();
        root.setCenterOn(CenterOn.CENTER_ON_SMALL);
        registerKeyEvents();
        dialogOnTopLast = dialogOnTop;
        dialogOnTop = this;
    }

    public void hideIfVisible() {
        if (isVisible()) {
            hide();
        }
    }

    public void hide() {
        unregisterKeyEvents();
        root.closeModal();
        removeFromParent();
        dialogOnTop = dialogOnTopLast;
    }

    public void setDismissable(boolean dismissable) {
        root.setDismissable(dismissable);
    }

}
