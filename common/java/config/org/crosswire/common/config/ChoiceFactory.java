package org.crosswire.common.config;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.crosswire.common.util.ClassUtil;
import org.jdom.Element;

/**
 * Factory for the well known Choices.
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
public class ChoiceFactory
{
    /**
     * Prevent Instansiation
     */
    private ChoiceFactory()
    {
    }

    /**
     * Get a ChoiceFactory by element.
     * @param option The element to check
     * @return One of the ChoiceTypes.
     */
    public static Choice getChoice(Element option, ResourceBundle configResources) throws ClassNotFoundException, IllegalAccessException, InstantiationException, StartupException
    {
        Class clazz = null;

        String type = option.getAttributeValue("type"); //$NON-NLS-1$
        if (type.equals("custom")) //$NON-NLS-1$
        {
            String clazzstr = option.getAttributeValue("class"); //$NON-NLS-1$
            clazz = Class.forName(clazzstr);
        }
        else
        {
            clazz = (Class) map.get(type);
        }

        Choice choice = (Choice) clazz.newInstance();
        choice.init(option, configResources);
        return choice;
    }

    /**
     * Method getDataMap.
     */
    public static Map getDataMap()
    {
        return datamap;
    }

    /**
     * Storage of various registered objects
     */
    private static Map datamap = new HashMap();

    /**
     * Store of the known ChoiceTypes
     */
    private static Map map;

    /**
     * Setup the map of Choices
     */
    static
    {
        map = ClassUtil.getImplementorsMap(Choice.class);
    }
}
