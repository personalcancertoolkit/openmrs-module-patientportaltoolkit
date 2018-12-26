
<head>
    <title>OpenMRS Patient Portal</title>
    <link rel="shortcut icon" type="image/ico" href="/openmrs/images/openmrs-favicon.ico">
    <link rel="icon" type="image/png\" href="/openmrs/images/openmrs-favicon.png">
    
    <script type="text/javascript" src="${ ui.resourceLink("patientportaltoolkit", "/scripts/jquery-1.11.1.min.js") }"></script>
    <script type="text/javascript" src="${ ui.resourceLink("patientportaltoolkit", "/scripts/bootstrap.min.js") }"></script>
    <link rel="stylesheet"
          href="${ ui.resourceLink("patientportaltoolkit", "styles/bootstrap.min.css") }"
          type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">

    <script> jq = jQuery;
    function loginForgotSwitch() {
        jq("#loginSnippet").toggle();
        jq("#forgotPasswordSnippet").toggle();
    }
    </script>



</head>
<!--
<div class="navbar navbar-default container">
    <div class="" style = 'color:white; height:60px; display:flex;'>
        <div style = 'margin:auto; margin-left:5px; font-size:18px;'>
            <div>
                Personal Cancer Toolkit
            </div>
            <div style = 'margin-left:10px; margin-top:-5px;'>
                <span style = 'font-size:10px;'> Catchy slogan goes here... </span>
            </div>
        </div>
        <div style = 'margin:auto;'></div>
        <a href = 'javascript:location.reload();' class = 'anchor_button' style = 'margin:auto; margin-right:5px; height:100%; display:flex;'>
            <div style = 'padding:15px; margin:auto;'>
                LOGIN
            </div>
        </a>
    </div>
</div>
-->
