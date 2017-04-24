<script>
    
    
    
    var managePreventiveCareModal_handler = {
        modal : null,
        appointment_id : null,
        data_manager: null,
        dropdown_handler : null,
        dropdown_initialized : false,

        open_modal_for : function(appointment_id){
            this.modal.modal('show');
            this.appointment_id = appointment_id;
            this.open_part('markCompleted'); // hardcoded as markCompleted as no other options are enabled
            this.open_sub_part(); // ensure special requirements for each procedure are accounted for
            this.update_visible_data_for_event();
        },
        
        //////////
        // DOM interactions
        //////////
        open_part : function(which_part){
            valid_parts = ["menu", "markCompleted"];
            if((valid_parts.indexOf(which_part) == -1)){
                console.log("Requested part to open is not valid");
                return;
            }
            this.hide_all_parts();
            //console.log(':  - ' + which_part);
            this.modal.find("."+which_part+"-part").show(); 
            this.modal.find(".all-parts").show(); 
            this.modal.find("."+which_part+"-exclude-part").hide(); 
            
            //if(which_part == "menu") this.update_valid_menu_options();
        },
        open_sub_part : function(){
            var event = this.data_manager.data[this.appointment_id];
            var concept_id = event.concept_id + "";
            console.log(concept_id);
            switch (concept_id){
                case "162938":
                    jq("#influenza-modal").show();
                    break;
                case "162939":
                    jq("#pneumococcal-modal").show();
                    break;
                case "162940":
                    jq("#cholesterol-modal").show();
                    break;
                case "162941":
                    jq("#bp-modal").show();
                    break;
                case "162942":
                    jq("#hiv-modal").show();
                    break;
                case "162943":
                    jq("#mammography-modal").show();
                    break;
                case "162944":
                    jq("#cervical-modal").show();
                    break;
            }  
        },
        update_visible_data_for_event : function(){
            var event = this.data_manager.data[this.appointment_id];
            
            // update title
            this.modal.find(".modal-title").html(event.followProcedureName + " Appointment");
            
            // update modify input defaults to current data
            //this.input.modify.appointment_date.data('datepicker').setValue(event.targetDate);
            
        },
        //update_valid_menu_options : function(){},
        hide_all_parts : function(){
            this.modal.find(".modal-part").hide();
        },
        close_modal : function(){
            this.modal.modal('toggle');
        },
        
        ///////////////
        // Handle Actions
        ///////////////
        trigger_action : function(which_part){
            valid_parts = ["markCompleted"];
            if((valid_parts.indexOf(which_part) == -1)){
                console.log("Requested part to trigger is not valid ( " + which_part + " )");
                return;
            }
            if(which_part == "markCompleted") {
                this.attempt_mark_completed();
            }
        },
        
        attempt_mark_completed : function(){
            
            /*
            var event = this.data_manager.data[this.appointment_id]
             
            //location.reload();
            var reminder_id         = encodeURIComponent(this.appointment_id);
            var concept_id          = encodeURIComponent(event.concept_id);
            var completed_date      = encodeURIComponent(this.input.markCompleted.completed_date.val());
            var doctor_name         = encodeURIComponent(this.input.markCompleted.doctor_name.val());
            var comments            = encodeURIComponent(this.input.markCompleted.comments.val());
            var personUuid          = encodeURIComponent(jq("#personUuid").val());
            var formatedTargetDate  = encodeURIComponent(event.formatedTargetDate);
            var parameters = 'reminderId='+reminder_id + '&conceptId='+concept_id + '&markCompletedDate='+completed_date + '&doctorName='+doctor_name + '&comments='+comments + '&personUuid='+personUuid + '&formatedTargetDate='+formatedTargetDate;
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "appointmentsManageModal/markCompleted.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                //console.log(this.responseText);
                console.log("Success!");
                window.location.reload();
            };
            xhr.send(null);
            */
            
            
            ////////////
            // Below is the old mark completed - unfinished - submission method.
            ////////////
            jq.get("preventiveCareManageModal/saveInfluenzaForm.action", {
                influenzaDate: jq("#influenzaDate").val()
            }, function () {
                //location.reload();
            });
            
        },
    }
    
    
    
    /////////////////////
    // Initialize Manage Event Modal Settings
    //////////////////////
    window.addEventListener("load", function(){
        var the_modal = jq('#managePreventiveCare-modal');
        
        // Define modal_handler values
        managePreventiveCareModal_handler.modal = the_modal;
        managePreventiveCareModal_handler.data_manager = preventive_data_manager;
        managePreventiveCareModal_handler.buttons = {
            menu : {
               //markCompleted : document.getElementById('managePreventive_menu_markCompleted'),
            },
            action : {
                markCompleted : document.getElementById('managePreventive_button_markCompleted'),
            },
            back : the_modal.find("#back_button"),
            cancel : the_modal.find(".modal_cancel_button"),
        }
        /*
        managePreventiveCareModal_handler.input = {
            markCompleted : {
                completed_date : the_modal.find('#completed_date'),  
                doctor_name : the_modal.find('#doctor_name'),
                comments : the_modal.find('#comments'),
            },
        }
        */
 
        // Initialize menu buttons
        var button_keys = Object.keys(managePreventiveCareModal_handler.buttons.menu);
        for (var i = 0; i<button_keys.length; i++){
            var this_key = button_keys[i]; 
            (function(key_by_value){ // If not wrapped in annon function all buttons.menu items open same `part`
                managePreventiveCareModal_handler.buttons.menu[key_by_value].onclick = function(){ managePreventiveCareModal_handler.open_part(key_by_value) };
            })(this_key);
        }
        
        // Initialize action buttons
        var button_keys = Object.keys(managePreventiveCareModal_handler.buttons.action);
        for (var i = 0; i<button_keys.length; i++){
            var this_key = button_keys[i]; 
            (function(key_by_value){ // If not wrapped in annon function all buttons.menu items open same `part`
                managePreventiveCareModal_handler.buttons.action[key_by_value].onclick = function(){
                    managePreventiveCareModal_handler.trigger_action(key_by_value)
                };
            })(this_key);
        }
        
        // Initialize back button
        managePreventiveCareModal_handler.buttons.back.on( "click", function(){
            managePreventiveCareModal_handler.open_menu_again();
        });
        
        // initialize cancel buttons
        managePreventiveCareModal_handler.buttons.cancel.on( "click", function(){
            managePreventiveCareModal_handler.close_modal();
        });
        
        // Initialize datepicker inputs
        var datepicker_ids = ["#influenzaDate", "#pneumococcalDate", "#cholesterolDate", "#bpDate", "#hivDate", "#mammographyDate", "#cervicalDate" ];
        //var datepicker_elements = [];
        //datepicker_elements.push(managePreventiveCareModal_handler.input.markCompleted.completed_date);
        for(var i = 0; i < datepicker_ids.length; i++){
            var element = jq(datepicker_ids[i]);
            element.datepicker({ format: 'mm/dd/yyyy' }).on('changeDate', function(){ this.data('datepicker').hide() }.bind(element));
        }
    });
  
    
    
    
