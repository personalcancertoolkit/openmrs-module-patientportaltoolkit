<script>
    /////////////////////////////////////////////////////
    // Initialize buttons and actions on thumbnails
    /////////////////////////////////////////////////////
    jq(document).ready(function(){
        //jq('#mycancerbuddiesSave').click(
                // function () {
                //     logEvent('clicked_MyCancerBuddies_Thumbnail_save','');
                //     if(jq("#mycancerbuddiesname").text() != null || jq("#mycancerbuddiesdescription").text() != '') {
                //         jq.get("myCancerBuddiesProfileCard/saveMyCancerBuddiesProfileCard.action", {
                //             mycancerbuddiesname: jq("#mycancerbuddiesname").text(),
                //             mycancerbuddiesdescription: jq("#mycancerbuddiesdescription").text()
                //        }).done(function() {
                //              location.reload();
                //          });
                //     }
                // });

        jq('.addFellowPatient').click(
                function () {
                    logEvent('clicked_MyCancerBuddies_addFellowPatient','');
                    var personId=this.id.split('addFellowPatient')[1];
                    logEvent('clicked_MyCancerBuddies_addFellowPatient',JSON.stringify({"personId": personId}));
                    
                    jq("#addFellowPatientPersonIdHolder").val(personId);
                    
                    jq("#mycancerbuddiesrelationshippersonNameInput").text(jq('#addFellowPatientName'+personId).text());
                    jq("#mycancerbuddiesrelationshipgenderInput").text(jq('#addFellowPatientGender'+personId).text());
                    jq("#mycancerbuddiesrelationshipAgeInput").text(jq('#addFellowPatientAge'+personId).text());

                });
        jq("#addmycancerbuddiesrelationshipbutton").click(
                function () {
                    logEvent('clicked_MyCancerBuddies_addRelationshipButton',JSON.stringify({"relationshipPersonId": jq("#addFellowPatientPersonIdHolder").val()}));
                    var checkboxValuesList=[];
                    jq(".mycancerbuddiesAddShareCheckbox:checkbox:checked").each(function () {
                        checkboxValuesList.push(jq(this).val());
                    });
                    var checkboxValues=checkboxValuesList.toString();
                    jq.get("myCancerBuddiesProfileThumbnails/addRelationshipforFellowPatients.action", {
                        relationshipPersonId: jq("#addFellowPatientPersonIdHolder").val(),
                        relationshipNote:jq("#mycancerbuddiesrelationshipnoteInput").val()
                    }).done(function() {
                        location.reload();
                    });
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
                            <div class="form-check form-check-inline">
                                <label class="form-check-label">
                                    <label class="control-label" id="mycancerbuddiesAddShareType"> To see your posts.</label>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mycancerbuddiesrelationshipnote">Add a note?</label>
                        <div class="col-sm-10" id="mycancerbuddiesrelationshipnote">
                            <textarea id="mycancerbuddiesrelationshipnoteInput" placeholder="If you want to connect with other cancer survivors please add a note of introduction here..." rows="4" cols="30"></textarea>
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

<hr>
<div style = 'width:100%; display:flex;'>
    <div style = 'margin:Auto; margin-left:0px;'>
        <div style = 'margin-left:25px; font-size:18px;'>
            Cancer Buddies Near You
        </div>
    </div>
    <div class="dropdown" style = 'margin:Auto; margin-right:25px;'>
      <button class="btn btn-info btn-xs addFellowPatient" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style = 'height:32px; width:125px;'>
        Sort By...
        <span class="caret"></span>
      </button>
      <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" style = 'font-size:14px;'>
        <li><a href="#" onclick = 'thumbnailSortingManager.sortBy("age", "closest");'>Age - Closest to You</a></li>
        <li><a href="#" onclick = 'thumbnailSortingManager.sortBy("age", "desc");'>Age - Oldest to Youngest</a></li>
        <li><a href="#" onclick = 'thumbnailSortingManager.sortBy("age", "asc");'>Age - Youngest to Oldest</a></li>
        <li role="separator" class="divider"></li>
        <li><a href="#" onclick = 'thumbnailSortingManager.sortBy("gender", "asc");'>Gender - Female then Male</a></li>
        <li><a href="#" onclick = 'thumbnailSortingManager.sortBy("gender", "desc");'>Gender - Male then Female</a></li>
      </ul>
    </div>
</div>        
        <Br>
        
        

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

                    if (pptrelation && pptrelation.shareStatus==0) {
                        the_button_to_display = '<hr/><span class="fa fa-hourglass-half text-warning"></span> <label class="text-warning"> Request Pending</label>';
                    }
                    if (pptrelation && pptrelation.shareStatus==1) {
                        the_button_to_display = '<hr/><span class="fa fa-smile-o text-success"></span> <label class="text-success">Connected</label>';
                    }
                    if (pptrelation==null || pptrelation.getShareStatus()==2) {
                       the_button_to_display = '<hr/><button id="addFellowPatient'+mycancerbuddiesperson.person.uuid+'" class="btn btn-info btn-xs addFellowPatient" role="button" data-toggle="modal" data-target="#add-mycancerbuddies-relationship-modal">Add Connection</button>';
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
            <script>
                ////////////////////////////
                // Store 'buddy' data in a global variable for use in sorting.
                /////////////////////////////
                if(window['cancerBuddyData'] == undefined){
                    window['cancerBuddyData'] = {};   
                }
                window['cancerBuddyData']['${mycancerbuddiesperson.person.uuid}'] = {
                    name : '${mycancerbuddiesperson.myCancerBuddiesName}',
                    age : parseInt('${mycancerbuddiesperson.person.age}'),
                    gender : '${mycancerbuddiesperson.person.gender}',
                };
            </script>
        <% } %> <!-- END for mycancerbuddiespeople.each { mycancerbuddiesperson ->  -->
    <% } %> <!-- end if(mycancerbuddiespeople) -->

</div>
<!-- End Listing Cancer Buddies -->
            
            
            
<script>
    ///////////////////////////
    // Define Handler for Sorting Thumbnails 
    ///////////////////////////
    var thumbnailSortingManager = {
        thumbnailSetHolderElement : null,
        thumbnailData : null,
        thumbnailHolderElementBaseID: null,
        thumbnailRootUserData : null,
        
        sortBy : function(attribute, type){
            /////////////
            // Ensure type requested is valid
            /////////////
            valid_types = ["asc", "desc", "closest", "furthest"];
            if (valid_types.indexOf(type) == -1){
                console.error(' sort type requested ('+type+') is not a valid sort type');
            }
            
            /////////////
            // If type is closest, grab the distance copmarison value
            /////////////
            if(type == 'closest'){
                distance_comparison_value = this.thumbnailRootUserData[attribute];
            }
            
            ///////////
            // Generate key value pair to sort on, key is uuid for person
            ///////////
            var keyValuePairs = {};
            var thumbkeys = Object.keys(this.thumbnailData);
            for (var i = 0; i < thumbkeys.length; i++){
                var this_key = thumbkeys[i];
                var this_thumbnail_data = this.thumbnailData[this_key];
                var this_value = this_thumbnail_data[attribute];
                
                // Note : for closest comparisons we are evaluating distance; i.e., the less similar the larger the_value
                if(type == "closest" && attribute == "age"){
                    this_value = Math.abs(distance_comparison_value - this_value);
                } else if (type == "closest" && attribute == "gender"){
                    this_value = (distance_comparison_value == this_value)? 0 : 1;   
                }
                keyValuePairs[this_key] = this_value;
            }
            
            ///////////
            // Run ascending sort on the keyValuePairs
            ///////////
            var sortedKeys = this.sortKeyValuePairsByValueAsc(keyValuePairs);
            
            ///////////
            // If descending sort, reverse the list order
            ///////////
            if(type == 'desc' || type == 'furthest'){
                sortedKeys = sortedKeys.reverse();  
            }
            
            ///////////
            // Update the order of elements inside of the thumbnailHolder
            ///////////
            var concatenatedSortedElements = document.createDocumentFragment();
            for (var i = 0; i < sortedKeys.length; i++){
                this_key = sortedKeys[i];
                this_element = document.getElementById(this.thumbnailHolderElementBaseID + this_key);
                concatenatedSortedElements.appendChild(this_element);
            }
            this.thumbnailSetHolderElement.innerHTML = null;
            this.thumbnailSetHolderElement.appendChild(concatenatedSortedElements);
            
        },
        
        sortKeyValuePairsByValueAsc(sourceKeyValuePairs){
            /////////////
            // Simple, very inefficient, sorting technique
            ////////////  
            var keyValuePairs = JSON.parse(JSON.stringify(sourceKeyValuePairs));  // Deep copy the source keyvaluepairs,
                                                                                  // useful for debuging, so as to not delete the original array
            var sortedKeys = [];
            while (Object.keys(keyValuePairs).length > 0){
                var the_keys = Object.keys(keyValuePairs);
                var min_value = null;
                var min_key = null;
                for(var i=0; i < the_keys.length; i++){
                    var this_key = the_keys[i];
                    var this_value = keyValuePairs[this_key];
                    if(min_value == null || this_value < min_value) {
                        min_value = this_value;
                        min_key = this_key;
                    }
                }
                sortedKeys.push(min_key);
                delete keyValuePairs[min_key];
            }
            return sortedKeys;
        },
        
        
    }
    
    ////////////////////////////
    // Initialize Handler for Sorting Thumbnails
    ////////////////////////////
    function theFunction(){
        thumbnailSortingManager.thumbnailSetHolderElement = document.getElementById('thumbnailSetHolderElement');
        thumbnailSortingManager.thumbnailData = window['cancerBuddyData'];
        thumbnailSortingManager.thumbnailHolderElementBaseID = 'thumbnailElementFor_';
        thumbnailSortingManager.thumbnailRootUserData = { 
            age : parseInt('${personPreferences.person.age}'),
            gender : '${personPreferences.person.gender}',
        }
        thumbnailSortingManager.sortBy("age", "closest");
    }
    window.addEventListener("load", theFunction);
    
    
    
</script>