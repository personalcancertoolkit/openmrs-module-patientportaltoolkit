<div id="newMessageComposeDiv" class="col-md-7 pull-left">
    <div style="backgroundColor: '#f00', display: 'none'">
        <form data-toggle="validator" role="form">
            <div class="form-group">
                <label htmlFor="sendingto">To</label>
                <input id="sendingto" class="form-control" />
            </div>
            <div class="form-group">
                <label htmlFor="subject">Subject</label>
                <input id="subject" class="form-control" />
            </div>
            <textarea class="form-control" value="Write a Message"></textarea>


            <br />
            <div class="pull-right">
                <div class="btn btn-primary btn-sm">Send</div>
                <input type="hidden" id="sendingPersonUUID">
            </div>
        </form>
    </div>
</div>