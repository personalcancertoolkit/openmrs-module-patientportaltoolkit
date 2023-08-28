<script>
    jq(document).ready(function() {
        jq("#editPostUpdate").click(
            function () {
                logEvent('clicked_MyPosts_Confirm_Update_Post',JSON.stringify({ 'postId': jq("#edit-postId").val() }));
                
                const title = jq("#editPostTitle").val();
                const content = jq("#editPostContent").val();

                if (title && content) {
                    jq.get("statusUpdater/editSavePost.action", { 
                        postId:  jq("#edit-postId").val(), 
                        title: title, 
                        content: content
                    }, function() {
                        location.reload();
                    });
                } else {
                    alert('Please include both title and content for the update');
                }
            });
    });
</script>
<div class="modal fade" id="post-edit" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Edit Post</h4>
            </div>
            <div class="modal-body">
                <span>Title: </span><input id="editPostTitle" name="title"/>
                <br>
                <span>Content: </span><textarea id="editPostContent" name="content"></textarea>
            </div>
            <div class="modal-footer">
                <input type="hidden" id="edit-postId">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="logEvent('clicked_MyPosts_GoBack_UpdatePost',JSON.stringify({'postId': jq('#edit-postId').val()}))">Go Back</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="editPostUpdate">Update</button>
            </div>
        </div>
    </div>
</div>