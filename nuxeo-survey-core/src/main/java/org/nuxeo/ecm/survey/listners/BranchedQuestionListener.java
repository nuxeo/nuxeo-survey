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
package org.nuxeo.ecm.survey.listners;

import static org.nuxeo.ecm.survey.Constants.BRANCHED_QUESTION_CONTAINER_DOCUMENT_TYPE;
import static org.nuxeo.ecm.survey.Constants.BRANCHED_QUESTION_DOCUMENT_TYPE;
import static org.nuxeo.ecm.survey.Constants.QUESTION_BRANCH_DOCUMENT_TYPE;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.event.DocumentEventTypes;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.survey.Question;

/**
 * @author <a href="mailto:ei@nuxeo.com">Eugen Ionica</a>
 *
 */
public class BranchedQuestionListener implements EventListener {

    public void handleEvent(Event event) throws ClientException {
        String eventName = event.getName();
        EventContext ctx = event.getContext();


        if (!(ctx instanceof DocumentEventContext)) {
            return;
        }
        DocumentEventContext docCtx = (DocumentEventContext) ctx;
        DocumentModel doc = docCtx.getSourceDocument();
        CoreSession session = ctx.getCoreSession();
        String docType = doc.getType();
        if (DocumentEventTypes.DOCUMENT_CREATED.equals(eventName)) {
            if (BRANCHED_QUESTION_CONTAINER_DOCUMENT_TYPE.equals(docType)) {
                doc.setPropertyValue("dc:title", "Empty Container");
                session.saveDocument(doc);
            } else if (BRANCHED_QUESTION_DOCUMENT_TYPE.equals(docType)) {
                DocumentModel parent = session.getDocument(doc.getParentRef());
                if (BRANCHED_QUESTION_CONTAINER_DOCUMENT_TYPE.equals(parent.getType())) {
                    // we are in a branched question case

                    // set the container title as question
                    parent.setPropertyValue("dc:title", doc.getPropertyValue("dc:title"));
                    session.saveDocument(parent);

                    // create a FolderStep for each answer
                    Question question = doc.getAdapter(Question.class);
                    if ( question != null) {
                        String[] answers = question.getAnswers();
                        if (answers != null) {
                            int count =0;
                            for (String answer : answers) {
                                DocumentModel d = session.createDocumentModel(
                                        parent.getPathAsString(), "answer"
                                                + (++count),
                                        QUESTION_BRANCH_DOCUMENT_TYPE);
                                d.setPropertyValue("dc:title", answer);
                                session.createDocument(d);
                            }
                        }
                    }
                }
            }
        }


    }

}
