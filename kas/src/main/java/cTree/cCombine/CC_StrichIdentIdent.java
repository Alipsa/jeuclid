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

import cTree.*;

public class CC_StrichIdentIdent extends CC_ {

	
	protected boolean canCombine(CElement cE1, CElement cE2){
		System.out.println("Repell add ident ident"); 
		return true;
	}
	
	private boolean gleicheMin(CElement el1, CElement el2){
		boolean result1 = (el1.hasExtMinus() && el2.hasExtMinus());
		boolean result2 = (!el1.hasExtMinus() && !el2.hasExtMinus());
		return result1 || result2;
	}
	
	protected CElement createCombination(CElement parent, CElement cE1, CElement cE2){
		System.out.println("Add Ident and Ident"); 
		CElement newChild = null;
		if (cE1.getCRolle()==CRolle.SUMMAND1){
			if (cE2.hasExtMinus()){ // bilde Zahl 0
				newChild = CNum.createNum(parent.getElement(), "0");
				newChild.setCRolle(cE1.getCRolle());
			} else { // bilde TimesRow				
				CElement newFirst = CNum.createNum(parent.getElement(), "2");
				newFirst.setCRolle(CRolle.FAKTOR1);
				CElement newSecond = cE1.cloneCElement(false);
				newSecond.setCRolle(CRolle.FAKTORN1);
				newSecond.setPraefix("*");
				newChild = CTimesRow.createRow(CTimesRow.createList(newFirst, newSecond));
				newChild.setCRolle(cE1.getCRolle());
			}
		} else {
			if (gleicheMin(cE1, cE2)){ // bilde TimesRow
				CElement newFirst = CNum.createNum(parent.getElement(), "2");
				newFirst.setCRolle(CRolle.FAKTOR1);
				CElement newSecond = cE1.cloneCElement(false);
				newSecond.setCRolle(CRolle.FAKTORN1);
				newSecond.setPraefix("*");
				newChild = CTimesRow.createRow(CTimesRow.createList(newFirst, newSecond));
				newChild.setCRolle(cE1.getCRolle());
			} else { // bilde 0
				newChild = CNum.createNum(parent.getElement(), "0");
				newChild.setCRolle(cE1.getCRolle());
			}
		}
		return newChild;
	}	
}
