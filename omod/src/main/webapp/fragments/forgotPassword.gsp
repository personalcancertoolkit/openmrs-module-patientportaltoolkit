<div id="forgotPasswordSnippet" method="post" class="modal-dialog form-group" style = 'margin:auto;  ' hidden>
    <div class="loginmodal-container" style = 'min-width:400px;'>
        <h1>Forgot Password</h1><br>

        <div id="forgotPasswordEmailError" class="alert alert-danger" role="alert" style="display: none">
           <span class="fa fa-exclamation-circle fa-lg"></span>
            Error: Please enter a valid email address
        </div>

        <div id="forgotPasswordEmailSent" class="alert alert-success" role="alert" style="display: none">
            <span class="fa fa-exclamation-circle fa-lg"></span>
            Check your email for the next steps.
        </div>
        <div id="forgotPasswordEmailSending" class="alert alert-info" role="alert" style="display: none">
            <span class="fa fa-exclamation-circle fa-lg"></span>
            Sending ...
        </div>
        <div id="emailIdgroup" class="form-group">
            <label class="control-label" style = 'font-weight:300;' for="forgotPasswordEmail">Email</label>
                <input id = 'forgotPasswordEmail' type="email" class="form-control"/>
        </div>
        <input type="submit" name="sendEmail" class="login loginmodal-submit submit-btn" value="Send Email" onclick = 'sendFogotPasswordEmail()'>

        <p align="left"><a href="javascript:loginForgotSwitch();" style="color: blue" class="go-back-btn">Go Back</a></p>
        <div style = "font-size: 12px;">

        </div>
    </div>
</div>

<script>

    var baseUrl = window.location.href.split("/openmrs")[0];
    function sendFogotPasswordEmail(){
        if(document.getElementById("forgotPasswordEmail").value == ""){
            jq("#forgotPasswordEmailError").show();
            jq("#emailIdgroup").addClass("has-error");
            jq("#forgotPasswordEmailSending").hide();
            return;
        } 
            
        jq("#forgotPasswordEmailSending").show();
        jq("#forgotPasswordEmail").prop("disabled", true);
        jq("#forgotPasswordEmailError").hide();
        jq("#emailIdgroup").removeClass("has-error");
        jq(".submit-btn").prop("disabled", true);
        jq("#forgotPasswordEmailSent").hide();

        console.log(document.getElementById("forgotPasswordEmail").value);       

        const url = baseUrl + "/openmrs/ws/patientportaltoolkit/sendForgotPasswordEmail/"+document.getElementById("forgotPasswordEmail").value;
        
        jq.get(url, function(){
            jq("#forgotPasswordEmailSent").show();
            jq(".go-back-btn").trigger("focus");
            jq("#forgotPasswordEmailSending").hide();

        });
    }
</script>