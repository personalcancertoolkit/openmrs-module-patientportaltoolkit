<form id="changePasswordForm" class="validatedForm form-horizontal col-xs-8" hidden>
    <div id="bothentriesNotSameError" class="alert alert-danger" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Error: Please make sure both entries are same
    </div>
    <div id="Error" class="alert alert-danger" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Error: <label id="error-message"/>
    </div>
    <div id="passwordSaveSuccess" class="alert alert-success" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Success: your new password has been successfully saved
    </div>
    <div id="passwordRequirements" class="alert alert-info" role="alert">
        <span class="fa fa-info-circle fa-lg"></span>
        Note: Please make sure the password is at least 8 characters long and includes a number,upper and lower case letters.
    </div>
    <br>
    <div class="form-group">
        <label class="control-label col-xs-4" for="currentPassword">Current Password</label>
        <div class="col-xs-8">
            <input class="form-control" id="currentPassword" type="password"/>
        </div>
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

            jq.post("changePassword/saveNewPassword.action", {currentPassword: jq("#currentPassword").val(), newPassword: jq("#newPassword").val()}, function ( ) {})
                .fail(function(jqXHR, textStatus, errorThrown) {
                    console.error({textStatus, errorThrown});
            }).success(function(){
                switch (response.status.toString()) {
                    case "200":
                        jq("#passwordSaveSuccess").show();
                        break;
                    case "500":
                        jq("#Error").show();
                        jq("#error-message").value(response.body);
                        break;
                }});

                jq("#currentPassword").val('');
                jq("#newPassword").val('');
                jq("#confirmPassword").val('');
        }
        else {
                jq("#bothentriesNotSameError").show();
            }
        });
</script>