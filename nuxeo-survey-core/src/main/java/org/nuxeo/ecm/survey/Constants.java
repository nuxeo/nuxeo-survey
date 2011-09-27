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

/**
 * @author <a href="mailto:ei@nuxeo.com">Eugen Ionica</a>
 *
 */
public class Constants {

    private Constants() {
        // Constants class
    }

    public static final String SURVEY_DOCUMENT_TYPE = "Survey";

    public static final String QUESTION_DOCUMENT_TYPE = "Question";

    public static final String SURVEY_START_DATE_PROPERTY = "survey:start_date";

    public static final String SURVEY_END_DATE_PROPERTY = "dc:expired";

    public static final String SURVEY_INTRODUCTION_TEXT_PROPERTY = "introductionText";

    public static final String SURVEY_FINAL_TEXT_PROPERTY = "survey:finalText";

    public static final String QUESTION_PROPERTY = "question:question";

    public static final String ANSWERS_PROPERTY = "question:answers";

}
