<div class="clearfix" id="friends-list">
    ${ ui.includeFragment("patientportaltoolkit", "removeRelationship") }
    ${ ui.includeFragment("patientportaltoolkit", "addRelationship") }
    ${ ui.includeFragment("patientportaltoolkit", "editRelationshipModal") }
    <div class="clearfix">
        <div class="button-div pull-right">
            <button type="button" class="btn btn-default pad-left" data-toggle="modal" data-target="#add-relationship-modal">Add Connections</button>
        </div>
    </div>
    <% if (relationships) { %>
    <ul class="media-list">
        <% relationships.each { relationship -> %>
        <li class="media friendlist-item">

            <div class="panel panel-default">
                <div class="panel-body">
                    <button type="button" id="removeRelation${ relationship.uuid }" class="close removeRelationCloseButton" aria-label="remove" data-toggle="modal" data-target="#confirm-friend-delete">
                        <span aria-hidden="true">&times;</span>
                    </button>

                    <div class="media-left">
                        <a href="#">
                            <img class="media-object imagePlaceHolders" alt="Default Picture" width="60" height="60"/>
                        </a>
                    </div>

                    <% if (relationship.getPerson() == user.getPerson()){%>
                    <div class="media-body">
                        <h4 class="media-heading" id = "${ relationship.uuid }relationPerson">${ (relationship.relatedPerson.getGivenName()) } ${ (relationship.relatedPerson.getFamilyName()) }
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
                    <% if (relationship.getRelatedPerson() == user.getPerson()){%>
                    <div class="media-body">
                        <h4 class="media-heading" id = "${ relationship.uuid }relationPerson">${ (relationship.person.getGivenName()) } ${ (relationship.person.getFamilyName()) }
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
    <% } %>
    </ul>
    <% } else { %>
    There are no connections for you yet!
    <% } %>
</div>