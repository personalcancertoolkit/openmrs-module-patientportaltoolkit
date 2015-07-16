<div class="modal fade" id="add-relationship-modal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Add Connection</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="personName">Person's Name:</label>
                        <div class="col-sm-10 form-inline" id="personName">
                            <input type="name" class="form-control" id="givenpersonName" placeholder="Given Name">
                            <input type="name" class="form-control" id="familypersonName" placeholder="Family Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="gender">Gender:</label>
                        <div class="col-sm-10" id="gender">
                            <select id="genderSelect" class="form-control">
                                <option value="M" selected>Male</option>
                                <option value="F">Female</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="personEmail">Email address:</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="personEmail" placeholder="Enter email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="relationship-type">Relationship Type:</label>
                        <div class="col-sm-10" id="relationship-type">
                            ${ ui.includeFragment("patientportaltoolkit", "relationshipSelect",[parentForm: "addRelation"]) }
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="share-connections">Share With:</label>
                        <div class="col-sm-10" id="share-connections">
                            <select class="form-control">
                                <option selected>All Connections</option>
                                <option>Personal Connections</option>
                                <option>Physician Connections</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button class="btn btn-default pad-left" id="addrelationshipbutton">Add</button>
                </div>
               </div>
        </div>
    </div>
</div>