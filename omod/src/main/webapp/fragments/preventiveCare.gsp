<script>
    
    
    function load_preventive_data() {
        var OpenMRSInstance=window.location.href;
        
        // use promises to ensure that all possible guidelines are loaded before we load reminder data. Because of this, we are able to remove non-valid reminders from the reminders we display.
        var load_all_possible_events = new Promise((resolve, reject)=>{
            //Load all possible guidelines that user can choose to create a new reminder from
            jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getpossiblenewpreventivecareeventsforpatient/'+ jq("#personUuid").val(), function (data) {
                resolve(data);
            });
        })
        var load_all_current_events = new Promise((resolve, reject)=>{
            //Load Reminder data, insert reminders into calendar and table
            jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getpreventivecareforpatient/'+ jq("#personUuid").val(), function (data) {
                resolve(data);
            });
        })
        
        var promise_to_load_valid_options_and_recorded_data = Promise.all([load_all_possible_events, load_all_current_events])
        
        var initialize_objects = promise_to_load_valid_options_and_recorded_data.then((data_array)=>{
            var possible_events = data_array[0];
            var events = data_array[1];

            // record the data in appointment data manager
            preventive_data_manager.set_possible_events(possible_events);

            // Set datasource for data_manager
            preventive_data_manager.set_data(events);
            
            // get valid events
            var valid_events = preventive_data_manager.nonkeyed_data;
            
            // fill table and calendar based on valid data
            preventive_table_handler.setDataSource(valid_events);
            preventive_calendar_handler.calendar_object.setDataSource(valid_events);
        })
        
    }
    
    window.addEventListener("load", function(){
        ///////////////////
        // Initialize Reminder Calendar & Calendar Handler
        ///////////////////
        window["preventive_calendar_handler"] = new Calendar_Handler(); // usage of window[""] is not technical; it is to clearly demonstrate that this object needs to be global.
        preventive_calendar_handler.calendar_DOM_identifier = '#preventiveCareCalendar';
        preventive_calendar_handler.return_action_for = function(the_event){
            return "javascript:preventive_calendar_handler.open_modification_modal_for('" + the_event.id + "');";   
        }
        var currentYear = new Date().getFullYear();
        var currentMonth = new Date().getMonth();
        var currentDay = new Date().getDate();
        var circleDateTime = new Date(currentYear, currentMonth, currentDay).getTime();
        preventive_calendar_handler.initialize_calendar(circleDateTime);
        preventive_calendar_handler.modification_modal_handler = managePreventiveCareModal_handler;
        
        ///////////////////
        // Initialize Data Manager
        ///////////////////
        window["preventive_data_manager"] = new Event_Data_Manager();
        
        ///////////////////
        // Initialize Reminder Table Handler
        ///////////////////
        window["preventive_table_handler"] = new Event_Table_Handler();
        preventive_table_handler.table_body_element = document.getElementById("preventive_table_body");
        preventive_table_handler.isACareGiver = ${isACareGiver};
        preventive_table_handler.modification_modal_handler = managePreventiveCareModal_handler;
        preventive_table_handler.button_identification_class = "managePreventiveCare_sourceButton";
        preventive_table_handler.add_new_appointment_button = "add_new_preventive_appointment_button";
        
        setTimeout(load_preventive_data, 1000);
    });

    
    
</script>
<div class="clearfix">
    ${ui.includeFragment("patientportaltoolkit", "preventiveCareManageModal")}
    <input id="preventivepersonUuid" value="${ person.uuid}" type="hidden">
    <h4>Upcoming Appointments</h4>
    <div>
        <table class="table table-hover-custom" id="due-preventive_care">
            <thead>
            <tr>
                <th>Appointment Type</th>
                <th>Recommended Date</th>
                <th>Completed Date </th>
                <% if(isACareGiver != 1) { %>
                    <th style = 'text-align:right;'>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody id = 'preventive_table_body'>
            </tbody>
        </table>
    </div>
    <div style = 'display:flex; margin-top:-20px; '>
        <div style = 'margin:auto; margin-left:0px;'>
            <a class='btn btn-secondary-customized btn-sm pull-right' style = 'font-size:16px; margin-left:-8px;' id = 'add_new_preventive_appointment_button'> Add New Appointment</a> <!-- id used in appointmentsManageModal.gsp -->
        </div>
    </div>
    <br/>
    <div id='preventiveCareCalendar' class="calendar" width="100%"></div>
</div>