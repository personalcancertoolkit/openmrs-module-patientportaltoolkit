<script>

    /////////////////////
    // Define Manage Event Modal Handler Object
    /////////////////////
    var manageAppointmentModal_handler = {
        modal : null,
        appointment_id : null,
        data_manager: null,

        open_modal_for : function(appointment_id){
            this.modal.modal('show');
            this.appointment_id = appointment_id;
            this.open_part('menu');
            this.update_visible_data();
        },
        
        //////////
        // DOM interactions
        //////////
        open_part : function(which_part){
            valid_parts = ["menu", "markCompleted", "modify", "remove"];
            if((valid_parts.indexOf(which_part) == -1)){
                console.error("Requested part to open is not valid");
                return;
            }
            this.hide_all_parts();
            //console.log(':  - ' + which_part);
            this.modal.find("."+which_part+"-part").show(); 
            this.modal.find(".all-parts").show(); 
            this.modal.find("."+which_part+"-exclude-part").hide(); 
        },
        update_visible_data : function(){
            var event = this.data_manager.data[this.appointment_id];
            
            // update title
            this.modal.find(".modal-title").html(event.followProcedureName + " Appointment");
            
            // update modify input defaults to current data
            this.input.modify.appointment_date.data('datepicker').setValue(event.startDate);
        },
        hide_all_parts : function(){
            this.modal.find(".modal-part").hide();
        },
        
        
        ///////////////
        // Handle Actions
        ///////////////
        trigger_action : function(type){
            if(type == "markCompleted") {
                this.attempt_mark_completed();
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
                console.log(this.responseText);
                console.log("Success!");
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
        manageAppointmentModal_handler.buttons = {
            menu : {
                markCompleted : document.getElementById('manageAppointment_menu_markCompleted'),
                modify : document.getElementById('manageAppointment_menu_modify'),
                remove : document.getElementById('manageAppointment_menu_remove'),
            },
            action : {
                markCompleted : document.getElementById('manageAppointment_button_markCompleted'),
                modify : document.getElementById('manageAppointment_button_modify'),
                remove : document.getElementById('manageAppointment_button_remove'),
            },
            back : the_modal.find("#back_button"),
        }
        manageAppointmentModal_handler.input = {
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
            manageAppointmentModal_handler.open_part('menu');
        });
        
        // Initialize datepicker inputs
        var datepicker_elements = [];
        datepicker_elements.push(manageAppointmentModal_handler.input.modify.appointment_date);
        datepicker_elements.push(manageAppointmentModal_handler.input.markCompleted.completed_date);
        for(var i = 0; i < datepicker_elements.length; i++){
            var element = datepicker_elements[i];
            element.datepicker({ format: 'mm/dd/yyyy' }).on('changeDate', function(){ this.data('datepicker').hide() }.bind(element));
        }
        
        
        
    });



</script>


<div class="modal fade modal-wide"  id="manageAppointment-modal" role="dialog" aria-labelledby="manageAppointmentLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"> Modal Title </h4>
            </div>

            <div class="modal-body">
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
                        <div class="row" id = '${data_element.id_mod}_menu_holder' style = ' display:flex; margin-bottom:5px;'>
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
                <div class="modal-part markCompleted-part">
                    <style>
                        .markCompletedLabel {
                            min-width:175px;   
                        }
                    </style>
                    <input id="markCompletedIdHolder" type="hidden" value="">
                    <form class="form-inline" role="form">
                        <label class = 'markCompletedLabel'>Mark Completed Date</label>
                        <input class="form-control datetype" id="completed_date" type="text" value=""/>
                    </form>
                    <form class="form-inline" role="form"> 
                        <label class = 'markCompletedLabel'>Doctor</label>
                        <input class="form-control " id="doctor_name" type="text" value=""/>
                    </form>
                    <form class="form-inline" role="form"> 
                        <label class = 'markCompletedLabel'>Comments</label>
                        <textarea class="form-control" id="comments"></textarea>
                    </form>
                </div>
                <div class="modal-part modify-part">
                    <style>
                        .markCompletedLabel {
                            min-width:175px;   
                        }
                    </style>
                    <input id="markCompletedIdHolder" type="hidden" value="">
                    
                    <form class="form-inline" role="form"> 
                        <label class = 'markCompletedLabel'>Appointment Date</label>
                        <input class="form-control datetype" id = 'appointment_date' >
                    </form>
                </div>
            </div>
            
            
            <div class="modal-footer">
                <div class="button-div pull-left  ">
                    <button type="button" class="btn btn-default all-parts menu-exclude-part" id = 'back_button'>Back</button>
                </div>
                <div class="button-div pull-right ">
                    <button type="button" class="btn btn-default cancelModal modal-part all-parts">Cancel</button>
                    <button type="button" class="btn btn-primary modal-part markCompleted-part" id="manageAppointment_button_markCompleted"> Mark Completed </button>
                    <button type="button" class="btn btn-primary modal-part modify-part" id="manageAppointment_button_modify"> Save Changes </button>
                    <button type="button" class="btn btn-primary modal-part remove-part" id="manageAppointment_button_remove"> Remove this Event </button>
                </div>
            </div>
        </div>
    </div>
</div>