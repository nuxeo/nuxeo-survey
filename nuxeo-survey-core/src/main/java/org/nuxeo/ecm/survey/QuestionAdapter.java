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

import static org.nuxeo.ecm.survey.Constants.ANSWERS_PROPERTY;
import static org.nuxeo.ecm.survey.Constants.QUESTION_PROPERTY;

import org.nuxeo.ecm.core.api.DocumentModel;

/**
 * Default implementation of {@link Question}.
 *
 * @author <a href="mailto:ei@nuxeo.com">Eugen Ionica</a>
 * @since 5.4.3
 */
public class QuestionAdapter extends BaseAdapter implements Question {

    public QuestionAdapter(DocumentModel doc) {
        super(doc);
    }

    public String getQuestion() {
        return (String) getPropertyValue(QUESTION_PROPERTY);
    }

    public String[] getAnswers() {
        return (String[]) getPropertyValue(ANSWERS_PROPERTY);
    }

    public void setQuestion(String value) {
        setPropertyValue(QUESTION_PROPERTY, value);
    }

    public void setAnswers(String[] value) {
        setPropertyValue(ANSWERS_PROPERTY, value);
    }

    @Override
    public DocumentModel getQuestionDocument() {
        return doc;
    }

}
