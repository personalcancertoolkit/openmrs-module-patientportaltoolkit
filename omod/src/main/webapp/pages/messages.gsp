${ ui.includeFragment("patientportaltoolkit", "patientPortalNav") }
<body>
<div>
    <div class="container pull-left col-md-4">
        <div class="clearfix">
            <h5 class="pull-left">
                Inbox
            </h5>
            <div id="composeMessageButton" class="btn btn-default btn-sm pull-right">Compose Message</div>
        </div>
        <hr style= "marginTop: '8px'"/>
        <ul class="media-list">
<% if (messages) { %>
<% messages.each { message -> %>
            <li class="media messagelistLink" id="${(message.uuid)}">
                <div class="media-left">
                    <a href="#">
                        <img class="media-object imagePlaceHolders" alt="Picture" width="40" height="40" />
                    </a>
                </div>

                <div class="media-body">


                    <h5 class="media-heading">
                        <strong>${(message.title)}</strong>
                        <br />
                        <small><strong>${(message.receiver.getPersonName())}</strong></small>
                        <br />
                        <small>${(message.content)}</small>
                    </h5>
                </div>
            </li>
<% } %>
<% } %>
        </ul>

    </div>
${ ui.includeFragment("patientportaltoolkit", "composeMessage") }

<div id="showDetailedList" class="col-md-7 pull-left">
<ul class="media-list">
<% if (messages) { %>
<% messages.each { message -> %>
    <li class="media detailedMessageList" id="mediaList${(message.uuid)}">
        <h4>${(message.receiver.getPersonName())}</h4>
        <div class="media-body">
            <h5 class="media-heading">
                <strong>${(message.title)}</strong><small><strong>-${(message.sender.getPersonName())}</strong></small>
                <br />
                <small>${(message.content)}</small>
            </h5>
            <% if (message.children) { %>
            <% message.children.each { messageChild -> %>
            <h5 class="media-heading">
                <strong>${(messageChild.title)}</strong><small><strong>-${(messageChild.sender.getPersonName())}</strong></small>
                <br />
                <small>${(messageChild.content)}</small>
            </h5>
            <% } %>
            <% } %>
        </div>
    </li>
<% } %>
<% } %>
</ul>
    </div>
    </div>
</div>
</body>