// ******************************************************************
//
// Copyright 2024 PSI Software SE. All rights reserved.
// PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
// ******************************************************************

package pl.psi;

import lombok.Builder;
import lombok.Value;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Value
@Builder
public class Resources
{
    private final int gold;
    private final int wood;
    private final int ore;
    private final int mercury;
    private final int sulfur;
    private final int cristals;
    private final int gems;

    public static Resources startRes(){
        return Resources.builder()
                .gold(15000)
                .wood(20)
                .ore(20)
                .mercury(10)
                .sulfur(10)
                .cristals(10)
                .gems(10)
                .build();
    }
}
