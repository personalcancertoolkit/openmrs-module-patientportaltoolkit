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
    width: 70%;
    height: 100%;
    border: none;
}
</style>

<div id="content" class="container">
    <script>
        function resize_iframe_height(obj) { obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px'; }
    </script>
    <!-- note: onload triggers after every time the url of the iframe changes; so, when height of iframe content changes so will the iframe when `onload="resize_iframe_height(this)"` -->
    <iframe id="portal" src="${SymptomManagementPortalUrl}" frameborder="0" scrolling="no" onload="resize_iframe_height(this)" style = 'width:100%; max-width:1000px;' />
</div>