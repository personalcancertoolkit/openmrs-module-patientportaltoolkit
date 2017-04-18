<script>

    /////////////////////
    // Define Manage Event Modal Handler Object
    /////////////////////
    var manageAppointmentModal_handler = {
        modal : null,
        appointment_id : null,
        data_manager: null,
        dropdown_handler : null,
        dropdown_initialized : false,

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
            //console.log(':  - ' + which_part);
            this.modal.find("."+which_part+"-part").show(); 
            this.modal.find(".all-parts").show(); 
            this.modal.find("."+which_part+"-exclude-part").hide(); 
            
            if(which_part == "menu") this.update_valid_menu_options();
        },
        update_visible_data_for_event : function(){
            var event = this.data_manager.data[this.appointment_id];
            
            // update title
            this.modal.find(".modal-title").html(event.followProcedureName + " Appointment");
            
            // update modify input defaults to current data
            this.input.modify.appointment_date.data('datepicker').setValue(event.targetDate);
            if(event.status == 1){
                this.input.markCompleted.completed_date.data('datepicker').setValue(event.completedDate);
                this.input.markCompleted.doctor_name.val(event.doctorName);
                this.input.markCompleted.comments.html(event.comments);
            } else {
                this.input.markCompleted.completed_date.data('datepicker').setValue(new Date());
                this.input.markCompleted.doctor_name.val("");
                this.input.markCompleted.comments.html("");
            }
            
        },
        update_visible_data_for_add : function(){
            this.modal.find(".modal-title").html("Add New Appointment");
            this.input.add_new.target_date.data('datepicker').setValue(new Date());
            if(this.dropdown_initialized == false){
                this.dropdown_handler.main_display_element = this.input.add_new.new_appointment_type;
                this.dropdown_handler.main_display_element_dropdown_contents = this.input.add_new.new_appointment_type_dropdown_contents;
                this.dropdown_handler.dropdown_contents = this.input.add_new.new_appointment_type_dropdown_contents;
                this.dropdown_handler.initialize_with_data(this.data_manager.valid_reminders);
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
            valid_parts = ["markCompleted", "modifyCompleted", "modify", "remove", "add_new"];
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
            
            
            /*
            jq.get("appointments/markCompleted.action", {
                reminderId: reminder_id, 
                markCompletedDate: completed_date, 
                doctorName:  doctor_name,
                comments:  comments,
            }, function(text){ console.log('here i am!'); console.log(text);});
            */
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "appointmentsManageModal/markCompleted.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                //console.log(this.responseText);
                console.log("Success!");
                window.location.reload();
            };
            xhr.send(null);
        },
        attempt_modify_completed : function(){
            var event = this.data_manager.data[this.appointment_id]
             
            //location.reload();
            var reminder_id         = encodeURIComponent(this.appointment_id);
            var completed_date      = encodeURIComponent(this.input.markCompleted.completed_date.val());
            var doctor_name         = encodeURIComponent(this.input.markCompleted.doctor_name.val());
            var comments            = encodeURIComponent(this.input.markCompleted.comments.val());
            var personUuid          = encodeURIComponent(jq("#personUuid").val());
            var parameters = 'reminderId='+reminder_id + '&markCompletedDate='+completed_date + '&doctorName='+doctor_name + '&comments='+comments + '&personUuid='+personUuid;
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "appointmentsManageModal/modifyCompleted.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                //console.log(this.responseText);
                console.log("Success!");
                window.location.reload();
            };
            xhr.send(null);
        },
        attempt_modify_appointment : function(){
            var event = this.data_manager.data[this.appointment_id]
             
            //location.reload();
            var reminder_id         = encodeURIComponent(this.appointment_id);
            var new_target_date      = encodeURIComponent(this.input.modify.appointment_date.val());
            var personUuid          = encodeURIComponent(jq("#personUuid").val());
            var formatedTargetDate  = encodeURIComponent(event.formatedTargetDate);
            var concept_id          = encodeURIComponent(event.concept_id);
            var parameters = 'reminderId='+reminder_id + '&newTargetDate='+new_target_date + '&personUuid='+personUuid + '&conceptId=' + concept_id + '&formatedTargetDate=' + formatedTargetDate;
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "appointmentsManageModal/modifyAppointment.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                //console.log(this.responseText);
                console.log("Success!");
                window.location.reload();
            };
            xhr.send(null);
            
        },
        attempt_remove_appointment : function(){
            var event = this.data_manager.data[this.appointment_id]
             
            //location.reload();
            var reminder_id         = encodeURIComponent(this.appointment_id);
            var personUuid          = encodeURIComponent(jq("#personUuid").val());
            var formatedTargetDate  = encodeURIComponent(event.formatedTargetDate);
            var concept_id          = encodeURIComponent(event.concept_id);
            var parameters = 'reminderId='+reminder_id + '&personUuid='+personUuid + '&conceptId=' + concept_id + '&formatedTargetDate=' + formatedTargetDate;
            
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "appointmentsManageModal/removeAppointment.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                //console.log(this.responseText);
                console.log("Success!");
                window.location.reload();
            };
            xhr.send(null);
        },
        attempt_add_new : function(){
            var event = this.dropdown_handler.current_selection;
            
            // check that user is sure they set the target date correctly
            var raw_formatedTargetDate = this.input.add_new.target_date.val(); 
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
            xhr.open("GET", "appointmentsManageModal/addAppointment.action?" + parameters, true);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.onload = function(){
                //console.log(this.responseText);
                console.log("Success!");
                window.location.reload();
            };
            xhr.send(null);
        },
    }
    
    

    /////////////////////
    // Initialize Manage Event Modal Settings
    //////////////////////
    window.addEventListener("load", function(){
        var the_modal = jq('#manageAppointment-modal');
        
        // Define modal_handler values
        manageAppointmentModal_handler.modal = the_modal;
        manageAppointmentModal_handler.data_manager = appointment_data_manager;
        manageAppointmentModal_handler.dropdown_handler = new_appointment_dropdown_handler;
        manageAppointmentModal_handler.buttons = {
            menu : {
                markCompleted : document.getElementById('manageAppointment_menu_markCompleted'),
                modifyCompleted : document.getElementById('manageAppointment_menu_modifyCompleted'),
                modify : document.getElementById('manageAppointment_menu_modify'),
                remove : document.getElementById('manageAppointment_menu_remove'),
            },
            action : {
                markCompleted : document.getElementById('manageAppointment_button_markCompleted'),
                modifyCompleted : document.getElementById('manageAppointment_button_modifyCompleted'),
                modify : document.getElementById('manageAppointment_button_modify'),
                remove : document.getElementById('manageAppointment_button_remove'),
                add_new : document.getElementById('manageAppointment_button_add_new'),
            },
            back : the_modal.find("#back_button"),
            cancel : the_modal.find(".modal_cancel_button"),
        }
        manageAppointmentModal_handler.input = {
            add_new : {
                target_date : the_modal.find('#new_appointment_target_date'),
                new_appointment_type : the_modal.find('#new_appointment_type'),
                new_appointment_type_dropdown_contents : the_modal.find('#new_appointment_type_dropdown_contents'),
            },
            modify : {
                appointment_date : the_modal.find('#appointment_date'),
            },
            markCompleted : {
                completed_date : the_modal.find('#completed_date'),  
                doctor_name : the_modal.find('#doctor_name'),
                comments : the_modal.find('#comments'),
            },
        }
 
        // Initialize menu buttons
        var button_keys = Object.keys(manageAppointmentModal_handler.buttons.menu);
        for (var i = 0; i<button_keys.length; i++){
            var this_key = button_keys[i]; 
            (function(key_by_value){ // If not wrapped in annon function all buttons.menu items open same `part`
                manageAppointmentModal_handler.buttons.menu[key_by_value].onclick = function(){ manageAppointmentModal_handler.open_part(key_by_value) };
            })(this_key);
        }
        
        // Initialize action buttons
        var button_keys = Object.keys(manageAppointmentModal_handler.buttons.action);
        for (var i = 0; i<button_keys.length; i++){
            var this_key = button_keys[i]; 
            (function(key_by_value){ // If not wrapped in annon function all buttons.menu items open same `part`
                manageAppointmentModal_handler.buttons.action[key_by_value].onclick = function(){
                    manageAppointmentModal_handler.trigger_action(key_by_value)
                };
            })(this_key);
        }
        
        // Initialize back button
        manageAppointmentModal_handler.buttons.back.on( "click", function(){
            manageAppointmentModal_handler.open_menu_again();
        });
        
        // initialize cancel buttons
        manageAppointmentModal_handler.buttons.cancel.on( "click", function(){
            manageAppointmentModal_handler.close_modal();
        });
        
        // Initialize datepicker inputs
        var datepicker_elements = [];
        datepicker_elements.push(manageAppointmentModal_handler.input.add_new.target_date);
        datepicker_elements.push(manageAppointmentModal_handler.input.modify.appointment_date);
        datepicker_elements.push(manageAppointmentModal_handler.input.markCompleted.completed_date);
        for(var i = 0; i < datepicker_elements.length; i++){
            var element = datepicker_elements[i];
            element.datepicker({ format: 'mm/dd/yyyy' }).on('changeDate', function(){ this.data('datepicker').hide() }.bind(element));
        }
    });

    var new_appointment_dropdown_handler = {
        main_display_element : null,
        main_display_element_dropdown_contents : null,
        current_selection : null,
        data : null,
        handle_dropdown_change : function(data_index){
            var this_data = this.data[data_index];
            this.current_selection = this_data;
            this.main_display_element.html(this_data.procedure_name);
        },
        initialize_with_data : function(data){
            this.data = data;
            //console.log("Initializing with data!");
            for(var i = 0; i < data.length; i++){
                this_event = data[i];
                this_event.list_id = i;
                this.append_to_dropdown(this_event);
            }
        },
        
        append_to_dropdown : function(the_event){
            //<li><a href="#" onclick = 'new_appointment_dropdown_handler.handle_dropdown_change(0)'>Age - Closest to You</a></li>  

            // create DOM elements
            var parent = document.createElement("li");

            var anchor = document.createElement("a");
            anchor.href = '#';
            anchor.onclick = function(){this.handle_dropdown_change(the_event.list_id)}.bind(this);
            anchor.innerHTML = this_event.procedure_name;

            //append them to their parents
            parent.appendChild(anchor);

            //append content to the dropdown
            this.main_display_element_dropdown_contents.append(parent);
        },
    }

