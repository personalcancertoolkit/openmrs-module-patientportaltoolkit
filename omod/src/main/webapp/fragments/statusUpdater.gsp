<form method="POST" class="form" id="status-update-form">
    <div class="form-group">
        <div class="panel panel-default">
            <div class="panel-body">
                <input id="statusUpdaterTitle" name="title" class="form-control" placeholder="Title for your Journal Entry...">
                <br>
                <textarea id="statusUpdaterContent" name="content" class="form-control" placeholder="Share your thoughts..."></textarea>
            </div>
            <div class="panel-footer clearfix">
                <div class="pull-right">
                    <span>Share with&ensp;</span>
                    <select>
                        <option selected>All Connections</option>
                        <option>Personal Connections</option>
                        <option>Physician Connections</option>
                    </select>
                    <button id="statusUpdaterButton" class="btn btn-primary btn-sm" >Post</button>
                </div>
            </div>
        </div>
    </div>
</form>
