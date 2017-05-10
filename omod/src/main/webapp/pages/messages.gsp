${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
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
            <li class="media messagelistLink" id="${(message.uuid)}" >
                <div class="media-left">
                    <a href="#">
                        <img class="media-object imagePlaceHolders" alt="Picture" width="40" height="40" />
                    </a>
                </div>

                <div class="media-body">

                    <% if (!message.children) { %>
                    <h5 class="media-heading">
                        <strong>${(message.title)}</strong>
                        <br />
                        <small><strong>${(message.receiver.getPersonName())}</strong></small>
                        <br />
                        <small>${(message.content)}</small>
                    </h5>
                    <% }else if (message.children) { def counter1=0; def counter2=0; %>
                    <% message.children.each { messageChild ->  counter1++%>
                    <% } %>
                    <% message.children.each { messageChild ->  counter2++%>
                    <% if (counter1==counter2) { %>
                    <h5 class="media-heading">
                        <strong>${(messageChild.title)}</strong>
                        <br />
                        <small><strong>${(message.receiver.getPersonName())}</strong></small>
                        <br />
                        <small>${(messageChild.content)}</small>
                    </h5>
                    <% } %>
                    <% } %>
                    <% } %>
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
                ${(message.content)}
            </h5>
            <% if (message.children) { %>
            <% message.children.each { messageChild -> %>
            <h5 class="media-heading">
                <strong>${(messageChild.title)}</strong><small><strong>-${(messageChild.sender.getPersonName())}</strong></small>
                <br />
                ${(messageChild.content)}
            </h5>
            <% } %>
            <% } %>
        </div>
    <hr style= "marginTop: '8px'"/>
    <div class="form-group">
        <label htmlFor="sendingReplyMessageSubject${(message.getUuid())}">Reply</label>
        <input id="sendingReplyMessageSubject${(message.getUuid())}" class="form-control" placeholder="subject" />
    </div>
    <textarea id="sendingReplyMessageText${(message.getUuid())}" class="form-control" placeholder="Write a Message"></textarea>
    <br />
    <div class="pull-right">
        <div id="sendReplyMessageButton${(message.getUuid())}" class="btn btn-primary btn-sm sendReplyMessageButton">Reply</div>
        <input type="hidden" id="replypersonId${(message.getUuid())}" value="${(message.receiver.getUuid())}" >
        <input type="hidden" id="replythreadparentid${(message.getUuid())}" value="${(message.getId())}">

    </div>
</li>
<% } %>
<% } %>
</ul>
    </div>
    </div>
</div>
</body>