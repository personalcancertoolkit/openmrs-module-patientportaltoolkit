<script>
    /////////////////////////////////////////////////////
    // Initialize buttons and actions on thumbnails
    /////////////////////////////////////////////////////
    jq(document).ready(function(){
        jq('#mycancerbuddiesSave').click(
                function () {
                    //console.log( jq("#mycancerbuddiesname").text()+"-------"+jq("#mycancerbuddiesdescription").text())
                    if(jq("#mycancerbuddiesname").text() != null || jq("#mycancerbuddiesdescription").text() != '') {
                        jq.get("myCancerBuddiesProfileCard/saveMyCancerBuddiesProfileCard.action", {
                            mycancerbuddiesname: jq("#mycancerbuddiesname").text(),
                            mycancerbuddiesdescription: jq("#mycancerbuddiesdescription").text()
                        }, function () {
                        });
                        setTimeout(
                                function () {
                                    location.reload();
                                }, 1000);
                    }
                });

        jq('.addFellowPatient').click(
                function () {
                    var personId=this.id.split('addFellowPatient')[1];
                    //console.log(personId);
                    jq("#addFellowPatientPersonIdHolder").val(personId);

                   // console.log(jq('#addFellowPatientName'+personId).text());
                   // console.log("hello");
                    jq("#mycancerbuddiesrelationshippersonNameInput").text(jq('#addFellowPatientName'+personId).text());
                    jq("#mycancerbuddiesrelationshipgenderInput").text(jq('#addFellowPatientGender'+personId).text());
                    jq("#mycancerbuddiesrelationshipAgeInput").text(jq('#addFellowPatientAge'+personId).text());

                });
        jq("#addmycancerbuddiesrelationshipbutton").click(
                function () {
                    jq.get("myCancerBuddiesProfileThumbnails/addRelationshipforFellowPatients.action", {relationshipPersonId: jq("#addFellowPatientPersonIdHolder").val(),securityLayerType:jq("#mycancerbuddiesrelationshipshare-connectionsaddRelationSecurityLevels").val(),relationshipNote:jq("#mycancerbuddiesrelationshipnoteInput").val()}, function(){
                    });
                    setTimeout(function(){
                        location.reload();
                    }, 3000);
                });
    });
</script>


<!-- Start Popup Add Cancer Buddy Dialog --->
<div class="modal fade" id="add-mycancerbuddies-relationship-modal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Add MyCancerBuddies Connection</h4>
            </div>

            <div class="modal-body">
                <input id="addFellowPatientPersonIdHolder" type="hidden"/>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mycancerbuddiesrelationshippersonName">Person's Name:</label>
                        <div class="col-sm-10 form-inline" id="mycancerbuddiesrelationshippersonName">
                            <label id="mycancerbuddiesrelationshippersonNameInput"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mycancerbuddiesrelationshipAge">Age:</label>
                        <div class="col-sm-10" id="mycancerbuddiesrelationshipAge">
                            <label id="mycancerbuddiesrelationshipAgeInput"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mycancerbuddiesrelationshipgender">Gender:</label>
                        <div class="col-sm-10" id="mycancerbuddiesrelationshipgender">
                            <label id="mycancerbuddiesrelationshipgenderInput"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mycancerbuddiesrelationshipshare-connections">Have Access:</label>
                        <div class="col-sm-10" id="mycancerbuddiesrelationshipshare-connections">
                            <select class="form-control" id="mycancerbuddiesrelationshipshare-connectionsaddRelationSecurityLevels">
                                <% securityLayers.each { securityLayer -> %>
                                <option  value="${securityLayer.getUuid()}">${securityLayer.getDescription()} </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mycancerbuddiesrelationshipnote">Add a note?</label>
                        <div class="col-sm-10" id="mycancerbuddiesrelationshipnote">
                            <textarea id="mycancerbuddiesrelationshipnoteInput"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button class="btn btn-default pad-left" id="addmycancerbuddiesrelationshipbutton">Add</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Popup Add Cancer Buddy Dialog --->


<!-- Begin Listing Cancer Buddies -->
<div id = 'thumbnailSetHolderElement' class="row">
    <% if (mycancerbuddiespeople) { %> <!-- begin if(mycancerbuddiespeople) -->
        <% mycancerbuddiespeople.each { mycancerbuddiesperson -> %>
            <%   
                // Determine whether a button, and if so which button, should be displayed
                the_button_to_display = "";
                if (mycancerbuddiespreferences.myCancerBuddies==true) { 
                    ///////////////
                    // Define pptrelation
                    ///////////////
                    def pptrelation = null;
                    if (pptutil.getRelationbetweenTwoPeople(person,mycancerbuddiesperson.person)) {
                        pptrelation = pptutil.getRelationbetweenTwoPeople(person, mycancerbuddiesperson.person)

                    }
                    if (pptutil.getRelationbetweenTwoPeople(mycancerbuddiesperson.person,person)) {
                        pptrelation = pptutil.getRelationbetweenTwoPeople(mycancerbuddiesperson.person, person)
                    }

                    if (pptrelation  && pptrelation.retired == false && pptrelation.shareStatus==0) { 
                        the_button_to_display = '<button  class="btn btn-info btn-xs disabled" role="button" data-toggle="modal">Pending</button>';
                    }
                    if (pptrelation==null || pptrelation.getShareStatus()==2) { 
                       the_button_to_display = '<button id="addFellowPatient${mycancerbuddiesperson.person.uuid}" class="btn btn-info btn-xs addFellowPatient" role="button" data-toggle="modal" data-target="#add-mycancerbuddies-relationship-modal">Add Connection</button>';
                    }
                } 
            %>
            <%
                // Display Templated Thumbnail  
            %>
            <div id = 'thumbnailElementFor_${mycancerbuddiesperson.person.uuid}' class="col-xs-18 col-sm-6 col-md-3">
                <div class="thumbnail">
                    <div class="caption">
                        <h4 id="addFellowPatientName${mycancerbuddiesperson.person.uuid}">${ (mycancerbuddiesperson.myCancerBuddiesName) }</h4>
                        <p>
                            <span id="addFellowPatientAge${mycancerbuddiesperson.person.uuid}">
                                <% if (mycancerbuddiesperson.person.birthdate && !mycancerbuddiesperson.person.getBirthdate().is(null) ){ %>
                                    <% if (mycancerbuddiesperson.person.age > 0) { %>
                                        ${ui.message("coreapps.ageYears", mycancerbuddiesperson.person.age)}
                                    <% } %>
                                <% } %>
                            </span>
                                ~
                            <span id="addFellowPatientGender${mycancerbuddiesperson.person.uuid}">${ui.message("coreapps.gender." + mycancerbuddiesperson.person.gender)}</span>
                        </p>
                        <hr>
                        <p id="addFellowPatientDesc${mycancerbuddiesperson.person.uuid}">${ (mycancerbuddiesperson.myCancerBuddiesDescription) } </p>
                        ${the_button_to_display}
                    </div>
                </div>
            </div>
           
        <% } %> <!-- END for mycancerbuddiespeople.each { mycancerbuddiesperson ->  -->
    <% } %> <!-- end if(mycancerbuddiespeople) -->

</div>
<!-- End Listing Cancer Buddies -->
            
            
            