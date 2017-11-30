package org.vaadin.addons.selection_range_extension.client;

import org.vaadin.addons.selection_range_extension.SelectionRangeExtension;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.textfield.AbstractTextFieldConnector;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(SelectionRangeExtension.class)
public class SelectionRangeExtensionConnector
        extends AbstractExtensionConnector {
    private TextBoxBase widget;
    private boolean listenEmptySelection = false;

    // ServerRpc is used to send events to server. Communication implementation
    // is automatically created here
    SelectionRangeExtensionServerRpc rpc = RpcProxy
            .create(SelectionRangeExtensionServerRpc.class, this);

    public SelectionRangeExtensionConnector() {
    }

    @Override
    protected void extend(ServerConnector target) {
        widget = (TextBoxBase) ((AbstractTextFieldConnector) target)
                .getWidget();
        listenEmptySelection = getState().listenEmptySelection;
        if (listenEmptySelection) {
            addEmptySelectionHandler(widget.getElement());
        } else {
            addSelectionHandler(widget.getElement());
        }
    }

    @Override
    public SelectionRangeExtensionState getState() {
        return (SelectionRangeExtensionState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
    }

    private final native void addEmptySelectionHandler(Element el)
    /*-{
        var self = this;
    
        var selectionStart = 0;
        var selectionEnd = 0;
        var s = "mouseup mouseover keyup select click blur";
        s.split(' ').forEach(function(e) { el.addEventListener(e, function () {
            if ((el.selectionStart != el.selectionEnd && (selectionStart != el.selectionStart || selectionEnd != el.selectionEnd))
                    || (el.selectionStart == el.selectionEnd && selectionStart != selectionEnd)) {
                self.@org.vaadin.addons.selection_range_extension.client.SelectionRangeExtensionConnector::selectionChanged(Ljava/lang/Double;Ljava/lang/Double;)(el.selectionStart,el.selectionEnd);
            }
            selectionStart = el.selectionStart;
            selectionEnd = el.selectionEnd;
            el.click();
        }, false)});
    }-*/;

    private final native void addSelectionHandler(Element el)
    /*-{
        var self = this;
        var selectionStart = 0;
        var selectionEnd = 0;
        var s = "mouseup mouseover keyup select click blur";
        s.split(' ').forEach(function(e) { el.addEventListener(e, function () {
            if (el.selectionStart != el.selectionEnd && (selectionStart != el.selectionStart || selectionEnd != el.selectionEnd)) {
                self.@org.vaadin.addons.selection_range_extension.client.SelectionRangeExtensionConnector::selectionChanged(Ljava/lang/Double;Ljava/lang/Double;)(el.selectionStart,el.selectionEnd);
            }
            selectionStart = el.selectionStart;
            selectionEnd = el.selectionEnd;
            el.click();
        }, false);});
    }-*/;

    private void selectionChanged(Double s, Double e) {
        int start = (int) (1 * s);
        int end = (int) (1 * e);
        rpc.selectionChanged(start, end);
    }
}
