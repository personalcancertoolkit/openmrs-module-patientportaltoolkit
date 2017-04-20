<script>
    
    
    function load_preventive_data() {
        //console.log(jq("#personUuid").val());
        var OpenMRSInstance=window.location.href;
        //Load Reminder data, insert reminders into calendar and table
        //console.log(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val());
        jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getpreventivecareforpatient/'+ jq("#personUuid").val(), function (preventiveData) {
            // Set datasource for reminder table
            preventive_table_handler.setDataSource(preventiveData);
            // Set datasource for calendar
            preventive_calendar_handler.calendar_object.setDataSource(preventiveData);
            // Set datasource for data_manager
            preventive_data_manager.set_data(preventiveData);
        });
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
                <th> </th>
                <% if(isACareGiver != 1) { %>
                    <th style = 'text-align:right;'>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody id = 'preventive_table_body'>
            </tbody>
        </table>
    </div>
    <br/>
    <div id='preventiveCareCalendar' class="calendar" width="100%"></div>
</div>