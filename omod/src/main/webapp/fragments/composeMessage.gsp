<div id="newMessageComposeDiv" class="col-md-7 pull-left">
    <div style="backgroundColor: '#f00'">
        <form data-toggle="validator" role="form" >
            <fieldset>
                <div class="form-group">
                    <label htmlFor="sendingto">To</label>
                    <input id="sendingto" class="form-control" />
                </div>
                <p id="recipientSuggestionsContainer" style="display:none">
                    Suggestions:
                </p>
                <div class="form-group">
                    <input id="sendingNewMessageSubject" class="form-control" placeholder="Subject" />
                </div>
                <textarea id="sendingNewMessageText" class="form-control" placeholder="Write a Message"></textarea>


                <br />
                <div class="pull-right">
                    <div id="sendNewMessageButton" class="btn btn-primary btn-sm" >Send</div>
                    <input type="hidden" id="recipientPersonUUID">
                </div>
            </fieldset>
        </form>
    </div>
</div>