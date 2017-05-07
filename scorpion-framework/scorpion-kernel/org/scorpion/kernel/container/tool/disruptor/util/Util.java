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
package org.scorpion.kernel.container.tool.disruptor.util;

import java.lang.reflect.Field;

import org.scorpion.kernel.container.tool.disruptor.EventProcessor;
import org.scorpion.kernel.container.tool.disruptor.Sequence;

import sun.misc.Unsafe;

/**
 * Set of common functions used by the Disruptor
 */
public final class Util
{
    /**
     * Calculate the next power of 2, greater than or equal to x.<p>
     * From Hacker's Delight, Chapter 3, Harry S. Warren Jr.
     *
     * @param x Value to round up
     * @return The next power of 2 from x inclusive
     */
    public static int ceilingNextPowerOfTwo(final int x)
    {
        return 1 << (32 - Integer.numberOfLeadingZeros(x - 1));
    }

    /**
     * @param sequences to compare.
     * @return the minimum sequence found or Long.MAX_VALUE if the array is empty.
     */
    public static long getMinimumSequence(final Sequence[] sequences)
    {
        long minimum = Long.MAX_VALUE;

        for (Sequence sequence : sequences)
        {
            long value = sequence.get();
            minimum = minimum < value ? minimum : value;
        }

        return minimum;
    }

    /**
     * Get an array of {@link Sequence}s for the passed {@link EventProcessor}s
     *
     * @param processors for which to get the sequences
     * @return the array of {@link Sequence}s
     */
    public static Sequence[] getSequencesFor(final EventProcessor... processors)
    {
        Sequence[] sequences = new Sequence[processors.length];
        for (int i = 0; i < sequences.length; i++)
        {
            sequences[i] = processors[i].getSequence();
        }

        return sequences;
    }

    private static final Unsafe THE_UNSAFE;
    static
    {
        try
        {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            THE_UNSAFE = (Unsafe)theUnsafe.get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to load unsafe", e);
        }        
    }
    
    public static Unsafe getUnsafe()
    {
        return THE_UNSAFE;
    }
}
