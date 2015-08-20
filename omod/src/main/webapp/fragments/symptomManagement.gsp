<script type="text/javascript">
    /*jQuery(document).ready(function(){
        var contextPath=window.location.href;
        contextPath=contextPath.split("patientportaltoolkit")[0];
        \$.get(contextPath + "module/patientportaltoolkit/portal.htm", function( data ) {
           // console.log (data);
            jQuery("#portal").attr("src","/"+data);
            //alert( "Load was performed." );
        });
       // jQuery("#portal").attr("src", ""+ contextPath + "module/patientportaltoolkit/portal.htm");
    });*/
</script>

<style>
#portal {
    width: 100%;
    height: 800px;
    border: none;
}
</style>

<div id="content" class="container">
    <iframe id="portal" src="${SymptomManagementPortalUrl}"></iframe>
</div>