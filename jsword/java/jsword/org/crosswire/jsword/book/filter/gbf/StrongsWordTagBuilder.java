package org.crosswire.jsword.book.filter.gbf;

import java.util.LinkedList;

import org.crosswire.jsword.book.DataPolice;
import org.crosswire.jsword.book.OSISUtil;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;

/**
 * Tag syntax: word&lt;WHxxxx> or word&lt;WGxxxx>.
 * 
 * <p><table border='1' cellPadding='3' cellSpacing='0'>
 * <tr><td bgColor='white' class='TableRowColor'><font size='-7'>
 *
 * Distribution Licence:<br />
 * JSword is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, or by writing to:
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see gnu.gpl.Licence
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id$
 */
public class StrongsWordTagBuilder implements TagBuilder
{
    /* (non-Javadoc)
     * @see org.crosswire.jsword.book.filter.gbf.TagBuilder#createTag(java.lang.String)
     */
    public Tag createTag(final String tagname)
    {
        if (!tagname.startsWith("WH") && !tagname.startsWith("WG")) //$NON-NLS-1$ //$NON-NLS-2$
        {
            return null;
        }

        return new Tag()
        {
            public void updateOsisStack(LinkedList stack)
            {
                String name = tagname.trim();

                Element ele = (Element) stack.get(0);
                int size = ele.getContentSize();
                if (size == 0)
                {
                    DataPolice.report("No content to attach word to: <" + name + ">."); //$NON-NLS-1$ //$NON-NLS-2$
                    return;
                }

                int lastIndex = size - 1;
                Content prevObj = ele.getContent(lastIndex);
                Element word = null;

                if (prevObj instanceof Text)
                {
                    Text textItem = (Text) prevObj;
                    word = OSISUtil.factory().createW();
                    ele.removeContent(textItem);
                    word.addContent(textItem);
                    ele.addContent(word);
                }
                else if (prevObj instanceof Element)
                {
                    word = (Element) prevObj;
                }
                else
                {
                    DataPolice.report("No words to attach word to: <" + name + ">."); //$NON-NLS-1$ //$NON-NLS-2$
                    return;
                }

                String existingLemma = word.getAttributeValue(OSISUtil.ATTRIBUTE_W_LEMMA);
                StringBuffer newLemma = new StringBuffer();

                if (existingLemma != null && existingLemma.length() > 0)
                {
                    newLemma.append(existingLemma).append('|');
                }

                newLemma.append(OSISUtil.LEMMA_STRONGS).append(name.substring(2)); //$NON-NLS-1$
                word.setAttribute(OSISUtil.ATTRIBUTE_W_LEMMA, newLemma.toString());
            }
        };
    }
}
