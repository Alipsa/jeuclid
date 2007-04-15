/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
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

/* $Id$ */

package net.sourceforge.jeuclid.element;

import java.awt.Graphics2D;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractUnderOverElement;
import net.sourceforge.jeuclid.element.generic.MathElement;
import net.sourceforge.jeuclid.element.helpers.AttributesHelper;

import org.w3c.dom.DOMException;
import org.w3c.dom.mathml.MathMLElement;
import org.w3c.dom.mathml.MathMLOperatorElement;

/**
 * This class arrange an element under an other element.
 * 
 * @todo common functionality should be merged into AbstractUnderOverElement
 */
public class MathUnder extends AbstractUnderOverElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "munder";

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathUnder(final MathBase base) {
        super(base);
    }

    /**
     * Space between base and under in pixels
     */
    private int getUnderSpace(final Graphics2D g) {
        return (int) AttributesHelper.convertSizeToPt(
                MathUnderOver.UNDER_OVER_SPACE, this, AttributesHelper.PT);
    };

    /**
     * Paints this element.
     * 
     * @param g
     *            The graphics context to use for painting.
     * @param posX
     *            The first left position for painting.
     * @param posY
     *            The position of the baseline.
     */
    @Override
    public final void paint(final Graphics2D g, final int posX, int posY) {
        super.paint(g, posX, posY);
        final MathElement e1 = this.getBase();
        final MathElement e2 = this.getUnderscript();

        if ((this.getBase() instanceof MathOperator)
                && Boolean.parseBoolean(((MathOperator) this.getBase())
                        .getMovablelimits())) {
            final int middleshift = (int) (e1.getHeight(g) * MathSubSup.DEFAULT_SCRIPTSHIFT);
            int e1DescentHeight = e1.getDescentHeight(g);
            if (e1DescentHeight == 0) {
                e1DescentHeight = this.getFontMetrics(g).getDescent();
            }
            int e1AscentHeight = e1.getAscentHeight(g);
            if (e1AscentHeight == 0) {
                e1AscentHeight = this.getFontMetrics(g).getAscent();
            }
            final int posY1 = posY + e1DescentHeight + e2.getAscentHeight(g)
                    - middleshift - 1;
            e1.paint(g, posX, posY);
            e2.paint(g, posX + e1.getWidth(g), posY1);
        } else {
            final int width = this.getWidth(g);
            e1.paint(g, posX + (width - e1.getWidth(g)) / 2, posY);
            posY = posY + e1.getDescentHeight(g) + e2.getAscentHeight(g)
                    + this.getUnderSpace(g) - 1;
            if (this.getAccentunderAsBoolean()) {
                posY = posY + this.getUnderSpace(g);
            }
            e2.paint(g, posX + (width - e2.getWidth(g)) / 2, posY);
        }
    }

    /** {@inheritDoc} */
    @Override
    public final int calculateWidth(final Graphics2D g) {
        if ((this.getBase() instanceof MathMLOperatorElement)) {
            if (Boolean.parseBoolean(((MathMLOperatorElement) this.getBase())
                    .getMovablelimits())) {
                return this.getBase().getWidth(g)
                        + this.getUnderscript().getWidth(g);
            }
        }
        return Math.max(this.getBase().getWidth(g), this.getUnderscript()
                .getWidth(g));
    }

    /** {@inheritDoc} */
    @Override
    public final int calculateAscentHeight(final Graphics2D g) {
        return this.getBase().getAscentHeight(g);
    }

    /** {@inheritDoc} */
    @Override
    public final int calculateDescentHeight(final Graphics2D g) {
        int res;
        if ((this.getBase() instanceof MathOperator)
                && Boolean.parseBoolean(((MathOperator) this.getBase())
                        .getMovablelimits())) {
            res = Math.max(this.getBase().getDescentHeight(g), this
                    .getUnderscript().getHeight(g)
                    - this.getMiddleShift(g));
        } else {
            res = this.getBase().getDescentHeight(g)
                    + this.getUnderscript().getHeight(g)
                    + this.getUnderSpace(g);
        }
        if (this.getAccentunderAsBoolean()) {
            res = res + this.getUnderSpace(g);
        }
        return res;
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return MathUnder.ELEMENT;
    }

    /** {@inheritDoc} */
    public MathMLElement getOverscript() {
        return null;
    }

    /** {@inheritDoc} */
    public MathElement getUnderscript() {
        return this.getMathElement(1);
    }

    /** {@inheritDoc} */
    public void setOverscript(final MathMLElement overscript) {
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                "munder does not have overscript");
    }

    /** {@inheritDoc} */
    public void setUnderscript(final MathMLElement underscript) {
        this.setMathElement(1, underscript);
    }

}
