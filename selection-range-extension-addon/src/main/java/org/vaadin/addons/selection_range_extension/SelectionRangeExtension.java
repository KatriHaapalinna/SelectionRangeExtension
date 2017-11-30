package org.vaadin.addons.selection_range_extension;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.vaadin.addons.selection_range_extension.client.SelectionRangeExtensionServerRpc;
import org.vaadin.addons.selection_range_extension.client.SelectionRangeExtensionState;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.AbstractTextField;

public class SelectionRangeExtension extends AbstractExtension {

    private final Set<SelectionRangeListener> listeners = new HashSet<SelectionRangeListener>();
    private int[] selection = { 0, 0 };

    protected SelectionRangeExtension() {
        registerRpc(new SelectionRangeExtensionServerRpc() {

            @Override
            public void selectionChanged(int start, int end) {
                for (SelectionRangeListener l : listeners) {
                    l.selectionChanged(start, end);
                }

            }

            @Override
            public void selectionUpdated(int startOfSelection,
                    int endOfSelection) {
                selection[0] = startOfSelection;
                selection[1] = endOfSelection;
            }
        });
    }

    @Override
    protected SelectionRangeExtensionState getState() {
        return (SelectionRangeExtensionState) super.getState();
    }

    /**
     *
     * @param component
     *            AbstractTextField
     * @param listener
     *            {@link SelectionRangeExtension.SelectionRangeListener}
     * @param listenEmptySelection
     *            boolean
     * @return SelectionRangeExtension
     */
    public static SelectionRangeExtension extend(AbstractTextField component,
            SelectionRangeListener listener, boolean listenEmptySelection) {
        SelectionRangeExtension e = new SelectionRangeExtension();
        e.listeners.add(listener);
        e.getState().listenEmptySelection = listenEmptySelection;
        e.extend(component);
        return e;
    }

    public boolean isListeningForEmptySelection() {
        return this.getState().listenEmptySelection;
    }

    /**
     * Listener that fires when input field's selection is changed
     *
     */
    public interface SelectionRangeListener extends Serializable {
        /**
         * Event is fired when input field's selection is changed
         *
         * @param selectionStart
         *            int
         * @param selectionEnd
         *            int
         */
        public void selectionChanged(int selectionStart, int selectionEnd);
    }
}
