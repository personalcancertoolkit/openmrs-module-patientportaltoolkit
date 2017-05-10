
${ ui.includeFragment("patientportaltoolkit", "headerForExternal") }

<link rel="stylesheet"
      href="${ ui.resourceLink("patientportaltoolkit", "styles/loginmodal.css") }"
      type="text/css">


<div style = 'position:absolute; top:0; bottom:0; left:0; right:0; overflow:hidden;'>
    <div style = 'position:absolute; width:100%; height:100%; background-color:rgba(250, 250, 250, 0.17);'></div> <!-- overlay color -->
    <!--
    doctors
    hallway
    Rx_and_stethoscope
    PC_and_stethoscope
    doctor_with_stethoscope
    food_wood_stethoscope -- good
    -->
    <img src = "${ ui.resourceLink("patientportaltoolkit", "/images/PC_and_stethoscope.jpg") }" style = 'max-width:100%'></img>
</div>
<div style = 'position:absolute; width:100%; top:100px; left:0; right:0; display:flex;' >
    <div style = 'margin:auto;'>
        <div class="modal-dialog" style = 'margin:auto;  '>
            <div class="loginmodal-container" style = 'min-width:400px;'>
                <h1>Welcome</h1><br>
                <div style = 'text-align:center;'>
                    To the Personal Cancer Toolkit Platform.
                </div>
            </div>
        </div>

        <div style = 'height:50px;'></div>

        <div class="modal-dialog" style = 'margin:auto;  '>
            <div class="loginmodal-container" style = 'min-width:400px;'>
                <h1>Login</h1><br>


                <input type="text" name="user" placeholder="Username">
                <input type="password" name="pass" placeholder="Password">
                <input type="submit" name="login" class="login loginmodal-submit" value="Login">

                <div style = "font-size: 12px;">
                <!-- 
                <a href="#">Register</a> 
                  - 
                -->
                <a href="javascript:alert('Coming Soon!');">Forgot Password</a>
                </div>
            </div>
        </div>
    </div>
</div>
