<script>
    var calendar_handler = {
        calendar_object : null,
        customDataSourceRenderer : function(element, date, events){ 
            var uncompleted_events = events.filter(function(an_event){ return an_event.status != 1; }); 
            var completed_events = events.filter(function(an_event){ return an_event.status == 1; }); 
            if(uncompleted_events.length == 0){
                // If all events are completed, show an underline for this date
                var colors = completed_events.map(function(a) {return a.color;});
                var the_colors = colors;
                var c = this.blend_these_colors(the_colors);
                jq(element).css('background-color', "white");
                jq(element).css('color', 'black');
                jq(element).css('border-bottom', '1px solid ' + c);
            } else {
                // Show a bubble for this date for all uncompleted events
                var colors = uncompleted_events.map(function(a) {return a.color;});
                var the_colors = colors;
                //the_colors = ["red", "blue"];
                var c = this.blend_these_colors(the_colors);
                //console.log(c);
                jq(element).css('background-color', c);
                jq(element).css('color', 'white');
                jq(element).css('border-radius', '15px');
            }
        },
        
        
        mouseOnDay : function(e) {
            if(e.events.length > 0) {
                var content = '';
                for(var i in e.events) {
                    /*
                    content += '<div class="event-tooltip-content">'
                        + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                        + '<div class="event-location">' + e.events[i].location + '</div>'
                        + '</div>';
                    */
                    var this_event = e.events[i];
                    
                    // create DOM elements
                    var wrapper = document.createElement("div");
                
                    var parent = document.createElement("div");
                    parent.className = "event-tooltip-content";
                    
                    var name = document.createElement("div");
                    name.class = 'event-name';
                    name.style.setProperty("color", this_event.color);
                    name.innerHTML = this_event.name;
                    if(this_event.status == 1){
                        name.style.setProperty("text-decoration", "line-through"); 
                        name.style.setProperty("opacity", "0.4");
                    }
                    
                    //append them to their parents
                    parent.appendChild(name);
                    wrapper.appendChild(parent);
                    
                    content += wrapper.innerHTML;
                }
                jq(e.element).popover({
                    trigger: 'manual',
                    container: 'body',
                    html:true,
                    content: content
                });
                jq(e.element).popover('show');
            }
        },
        
        mouseOutDay : function(e) {
            jq(e.element).popover('hide');
        },
        initialize_calendar : function(circleDateTime){
            var calendar = jq('#calendar').calendar({
                customDayRenderer: function(element, date) {
                    if(date.getTime() == circleDateTime) {
                        jq(element).css('border', '1px dashed red');
                        jq(element).css('border-radius', '15px');
                    } 
                },
                style : 'custom', // required for customDataSourceRenderer to be triggered
                customDataSourceRenderer: this.customDataSourceRenderer.bind(this),
                enableContextMenu: true,
                mouseOnDay: this.mouseOnDay.bind(this),
                mouseOutDay: this.mouseOutDay.bind(this),
            });
            this.calendar_object = calendar;
        },
        
        color_name_to_hex : function(colour) {
            var colours = {"black":"#000000", "blue":"#0000ff", "blueviolet":"#8a2be2", "brown":"#a52a2a", "crimson":"#dc143c", "cyan":"#00ffff", "darkred":"#8b0000", "gold":"#ffd700", "gray":"#808080", "grey"
:"#808080", "green":"#008000", "ivory":"#fffff0", "lavender":"#e6e6fa", "magenta":"#ff00ff", "maroon":"#800000", "navy":"#000080", "orange":"#ffa500", "orangered":"#ff4500", "plum":"#dda0dd", "powderblue":"#b0e0e6", "purple":"#800080", "red":"#ff0000", "royalblue":"#4169e1", "salmon":"#fa8072", "sandybrown":"#f4a460", "seagreen":"#2e8b57", "tan":"#d2b48c", "teal":"#008080", "turquoise":"#40e0d0", "violet":"#ee82ee", "white":"#ffffff", "yellow":"#ffff00"};

            if (typeof colours[colour.toLowerCase()] != 'undefined')
                return colours[colour.toLowerCase()];
            console.log(colour + "was not found" )
            return  colours["gray"];
        },
        
        blend_these_colors : function(the_colors){
            var c = "#";
            for(var i = 0; i<3; i++) {
                 var total_value = 0;
                 for(var j = 0; j < the_colors.length; j++){
                     this_color = this.color_name_to_hex(the_colors[j]);
                     var this_sub = this_color.substring(1+2*i, 3+2*i);
                     var this_value = parseInt(this_sub, 16);
                     total_value += this_value;
                 }
                 var v = Math.floor(total_value / the_colors.length);
                 var sub = v.toString(16).toUpperCase();
                 var padsub = ('0'+sub).slice(-2);
                 c += padsub;
            }   
            return c;
        },
        
        
        
    }; // end calendar_handler
    
    
    
    
    function load_reminder_data() {
        //console.log(jq("#personUuid").val());
        var OpenMRSInstance=window.location.href;
        
        //Load Reminder data, insert reminders into calendar and table
        console.log(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val());
        jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getremindersforpatient/'+ jq("#personUuid").val(), function (reminderData) {
            // Set datasource for reminder table
            reminder_table_handler.setDataSource(reminderData);
            // Set datasource for calendar
            calendar_handler.calendar_object.setDataSource(reminderData);
            // Set datasource for data_manager
            appointment_data_manager.set_data(reminderData);
        });
    }
    
    window.addEventListener("load", function(){
        ///////////////////
        // Initialize Reminder Calendar & Calendar Handler
        ///////////////////
        var currentYear = new Date().getFullYear();
        var currentMonth = new Date().getMonth();
        var currentDay = new Date().getDate();
        var circleDateTime = new Date(currentYear, currentMonth, currentDay).getTime();
        calendar_handler.initialize_calendar(circleDateTime);
        
        ///////////////////
        // Initialize Reminder Table Handler
        ///////////////////
        reminder_table_handler.table_body_element = document.getElementById("table_body");
        reminder_table_handler.isACareGiver = ${isACareGiver};
        reminder_table_handler.modification_modal_handler = manageAppointmentModal_handler;
        reminder_table_handler.button_identification_class = "manageAppointment_sourceButton";
        
        setTimeout(load_reminder_data, 1000);
    });

    
    var appointment_data_manager = {
        data : {},   
        set_data : function(the_data){
            for(var i = 0; i < the_data.length; i++){
                var this_event = the_data[i];
                this.data[this_event.id] = this_event;
            }
        },
    };
    
    
    var reminder_table_handler = {
        table_body_element : null,
        isACareGiver : null,
        modification_modal_handler : null,
        button_identification_class : null,
        
        setDataSource : function(the_data){
            console.log(the_data);
            for(var i = 0; i < the_data.length; i++){
                var this_event = the_data[i];
                // Add event to list
                if(this.should_append_to_list(this_event)) this.append_to_list(this_event);
            }
            this.initialize_buttons();
        },
        
        should_append_to_list : function(this_event){
            var today_timestamp = new Date().getTime();
            var time_difference_90_days = (90 * 24 * 60 * 60 * 1000)
            //                            day  hour  min  sec  msec
            var forward_90_timestamp  = today_timestamp  + time_difference_90_days;
            var backward_90_timestamp = today_timestamp  - time_difference_90_days;
            var event_timestamp = new Date(this_event.targetDate).getTime();  
            if(event_timestamp > forward_90_timestamp || event_timestamp < backward_90_timestamp) return false;
            return true;
        },
        
        append_to_list : function(this_event){
            /*
            <tr class = 'datarow'>
                <td>{(reminder.followProcedureName)}</td>
                <td class="clearfix">
                    
                </td>
                <% if(isACareGiver != 1) { %>
                    <td class="clearfix">
                        <a id="manageAppointment{(reminder.id)}" class="btn btn-primary btn-sm manageAppointment_sourceButton" data-toggle="modal" data-id = "{(reminder.id)}" data-target="#manageAppointment-modal">Manage</a>
                    </td>
                <% } %>
            </tr>
            */
            // Create row element
            var row = document.createElement("tr");
            if(this_event.status == 1)  row.style = 'color:rgba(0, 0, 0, 0.4);';
            
            // Create row selector element
            var row_selector = document.createElement("div");
            row_selector.style = 'position:absolute; margin-left:-20px; margin-top:-16px;  width:10px; height:10px; '; 
            row_selector.className = "row_selector";
            
            // Create first col
            var td1 = document.createElement("td");
            td1.innerHTML= this_event.followProcedureName;
            td1.appendChild(row_selector);
            row.appendChild(td1);
            
            // Create second col
            var td2 = document.createElement("td"); 
            td2.className = 'clearfix';
            td2.innerHTML = "<span class='pull-left'>" + this_event.formatedTargetDate + "</span>";
            row.appendChild(td2);
            
            // Create third col
            var td3 = document.createElement("td"); 
            td3.className = 'clearfix';
            if(this_event.status == 1){
                td3.innerHTML = "<span class='pull-left'>" + this_event.formatedCompletedDate + "</span>";
            } 
            row.appendChild(td3);
            
            // Create third col
            var td4 = document.createElement("td"); 
            td4.className = 'clearfix';
            var button_class = (this_event.status == 1) ? "btn-custom-completed" : "btn-primary";
            td4.innerHTML = "<a class='btn " + button_class + " btn-sm pull-right "+ this.button_identification_class + "' data-id = '"+escape(this_event.id)+"'>Manage</a>";
            if(this.isACareGiver != 1) row.appendChild(td4); // only append third column if user is a care giver
            
            this.table_body_element.appendChild(row);
        },
        
        
        initialize_buttons : function(){
            jq('.'+this.button_identification_class).on('click', function(e){
                var id_of_appointment = jq(e.target).data('id'); 
                this.modification_modal_handler.open_modal_for(id_of_appointment);
            }.bind(this));  
        },
        
    };

    
    
    
    
    
    
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
</style>

<div class="clearfix">


    ${ ui.includeFragment("patientportaltoolkit", "appointmentsManageModal") }
    <input id="personUuid" value="${ person.uuid}" type="hidden">
    <h4>Upcoming Appointments</h4>
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
            <tbody id = 'table_body'>
            </tbody>
        </table>
    </div>
    <!--<div id="chart" width="100%">
    </div>-->
    <br/>
    <div id='calendar' class="calendar" width="100%"></div>
</div>