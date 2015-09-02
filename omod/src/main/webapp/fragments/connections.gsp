<div class="clearfix" id="friends-list">
    ${ ui.includeFragment("patientportaltoolkit", "removeRelationship") }
    ${ ui.includeFragment("patientportaltoolkit", "addRelationship") }
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

                    <div class="media-body">
                        <h4 class="media-heading">${ (relationship.relatedPerson.getGivenName()) } ${ (relationship.relatedPerson.getFamilyName()) } </h4>
                        <form class="form-inline" role="form">
                            <div class="form-group">
                                ${ ui.includeFragment("patientportaltoolkit", "relationshipSelect",[selectedRelationShip: (relationship.relationType)]) }
                            </div>
                            <div class="form-group">
                                <select class="form-control">
                                    <option selected>All Connections</option>
                                    <option>Personal Connections</option>
                                    <option>Physician Connections</option>
                                </select>
                            </div>
                            </form>

                    </div>
                </div>
            </div>
        </li>
    <% } %>
    </ul>
    <% } else { %>
    There are no connections for you yet!
    <% } %>
</div>