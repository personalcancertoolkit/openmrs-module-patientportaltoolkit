<% if(twoDirectionFragmentAccepted==true) { %>
    <li class="media friendlist-item">
        <input id="checkPersonInRelation${ twoDirectionFragmentRelation.uuid }" value="0" hidden>
        <div class="panel panel-default">
            <div class="panel-body">
                <button type="button" id="removeRelation${ twoDirectionFragmentRelation.uuid }" class="close removeRelationCloseButton" aria-label="remove" data-toggle="modal" data-target="#confirm-friend-delete">
                    <span aria-hidden="true">&times;</span>
                </button>

                <div class="media-left">
                    <a href="#">
                        <div class="profileBadge">${ (twoDirectionFragmentPerson.getGivenName()) } ${ (twoDirectionFragmentPerson.getFamilyName()) } </div>
                    </a>
                </div>
                <div class="media-body">
                    <h4 class="media-heading" id = "${ twoDirectionFragmentRelation.uuid }relationPerson"><a class="no-underline-edit connectionlink" id ="connectionlink${ twoDirectionFragmentPerson.uuid}"> ${ (twoDirectionFragmentPerson.getGivenName()) } ${ (twoDirectionFragmentPerson.getFamilyName()) }</a>
                        <% if(person.isPatient()) { %>
                        <div class="pull-right">
                            <a id="relationedit${ twoDirectionFragmentRelation.uuid }" class="no-underline-edit fa fa-pencil editRelationButton"  data-toggle="modal" data-target="#edit-relationship-modal"></a>
                        </div>
                        <% } %>
                    </h4>
                    <form class="form-inline" role="form">
                        <% if(twoDirectionFragmentSide=="aIsToB") { %>
                        <div class="form-group">
                            <span id="${ twoDirectionFragmentRelation.uuid }relationType" hidden>${ (twoDirectionFragmentRelation.getRelationType().getId()) }</span> ${ (twoDirectionFragmentRelation.getRelationType().getaIsToB()) } -  <span id="${ twoDirectionFragmentRelation.uuid }relationShare"> This person can see my ${twoDirectionFragmentRelation.getShareTypeA().getDescription()}</span> -  <span> You can see their ${twoDirectionFragmentRelation.getShareTypeB().getDescription()}</span> <input id="${ twoDirectionFragmentRelation.uuid }relationShareID" value="${twoDirectionFragmentRelation.getShareTypeA().getUuid()}" type="hidden"/>
                        </div>
                        <% } %>
                        <% if(twoDirectionFragmentSide=="bIsToA") { %>
                        <div class="form-group">
                            <span id="${ twoDirectionFragmentRelation.uuid }relationType" hidden>${ (twoDirectionFragmentRelation.getRelationType().getId()) }</span> ${ (twoDirectionFragmentRelation.getRelationType().getaIsToB()) } -  <span id="${ twoDirectionFragmentRelation.uuid }relationShare"> This person can see my ${twoDirectionFragmentRelation.getShareTypeB().getDescription()}</span> -  <span> You can see their ${twoDirectionFragmentRelation.getShareTypeA().getDescription()}</span> <input id="${ twoDirectionFragmentRelation.uuid }relationShareID" value="${twoDirectionFragmentRelation.getShareTypeB().getUuid()}" type="hidden"/>
                        </div>
                        <% } %>
                    </form>

                </div>

            </div>
        </div>
    </li>
<% } %>
<% if(twoDirectionFragmentAccepted==false) { %>
    <li class="media friendlist-item">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="media-left">
                    <a href="#">
                        <div class="profileBadge">${ (twoDirectionFragmentPerson.getGivenName()) } ${ (twoDirectionFragmentPerson.getFamilyName()) }</div>
                    </a>
                </div>
                <div class="media-body">
                    <form class="form-inline" role="form">
                        <span class="media-heading form-group form-inline">
                            <h4 id = "${ twoDirectionFragmentRelation.uuid }relationPerson">${ (twoDirectionFragmentPerson.getGivenName()) } ${ (twoDirectionFragmentPerson.getFamilyName()) } <small>sent you a connection request</small></h4>
                            <% if (twoDirectionFragmentRelation.addConnectionNote !=null){%>
                            <p class="text-warning">${ twoDirectionFragmentRelation.addConnectionNote}</p>
                            <% } %>
                            <button type="button" class="btn btn-primary acceptConnectionRequest" id="${ twoDirectionFragmentRelation.uuid }acceptConnectionRequest">Accept Request</button>
                            <% def date = new Date() %>
                            <% if (twoDirectionFragmentRelation.getDateCreated()  < date-180){%><button type="button" class="btn btn-default ignoreConnectionRequest" id="${ twoDirectionFragmentRelation.uuid }ignoreConnectionRequest">Ignore Request</button>  <% } %>
                        </span>
                    </form>
                </div>
            </div>
        </div>
    </li>
<% } %>