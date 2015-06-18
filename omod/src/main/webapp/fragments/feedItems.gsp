<% if (journals) { %>
<ul class="media-list">
    <% journals.each { %>
    <li class="media feed-item">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="media-left">
                    <a href="#">
                        <img class="media-object" src="/openmrs/images/openmrs_logo_white.gif" alt="sample" width="30" height="30"/>
                    </a>
                </div>
                <div class="media-body">
                    <h5 class="media-heading">${ (it.getCreator().getGivenName()) }<small>&emsp;${ (it.getDateCreated()) }</small></h5>
                    <p>${ (it.getContent()) }</p>
                </div>
            </div>
            <div class="panel-footer clearfix">

                <ul class="media-list feed-subitems clearfix">
                    <% if (it.getChildren()) { %>
                    <% it.getChildren().each { %>
                <li class="media feed-item">
                    <div>
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object" src="/openmrs/images/openmrs_logo_white.gif" alt="sfs" width="30" height="30" />
                            </a>
                        </div>
                        <div class="media-body">
                            <h5 class="media-heading">${ (it.getCreator().getGivenName()) }<small>&emsp;${ (it.getDateCreated()) }</small></h5>
                            <p>${ (it.getContent()) }</p>
                        </div>
                    </div>
                </li>
                <% } %>
                <% } %>
                    <li class="media feed-comment">
                        <div class="separator">
                            <div class="media-left">
                                <a href="#">
                                    <img class="media-object" src="/openmrs/images/openmrs_logo_white.gif" alt="alt"  width="30" height="30" />
                                </a>
                            </div>
                            <div class="media-body">
                                <form>
                                    <div class="form-group-sm" >
                                        <input class="form-control"  placeholder="Comment&hellip;"/>
                                    </div>
                                </form>
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