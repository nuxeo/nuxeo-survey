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

import static org.nuxeo.ecm.survey.Constants.SURVEY_END_DATE_PROPERTY;
import static org.nuxeo.ecm.survey.Constants.SURVEY_FINAL_TEXT_PROPERTY;
import static org.nuxeo.ecm.survey.Constants.SURVEY_INTRODUCTION_TEXT_PROPERTY;
import static org.nuxeo.ecm.survey.Constants.SURVEY_START_DATE_PROPERTY;

import java.util.Calendar;
import java.util.Date;

import org.nuxeo.ecm.core.api.DocumentModel;


/**
 * Default implementation of {@link Survey}.
 *
 * @author <a href="mailto:ei@nuxeo.com">Eugen Ionica</a>
 * @since 5.4.3
 */
public class SurveyAdapter extends BaseAdapter implements Survey {

    public SurveyAdapter(DocumentModel doc) {
        super(doc);
    }

    public String getIntroductionText() {
        return (String) getPropertyValue(SURVEY_INTRODUCTION_TEXT_PROPERTY);
    }

    public String getFinalText() {
        return (String) getPropertyValue(SURVEY_FINAL_TEXT_PROPERTY);
    }

    public Date getStartDate() {
        Calendar cal = (Calendar) getPropertyValue(SURVEY_START_DATE_PROPERTY);
        return cal == null ? null : cal.getTime();
    }

    public Date getEndDate() {
        Calendar cal = (Calendar) getPropertyValue(SURVEY_END_DATE_PROPERTY);
        return cal == null ? null : cal.getTime();
    }

    @Override
    public void setIntroductionText(String value) {
        setPropertyValue(SURVEY_INTRODUCTION_TEXT_PROPERTY, value);
    }

    @Override
    public void setFinalText(String value) {
        setPropertyValue(SURVEY_FINAL_TEXT_PROPERTY, value);
    }

    @Override
    public void setStartDate(Date value) {
        setPropertyValue(SURVEY_START_DATE_PROPERTY, value);
    }

    @Override
    public void setEndDate(Date value) {
        setPropertyValue(SURVEY_FINAL_TEXT_PROPERTY, value);
    }

    @Override
    public DocumentModel getSurveyDocument() {
        return doc;
    }

}
