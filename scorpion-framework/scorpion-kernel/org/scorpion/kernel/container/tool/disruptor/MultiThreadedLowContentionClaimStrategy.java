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
package org.scorpion.kernel.container.tool.disruptor;


/**
 * Strategy to be used when there are multiple publisher threads claiming sequences.
 *
 * This strategy requires sufficient cores to allow multiple publishers to be concurrently claiming sequences and those
 * thread a contented relatively infrequently.
 */
public final class MultiThreadedLowContentionClaimStrategy
    extends AbstractMultithreadedClaimStrategy
{
    /**
     * Construct a new multi-threaded publisher {@link ClaimStrategy} for a given buffer size.
     *
     * @param bufferSize for the underlying data structure.
     */
    public MultiThreadedLowContentionClaimStrategy(final int bufferSize)
    {
        super(bufferSize);
    }

    @Override
    public void serialisePublishing(final long sequence, final Sequence cursor, final int batchSize)
    {
        final long expectedSequence = sequence - batchSize;
        while (expectedSequence != cursor.get())
        {
            // busy spin
        }

        cursor.set(sequence);
    }
}
