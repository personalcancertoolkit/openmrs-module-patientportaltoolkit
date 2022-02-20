<form id="changePasswordForm" class="form-horizontal col-xs-8" hidden>
    <div id="bothentriesNotSameError" class="alert alert-danger" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Error: Please make sure both entries are same
    </div>
    <div id="passwordSaveSuccess" class="alert alert-success" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Success: your new password has been successfully saved
    </div>
    <div class="form-group">
        <label class="control-label col-xs-4" for="newPassword">New Password</label>
        <div class="col-xs-8">
            <input class="form-control" id="newPassword" type="password"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-xs-4" for="confirmPassword">Confirm New Password</label>
        <div class="col-xs-8">
            <input class="form-control" id="confirmPassword" type="password"/>
        </div>
    </div>
    <div class="form-group pull-right">
        <button type="button" class="btn btn-default cancelModal" id="saveNewPasswordCancel">Cancel</button>
        <button type="button" class="btn btn-primary" id="saveNewPassword">Save</button>
    </div>
</form>
<script>
    jq('#saveNewPassword').click(
        function () {
            logEvent('clicked_ChangePassword_saveNewPassword','');
            if (jq("#newPassword").val() == jq("#confirmPassword").val()) {
                jq("#bothentriesNotSameError").hide();
                jq("#passwordSaveSuccess").show();

            jq.post("changePassword/saveNewPassword.action", {newPassword: jq("#newPassword").val()}, function () {
            });

            //alert("refreshing");
            setTimeout(function () {
                location.reload();
            }, 1000);
        }
        else {
                jq("#bothentriesNotSameError").show();
            }
        });
</script>