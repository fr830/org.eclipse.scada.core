/*******************************************************************************
 * Copyright (c) 2010 TH4 SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TH4 SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.scada.core.client.net;

public class ProtocolErrorException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 7382933809822999189L;

    public ProtocolErrorException ()
    {
        super ( "Protocol error" );
    }
}
