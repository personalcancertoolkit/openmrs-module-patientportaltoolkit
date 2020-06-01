<script>
    jq(document).ready(function() {
        jq("#post-delete-btn").click(
            function () {
                jq.get("statusUpdater/deletePost.action", {postId:  jq("#remove-postId").val()}, function(){
                    location.reload();
                });
            });
    });
</script>
<div class="modal fade" id="confirm-post-remove" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">remove Post?</h4>
            </div>

            <div class="modal-body">
                Are you sure that you'd like to remove this post?
            </div>
            <div class="modal-footer">
                <input type="hidden" id="remove-postId">
                <button type="button" class="btn btn-default" data-dismiss="modal">Go Back</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="post-delete-btn">Yes, Remove post</button>
            </div>
        </div>
    </div>
</div>