</script>
<div class="modal fade modal-wide"  id="managePreventiveCare-modal" role="dialog" aria-labelledby="preventiveCareLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close modal_cancel_button" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"> Modal Title </h4>
            </div>
            <div class="modal-body">
                <div class = 'markCompleted-part modal-part'>
                    <% if (influenzaConcepts) { %>
                        <div id="influenza-modal" class="modal-part">
                            <label>Mark Influenza Vaccine Completed</label>
                            <br/>
                            <br/>
                            <% influenzaConcepts.concepts.each { question -> %>
                                <% /* influenza date*/ %>
                                <% if (question.uuid=="f1cba252-751f-470b-871b-2399565af396") { %>
                                    <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                        <input class="form-control" id="influenzaDate" type="text"/>
                                    </form>
                                <% } %>
                            <% } %>
                        </div>
                    <% } %>
                    <% if (pneumococcalConcepts) { %>
                        <div id="pneumococcal-modal" class="modal-part">
                            <label>Mark Pneumococcal Vaccine Completed</label>
                            <br/>
                            <br/>
                            <% pneumococcalConcepts.concepts.each { question -> %>
                                <% /* pneumococcal date*/ %>
                                    <% if (question.uuid=="c93df44f-d5b7-49a6-8539-e8265c03dbb3") { %>
                                    <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                        <input class="form-control" id="pneumococcalDate" type="text"/>
                                    </form>
                                <% } %>
                            <% } %>
                        </div>
                    <% } %>
                    <% if (cholesterolConcepts) { %>
                        <div id="cholesterol-modal" class="modal-part">
                            <label>Mark Cholesterol Screening Completed</label>
                            <br/>
                            <br/>
                            <% cholesterolConcepts.concepts.each { question -> %>
                                <% /* cholesterol LDL*/ %>
                                    <% if (question.uuid=="b0a44f7a-4188-44b3-b86f-955a32d8f4cd") { %>
                                        <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                                            <input class="form-control" id="cholesterolLDLNumber" type="number"/>
                                        </form>
                                    <% } %>
                                    <% if (question.uuid=="4788cb2c-6324-412f-b617-31ef341e7455") { %>
                                        <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                                            <input class="form-control" id="cholesterolTotalNumber" type="number"/>
                                        </form>
                                    <% } %>
                                    <% if (question.uuid=="01f5d7c7-f0c5-4329-8b2d-2053155a962f") { %>
                                        <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                            <input class="form-control" id="cholesterolDate" type="text"/>
                                        </form>
                                    <% } %>
                            <% } %>
                        </div>
                    <% } %>
                    <% if (bpConcepts) { %>
                        <div id="bp-modal" class="modal-part">
                            <label>Mark Blood Pressure Screening Completed</label>
                            <br/>
                            <br/>
                            <% bpConcepts.concepts.each { question -> %>
                                <% /* BP Top number*/ %>
                                <% if (question.uuid=="63ee5099-567e-4b55-936c-c4c8d71d1144") { %>
                                    <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                                        <input class="form-control" id="bpTopNumber" type="number"/>
                                    </form>
                                <% } %>
                                <% /* BP bottom number*/ %>
                                <% if (question.uuid=="02310664-f7bb-477c-a703-0325af4c3f46") { %>
                                    <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                                        <input class="form-control" id="bpBottomNumber" type="number"/>
                                    </form>
                                <% } %>
                                <% if (question.uuid=="bec04eab-2be5-4f9e-a017-873e3a0b32ab") { %>
                                    <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                        <input class="form-control" id="bpDate" type="text"/>
                                    </form>
                                <% } %>
                                <br/>
                            <% } %>
                        </div>
                    <% } %>
                    <% if (hivConcepts) { %>
                    <div id="hiv-modal" class="modal-part">
                        <label>Mark HIV Screening Completed</label>
                        <br/>
                        <br/>
                        <% hivConcepts.concepts.each { question -> %>
                            <% if (question.uuid=="785fd684-c6ca-48d7-9f71-07ae9b5e93d2") { %>
                                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                <br>
                                <div class="radio">
                                    <label><input type="radio" name="hivradio" value="true"> <span class="reformatText">Positive</span></label>
                                    <br/>
                                    <label><input type="radio"  name="hivradio" value="false"> <span class="reformatText">Negative</span></label>
                                </div>
                                </form>
                            <% } %>
                            <% if (question.uuid=="695ccb4a-a01f-4039-9e00-8f2679b63065") { %>
                                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                    <input class="form-control" id="hivDate" type="text"/>
                                </form>
                            <% } %>
                        <% } %>
                        <br/>
                    </div>
                    <% } %>
                    <% if (mammographyConcepts) { %>
                        <div id="mammography-modal" class="modal-part">
                        <label>Mark Mammography Screening Completed</label>
                        <br/>
                        <br/>
                        <% mammographyConcepts.concepts.each { question -> %>
                            <% if (question.uuid=="39ca0f60-ffe3-49cc-9dcf-7cce8f69c0f5") { %>
                                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                    <br>
                                    <div class="radio">
                                        <label><input type="radio" name="mammographyradio" value="true"> <span class="reformatText">Positive</span></label>
                                        <br/>
                                        <label><input type="radio"  name="mammographyradio" value="false"> <span class="reformatText">Negative</span></label>
                                    </div>
                                </form>
                            <% } %>
                            <% if (question.uuid=="d32ef213-d270-4682-bf3a-b81d40b1d661") { %>
                                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                    <input class="form-control" id="mammographyDate" type="text"/>
                                </form>
                            <% } %>
                            <% if (question.uuid=="c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                    <input class="form-control" id="mammographyDoctorName" type="text"/>
                                </form>
                            <% } %>
                            <br/>
                        <% } %>
                        </div>
                    <% } %>
                    <% if (cervicalConcepts) { %>
                        <div id="cervical-modal" class="modal-part">
                            <label>Mark Cervical Cancer Screening Completed</label>
                            <br/>
                            <br/>
                            <% cervicalConcepts.concepts.each { question -> %>
                                <% if (question.uuid=="838800a3-9991-4fd8-9df1-d6c4f9c2ffae") { %>
                                    <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                        <br>
                                        <div class="radio">
                                            <label><input type="radio" name="cervicalradio" value="true"> <span class="reformatText">Positive</span></label>
                                            <br/>
                                            <label><input type="radio"  name="cervicalradio" value="false"> <span class="reformatText">Negative</span></label>
                                        </div>
                                    </form>
                                <% } %>
                                <% if (question.uuid=="baf0de5b-17e7-47c5-a8f5-87d3df4966b4") { %>
                                    <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                        <input class="form-control" id="cervicalDate" type="text"/>
                                    </form>
                                <% } %>
                                <% if (question.uuid=="c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                                    <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                                        <input class="form-control" id="cervicalDoctorName" type="text"/>
                                    </form>
                                    <br/>
                                <% } %>
                            <% } %>
                        </div>
                    <% } %>
                </div>
            </div>
            
            <div class="modal-footer" style = 'z-index:10;'>
                <div class="button-div pull-left  ">
                    <!-- commented out because there is no menu yet 
                    <button type="button" class="btn btn-default all-parts menu-exclude-part add_new-exclude-part" id = 'back_button'>Back</button>
                    -->
                </div>
                <div class="button-div pull-right ">
                    <button type="button" class="btn btn-default modal_cancel_button modal-part all-parts">Cancel</button>
                    <button type="button" class="btn btn-primary modal-part markCompleted-part" id="managePreventive_button_markCompleted"> Mark Completed </button>
                </div>
            </div>
        </div>
    </div>
</div>