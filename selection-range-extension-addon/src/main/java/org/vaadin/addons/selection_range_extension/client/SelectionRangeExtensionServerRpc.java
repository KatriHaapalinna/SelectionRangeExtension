package org.vaadin.addons.selection_range_extension.client;

import com.vaadin.shared.communication.ServerRpc;

// ServerRpc is used to pass events from client to server
public interface SelectionRangeExtensionServerRpc extends ServerRpc {

    /**
     *
     * @param startOfSelection
     *            start of selection, 0-indexed inclusive
     * @param endOfSelection
     *            end of selection, 0-indexed exclusive
     */
    public void selectionChanged(int startOfSelection, int endOfSelection);

    public void selectionUpdated(int startOfSelection, int endOfSelection);

}
