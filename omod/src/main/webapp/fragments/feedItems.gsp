<% if (journals) { %>
<ul class="media-list">
    <% journals.each { journal -> %>
    <li class="media feed-item">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="media-left">
                    <a href="#">
                        <div class="profileBadgeJournals">${ (journal.creator.getGivenName()) } ${ (journal.creator.getFamilyName()) }</div>
                    </a>
                </div>

                <div class="media-body">
                    <h5 class="media-heading">${(journal.creator.getGivenName())}  - ${(journal.title)} <small>&emsp;
                            ${ pptutil.formatDate(journal.dateCreated)}</small></h5>

                    <p>${(journal.content)}</p>
                </div>
            </div>

            <div class="panel-footer clearfix">

                <ul class="media-list feed-subitems clearfix">
                    <% if (journal.children) { %>
                    <% journal.children.each { %>
                    <li class="media feed-item">
                        <div>
                            <div class="media-left">
                                <a href="#">
                                    <div class="profileBadgeJournals">${ (it.creator.getGivenName()) } ${ (it.creator.getFamilyName()) }</div>
                                </a>
                            </div>

                            <div class="media-body">
                                <h5 class="media-heading">${(it.creator.getGivenName())}  - ${
                                        (it.title)} <small>&emsp; ${ pptutil.formatDate(it.dateCreated) }</small></h5>

                                <p>${(it.content)}</p>
                            </div>
                        </div>
                    </li>
                    <% } %>
                    <% } %>
                    <li class="media feed-comment">
                        <div class="separator">
                            <div class="media-left">
                                <a href="#">
                                    <div class="profileBadgeJournals">${ (user.getPerson().getGivenName()) } ${ (user.getPerson().getFamilyName()) }</div>
                                </a>
                            </div>
                            <div class="media-body" style="width: 100%">

                                ${ ui.includeFragment("patientportaltoolkit", "commentBox",[parentId: (journal.uuid)]) }
                                </div>
                            <label><small><span class="fa fa-info-circle fa-lg"></span> &nbsp;Your comment will be visible with all the people with access to this post</small></label>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </li>
    <% } %>

</ul>
<% } %>