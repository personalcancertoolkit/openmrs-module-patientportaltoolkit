<div class="container bgcontent col-sm-8 col-sm-offset-2">
    Please write down your questions or feedback. For us to be able to respond, please write your contact information below including your name, email address and/or phone number so that the Personal Cancer Toolkit support team may contact you whenever needed. Please direct any questions regarding your health directly to your medical or surgical health care providers.
    <br>
    <br>
    <textarea id="feedbacktextdata" class="form-control" placeholder="Enter your feedback"></textarea>
    <br>
    <!--
        cancel button does nothing
        <button type="button" class="btn btn-default cancelFeedback">Cancel</button>
    -->
    <button type="button" class="btn btn-primary" id="sendFeedback">Send</button>
</div>

<script>
    document.getElementById("sendFeedback").onclick = function(){
        if(jq("#feedbacktextdata").val() == null || jq("#feedbacktextdata").val() == '') return alert("Please make sure your message is not empty!"); // dont submit w/ empty text data
        jq.get("feedback/sendFeedback.action", {
            feedbackMessage: jq("#feedbacktextdata").val()
        }, function (text) { 
            alert("Your feedback has been successfuly sent!")
            location.reload();
        }).fail(function() {
            alert('Sorry, unfortunately there was some sort of error sending this feedback. Please contact an administrator manually!'); 
        });
    }

</script>