<% if(oneDirectionFragmentAccepted==true) { %>
    <li class="media friendlist-item">
        <input id="checkPersonInRelation${ oneDirectionFragmentRelation.uuid }" value="0" hidden>
        <div class="panel panel-default">
            <div class="panel-body">
                <button type="button" id="removeRelation${ oneDirectionFragmentRelation.uuid }" class="close removeRelationCloseButton" aria-label="remove" data-toggle="modal" data-target="#confirm-friend-delete">
                    <span aria-hidden="true">&times;</span>
                </button>

                <div class="media-left">
                    <a href="#">
                        <div class="profileBadge">${ (oneDirectionFragmentPerson.getGivenName()) } ${ (oneDirectionFragmentPerson.getFamilyName()) } </div>
                    </a>
                </div>
                <div class="media-body">
                    <h4 class="media-heading" id = "${ oneDirectionFragmentRelation.uuid }relationPerson"><a class="connectionlink" id ="connectionlink${ oneDirectionFragmentPerson.uuid}"> ${ (oneDirectionFragmentPerson.getGivenName()) } ${ (oneDirectionFragmentPerson.getFamilyName()) }</a>
                        <% if(person.isPatient()) { %>
                        <div class="pull-right">
                            <a id="relationedit${ oneDirectionFragmentRelation.uuid }" class="glyphicon glyphicon-pencil editRelationButton"  data-toggle="modal" data-target="#edit-relationship-modal"></a>
                        </div>
                        <% } %>
                    </h4>
                    <form class="form-inline" role="form">
                        <% if(oneDirectionFragmentSide=="patient") { %>
                        <div class="form-group">
                            <span id="${ oneDirectionFragmentRelation.uuid }relationType" hidden>${ (oneDirectionFragmentRelation.getRelationType().getId()) }</span>  ${ (oneDirectionFragmentRelation.getRelationType().getaIsToB()) } -  <span id="${ oneDirectionFragmentRelation.uuid }relationShare"> This Person ${oneDirectionFragmentRelation.getShareTypeA().getDescription()}</span> <input id="${ oneDirectionFragmentRelation.uuid }relationShareID" value="${oneDirectionFragmentRelation.getShareTypeA().getUuid()}" type="hidden"/>
                        </div>
                        <% } %>
                        <% if(oneDirectionFragmentSide=="other") { %>
                        <div class="form-group">
                            <span id="${ oneDirectionFragmentRelation.uuid }relationType" hidden>${ (oneDirectionFragmentRelation.getRelationType().getId()) }</span>  ${ (oneDirectionFragmentRelation.getRelationType().getbIsToA()) } -  <span id="${ oneDirectionFragmentRelation.uuid }relationShare"> You ${oneDirectionFragmentRelation.getShareTypeA().getDescription()}</span> <input id="${ oneDirectionFragmentRelation.uuid }relationShareID" value="${oneDirectionFragmentRelation.getShareTypeB().getUuid()}" type="hidden"/>
                        </div>
                        <% } %>
                    </form>
                </div>

            </div>
        </div>
    </li>
<% } %>
<% if(oneDirectionFragmentAccepted==false) { %>
<li class="media friendlist-item">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="media-left">
                <a href="#">
                    <div class="profileBadge">${ (oneDirectionFragmentPerson.getGivenName()) } ${ (oneDirectionFragmentPerson.getFamilyName()) }</div>
                </a>
            </div>
            <div class="media-body">
                <form class="form-inline" role="form">
                    <span class="media-heading form-group form-inline">
                        <h4 id = "${ oneDirectionFragmentRelation.uuid }relationPerson">${ (oneDirectionFragmentPerson.getGivenName()) } ${ (oneDirectionFragmentPerson.getFamilyName()) } <small>sent you a connection request</small></h4>
                        <% if (oneDirectionFragmentRelation.addConnectionNote !=null){%>
                        <p class="text-warning">${ oneDirectionFragmentRelation.addConnectionNote}</p>
                        <% } %>
                        <button type="button" class="btn btn-primary acceptConnectionRequest" id="${ oneDirectionFragmentRelation.uuid }acceptConnectionRequest">Accept Request</button>
                        <% def date = new Date() %>
                        <% if (oneDirectionFragmentRelation.getDateCreated()  < date-180){%><button type="button" class="btn btn-default ignoreConnectionRequest" id="${ oneDirectionFragmentRelation.uuid }ignoreConnectionRequest">Ignore Request</button>  <% } %>
                    </span>
                </form>
            </div>
        </div>
    </div>
</li>
<% } %>