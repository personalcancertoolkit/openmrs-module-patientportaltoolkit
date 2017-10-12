<div class="modal fade" id="edit-relationship-modal" role="dialog" aria-labelledby="editRelationshipModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editRelationshipModalLabel">Edit Connection</h4>
            </div>

            <div class="modal-body">
                <input id="editRelationshipIdHolder" type="hidden"/>

                <form class="form-horizontal" role="form">
                    <div class="form-group ">
                        <div class="media">
                            <div class="media-left">
                                <a href="#"> 
                                    <div class="profileBadge" id="editRelationProfileBadge">
                                        P B
                                    </div> 
                                </a>
                            </div>

                            <div class="media-body">
                                <h4 class="media-heading" id="editPersonName"></h4>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="editRelationship-type">Is My:</label>

                        <div class="col-sm-10" id="editRelationship-type">
                            <% if (relationshipTypes) { %>
                            <select class="form-control" id="editRelationshipSelect">
                                <% relationshipTypes.each { relationshipType -> %>
                                <option value="${(relationshipType.getId())}">${(relationshipType.aIsToB)}</option>
                                <% } %>
                            </select>
                            <% } %>
                        </div>
                    </div>
                    <!-- <div class="form-group">
                         <label class="control-label col-sm-2" for="editRelationSecurityLevels">Have Access:</label>

                         <div class="col-sm-10 form-group">
                             <select class="form-control" id="editRelationSecurityLevels">
                                 <% securityLayers.each { securityLayer -> %>
                                 <option value="${securityLayer.getUuid()}">${securityLayer.getDescription()}</option>
                                 <% } %>
                             </select>
                         </div>
                     </div>-->

                    <div class="form-group">
                        <label class="control-label col-sm-2">Have Access:</label>
                        <% securityLayers.each { securityLayer -> %>
                        <div class="form-check form-check-inline">
                            <label class="form-check-label">
                                <input class="form-check-input editRelationShareCheckbox" type="checkbox" id="${securityLayer.getUuid()}"
                                       value="${securityLayer.getUuid()}"> ${securityLayer.getDescription()}
                            </label>
                        </div>
                        <% } %>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button class="btn btn-default pad-left" id="editRelationshipSaveButton">Save</button>
                </div>
            </div>
        </div>
    </div>
</div>
