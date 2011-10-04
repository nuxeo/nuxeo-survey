/*
 * (C) Copyright 2011 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     eugen
 */
package org.nuxeo.ecm.survey;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.adapter.DocumentAdapterFactory;

/**
 * 
 * @author <a href="mailto:ei@nuxeo.com">Eugen Ionica</a>
 * @since 5.4.3
 * 
 */
public class AdapterFactory implements DocumentAdapterFactory {

    public Object getAdapter(DocumentModel doc, Class<?> itf) {
        if (doc == null) {
            return null;
        }

        if (Constants.SURVEY_DOCUMENT_TYPE.equals(doc.getType())) {
            return new SurveyAdapter(doc);
        }
        if (Constants.QUESTION_DOCUMENT_TYPE.equals(doc.getType())) {
            return new QuestionAdapter(doc);
        }
        if (Constants.BRANCHED_QUESTION_DOCUMENT_TYPE.equals(doc.getType())) {
            return new QuestionAdapter(doc);
        }
        return null;
    }

}
