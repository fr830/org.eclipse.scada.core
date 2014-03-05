/*******************************************************************************
 * Copyright (c) 2014 IBH SYSTEMS GmbH Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.scada.da.server.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.scada.da.server.browser.common.FolderCommon;

public class ComponentNode
{

    private final Map<String, ComponentNode> nodes = new HashMap<> ();

    private final Map<String, Component> components = new HashMap<> ();

    private final FolderCommon folder;

    private final ComponentNode parentNode;

    public ComponentNode ( final ComponentNode parentNode, final FolderCommon folder )
    {
        this.parentNode = parentNode;
        this.folder = folder;
    }

    public void registerComponent ( final LinkedList<String> prefix, final ComponentFolder componentFolder, final Component component )
    {
        // first get the name
        final String next = prefix.pop ();

        if ( prefix.isEmpty () )
        {
            add ( next, componentFolder, component );
        }
        else
        {
            // add another sub level
            ComponentNode node = this.nodes.get ( next );
            if ( node == null )
            {
                if ( this.components.containsKey ( next ) )
                {
                    // blocked by component

                    // remove all folders we might have created
                    checkRemove ();
                    // throw exception
                    throw new IllegalStateException ( "Namespace blocked by other component" );
                }

                final FolderCommon folder = new FolderCommon ();
                this.folder.add ( next, folder, null );

                node = new ComponentNode ( this, folder );
                this.nodes.put ( next, node );
            }
            node.registerComponent ( prefix, componentFolder, component );
        }
    }

    public void unregisterComponent ( final LinkedList<String> prefix, final Component component )
    {
        // first get the name
        final String next = prefix.pop ();

        if ( prefix.isEmpty () )
        {
            remove ( next, component );
        }
        else
        {
            final ComponentNode node = this.nodes.get ( next );
            if ( node == null )
            {
                return;
            }
            node.unregisterComponent ( prefix, component );
        }
    }

    private void add ( final String name, final ComponentFolder componentFolder, final Component component )
    {
        if ( this.components.containsKey ( name ) )
        {
            throw new IllegalStateException ( "There is already a component registered with this name" );
        }
        if ( !this.folder.add ( name, componentFolder, null ) )
        {
            throw new IllegalStateException ( "Namespace is blocked by other component" );
        }
    }

    private void remove ( final String name, final Component component )
    {
        final Component c = this.components.get ( name );
        if ( c == component )
        {
            // it is our component
            this.components.remove ( name );
            this.folder.remove ( name );
            checkRemove ();
        }
    }

    private void checkRemove ()
    {
        if ( this.parentNode != null && this.folder.size () == 0 )
        {
            this.parentNode.remove ( this );
        }
    }

    private void remove ( final ComponentNode componentNode )
    {
        final Iterator<Map.Entry<String, ComponentNode>> i = this.nodes.entrySet ().iterator ();
        while ( i.hasNext () )
        {
            if ( i.next ().getValue () == componentNode )
            {
                i.remove ();
            }
        }

        checkRemove ();
    }

}