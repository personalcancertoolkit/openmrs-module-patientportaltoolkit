${ ui.includeFragment("patientportaltoolkit", "patientPortalNav") }
<body>
<div>
    <div class="container pull-left col-md-4">
        <div class="clearfix">
            <h5 class="pull-left">
                Inbox
            </h5>
            <div class="btn btn-default btn-sm pull-right">Compose Message</div>
        </div>
        <hr style= "marginTop: '8px'"/>
        <ul class="media-list">
            <li class="media">
                <div class="media-left">
                    <a href="#">
                        <img class="media-object" alt="Picture" width="40" height="40" />
                    </a>
                </div>
                <div class="media-body">
                    <h5 class="media-heading">
                        <strong>subject</strong>
                        <br />
                        <small><strong>userID</strong></small>
                        <br />
                        <small>content</small>
                    </h5>
                </div>
            </li>
        </ul>
    </div>

    <div class="col-md-7 pull-left">
        <div style="backgroundColor: '#f00', display: 'none'"/>
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
            </div>
        </form>
</div>
    <li class="media">
        <h4>userID</h4>
        <div class="media-body">
            <h5 class="media-heading">
                <strong>subject</strong>
                <br />
                <small><strong>userID</strong></small>
                <br />
                <small>content</small>
            </h5>
        </div>
    </li>
    </div>
</div>
</body>