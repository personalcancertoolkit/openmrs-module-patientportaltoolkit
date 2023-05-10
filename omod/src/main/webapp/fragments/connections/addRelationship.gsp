<div class="modal fade" id="add-relationship-modal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Add connection</h4>
            </div>

            <div class="modal-body">
                <div id="connectionSaveSuccess" class="alert alert-success" role="alert" style="display: none">
                    <i class="fa fa-solid fa-address-card"></i> &nbsp;
                Success: Your new connection has been saved
                </div>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="personName">Person's name:</label>
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
                        <label class="control-label col-sm-2" for="relationship-type">Is my:</label>
                        <div class="col-sm-10" id="relationship-type">
                            <% if (relationshipTypes) { %>
                            <select class="form-control" id="addRelationshipSelect">
                                <% relationshipTypes.each { relationshipType -> %>
                                <option value="${(relationshipType.getId())}">${(relationshipType.aIsToB)}</option>
                                <% } %>
                            </select>
                            <% } %>
                        </div>
                    </div>
                    <!-- <div class="form-group">
                        <label class="control-label col-sm-2" for="share-connections">Have Access:</label>
                        <div class="col-sm-10" id="share-connections">
                            <select class="form-control" id="addRelationSecurityLevels">
                                <% securityLayers.each { securityLayer -> %>
                                <option  value="${securityLayer.getUuid()}">Can see my ${securityLayer.getDescription()} </option>
                                <% } %>
                            </select>
                        </div>
                    </div>-->
                    <div class="form-group">
                        <label class="control-label col-sm-2">Have access:</label>
                        <% securityLayers.each { securityLayer -> %>
                        <div class="form-check form-check-inline">
                            <label class="form-check-label">
                                <input class="form-check-input addRelationShareCheckbox" 
                                type="checkbox" 
                                id="addShareType${securityLayer.getUuid()}"
                                value="${securityLayer.getUuid()}"
                                data-security-layer-description="${securityLayer.getDescription()}"> Can see my ${securityLayer.getDescription()}
                            </label>
                        </div>
                        <% } %>
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
<script>
    jq(document).ready(function() {

        const addRelationshipModal = jq("#add-relationship-modal");
        const addRelationshipSelect = addRelationshipModal.find("#addRelationshipSelect");
        const canSeeMyPostsCheckbox = addRelationshipModal.find('.addRelationShareCheckbox[data-security-layer-description="Posts"]');

        if (addRelationshipSelect.children("option:selected").text() === "Doctor") {
            canSeeMyPostsCheckbox.prop("checked", false).prop("disabled", true).parents(".form-check").hide();
        }

        addRelationshipSelect.on('change', function () {
            if(addRelationshipSelect.children("option:selected").text() === "Doctor") {
                canSeeMyPostsCheckbox.prop("checked", false).prop("disabled", true).parents(".form-check").hide();
            } else {
                canSeeMyPostsCheckbox.prop("disabled", false).parents(".form-check").show();
            }
        });
        
    });

</script>