</script>


<div class="modal fade modal-wide"  id="manageAppointment-modal" role="dialog" aria-labelledby="manageAppointmentLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close modal_cancel_button" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"> Modal Title </h4>
            </div>

            <style>
                .manageAppointmentModalLabel {
                    min-width:175px;   
                }
            </style>
            <div class="modal-body" style = 'overflow-y:visible;'>
                <div class="menu-part modal-part" style = 'padding: 0px 15px; '>
                    <div style = 'font-size:25px; margin-left:-10px;'>
                        What would you like to do?
                    </div>
                    <Br>
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
                                <div style = ' margin:auto; margin-left:5px;'>
                                    ${data_element.desc}
                                </div>
                            </div>
                            <div style = 'display:flex'>
                                <div style = 'margin:auto; margin-right:5px;'>
                                    <button type="button" class="btn btn-primary  pull-right" id = 'manageAppointment_menu_${data_element.id_mod}' style = 'width:175px;'> ${data_element.button_text}</button>
                                </div>
                            </div>
                        </div>
                    <% 
                       } // end data.each
                    %>
                </div>
                <div class="modal-part modifyCompleted-part markCompleted-part">
                    <input id="markCompletedIdHolder" type="hidden" value="">
                    <form class="form-inline" role="form">
                        <label class = 'manageAppointmentModalLabel'>Mark Completed Date</label>
                        <input class="form-control datetype" id="completed_date" type="text" value=""/>
                    </form>
                    <form class="form-inline" role="form"> 
                        <label class = 'manageAppointmentModalLabel'>Doctor</label>
                        <input class="form-control " id="doctor_name" type="text" value=""/>
                    </form>
                    <form class="form-inline" role="form"> 
                        <label class = 'manageAppointmentModalLabel'>Comments</label>
                        <textarea class="form-control" id="comments"></textarea>
                    </form>
                </div>
                <div class="modal-part modify-part">
                    <input id="markCompletedIdHolder" type="hidden" value="">
                    
                    <form class="form-inline" role="form"> 
                        <label class = 'manageAppointmentModalLabel'>Appointment Date</label>
                        <input class="form-control datetype" id = 'appointment_date' >
                    </form>
                </div>
                <div class="modal-part remove-part">
                    Are you sure you wish to remove this appointment? This can not be undone. 
                </div>
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
                <div class="modal-part add_new-part">
                    <div style = 'display:flex;'>
                        <div style = 'margin: auto 0px;'>
                            <label class = 'manageAppointmentModalLabel'>Appointment Type</label>
                        </div>
                        <div class="dropdown " style = 'position:relative; z-index: 5000;'>
                            <button class="btn-like-input " type="button" id="new_appointment_type_dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" style = ' padding:0px 13px; height:43px; margin-left:3px; min-width:230px; display:flex;'>
                                <div id = 'new_appointment_type' style = 'margin:auto; margin-left:0px;'> PlaceHolder </div> 
                                <div class="caret" style = 'margin:auto; margin-right:0px;'></div>
                            </button>
                            <ul class="dropdown-menu " id = 'new_appointment_type_dropdown_contents' aria-labelledby="new_appointment_type_dropdown" style = 'font-size:14px; '>
                                <!--
                                <li><a href="#" onclick = 'new_appointment_dropdown_handler.handle_dropdown_change(0)'>Age - Closest to You</a></li>
                                -->
                            </ul>
                        </div>
                    </div>
                        
                    <div style = 'height:15px;'></div>
                    
                    <form class="form-inline" role="form">
                        <label class = 'manageAppointmentModalLabel'>Target Date</label>
                        <input class="form-control datetype" id="new_appointment_target_date"  style = 'min-width:230px;' type="text" value=""/>
                    </form>
                </div>
            </div>
            
            
            <div class="modal-footer" style = 'z-index:10;'>
                <div class="button-div pull-left  ">
                    <button type="button" class="btn btn-default all-parts menu-exclude-part add_new-exclude-part" id = 'back_button'>Back</button>
                </div>
                <div class="button-div pull-right ">
                    <button type="button" class="btn btn-default modal_cancel_button modal-part all-parts">Cancel</button>
                    <button type="button" class="btn btn-primary modal-part markCompleted-part" id="manageAppointment_button_markCompleted"> Mark Completed Record </button>
                    <button type="button" class="btn btn-primary modal-part modifyCompleted-part" id="manageAppointment_button_modifyCompleted"> Modify Completed Record </button>
                    <button type="button" class="btn btn-primary modal-part modify-part" id="manageAppointment_button_modify"> Save Changes </button>
                    <button type="button" class="btn btn-primary modal-part remove-part" id="manageAppointment_button_remove"> Remove this Event </button>
                    <button type="button" class="btn btn-primary modal-part add_new-part" id="manageAppointment_button_add_new"> Add New Appointment </button>
                </div>
            </div>
        </div>
    </div>
</div>