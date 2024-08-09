${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<body>
<div>
    <div id="inboxContainer" 
        class="container pull-left col-sm-4" data-sending-person-uuid="${(personUUID)}"
        style="max-height:100vh; overflow:scroll"
    >
        <div style="display:flex; justify-content:space-between">
            <h5>
                Inbox
            </h5>
            <div style="display:flex; align-self:flex-end: align-items: center">
                <% if(contextUser.isSuperUser()) { %>
                    <button type="button" 
                        id="broadcastMessageButton" 
                        class="btn btn-primary btn-sm" 
                        style="margin-right:4px"
                        title="Broadcast Message To All Active Patients">
                        <span class="fa fa-bullhorn fa-lg"></span>
                    </button>
                <% } %>     
                <button class="btn btn-default btn-sm pull-right" 
                    onclick="logEvent('clicked_Messages_ComposeMessage_Clicked','')" 
                    type="button" id="composeMessageButton">Compose Message
                </button>            
            </div>
        </div>
        <hr style= "marginTop: '8px'"/>
        <ul class="media-list">
            <% if (messages) { %>
                <% messages.each { message -> 
                    def otherPersonNameInMessage = message.sender.equals(contextUser.getPerson()) ? message.receiver.getPersonName(): message.sender.getPersonName() 
                %>
                    <li class="media messagelistLink" 
                        id="${(message.uuid)}" 
                        data-message-title="${(message.title)}"
                        style="padding:8px; padding-top:0px; cursor: pointer"
                    >
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object imagePlaceHolders" alt="Picture" width="40" height="40" />
                            </a>
                        </div>

                        <div class="media-body">
                            <% if (!message.children) { %>
                                <h4 class="media-heading">
                                    <strong>${(message.title)}</strong>
                                </h4>
                                <small><strong> 
                                    ${(otherPersonNameInMessage)}
                                    </strong> - ${(message.getDateCreatedFormatedAsDateTime())}
                                </small>
                                <%if (!message.hasBeenViewedByReceiver() && message.receiver.equals(contextUser.getPerson())) {%>
                                    <span class="fa fa-circle fa-md" display="block" style="float: right; color: orange;" title="Unread"></span>
                                <% } %>
                                <br />
                                <small >
                                    ${(message.getContentPreview())}
                                </small>
                            <% } else if (message.children) { def counter1 = 0; def counter2 = 0; %>
                                <% message.children.each { messageChild ->  counter1++%>
                                <% } %>
                                <% message.children.each { messageChild ->  counter2++ %>
                                    <% if (counter1 == counter2) { %>
                                        <h4 class="media-heading">
                                            <strong>${(messageChild.title)}</strong>
                                        </h4>
                                        <small><strong> 
                                            ${(otherPersonNameInMessage)}
                                            </strong> - ${(message.getDateCreatedFormatedAsDateTime())}
                                        </small>
                                        <%if (!messageChild.hasBeenViewedByReceiver()  && messageChild.receiver.equals(contextUser.getPerson())) {%>
                                            <span class="fa fa-circle fa-md" display="block" style="float: right; color: orange;" title="Unread"></span>
                                        <% } %>
                                        <br />
                                        <small >
                                            ${(messageChild.getContentPreview())}
                                        </small>
                                    <% } %>
                                <% } %>
                            <% } %>
                        </div>
                    </li>
                    <hr style="margin-top:4px; margin-bottom-4px"/>
                <% } %>
            <% } %>
        </ul>

    </div>
${ ui.includeFragment("patientportaltoolkit", "composeMessage") }

    <div id="showDetailedList" class="col-sm-8 col-md-7 pull-left">
        <ul class="media-list">
        <% if (messages) { %>
            <% messages.each { message -> 
                def otherPersonNameInMessage = message.sender.equals(contextUser.getPerson()) ? message.receiver.getPersonName(): message.sender.getPersonName(); 
                def otherPersonIsSender = message.receiver.equals(contextUser.getPerson()); 
            %>
            <li class="media detailedMessageList" id="mediaList${(message.uuid)}">
                <h4>
                    ${(otherPersonNameInMessage)} | ${(message.title)}
                </h4>
                <div class="media-body">
                    <h5 class="media-heading" style="padding-top: 8px">
                        <strong>${(otherPersonIsSender ? message.sender.getPersonName():  "You")} - ${(message.getDateCreatedFormatedAsDateTime())}</strong>                   
                        <%if (!message.hasBeenViewedByReceiver() && otherPersonIsSender) {%>
                            <span class="fa fa-circle fa-md" display="block" style="float: right; color: orange; " title="Unread"></span>
                        <% } %>
                    </h5>
                    <p>${(message.content)}</p>
                    <% if (message.children) { %>
                        <% message.children.each { messageChild -> %>
                            <h5 class="media-heading" style="padding-top: 8px">
                                <strong>${(messageChild.sender.equals(contextUser.getPerson()) ? "You" : messageChild.sender.getPersonName())} - ${(messageChild.getDateCreatedFormatedAsDateTime())}</strong>                
                                <%if (!messageChild.hasBeenViewedByReceiver() && messageChild.receiver.equals(contextUser.getPerson())) {%>
                                    <span class="fa fa-circle fa-md" display="block" style="float: right; color: orange; " title="Unread"></span>
                                <% } %>
                            </h5>                            
                            <p>${(messageChild.content)}</p>
                        <% } %>
                    <% } %>
                </div>

                <hr style= "marginTop: '8px'"/>

                <div class="form-group" style="display:none">
                    <label htmlFor="sendingReplyMessageSubject${(message.getUuid())}">Reply</label>
                    <input id="sendingReplyMessageSubject${(message.getUuid())}" class="form-control" placeholder="subject" />
                </div>

                <textarea id="sendingReplyMessageText${(message.getUuid())}" class="form-control" 
                    placeholder="Reply to ${(message.receiver.equals(contextUser.getPerson()) ? message.sender.getPersonName() : message.receiver.getPersonName())}"></textarea>
                <br />

                <div class="pull-right">
                    <div id="sendReplyMessageButton${(message.getUuid())}" 
                        class="btn btn-primary btn-sm sendReplyMessageButton">Send
                    </div>
                </div>
            </li>
            <% } %>
        <% } %>
        </ul>
    </div>
</div>
</div>
</body>