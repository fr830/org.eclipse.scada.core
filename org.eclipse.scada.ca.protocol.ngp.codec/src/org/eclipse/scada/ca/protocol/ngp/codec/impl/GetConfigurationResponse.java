/*******************************************************************************
 * Copyright (c) 2010, 2013 TH4 SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TH4 SYSTEMS GmbH - initial API and implementation
 *     Jens Reimann - implement security callback system
 *******************************************************************************/
package org.eclipse.scada.ca.protocol.ngp.codec.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.eclipse.scada.core.ngp.common.codec.osbp.BinaryContext;
import org.eclipse.scada.core.ngp.common.codec.osbp.BinaryMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetConfigurationResponse implements BinaryMessageCodec
{
    private final static Logger logger = LoggerFactory.getLogger ( GetConfigurationResponse.class );

    public static final int MESSAGE_CODE = 16390;

    @Override
    public int getMessageCode ()
    {
        return MESSAGE_CODE;
    }

    @Override
    public Class<?> getMessageClass ()
    {
        return org.eclipse.scada.ca.data.message.GetConfigurationResponse.class;
    }

    @Override
    public org.eclipse.scada.ca.data.message.GetConfigurationResponse decodeMessage ( final BinaryContext _context, final IoBuffer _data ) throws Exception
    {
        // message code
        {
            final int messageCode = _data.getInt ();

            if ( messageCode != MESSAGE_CODE )
            {
                throw new IllegalStateException ( String.format ( "Expected messageCode %s but found %s", MESSAGE_CODE, messageCode ) );
            }
        }

        final byte numberOfFields = _data.get ();

        // decode attributes

        org.eclipse.scada.core.data.Response response = null;
        org.eclipse.scada.ca.data.ConfigurationInformation configuration = null;

        logger.trace ( "Decoding {} fields", numberOfFields );

        for ( int i = 0; i < numberOfFields; i++ )
        {

            final byte fieldNumber = _data.get ();
            switch ( fieldNumber )
            {
                case 1:
                {
                    response = org.eclipse.scada.core.protocol.ngp.codec.Structures.decodeResponse ( _context, _data, false );
                }
                    break;
                case 2:
                {
                    configuration = org.eclipse.scada.ca.protocol.ngp.codec.Structures.decodeConfigurationInformation ( _context, _data, false );
                }
                    break;
                default:
                    logger.warn ( "Received unknown field number: {}", fieldNumber );
                    break;
            }

        }

        // create object
        return new org.eclipse.scada.ca.data.message.GetConfigurationResponse ( response, configuration );
    }

    @Override
    public IoBuffer encodeMessage ( final BinaryContext context, final Object objectMessage ) throws Exception
    {
        final org.eclipse.scada.ca.data.message.GetConfigurationResponse value = (org.eclipse.scada.ca.data.message.GetConfigurationResponse)objectMessage;

        final IoBuffer data = IoBuffer.allocate ( 64 );
        data.setAutoExpand ( true );

        // encode message base
        data.putInt ( MESSAGE_CODE );

        // number of fields 
        data.put ( (byte)2 );

        // encode attributes
        org.eclipse.scada.core.protocol.ngp.codec.Structures.encodeResponse ( context, data, (byte)1, value.getResponse () );
        org.eclipse.scada.ca.protocol.ngp.codec.Structures.encodeConfigurationInformation ( context, data, (byte)2, value.getConfiguration () );

        data.flip ();
        return data;
    }

}
