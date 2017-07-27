<script>
    function load_reminder_data() {
        //console.log(jq("#personUuid").val());
        var OpenMRSInstance=window.location.href;
        
        // use promises to ensure that all possible guidelines are loaded before we load reminder data. Because of this, we are able to remove non-valid reminders from the reminders we display.
        var load_all_possible_events = new Promise((resolve, reject)=>{
            //Load all possible guidelines that user can choose to create a new reminder from
            jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getpossiblenewremindersforpatient/'+ jq("#personUuid").val(), function (data) {
                resolve(data);
            });
        })
        var load_all_current_events = new Promise((resolve, reject)=>{
            //Load Reminder data, insert reminders into calendar and table
            //console.log(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val());
            jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val(), function (data) {
                resolve(data);
            });
        })
        
        var promise_to_load_valid_options_and_recorded_data = Promise.all([load_all_possible_events, load_all_current_events])
        
        var initialize_objects = promise_to_load_valid_options_and_recorded_data.then((data_array)=>{
            var possible_events = data_array[0];
            var events = data_array[1];

            // record the data in appointment data manager
            appointment_data_manager.set_possible_events(possible_events);

            // Set datasource for data_manager
            appointment_data_manager.set_data(events);
            
            // get valid events
            var valid_events = appointment_data_manager.nonkeyed_data;
            
            // fill table and calendar based on valid data
            reminder_table_handler.setDataSource(valid_events);
            reminder_calendar_handler.calendar_object.setDataSource(valid_events);
        })
    }
    window.addEventListener("load", function(){
        ///////////////////
        // Initialize Reminder Calendar & Calendar Handler
        ///////////////////
        window["reminder_calendar_handler"] = new Calendar_Handler(); // usage of window[""] is not technical; it is to clearly demonstrate that this object needs to be global.
        reminder_calendar_handler.calendar_DOM_identifier = '#reminderCalendar';
        reminder_calendar_handler.return_action_for = function(the_event){
            return "javascript:reminder_calendar_handler.open_modification_modal_for('" + the_event.id + "');";   
        }
        var currentYear = new Date().getFullYear();
        var currentMonth = new Date().getMonth();
        var currentDay = new Date().getDate();
        var circleDateTime = new Date(currentYear, currentMonth, currentDay).getTime();
        reminder_calendar_handler.initialize_calendar(circleDateTime);
        reminder_calendar_handler.modification_modal_handler = manageAppointmentModal_handler;
        
        ///////////////////
        // Initialize Data Manager
        ///////////////////
        window["appointment_data_manager"] = new Event_Data_Manager();
        
        ///////////////////
        // Initialize Reminder Table Handler
        ///////////////////
        window["reminder_table_handler"] = new Event_Table_Handler();
        reminder_table_handler.table_body_element = document.getElementById("reminder_table_body");
        reminder_table_handler.isACareGiver = ${isACareGiver};
        reminder_table_handler.modification_modal_handler = manageAppointmentModal_handler;
        reminder_table_handler.button_identification_class = "manageAppointment_sourceButton";
        reminder_table_handler.add_new_appointment_button = "add_new_followup_appointment_button";
        
        setTimeout(load_reminder_data, 1000);
    });
</script>
<style>
    
.table-hover-custom > tbody > tr > td > .row_selector {
    background-color:white;
    border-radius:25px;
}
.table-hover-custom > tbody > tr:hover > td > .row_selector {
    background-color:#34BCA5;
}
    
.btn-secondary-customized {
}
.btn-secondary-customized:hover {
    background-color:#ECF0F1;
    color:#1EBC9C;
}
</style>

<div class="clearfix">

    ${ ui.includeFragment("patientportaltoolkit", "appointmentsManageModal") }
    <div style = 'height:15px;'></div>
    <input id="personUuid" value="${ person.uuid}" type="hidden">
    <h4>Upcoming Appointments</h4>
    <div style = 'height:10px;'></div>
    <div>
        <table class="table table-hover-custom" id="due-appointments">
            <thead>
            <tr>
                <th>Appointment Type</th>
                <th>Recommended Date</th>
                <th>Completed Date</th>
                <% if(isACareGiver != 1) { %>
                    <th style = 'text-align:right;'>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody id = 'reminder_table_body'>
            </tbody>
        </table>
    </div>
    <div style = 'display:flex; margin-top:-20px; '>
        <div style = 'margin:auto; margin-left:0px;'>
            <a class='btn btn-secondary-customized btn-sm pull-right' style = 'font-size:16px; margin-left:-8px;' id = 'add_new_followup_appointment_button'> Add New Appointment</a> <!-- id used in appointmentsManageModal.gsp -->
        </div>
    </div>
    <!--<div id="chart" width="100%">
    </div>-->
    <br/>
    <div id='reminderCalendar' class="calendar" width="100%"></div>
</div>