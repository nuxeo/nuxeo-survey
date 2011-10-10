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

import static org.nuxeo.ecm.survey.Constants.BRANCHED_QUESTION_CONTAINER_DOCUMENT_TYPE;
import static org.nuxeo.ecm.survey.Constants.BRANCHED_QUESTION_DOCUMENT_TYPE;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.international.StatusMessage;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentRef;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.event.CoreEventConstants;
import org.nuxeo.ecm.platform.routing.api.DocumentRouteElement;
import org.nuxeo.ecm.platform.routing.api.exception.DocumentRouteNotLockedException;
import org.nuxeo.ecm.platform.routing.web.DocumentRoutingActionsBean;
import org.nuxeo.ecm.platform.ui.web.api.NavigationContext;
import org.nuxeo.ecm.webapp.helpers.EventNames;


@Name("routingActions")
@Scope(ScopeType.CONVERSATION)
public class SurveyActionBean extends DocumentRoutingActionsBean implements
        Serializable {

    private static final long serialVersionUID = 1L;

    protected static final Log log = LogFactory.getLog(SurveyActionBean.class);

    @In(required = true, create = true)
    protected NavigationContext navigationContext;

    @In(create = true, required = false)
    protected CoreSession documentManager;

    public String saveRouteElement() throws ClientException {
        DocumentModel newDocument = navigationContext.getChangeableDocument();
        if (BRANCHED_QUESTION_DOCUMENT_TYPE.equals(newDocument.getType())
                && newDocument.getId() == null) {
            // create the container first
            String parentDocumentPath = (String) newDocument.getContextData().get(
                    CoreEventConstants.PARENT_PATH);
            String sourceDocumentName = (String) newDocument.getContextData().get(
                    SOURCE_DOC_NAME);
            DocumentRef routeDocRef = (DocumentRef) newDocument.getContextData().get(
                    ROUTE_DOCUMENT_REF);
            System.out.println("path = " + parentDocumentPath);
            // create first BranchedQuestionContainer
            DocumentModel bqc = documentManager.createDocumentModel(
                    parentDocumentPath, "bcq" + System.currentTimeMillis(),
                    BRANCHED_QUESTION_CONTAINER_DOCUMENT_TYPE);
            try {
                getDocumentRoutingService().addRouteElementToRoute(
                        new PathRef(parentDocumentPath), sourceDocumentName,
                        bqc.getAdapter(DocumentRouteElement.class),
                        documentManager);
                // change the parent for branched question
                newDocument.getContextData().put(
                        CoreEventConstants.PARENT_PATH, bqc.getPathAsString());
            } catch (DocumentRouteNotLockedException e) {
                facesMessages.add(
                        StatusMessage.Severity.WARN,
                        resourcesAccessor.getMessages().get(
                                "feedback.casemanagement.document.route.not.locked"));
                return null;
            }
            DocumentModel routeDocument = documentManager.getDocument(routeDocRef);
            Events.instance().raiseEvent(EventNames.DOCUMENT_CHILDREN_CHANGED,
                    routeDocument);

        }
        return super.saveRouteElement();
    }
}
