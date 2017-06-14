<script>
window.addEventListener("message", function(e){
    //console.log("here is a message");
    //console.log(e.data);
    var this_frame = document.getElementById("symptom_managment_iframe");
    //console.log("Is our frame?");
    //console.log(this_frame.contentWindow === e.source)
    if (this_frame.contentWindow === e.source) {
        console.log("updating smyp man height");
        this_frame.height = e.data.height + "px";
        this_frame.style.height = e.data.height + "px";
    }
})
</script>

<div id="content" class="container">
    <!-- note: onload triggers after every time the url of the iframe changes; so, when height of iframe content changes so will the iframe when `onload="resize_iframe_height(this)"` -->
    <iframe class="portal" id = 'symptom_managment_iframe' src="${SymptomManagementPortalUrl}" frameborder="0" scrolling="no" style = 'width:100%; max-width:1000px; height:100%; border: none;' > </iframe>
</div>