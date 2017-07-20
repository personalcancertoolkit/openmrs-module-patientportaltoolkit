<!--
<div class="modal-dialog" style = 'margin:auto;  '>
    <div class="loginmodal-container" style = 'min-width:400px;'>
        <h1>Welcome to</h1><br>
        <div style = 'text-align:center;'>
            <img src = "${ ui.resourceLink("patientportaltoolkit", "/images/Personal-Cancer-Toolkit-Logo.png") }" style = 'max-width:100%'></img>
        </div>
    </div>
</div>
<div style = 'height:50px;'></div>
-->


<div id="loginSnippet" class="modal-dialog" style = 'margin:auto;  '>
    <div class="loginmodal-container" style = 'min-width:400px;'>
        <h1 style = ''> <div style = 'margin-top:-15px;'></div>Login <Br> <div style = 'font-size:16px; margin-top:10px;'> to your Personal Cancer Toolkit</div></h1><br> 
        <% if (passwordChange){ %>
        <div id="forgotPasswordReset" class="alert alert-success" role="alert">
            <span class="fa fa-exclamation-circle fa-lg"></span>
            Your password has been successfully changed
        </div>
        <% } %>
        <input type="text" id = 'username' name="user" placeholder="Username">
        <input type="password" id = 'password' name="pass" placeholder="Password">
        <input type="submit" name="login" class="login loginmodal-submit" value="Login" onclick = 'attempt_login()'>
        <p align="left"><a href="javascript:loginForgotSwitch()" style="color: blue; font-size:12px;">Forgot Password?</a></p>

        <div style = "font-size: 12px;">
        <!-- 
        <a href="#">Register</a> 
          - 
        -->
        </div>
    </div>
</div>


<script>
    function attempt_login(){
        if(document.getElementById("username").value == "" || document.getElementById("password").value == ""){
            alert("Please ensure both username and password are filled out.");
            return;
        }
        jq.post("", {
            username:document.getElementById("username").value,
            password:document.getElementById("password").value
        }, function (response) {
            //console.log(response);
            location.reload();
        });
    }
</script>