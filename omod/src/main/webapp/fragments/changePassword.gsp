<form id="changePasswordForm" class="validatedForm form-horizontal col-xs-8" hidden>
    <div id="bothentriesNotSameError" class="alert alert-danger" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Error: Please make sure both entries are same
    </div>
    <div id="Error" class="alert alert-danger" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Error: Something went wrong while saving your new password. Check if it is long enough and conforms to the pattern. Then try again
    </div>
    <div id="passwordSaveSuccess" class="alert alert-success" role="alert" style="display: none">
        <span class="fa fa-exclamation-circle fa-lg"></span>
        Success: your new password has been successfully saved
    </div>
    <div id="passwordRequirements" class="alert alert-info" role="alert">
        <span class="fa fa-info-circle fa-lg"></span>
        Note: Please make sure the password is at least 8 characters long and includes a number, upper and lower case letters.
    </div>
    <br>
    <div class="form-group">
        <label class="control-label col-xs-4" for="currentPassword">Current Password</label>
        <div class="col-xs-8">
            <input class="form-control" id="currentPassword" type="password" required/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-xs-4" for="newPassword">New Password</label>
        <div class="col-xs-8">
            <input class="form-control" id="newPassword" type="password" required/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-xs-4" for="confirmPassword">Confirm New Password</label>
        <div class="col-xs-8">
            <input class="form-control" id="confirmPassword" type="password" required/>
        </div>
    </div>
    <div class="form-group pull-right">
        <button type="button" class="btn btn-default cancelModal" id="saveNewPasswordCancel">Cancel</button>
        <button type="submit" class="btn btn-primary" id="saveNewPassword">Save</button>
    </div>
</form>
<script>
    jq('#changePasswordForm').submit(
    function(e) {
        logEvent('clicked_ChangePassword_saveNewPassword', '');

        const newPassword = jq("#newPassword").val();
        const confirmPassword = jq("#confirmPassword").val();

        if (newPassword === confirmPassword) {
            jq("#bothentriesNotSameError").hide();

            jq.post("changePassword/saveNewPassword.action", {
                currentPassword: jq("#currentPassword").val(),
                newPassword: newPassword
            }).fail(function(jqXHR, textStatus, errorThrown) {
                console.log({jqXHR, textStatus, errorThrown})
                jq('#Error').show();
            }).success(function() {
                jq("#passwordSaveSuccess").show();
                jq("#currentPassword").val('');
                jq("#newPassword").val('');
                jq("#confirmPassword").val('');                
                jq('#Error').hide();
            });
        } else {
            jq("#bothentriesNotSameError").show();
        }

        e.preventDefault();
        e.stopPropagation();
    });
</script>