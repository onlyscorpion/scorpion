package org.scorpion.kernel.container.tool.disruptor.dsl;

import org.scorpion.kernel.container.tool.disruptor.EventHandler;
import org.scorpion.kernel.container.tool.disruptor.EventProcessor;
import org.scorpion.kernel.container.tool.disruptor.SequenceBarrier;


/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


class EventProcessorInfo<T>
{
    private final EventProcessor eventprocessor;
    private final EventHandler<T> handler;
    private final SequenceBarrier barrier;
    private boolean endOfChain = true;

    EventProcessorInfo(final EventProcessor eventprocessor, final EventHandler<T> handler, final SequenceBarrier barrier)
    {
        this.eventprocessor = eventprocessor;
        this.handler = handler;
        this.barrier = barrier;
    }

    public EventProcessor getEventProcessor()
    {
        return eventprocessor;
    }

    public EventHandler<T> getHandler()
    {
        return handler;
    }

    public SequenceBarrier getBarrier()
    {
        return barrier;
    }

    public boolean isEndOfChain()
    {
        return endOfChain;
    }

    public void markAsUsedInBarrier()
    {
        endOfChain = false;
    }
}
