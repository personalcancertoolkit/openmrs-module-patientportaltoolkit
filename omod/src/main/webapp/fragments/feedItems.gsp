<script>

    jq(document).ready(function() {
        jq(".removeJournalButton").click(
            function () {
                jq("#remove-postId").val(this.id.split("journalRemove")[1]);
                logEvent('clicked_MyPosts_Remove_Post',JSON.stringify({'postId': jq("#remove-postId").val()}));
            });
        jq(".editJournalButton").click(
            function () {
                const journalId = this.id.split("journalEdit")[1];                
                logEvent('clicked_MyPosts_Edit_Post',JSON.stringify({'postid': journalId}));
                jq("#edit-postId").val(journalId);
                jq("#editPostTitle").val(jq("#journalTitle"+journalId).text());
                jq("#editPostContent").val(jq("#journalContent"+journalId).text());
            });
        jq(".journalComment").keypress(
            function (event) {
                const commentContent = this.value;
                if (event.which === 13 && commentContent) {                    
                    logEvent('clicked_MyPosts_Add_Comment','');
                    const journalID = (this.id).split("commentbox")[1];
                    
                    jq.get("commentBox/saveComment.action", {
                        commentContent: this.value,
                        parentId: journalID
                    }, function () {
                        location.reload();
                    });
                }
            });
    });
</script>
<% if (journals) { %>
${ ui.includeFragment("patientportaltoolkit", "removePost") }
${ ui.includeFragment("patientportaltoolkit", "editPost") }
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
                    <h5 class="media-heading">${(journal.creator.getGivenName())}  - <span id="journalTitle${ journal.uuid }">${(journal.title)}</span> <small>&emsp;
                            ${ pptutil.formatDate(journal.dateCreated)}</small></h5>
                    <% if(journal.creator.person==person) { %>
                    <div class="pull-right">
                        <a id="journalEdit${ journal.uuid }" class="no-underline-edit fa fa-pencil editJournalButton"  data-toggle="modal" data-target="#post-edit"></a>
                        &nbsp;
                        <a id="journalRemove${ journal.uuid }" class="no-underline-edit fa fa-trash removeJournalButton"  data-toggle="modal" data-target="#confirm-post-remove"></a>
                    </div>
                    <% } %>
                    <p id="journalContent${ journal.uuid }">${(journal.content)}</p>
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
                                        <h5 class="media-heading">${(it.creator.getGivenName())}  - <span id="journalTitle${ it.uuid }">${(it.title)}</span> <small>&emsp; ${ pptutil.formatDate(it.dateCreated) }</small></h5>
                                        <% if(it.creator.person==person) { %>
                                        <div class="pull-right">
                                            <a id="journalEdit${ it.uuid }" class="no-underline-edit fa fa-pencil editJournalButton"  data-toggle="modal" data-target="#post-edit"></a>
                                        &nbsp;
                                            <a id="journalRemove${ it.uuid }" class="no-underline-edit fa fa-trash removeJournalButton"  data-toggle="modal" data-target="#confirm-post-remove"></a>

                                        </div>
                                        <% } %>
                                        <p id="journalContent${ it.uuid }">${(it.content)}</p>
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
                            <label><small><span class="fa fa-info-circle fa-lg"></span> &nbsp;Comments are visible to anyone who can see this post (including doctors or family members)</small></label>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </li>
    <% } %>

</ul>
<% } %>