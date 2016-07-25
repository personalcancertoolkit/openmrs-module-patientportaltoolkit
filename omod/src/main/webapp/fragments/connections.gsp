<div class="clearfix" id="friends-list">
    ${ ui.includeFragment("patientportaltoolkit", "removeRelationship") }
    ${ ui.includeFragment("patientportaltoolkit", "addRelationship") }
    ${ ui.includeFragment("patientportaltoolkit", "editRelationshipModal") }
    <% if (user.getPerson().isPatient()){%>
    <div class="clearfix">
        <div class="button-div pull-right">
            <button type="button" class="btn btn-default pad-left" data-toggle="modal" data-target="#add-relationship-modal">Add Connections</button>
        </div>
    </div>
    <% } %>
    <% if (relationships) { %>

    <% relationships.each { relationship -> %>
    <ul class="media-list">
        <% if (relationship.getRelatedPerson() == user.getPerson() && relationship.getShareStatus()==0){%>

        <li class="media friendlist-item">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="media-left">
                        <a href="#">
                            <div class="profileBadge">${ (relationship.relatedPerson.getGivenName()) } ${ (relationship.relatedPerson.getFamilyName()) }</div>
                        </a>
                    </div>
                    <div class="media-body">
                        <form class="form-inline" role="form">
                            <span class="media-heading form-group form-inline">
                                <h4 id = "${ relationship.uuid }relationPerson">${ (relationship.person.getGivenName()) } ${ (relationship.person.getFamilyName()) } <small>sent you a connection request</small></h4>
                                <button type="button" class="btn btn-primary acceptConnectionRequest" id="${ relationship.uuid }acceptConnectionRequest">Accept Request</button> <button type="button" class="btn btn-default ignoreConnectionRequest" id="${ relationship.uuid }ignoreConnectionRequest">Ignore Request</button>
                            </span>
                        </form>

                    </div>

                </div>
            </div>
        </li>
        <% } %>
    </ul>
    <% } %>
    <% relationships.each { relationship -> %>
    <ul class="media-list">
        <% if (relationship.getPerson() == user.getPerson()){%>
        <li class="media friendlist-item">

            <div class="panel panel-default">
                <div class="panel-body">
                    <button type="button" id="removeRelation${ relationship.uuid }" class="close removeRelationCloseButton" aria-label="remove" data-toggle="modal" data-target="#confirm-friend-delete">
                        <span aria-hidden="true">&times;</span>
                    </button>

                    <div class="media-left">
                        <a href="#">
                            <div class="profileBadge">${ (relationship.relatedPerson.getGivenName()) } ${ (relationship.relatedPerson.getFamilyName()) }</div>
                        </a>
                    </div>
                    <div class="media-body">
                        <h4 class="media-heading" id = "${ relationship.uuid }relationPerson"><a href="http://localhost:8081/openmrs2/patientportaltoolkit/home.page?personId=${ relationship.relatedPerson.uuid }"> ${ (relationship.relatedPerson.getGivenName()) } ${ (relationship.relatedPerson.getFamilyName()) }</a>
                            <div class="pull-right">
                                <a id="relationedit${ relationship.uuid }" class="glyphicon glyphicon-pencil editRelationButton"  data-toggle="modal" data-target="#edit-relationship-modal"></a>
                            </div></h4>
                        <form class="form-inline" role="form">
                            <div class="form-group">
                                <span id="${ relationship.uuid }relationType">${ (relationship.getRelationType()) }</span> -  <span id="${ relationship.uuid }relationShare">${relationship.getShareType().getDescription()}</span> <input id="${ relationship.uuid }relationShareID" value="${relationship.getShareType().getUuid()}" type="hidden"/>
                            </div>
                        </form>

                    </div>
                    <% } %>
                </div>
            </div>
        </li>
    </ul>
    <% } %>
    <% } else { %>
    There are no connections for you yet!
    <% } %>
</div>