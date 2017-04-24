<script>
    function load_reminder_data() {
        //console.log(jq("#personUuid").val());
        var OpenMRSInstance=window.location.href;
        //Load Reminder data, insert reminders into calendar and table
        //console.log(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val());
        jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val(), function (reminderData) {
            // Set datasource for reminder table
            reminder_table_handler.setDataSource(reminderData);
            // Set datasource for calendar
            reminder_calendar_handler.calendar_object.setDataSource(reminderData);
            // Set datasource for data_manager
            appointment_data_manager.set_data(reminderData);
        });
        //Load all possible guidelines that user can choose to create a new reminder from
        jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getpossiblenewremindersforpatient/'+ jq("#personUuid").val(), function (reminderData) {
            // console.log(reminderData);
            // record the data in appointment data manager
            appointment_data_manager.set_valid_reminders(reminderData);
        });
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
    
.btn-custom-completed {
  color: rgba(0, 0, 0, 0.4);
  background-color: #fff;
  border-color: rgba(0, 0, 0, 0.3);
  border-width:1px;
  margin-right:1px; margin-left:1px; /* since regular btn's border is 2px, compensate to make them same total width */
}
.btn-custom-completed:hover,
.btn-custom-completed:focus,
.btn-custom-completed.focus,
.btn-custom-completed:active,
.btn-custom-completed.active {
  color: rgba(0, 0, 0, 0.4);
  background-color: #ebebeb;
  border-color: rgba(0, 0, 0, 0.3);
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