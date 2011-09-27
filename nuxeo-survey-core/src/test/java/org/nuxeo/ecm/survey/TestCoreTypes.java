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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.BackendType;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import com.google.inject.Inject;

/**
 * @author <a href="mailto:ei@nuxeo.com">Eugen Ionica</a>
 *
 */

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@RepositoryConfig(type = BackendType.H2, user = "Administrator", init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy({ "org.nuxeo.ecm.core.api", "org.nuxeo.ecm.platform.routing.api",
        "org.nuxeo.ecm.platform.routing.core", "org.nuxeo.ecp.survey.core" })
public class TestCoreTypes {
    @Inject
    protected CoreSession session;

    @Test
    public void testDeployment() throws Exception {
        DocumentModel survey = session.createDocumentModel("/", "FirstSurvey",
                "Survey");
        survey = session.createDocument(survey);

        Survey surveyAdapter = survey.getAdapter(Survey.class);
        assertNotNull(surveyAdapter);

        DocumentModel question = session.createDocumentModel(
                survey.getPathAsString(), "FirstQuestion", "Question");
        question = session.createDocument(question);

        Question questionAdapter = question.getAdapter(Question.class);
        assertNotNull(questionAdapter);

        questionAdapter.setAnswers(new String[] { "a1", "a2" });
        session.saveDocument(question);
        session.save();

        question = session.getDocument(question.getRef());
        questionAdapter = question.getAdapter(Question.class);
        assertEquals(2, questionAdapter.getAnswers().length);

    }

}
