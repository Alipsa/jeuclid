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

public class CC_StrichAny0 extends CC_ {

    @Override
    protected boolean canCombine(final CElement parent,
            final CElement minTerm, final CElement tRow) {
        System.out.println("Repell add Any 0");
        return true;
    }

    @Override
    protected CElement createCombination(final CElement parent,
            final CElement cE1, final CElement cE2) {
        System.out.println("Add Any 0");
        return cE1.cloneCElement(false); // parent.cloneChild(cE1, false);
    }
}
