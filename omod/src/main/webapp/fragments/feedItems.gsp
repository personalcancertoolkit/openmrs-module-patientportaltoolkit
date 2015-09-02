<% if (journals) { %>
<ul class="media-list">
    <% journals.each { journal -> %>
    <li class="media feed-item">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="media-left">
                    <a href="#">
                        <img class="media-object imagePlaceHolders" alt="sample" width="30"
                             height="30"/>
                    </a>
                </div>

                <div class="media-body">
                    <h5 class="media-heading">${(journal.creator.getGivenName())}  - ${(journal.title)} <small>&emsp;
                            ${ ui.formatDatetimePretty(journal.dateCreated)}</small></h5>

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
                                    <img class="media-object imagePlaceHolders" alt="sfs"
                                         width="30" height="30"/>
                                </a>
                            </div>

                            <div class="media-body">
                                <h5 class="media-heading">${(it.creator.getGivenName())}  - ${
                                        (it.title)} <small>&emsp; ${ ui.formatDatetimePretty(it.dateCreated) }</small></h5>

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
                                    <img class="media-object imagePlaceHolders" alt="alt" width="30" height="30" />
                                </a>
                            </div>
                            <div class="media-body" style="width: 100%">

                                ${ ui.includeFragment("patientportaltoolkit", "commentBox",[parentId: (journal.uuid)]) }
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </li>
    <% } %>

</ul>
<% } %>