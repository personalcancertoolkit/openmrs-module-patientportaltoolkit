<script>
    var user_is_logged_in = ${loggedInBoolean};
    //console.log(user_is_logged_in);
    if(user_is_logged_in) window.location.replace("/openmrs/patientportaltoolkit/home.page");
</script>


${ ui.includeFragment("patientportaltoolkit", "headerForExternal") }
<link rel="stylesheet"
      href="${ ui.resourceLink("patientportaltoolkit", "styles/loginmodal.css") }"
      type="text/css">



<div class = 'flexme ' style = 'position:absolute; top:0; left:0; right:0; bottom:0;'> 
    <div style = 'position:relative; width:100%; height:100%; max-height:1080px;  '>
        <div  class = '' style = 'position:absolute; width:100%; height:100%; overflow:hidden; z-index:-333; '>
            <div class = 'image_element_to_place_gradient_over' style = 'position:absolute; top:0px; bottom:0px; left:0px; right:0px;  background-image:url("${ ui.resourceLink("patientportaltoolkit", "/images/diversity.jpg") }"); background-position:top;  z-index:-332;'></div>
            <!--<img src = '/img/toy_plane.jpg' style = 'position:absolute; margin:auto; bottom:-100; width:100%;  z-index:-333;'> -->
        </div>    
    </div>
</div>

<div style = 'position:absolute; width:100%; top:100px; left:0; right:0; display:flex;' >
    <div style = 'margin:auto;'>
        ${ui.includeFragment("patientportaltoolkit","customLoginService")}
        ${ui.includeFragment("patientportaltoolkit","forgotPassword")}
    </div>
</div>


<style>
.image_element_to_place_gradient_over {
    background-size: cover;
    /* background-image:  url('http://unsplash.it/1200x800'); */
}

.image_element_to_place_gradient_over:before{
    content: '';
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-image: linear-gradient(to bottom right,rgba(0, 47, 75, 0.59),rgba(248, 248, 248, 0.83));
    opacity: .6; 
}
</style>