<script>
    
    
    
    var managePreventiveCareModal_handler = {
        modal : null,
        appointment_id : null,
        concepts : null,
        data_manager: null,
        dropdown_handler : null,
        dropdown_initialized : false,
        defined_relative_orders : [ // note - for best results, only define one relative ordering (as many questions as desired) per concept
            [
                "63ee5099-567e-4b55-936c-c4c8d71d1144",  // systolic
                "02310664-f7bb-477c-a703-0325af4c3f46",  // diastolic
            ],
         ],
        
        
        open_modal_for : function(appointment_id){
            this.modal.modal('show');
            if(appointment_id == "add_new"){
                this.open_part("add_new");
                this.update_visible_data_for_add();
            } else { // return so that the rest of the code is not run.
                this.appointment_id = appointment_id;
                this.open_part('menu');
                this.update_visible_data_for_event();
            }
        },

        //////////
        // DOM interactions
        //////////
        open_part : function(which_part){
            valid_parts = ["menu", "markCompleted", "modifyCompleted", "modify", "remove", "add_new"];
            if((valid_parts.indexOf(which_part) == -1)){
                console.log("Requested part to open is not valid");
                return;
            }
            this.hide_all_parts();
            
            this.modal.find("."+which_part+"-part").show(); 
            this.modal.find(".all-parts").show(); 
            this.modal.find("."+which_part+"-exclude-part").hide(); 
            
            // show all completed_event parts if this event is completed
            var event = this.data_manager.data[this.appointment_id];
            if(event != null && event.status == 1) this.modal.find(".completed_event-part").show()
            
            // handle additional, part specific, requirements
            if(which_part == "menu") this.update_valid_menu_options();
            
            // open all concept specific parts
            this.open_sub_part(); // ensure special requirements for each procedure are accounted for
        },
        open_sub_part : function(){
            var event = this.data_manager.data[this.appointment_id];
            if(event == null) return;
            var concept_id = event.concept_id + "";
            this.modal.find("."+concept_id+"-part").show();
        },
        update_visible_data_for_event : function(){
            var event = this.data_manager.data[this.appointment_id];
            var concept_id = event.concept_id + "";
            
            // update title
            this.modal.find(".modal-title").html(event.followProcedureName + " Appointment");
            
            // update modify input defaults to current data
            this.input.modify.appointment_date.data('datepicker').setValue(event.targetDate);
            
            // update completion inputs to defaults 
            var questions = this.concepts[concept_id].questions;
            var concepts_holder = this.DOM.markCompleted_concept_holder;
            var question_holder_parent = concepts_holder.find("."+concept_id+"-part");
            var request_data = []; 
            for (var i = 0; i < questions.length; i++){
                var this_question = questions[i];
                var this_uuid = this_question.uuid;
                var this_datatype = this_question.datatype;
                var this_holder = question_holder_parent.find("#question_"+this_question.uuid);
                this.set_response_to(this_holder, this_datatype, false);
            }
            
            // update records && completion data if event has been completed 
            if(event.status == 1){
                var questions_answered = event.questionsAnswered;
                var record_concepts_holder = this.DOM.record_concept_holder;
                var modify_concepts_holder = this.DOM.modifyCompleted_concept_holder;
                var record_question_holder_parent = record_concepts_holder.find("."+concept_id+"-part");
                var modify_question_holder_parent = modify_concepts_holder.find("."+concept_id+"-part");
                for(var i = 0; i < questions_answered.length; i++){
                    question_answer = questions_answered[i];
                    var this_uuid = question_answer.uuid;
                    var this_answer = question_answer.answer;
                    var this_datatype = question_answer.datatype;
                    var this_record_holder = record_question_holder_parent.find("#question_answer_"+this_uuid);
                    var this_modify_holder = modify_question_holder_parent.find("#question_"+this_uuid);
                    this_record_holder.find(".preventive_care_modal_record_question_answer").html(this_answer);
                    this.set_response_to(this_modify_holder, this_datatype, this_answer);
                }
            }
            
        },
        update_visible_data_for_add : function(){
            this.modal.find(".modal-title").html("Add New Appointment");
            this.input.add_new.target_date.data('datepicker').setValue(new Date());
            if(this.dropdown_initialized == false){
                this.dropdown_initialized = true;
                this.dropdown_handler.main_display_element = this.input.add_new.new_appointment_type;
                this.dropdown_handler.main_display_element_dropdown_contents = this.input.add_new.new_appointment_type_dropdown_contents;
                this.dropdown_handler.dropdown_contents = this.input.add_new.new_appointment_type_dropdown_contents;
                this.dropdown_handler.initialize_with_data(this.data_manager.possible_events);
            }
            this.dropdown_handler.handle_dropdown_change(0);
        },
        update_valid_menu_options : function(){
            // modify visible menu items due to event.status (e.g., if its completed then instead of markCompleted display modifyCompleted)
            var event = this.data_manager.data[this.appointment_id];
            if(event.status == 1){
               this.modal.find("#modal_menu_part_markCompleted").hide();
               this.modal.find("#modal_menu_part_modify").hide();
               this.modal.find("#modal_menu_part_remove").hide();
            } else {
               this.modal.find("#modal_menu_part_modifyCompleted").hide();   
            }
        },
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
            valid_parts = ["markCompleted", "modifyCompleted", "add_new", "modify", "remove"];
            if((valid_parts.indexOf(which_part) == -1)){
                console.log("Requested part to trigger is not valid ( " + which_part + " )");
                return;
            }
            if(which_part == "markCompleted") {
                this.attempt_mark_completed();
            }
            if(which_part == "modifyCompleted"){
                this.attempt_modify_completed();
            }
            if(which_part == "modify"){
                this.attempt_modify_appointment();
            }
            if(which_part == "remove"){
                this.attempt_remove_appointment();
            }
            if(which_part == "add_new"){
                this.attempt_add_new();   
            }
        },
        
        attempt_mark_completed : function(){
            var event = this.data_manager.data[this.appointment_id];
            var concept_id = event.concept_id;            

            //////////////
            // Get question responses 
            /////////////
            var request_data = this.grab_full_response();
            
            
            //////////////////////
            // Grab all relevant data
            //////////////////////
            var event_id             = this.appointment_id;
            var person_uuid          = jq("#personUuid").val();
            var formated_target_date = event.formatedTargetDate;
            var concept_id           = concept_id;
            var json_data            = JSON.stringify(request_data);
            
            ////////////
            // Submit request
            ////////////
            jq.get("preventiveCareManageModal/markPreventiveCareCompleted.action", {
                eventId : event_id,
                personUuid : person_uuid,
                formatedTargetDate : formated_target_date,
                conceptId : concept_id,
                jsonData : json_data,
            }, function () {
                console.log("Request Responded");
                location.reload();
            }).fail(()=>{
                if(typeof window.submission_error_count == "undefined") window.submission_error_count = 0;
                window.submission_error_count += 1;
                if(window.submission_error_count <= 2){
                    alert("Please make sure you have filled out all input fields before submitting.");
                } else {
                    alert("An error occured while sending your feedback. Please notify an administrator or try again later.");
                }
            });
        },
        
        
        attempt_modify_completed : function(){
            var event = this.data_manager.data[this.appointment_id];
            var concept_id = event.concept_id;

            //////////////
            // Get question responses 
            /////////////
            var request_data = this.grab_full_response();
            
            //////////////////////
            // Grab all relevant data
            //////////////////////
            var event_id             = this.appointment_id;
            var person_uuid          = jq("#personUuid").val();
            var formated_target_date = event.formatedTargetDate;
            var concept_id           = concept_id;
            var json_data            = JSON.stringify(request_data);
            console.log(json_data);
            
            ////////////
            // Submit request
            ////////////
            jq.get("preventiveCareManageModal/modifyCompleted.action", {
                eventId : event_id,
                personUuid : person_uuid,
                formatedTargetDate : formated_target_date,
                conceptId : concept_id,
                jsonData : json_data,
            }, function () {
                console.log("Request Responded");
                location.reload();
            });
        },
        
        attempt_modify_appointment : function(){
            var event = this.data_manager.data[this.appointment_id]
             
            //location.reload();
            var appointment_id         = encodeURIComponent(this.appointment_id);
            var new_target_date        = encodeURIComponent(this.input.modify.appointment_date.val());
            var personUuid             = encodeURIComponent(jq("#personUuid").val());
            var formatedTargetDate     = encodeURIComponent(event.formatedTargetDate);
            var concept_id             = encodeURIComponent(event.concept_id);
            var parameters = 'eventId='+appointment_id + '&newTargetDate='+new_target_date + '&personUuid='+personUuid + '&conceptId=' + concept_id + '&formatedTargetDate=' + formatedTargetDate;
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "preventiveCareManageModal/modifyAppointment.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                window.location.reload();
            };
            xhr.send(null);
            
        },
        attempt_remove_appointment : function(){
            var event = this.data_manager.data[this.appointment_id]
             
            //location.reload();
            var appointment_id         = encodeURIComponent(this.appointment_id);
            var personUuid          = encodeURIComponent(jq("#personUuid").val());
            var formatedTargetDate  = encodeURIComponent(event.formatedTargetDate);
            var concept_id          = encodeURIComponent(event.concept_id);
            var parameters = 'eventId='+appointment_id + '&personUuid='+personUuid + '&conceptId=' + concept_id + '&formatedTargetDate=' + formatedTargetDate;
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "preventiveCareManageModal/removeAppointment.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                window.location.reload();
            };
            xhr.send(null);
        },
        attempt_add_new : function(){
            var event = this.dropdown_handler.current_selection;
            
            // check that user is sure they set the target date correctly
            var raw_formatedTargetDate = this.input.add_new.target_date.val(); 

            if (!raw_formatedTargetDate) {
                alert('Please include the Target Date');
                return;
            }
            if((new Date(raw_formatedTargetDate)).toDateString() === (new Date()).toDateString()){
                response = confirm("Are you sure you want the target date for this new appointment to be today?"); 
                if(!response) return;
            }
            
            // generate parameters
            var concept_id          = encodeURIComponent(event.concept_id);
            var formatedTargetDate  = encodeURIComponent(raw_formatedTargetDate);
            var personUuid          = encodeURIComponent(jq("#personUuid").val());
            var parameters = 'conceptId='+concept_id + '&formatedTargetDate='+formatedTargetDate + '&personUuid='+personUuid;
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "preventiveCareManageModal/addAppointment.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                window.location.reload();
            };
            xhr.send(null);
        },
        
        /////////////////////////////////////
        // Concept Question Value Interfaces
        //////////////////////////////////////
        grab_full_response : function(){
            var event = this.data_manager.data[this.appointment_id];
            var concept_id = event.concept_id;
            
            var questions = this.concepts[concept_id].questions;
            var concepts_holder = this.DOM.markCompleted_concept_holder;
            var question_holder_parent = concepts_holder.find("."+concept_id+"-part");
            var request_data = []; 
            for (var i = 0; i < questions.length; i++){
                var this_question = questions[i];
                var this_uuid = this_question.uuid;
                var this_datatype = this_question.datatype;
                var this_holder = question_holder_parent.find("#question_"+this_question.uuid);
                var this_response = this.grab_response_from(this_holder, this_datatype);
                
                request_data.push({
                    uuid : this_uuid,
                    datatype : this_datatype, 
                    response : this_response, 
                });
            }
            
            return request_data;
        },
        grab_response_from : function(question_holder, question_datatype){
            // Grab input element(s)
            var response_holder = question_holder.find(".preventive_care_input");
            
            // parse input elements
            var response = null;
            if(question_datatype == "NM") response = response_holder[0].value;
            if(question_datatype == "DT") response = response_holder[0].value;
            if(question_datatype == "BIT") {
                if(response_holder[0].checked) response = "true";
                if(response_holder[1].checked) response = "false";
            }
            
            return response;
        },
        set_response_to : function(question_holder, question_datatype, new_value){
            // Grab input element(s)
            var response_holder = question_holder.find(".preventive_care_input");
            
            if(new_value == false){
                // default response
                if(question_datatype == "NM") response_holder[0].value = "";
                if(question_datatype == "DT") response_holder.data('datepicker').setValue(new Date());
                if(question_datatype == "BIT") {
                    response_holder[0].checked = false;
                    response_holder[1].checked = false;
                }
            } else {
                if(question_datatype == "NM") response_holder[0].value = new_value;
                if(question_datatype == "DT") response_holder.data('datepicker').setValue(new_value);
                if(question_datatype == "BIT") {
                    response_holder[0].checked = true;
                    response_holder[1].checked = false;
                    if(question_datatype == "true") response_holder[0].checked = true;
                    if(question_datatype == "false") response_holder[1].checked = true;
                }
            }
        },
        
        /////////////////////////////////////
        // Initialize concept data and generate input templates
        /////////////////////////////////////
        initialize_concepts : function(){
            const baseUrl = window.location.href.split("/patientportaltoolkit")[0];
            //Load Reminder data, insert reminders into calendar and table
            jq.get(baseUrl+'/ws/patientportaltoolkit/getRelevantPreventiveCareConcepts/'+ jq("#personUuid").val(), function (relevantConcepts) {
                this.setDataSource(relevantConcepts);
            }.bind(this));
        },
        setDataSource : function(relevant_concepts){
            // Store concepts by concept id
            this.concepts = [];
            for(var i = 0; i < relevant_concepts.length; i++){
                var this_concept = relevant_concepts[i];
                this.concepts[this_concept.concept_id] = this_concept;
            }
            
            // Build dom input elements for concept questions
            this.build_UI_from_concepts(relevant_concepts);
        },
        
        build_UI_from_concepts : function(concepts){
            // Sort concept questions by predefined orders (this.defined_relative_orders)
            concepts = this.sort_concept_questions(concepts); 
            
            this.build_record_holders_from_concepts(concepts);
            this.build_inputs_from_concepts(concepts);
        },
        
        build_record_holders_from_concepts : function(concepts){
        
            //////////
            // Insert questionAnswers holder for each concept into Records holder
            //////////
            var holder = this.DOM.record_concept_holder;
            for(var i = 0; i < concepts.length; i++){
                var this_concept = concepts[i];
                
                //var wrapper = document.createElement("div");
                var parent = document.createElement("div");
                parent.className = this_concept.concept_id + "-part modal-part"; // Note: Parent holder can be found by .find("."+contept_id+"-part");
                for(var j = 0; j < this_concept.questions.length; j++){
                    var this_question = this_concept.questions[j];
                    
                    var question = document.createElement("form");
                    question.id = "question_answer_" + this_question.uuid; // Note: Each question can be found by .find("#question_answer_"+question.uuid);, recommended parent.find(...)
                    question.className = "form-inline";
                    
                    var label = document.createElement("label");
                    label.className = "uniform_width_manage_preventive_care_modal_question_label reformatText";
                    label.innerHTML = this_question.name;
                    
                    var answer = document.createElement("span");
                    answer.className = "preventive_care_modal_record_question_answer"; // used to locate dynamic 'answer' element
                    
                    question.appendChild(label);
                    question.appendChild(answer);
                    parent.appendChild(question);
                }
                
                // append to content holder
                holder.append(parent);
            }
        },
        
        build_inputs_from_concepts : function(concepts){
            
            //////////
            // Insert questions for each concept into markcompleted holder
            //////////
            var holder = this.DOM.markCompleted_concept_holder;
            for(var i = 0; i < concepts.length; i++){
                var this_concept = concepts[i];
                
                //var wrapper = document.createElement("div");
                var parent = document.createElement("div");
                parent.className = this_concept.concept_id + "-part modal-part"; // Note: Parent holder can be found by .find("."+contept_id+"-part");
                for(var j = 0; j < this_concept.questions.length; j++){
                    var this_question = this_concept.questions[j];
                    
                    var question = document.createElement("form");
                    question.id = "question_" + this_question.uuid; // Note: Each question can be found by .find("#question_"+question.uuid);, recommended parent.find(...)
                    question.className = "form-inline";
                    
                    var label = document.createElement("label");
                    label.className = "uniform_width_manage_preventive_care_modal_question_label reformatText";
                    label.innerHTML = this_question.name;
                    
                    var input_element_class_name = "preventive_care_input form-control openmrs_concept_datatype_" + this_question.datatype; // datatype part used to instantiate datetime elements 
                    if(this_question.datatype == "BIT"){
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
                            boolean_input.name = "preventive_care_radio";
                            boolean_input.value = these_constants[0];
                            boolean_input.className = input_element_class_name; 
                            boolean.appendChild(boolean_input);
                            boolean.innerHTML += these_constants[1];
                            input.appendChild(boolean);
                        }
                    } else {
                        var input = document.createElement("input");
                        input.type = (this_question.datatype == "NM") ? "number" : "text"; 
                        input.className = input_element_class_name; 
                        if(this_question.datatype == "DM") input.className += " datepicker"
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
            for(var i = 0; i < datepicker_elements.length; i++){
                var element = this.modal.find(datepicker_elements[i]);
                element.datepicker({ format: 'mm/dd/yyyy' }).on('changeDate', function(){ this.data('datepicker').hide() }.bind(element));
            }
        },
        
        sort_concept_questions : function(concepts){
            ////////////
            // For each concept, check the question list, and ensure that the order of the questions matches the defined relative orders
            //      implementation - determine if a question can be added to the list based on the relative orders defined and the elements currently in front of it in the list. 
            //                      Keep a queue for those who cant be yet added and recheck the queued elements each time an element is added
            ////////////
            for(var i = 0; i < concepts.length; i++){
                var these_questions = concepts[i].questions;
                var final_questions = [];
                var questions_queue = [];
                for(var j = 0; j < these_questions.length; j++){
                    var this_question = these_questions[j];
                    var this_uuid = this_question.uuid;
                    
                    // determine whether this element can be added to list
                        // if the element is defined in relative orderings, ensure that all elements before it in the orderings exist in the list before adding
                        // if the elements required to be before it dont exist in the list yet, then put this element in a queue to be rechecked after another element is added
                    var can_add_to_list = this.can_this_question_uuid_be_added_to_list(this_uuid, final_questions);
                    if(can_add_to_list){
                        final_questions.push(this_question);
                    } else{
                        questions_queue.push(this_question);
                    }
                    
                    
                    // if something was added to the list, recheck all the queued elements (keep checking if a queued element was added, too)
                    if(can_add_to_list){
                        var one_was_just_added = true;
                        while(one_was_just_added){
                            one_was_just_added = false;
                            var first_addable_queued_question = this.find_first_addable_queued_question(questions_queue, final_questions);
                            if(first_addable_queued_question == false) continue; // if none can be found, exit the loop
                            one_was_just_added = true;
                            final_questions.push(first_addable_queued_question); // add item to questions list
                            questions_queue = questions_queue.filter(function(element) { return element !== first_addable_queued_question}); // remove item from queue
                        }
                    }
                }
                concepts[i].questions = final_questions;
            }
            return concepts;
        },
        can_this_question_uuid_be_added_to_list : function(this_uuid, current_list){
            var defined_relative_orders = this.defined_relative_orders;
            var relevant_question_uuid_list = [].concat.apply([], defined_relative_orders); // flatten list of lists
            var uuid_in_current_list = current_list.map(function(a) {return a.uuid;});
            var can_add_to_list = true;
            if(relevant_question_uuid_list.includes(this_uuid)){
                // for each relative order, ensure that either it is not included in the ordering or that all uuid defined to come before this uuid have been included in final_questions list
                for(var i = 0; i < defined_relative_orders.length; i++){
                    if(!can_add_to_list) break; // if it has been found that it can not be added, dont continue to assess
                    var this_relative_ordering = defined_relative_orders[i];
                    if(!this_relative_ordering.includes(this_uuid)) continue; // uuid is not included in this ordering
                    // for each element before this uuid in the ordering,
                    for(var j = 0; j < this_relative_ordering.indexOf(this_uuid); j++){
                        // ensure it is in the list, if it is not, break - you can not add this element to the list
                        var uuid_to_be_added_before_this_uuid = this_relative_ordering[j]; 
                        if(!uuid_in_current_list.includes(uuid_to_be_added_before_this_uuid)){
                            can_add_to_list = false;
                            break;
                        }  
                    } 
                }
            }
            return can_add_to_list;
        },
        find_first_addable_queued_question : function(queued_list, current_list){
            for(var i = 0; i < queued_list.length; i++){
                var queued_question = queued_list[i];
                var queued_uuid = queued_question.uuid;
                var can_add_this_queued_question_to_list = this.can_this_question_uuid_be_added_to_list(queued_uuid, current_list);
                if(can_add_this_queued_question_to_list){
                    return queued_question;
                }
            }
            return false;
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
        managePreventiveCareModal_handler.dropdown_handler = new Event_New_Appointment_Dropdown_Handler();
        managePreventiveCareModal_handler.buttons = {
            menu : {
               markCompleted : document.getElementById('managePreventiveAppointment_menu_markCompleted'),
               modifyCompleted : document.getElementById('managePreventiveAppointment_menu_modifyCompleted'),
               modify : document.getElementById('managePreventiveAppointment_menu_modify'),
               remove : document.getElementById('managePreventiveAppointment_menu_remove'),
            },
            action : {
                markCompleted : document.getElementById('managePreventive_button_markCompleted'),
                modifyCompleted : document.getElementById('managePreventive_button_modifyCompleted'),
                modify : document.getElementById('managePreventive_button_modify'),
                remove : document.getElementById('managePreventive_button_remove'),
                add_new : document.getElementById('managePreventive_button_add_new'),
            },
            back : the_modal.find("#managePreventive_back_button"),
            cancel : the_modal.find(".modal_cancel_button"),
        }
        managePreventiveCareModal_handler.input = {
            add_new : {
                target_date : the_modal.find('#preventiveCare_new_appointment_target_date'),
                new_appointment_type : the_modal.find('#preventiveCare_new_appointment_type'),
                new_appointment_type_dropdown_contents : the_modal.find('#preventiveCare_new_appointment_type_dropdown_contents'),
            },
            modify : {
                appointment_date : the_modal.find('#preventive_appointment_date'),
            },
        }
 
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
            managePreventiveCareModal_handler.open_part("menu");
        });
        
        // initialize cancel buttons
        managePreventiveCareModal_handler.buttons.cancel.on( "click", function(){
            managePreventiveCareModal_handler.close_modal();
        });
        
        
        // Load concepts from database w/ async request, build the input forms dynamically.
        managePreventiveCareModal_handler.initialize_concepts(); 
        managePreventiveCareModal_handler.DOM = {
            markCompleted_concept_holder : the_modal.find("#completionData_concept_holder"),
            modifyCompleted_concept_holder : the_modal.find("#completionData_concept_holder"),
            record_concept_holder : the_modal.find("#record_concept_holder"),
        }
        // Initialize datepicker inputs
        var datepicker_elements = [];
        datepicker_elements.push(managePreventiveCareModal_handler.input.add_new.target_date);
        datepicker_elements.push(managePreventiveCareModal_handler.input.modify.appointment_date);
        for(var i = 0; i < datepicker_elements.length; i++){
            var element = datepicker_elements[i];
            element.datepicker({ format: 'mm/dd/yyyy' }).on('changeDate', function(){ this.data('datepicker').hide() }.bind(element));
        }
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
            <div class="modal-body" style = 'overflow-y:visible;'>
                
                
                <!-- menu part-->
                <div class = 'menu-part modal-part' style = 'padding: 0px 15px; '>
                    <div id = "record_concept_holder" class = 'completed_event-part modal-part'>
                    </div>
                    
                    <div style = 'font-size:25px; margin-left:-10px;'>
                        What would you like to do?
                    </div>
                    <!-- Menu items are displayed using a Java based template -->
                    <% 
                        // Define Menu Items
                        List<Map<String, String>> data = new ArrayList<>();
                        Map<String, String> map = new HashMap<String, String>();
                        
                        map.put("desc", "Mark this Appointment as Completed");
                        map.put("button_text", "Mark Completed");
                        map.put("id_mod", "markCompleted");
                        data.add(map.clone());
                       
                        map.put("desc", "Modify Completed Record");
                        map.put("button_text", "Modify Completed");
                        map.put("id_mod", "modifyCompleted");
                        data.add(map.clone());
                       
                        map.put("desc", "Modify this Appointment");
                        map.put("button_text", "Modify");
                        map.put("id_mod", "modify");
                        data.add(map.clone());
                        
                        map.put("desc", "Remove this Appointment");
                        map.put("button_text", "Remove");
                        map.put("id_mod", "remove");
                        data.add(map.clone());
                    %>
                    <% 
                       // Display Templated Menu Item
                       data.each { data_element -> 
                    %>
                        <div class="row menu-part" id = "modal_menu_part_${data_element.id_mod}" style = ' display:flex; margin-bottom:5px;'>
                            <div style = "display:flex;  flex-grow:1;">
                                
                                <div style = ' margin:auto; margin-left:15px; '>
                                    ${data_element.desc}
                                </div>
                            </div>
                            <div style = 'display:flex'>
                                <div style = 'margin:auto; margin-right:5px;'>
                                    <button type="button" class="btn btn-primary  pull-right" id = 'managePreventiveAppointment_menu_${data_element.id_mod}' style = 'width:175px;'> ${data_element.button_text}</button>
                                </div>
                            </div>
                        </div>
                    <% 
                       } // end data.each
                    %>
                </div>
                
                <!-- markCompleted part-->
                <div id = "completionData_concept_holder" class = 'markCompleted-part modifyCompleted-part modal-part'>
                   
                </div>
                
                <div class="modal-part modify-part">
                    <input id="markCompletedIdHolder" type="hidden" value="">
                    
                    <form class="form-inline" role="form"> 
                        <label class = 'manageAppointmentModalLabel'>Appointment Date</label>
                        <input class="form-control datetype" id = 'preventive_appointment_date' >
                    </form>
                </div>
                <div class="modal-part remove-part">
                    Are you sure you wish to remove this appointment? This can not be undone. 
                </div>
                        
                <!-- add appointment part-->
                <div class="modal-part add_new-part">
                    <style>
                    .btn-like-input{
                        background-color:white;
                        border:2px solid #DCE4EC;
                        border-radius:3px;
                    }

                    .btn-like-input:focus{
                        border-color:black;
                    }
                    </style>
                    <div style = 'display:flex;'>
                        <div style = 'margin: auto 0px;'>
                            <label class = 'manageAppointmentModalLabel'>Appointment Type</label>
                        </div>
                        <div class="dropdown " style = 'position:relative; z-index: 5000;'>
                            <button class="btn-like-input " type="button" id="preventiveCare_new_appointment_type_dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style = ' padding:0px 13px; height:43px; margin-left:3px; min-width:230px; display:flex;'>
                                <div id = 'preventiveCare_new_appointment_type' style = 'margin:auto; margin-left:0px;'> PlaceHolder </div> 
                                <div class="caret" style = 'margin:auto; margin-right:0px;'></div>
                            </button>
                            <ul class="dropdown-menu " id = 'preventiveCare_new_appointment_type_dropdown_contents' aria-labelledby="preventiveCare_new_appointment_type_dropdown" style = 'font-size:14px; '>
                                <!--
                                <li><a href="#" onclick = 'new_appointment_dropdown_handler.handle_dropdown_change(0)'>Age - Closest to You</a></li>
                                -->
                            </ul>
                        </div>
                    </div>
                        
                    <div style = 'height:15px;'></div>
                    <form class="form-inline" role="form">
                        <label class = 'manageAppointmentModalLabel'>Target Date</label>
                        <input class="form-control datetype" id="preventiveCare_new_appointment_target_date"  style = 'min-width:230px;' type="text" value=""/>
                    </form>
                </div>
                
            </div>
            
            <div class="modal-footer" style = 'z-index:10;'>
                <div class="button-div pull-left  ">
                    <button type="button" class="btn btn-default all-parts menu-exclude-part add_new-exclude-part" id = 'managePreventive_back_button'>Back</button>
                </div>
                <div class="button-div pull-right ">
                    <button type="button" onclick="logEvent('clicked_cancel_preventive_appointment','')" class="btn btn-default modal_cancel_button modal-part all-parts">Cancel</button>
                    <button type="button" onclick="logEvent('clicked_mark_completed_preventive_appointment','')" class="btn btn-primary modal-part markCompleted-part" id="managePreventive_button_markCompleted"> Mark Completed </button>
                    <button type="button" onclick="logEvent('clicked_modify_completed_preventive_appointment','')" class="btn btn-primary modal-part modifyCompleted-part" id="managePreventive_button_modifyCompleted"> Modify Completed Record </button>
                    <button type="button" onclick="logEvent('clicked_save_changes_preventive_appointment','')" class="btn btn-primary modal-part modify-part" id="managePreventive_button_modify"> Save Changes </button>
                    <button type="button" onclick="logEvent('clicked_remove_this_event_preventive_appointment','')" class="btn btn-primary modal-part remove-part" id="managePreventive_button_remove"> Remove this Event </button>
                    <button type="button" onclick="logEvent('clicked_add_new_appointment_preventive_appointment','')" class="btn btn-primary modal-part add_new-part" id="managePreventive_button_add_new"> Add New Appointment </button>
                </div>
            </div>
        </div>
    </div>
</div>