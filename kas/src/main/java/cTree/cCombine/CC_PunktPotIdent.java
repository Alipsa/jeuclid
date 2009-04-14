/*
 * Copyright 2009 Erhard Kuenzel 03/09
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

import org.w3c.dom.Element;

import cTree.CElement;
import cTree.CNum;
import cTree.CPot;
import cTree.CRolle;
import cTree.adapter.EElementHelper;

public class CC_PunktPotIdent extends CC_ {

    @Override
    protected CElement createCombination(final CElement parent,
            final CElement firstPot, final CElement ident) {
        System.out.println("Multipliziere Pot Ident");
        CElement newChild = null;
        // erster Faktor
        if (firstPot.getCRolle() == CRolle.FAKTOR1) {
            if (ident.hasExtDiv()) {
                final int iExp = ((CNum) ((CPot) firstPot).getExponent())
                        .getValue();
                if (iExp > 0) {
                    final CPot newPot = (CPot) firstPot.cloneCElement(false);
                    newPot.getExponent().setText("" + (iExp - 1));
                    newChild = newPot;
                } else {
                    firstPot.setCActiveProperty();
                    return firstPot;
                }
            } else {
                final int iExp = ((CNum) ((CPot) firstPot).getExponent())
                        .getValue();
                final CPot newPot = (CPot) firstPot.cloneCElement(false);
                newPot.getExponent().setText("" + (iExp + 1));
                newChild = newPot;
            }
            // weitere Faktoren
        } else {
            if (this.gleicheDiv(firstPot.getExtPraefix(), ident
                    .getExtPraefix())) {
                final int iExp = ((CNum) ((CPot) firstPot).getExponent())
                        .getValue();
                final CPot newPot = (CPot) firstPot.cloneCElement(false);
                newPot.getExponent().setText("" + (iExp + 1));
                newChild = newPot;
            } else {
                final int iExp = ((CNum) ((CPot) firstPot).getExponent())
                        .getValue();
                if (iExp > 0) {
                    final CPot newPot = (CPot) firstPot.cloneCElement(false);
                    newPot.getExponent().setText("" + (iExp - 1));
                    newChild = newPot;
                } else {
                    firstPot.setCActiveProperty();
                    return firstPot;
                }
            }
        }
        return newChild;
    }

    @Override
    protected boolean canCombine(final CElement parent, final CElement cE1,
            final CElement cE2) {
        System.out.println("Repell pot times ident?");
        return true;
    }

    private boolean gleicheDiv(final Element op1, final Element op2) {
        final boolean result = (EElementHelper.isTimesOp(op1) && EElementHelper
                .isTimesOp(op2));
        return result
                || (":".equals(op1.getTextContent()) && ":".equals(op2
                        .getTextContent()));
    }
}
