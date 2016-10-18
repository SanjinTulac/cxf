/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.jaxrs.nio;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.continuations.ContinuationCallback;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.ServiceInvokerInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class NioInInterceptor extends AbstractPhaseInterceptor<Message> {
    public NioInInterceptor() {
        super(Phase.INVOKE);
        addAfter(ServiceInvokerInterceptor.class.getName());
    }

    public void handleMessage(Message message) throws Fault {
        final Exchange exchange = message.getExchange();
        if (exchange.isOneWay()) {
            return;
        }
        
        if (exchange.getOutMessage() != null) {
            final Message out = exchange.getOutMessage();
            
            Object result = out.getContent(Object.class);
            if (result == null) {
                result = out.getContent(List.class);
                if (result != null) {
                    final List<?> results = (List<?>)result;
                    if (!results.isEmpty()) {
                        result = results.get(0);
                    }
                }
            }
            
            Object entity = result;
            if (result instanceof Response) {
                entity = ((Response)result).getEntity();
            }
            
            if (entity instanceof NioWriteEntity) {
                message.getExchange().put(ContinuationCallback.class, new ContinuationCallback() {
                    @Override
                    public void onError(Throwable error) {
                        System.out.println("onError()");
                    }
                    
                    @Override
                    public void onDisconnect() {
                        System.out.println("onDisconnect()");
                    }
                    
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete()");
                    }
                });

                
                final ContinuationProvider provider = message.get(ContinuationProvider.class);
                final Continuation continuation = provider.getContinuation();
                continuation.suspend(0);
            }
        }
    }
}
