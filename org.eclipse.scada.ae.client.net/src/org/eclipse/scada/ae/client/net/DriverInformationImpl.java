/*******************************************************************************
 * Copyright (c) 2009, 2010 TH4 SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TH4 SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.scada.ae.client.net;

import org.eclipse.scada.core.ConnectionInformation;
import org.eclipse.scada.core.client.Connection;

public class DriverInformationImpl implements org.eclipse.scada.core.client.DriverInformation
{
    public Connection create ( final ConnectionInformation connectionInformation )
    {
        if ( connectionInformation.getSecondaryTarget () == null )
        {
            return null;
        }

        return new org.eclipse.scada.ae.client.net.ConnectionImpl ( connectionInformation );
    }

    public Class<?> getConnectionClass ()
    {
        return org.eclipse.scada.ae.client.net.ConnectionImpl.class;
    }

    public void validate ( final ConnectionInformation connectionInformation ) throws Throwable
    {
    }

}
