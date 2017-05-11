<script>
    var user_is_logged_in = ${loggedInBoolean};
    //console.log(user_is_logged_in);
    if(user_is_logged_in) window.location.replace("/openmrs/patientportaltoolkit/home.page"); 
</script>





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
    <img src = "${ ui.resourceLink("patientportaltoolkit", "/images/cancer-patient-picture.jpg") }" style = 'max-width:110%'></img>
</div>
<div style = 'position:absolute; width:100%; top:100px; left:0; right:0; display:flex;' >
    <div style = 'margin:auto;'>
        ${ui.includeFragment("patientportaltoolkit","customLoginService")}
    </div>
</div>


