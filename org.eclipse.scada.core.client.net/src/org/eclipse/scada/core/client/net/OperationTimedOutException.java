/*******************************************************************************
 * Copyright (c) 2010, 2011 TH4 SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TH4 SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.scada.core.client.net;

public class OperationTimedOutException extends Exception
{
    private static final long serialVersionUID = 1L;

    public OperationTimedOutException ()
    {
        super ( Messages.getString ( "OperationTimedOutException.Message" ) ); //$NON-NLS-1$
    }

}
