/*
 * Copyright 2009 Erhard Kuenzel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cTree.cCombine;

import cTree.CElement;
import cTree.CType;

public class CCombiner1StrichIdent extends CCombiner1 {
    public CCombiner1StrichIdent() {
        super();
        this.op2Combiner.put(CType.IDENT, new CC_StrichIdentIdent());
        this.op2Combiner.put(CType.TIMESROW, new CC_StrichIdentTR());
    }

    @Override
    public CElement combine(final CElement parent, final CElement cE1,
            final CElement cE2) {
        System.out.println("Add Ident");
        if (cE1.istGleichartigesMonom(cE2)) {
            System.out.println("Gleichartig");
            return this.op2Combiner.get(cE2.getCType()).combine(parent, cE1,
                    cE2);
        }
        return cE1;
    }

    @Override
    public boolean canCombine(final CElement parent, final CElement cE1,
            final CElement cE2) {
        if (cE1.istGleichartigesMonom(cE2)) {
            System.out.println("Gleichartig");
            return this.op2Combiner.get(cE2.getCType()).canCombine(parent,
                    cE1, cE2);
        }
        return false;
    }
}
