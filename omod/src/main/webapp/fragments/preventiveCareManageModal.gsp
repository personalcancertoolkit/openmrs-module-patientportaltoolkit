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
            this.modal.find("."+concept_id+"-part").show();
            console.log(concept_id);
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
            
            var event = this.data_manager.data[this.appointment_id];
            
            ////////////
            // Below is the old mark completed - unfinished - submission method.
            ////////////
            jq.get("preventiveCareManageModal/saveInfluenzaForm.action", {
                influenzaDate: jq("#influenzaDate").val()
            }, function () {
                //location.reload();
            });
        },
        
        
        /////////////////////////////////////
        // Initialize concept data and generate input templates
        /////////////////////////////////////
        initialize_concepts : function(){
            //console.log(jq("#personUuid").val());
            var OpenMRSInstance=window.location.href;
            //Load Reminder data, insert reminders into calendar and table
            //console.log(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val());
            jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getRelevantPreventiveCareConcepts/'+ jq("#personUuid").val(), function (relevantConcepts) {
                console.log(relevantConcepts);
                // Set datasource for reminder table
                this.setDataSource(relevantConcepts);
            }.bind(this));
        },
        setDataSource : function(relevant_concepts){
            this.concepts = relevant_concepts;
            this.build_input_from_concepts();
        },
        
        build_input_from_concepts : function(){
            var holder = this.DOM.markCompleted_concept_holder;
            var concepts = this.concepts;
            
            //////////
            // Insert each concept into holder
            //////////
            for(var i = 0; i < concepts.length; i++){
                var this_concept = concepts[i];
                
                //var wrapper = document.createElement("div");
                var parent = document.createElement("div");
                parent.className = this_concept.concept_id + "-part modal-part";
                for(var j = 0; j < this_concept.questions.length; j++){
                    var this_question = this_concept.questions[j];
                    
                    var question = document.createElement("form");
                    question.id = "question_" + this_question.uuid;
                    question.className = "form-inline";
                    
                    var label = document.createElement("label");
                    label.className = "uniform_width_manage_preventive_care_modal_question_label reformatText";
                    label.innerHTML = this_question.name;
                    
                    if(this_question.dataType == "BIT"){
                        // Boolean data
                        var input = document.createElement("div");
                        input.className = "radio";
                        
                        boolean_constants = [["true", "Yes"], ["false", "No"]]; // creates two radio buttons, "name" = [0], title = [1]
                        for(var k = 0; k < boolean_constants.length; k++){
                            var these_constants = boolean_constants[k];
                            var boolean = document.createElement("label");
                            boolean.className = "radio-inline";
                            var boolean_input = document.createElement("input");
                            boolean_input.type = "radio";
                            boolean_input.name = these_constants[0];
                            boolean_input.class = "openmrs_concept_datatype_"+this_question.dataType + " form-control";
                            boolean.appendChild(boolean_input);
                            boolean.innerHTML += these_constants[1];
                            input.appendChild(boolean);
                        }
                        
                    } else {
                        var input = document.createElement("input");
                        input.type = (this_question.dataType == "NM") ? "number" : "text"; 
                        input.className = "openmrs_concept_datatype_"+this_question.dataType + " form-control"; //used to target and instantiate all datetime inputs
                        if(this_question.dataType == "DM") input.className += " datepicker"
                    }
                    question.appendChild(label);
                    question.appendChild(input);
                    parent.appendChild(question);
                }
                
                // append to content holder
                holder.append(parent);
                
            }
            
            //////////
            // initialize all date time inputs
            //////////
            var datepicker_elements = this.modal.find(".openmrs_concept_datatype_DT");
            console.log(datepicker_elements);
            for(var i = 0; i < datepicker_elements.length; i++){
                var element = this.modal.find(datepicker_elements[i]);
                element.datepicker({ format: 'mm/dd/yyyy' }).on('changeDate', function(){ this.data('datepicker').hide() }.bind(element));
            }
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
        
        
        // Load concepts from database w/ async request, build the input forms dynamically.
        managePreventiveCareModal_handler.initialize_concepts(); 
        managePreventiveCareModal_handler.DOM = {
            markCompleted_concept_holder : the_modal.find("#markCompleted_concept_holder"),
        }
        /*
        // Initialize datepicker inputs
        var datepicker_ids = ["#influenzaDate", "#pneumococcalDate", "#cholesterolDate", "#bpDate", "#hivDate", "#mammographyDate", "#cervicalDate" ];
        //var datepicker_elements = [];
        //datepicker_elements.push(managePreventiveCareModal_handler.input.markCompleted.completed_date);
        for(var i = 0; i < datepicker_ids.length; i++){
            var element = jq(datepicker_ids[i]);
            element.datepicker({ format: 'mm/dd/yyyy' }).on('changeDate', function(){ this.data('datepicker').hide() }.bind(element));
        }
        */
    });
  
    
    
    
</script>
<style>
.uniform_width_manage_preventive_care_modal_question_label {
    width:300px;   
}
</style>
<div class="modal fade modal-wide"  id="managePreventiveCare-modal" role="dialog" aria-labelledby="preventiveCareLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close modal_cancel_button" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"> Modal Title </h4>
            </div>
            <div class="modal-body">
                <div id = "markCompleted_concept_holder" class = 'markCompleted-part modal-part'>
                   
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