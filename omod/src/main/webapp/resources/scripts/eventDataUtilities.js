function Calendar_Handler(){ // Dynamic Properties and Methods
    // properties
    this.calendar_object = null;
    this.persistent_popover_parent_event = null;
    this.bound_popover_remover_function = null;
    this.modification_modal_handler = null;
    
    // methods
    this.return_action_for = null;
}
Calendar_Handler.prototype = { // Static Properties and Methods
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
        if(!(e.events.length > 0)) return; 
        if(this.persistent_popover_parent_event !== null && this.persistent_popover_parent_event.date.getTime() == e.date.getTime()) return; // if is persistant its already open

        // create content
        var content = '';
        for(var i in e.events) {
            /*
            content += '<div class="event-tooltip-content">'
                + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                + '<div class="event-location">' + e.events[i].location + '</div>'
                + '</div>';
            */
            var this_event = e.events[i];

            // generate DOM elements
            var wrapper = document.createElement("div");

            var parent = document.createElement("div");
            parent.className = "event-tooltip-content";
            if(i != 0) parent.style.marginTop = "5px";

            var name = document.createElement("a");
            name.class = 'event-name';
            name.style.setProperty("color", this_event.color);
            if(this_event.status == 1){
                name.style.setProperty("text-decoration", "line-through"); 
                name.style.setProperty("opacity", "0.4");
            } else {
                name.style.setProperty("cursor", "pointer");
                //name.onclick = function(){ preventive_calendar_handler.modification_modal_handler.open_modal_for(id_of_appointment) };
                name.href = this.return_action_for(this_event);
            }

            var sp1 = document.createElement("span");
            sp1.innerHTML = this_event.followProcedureName;


            //append them to their parents
            name.appendChild(sp1);
            parent.appendChild(name);
            wrapper.appendChild(parent);

            content += wrapper.innerHTML;
        }
        jq(e.element).popover({
            trigger: 'focus',
            container: 'body',
            html:true,
            content: content,
        });
        var this_date = e.date; 
        jq(e.element).data('bs.popover').tip().find(".popover-content")[0].addEventListener("click", function(event){ // ensure popover does not close when clicked on
            event.stopPropagation();
        }.bind(this), true); 
        jq(e.element).popover('show');
    },
    mouseOutDay : function(e) {
        if(!(e.events.length > 0)) return; 
        if(this.persistent_popover_parent_event !== null && this.persistent_popover_parent_event.date.getTime() == e.date.getTime()) return; // is persistant after mouse hover leaves
        jq(e.element).popover('hide');
    },
    clickDay : function(e){
        if(!(e.events.length > 0)) return; 
        if(this.persistent_popover_parent_event !== null && this.persistent_popover_parent_event.date.getTime() == e.date.getTime()) return;  // already done
        if(this.persistent_popover_parent_event !== null) this.popover_remove_persistance_and_hide();
        this.persistent_popover_parent_event = e; 
        this.bound_popover_remover_function = this.popover_remove_persistance_and_hide.bind(this);
        document.addEventListener("click", this.bound_popover_remover_function, false);
    },
    popover_remove_persistance_and_hide : function(){
        // get event
        var e = this.persistent_popover_parent_event;
        // remove persistence
        this.persistent_popover_parent_event = null;  
        // hide event
        jq(e.element).popover('hide');
        // remove listener
        document.removeEventListener("click", this.bound_popover_remover_function, false);
    },
    initialize_calendar : function(circleDateTime){
        var calendar = jq(this.calendar_DOM_identifier).calendar({
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
            clickDay: this.clickDay.bind(this),
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
    open_modification_modal_for : function(appointment_id){
        this.popover_remove_persistance_and_hide();
        this.modification_modal_handler.open_modal_for(appointment_id);  
    },
};



function Event_Data_Manager(){
    this.data = {};
    this.valid_reminders = [];
}
Event_Data_Manager.prototype = {
    set_data : function(the_data){
        for(var i = 0; i < the_data.length; i++){
            var this_event = the_data[i];
            this.data[this_event.id] = this_event;
        }
    },
    set_valid_reminders : function(the_data){
        var added_reminders = [];
        var valid_reminders = [];
        for(var i = 0; i < the_data.length; i++){
            var this_data = the_data[i];
            var this_reminder_name = the_data[i].procedure_name;
            if((added_reminders.indexOf(this_reminder_name) != -1)) continue; // if its already in list, don't re-add it.
            added_reminders.push(this_reminder_name);
            valid_reminders.push(this_data);
        }
        this.valid_reminders = valid_reminders.sort(this.reminder_sort_function);
        console.log(this.valid_reminders);
    },
    reminder_sort_function : function(a,b){
        if(a.procedure_name < b.procedure_name) return -1;
        if(a.procedure_name > b.procedure_name) return 1;
        return 0;
    },
}
       

function Event_Table_Handler(){
    this.table_body_element = null;
    this.isACareGiver = null;
    this.modification_modal_handler = null;
    this.button_identification_class = null;
}
Event_Table_Handler.prototype = {
    setDataSource : function(the_data){
        //console.log(the_data);
        the_data = the_data.sort(this.sorting_comparison_function);
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
        var forward_90_timestamp  = today_timestamp  + time_difference_90_days;
        var backward_90_timestamp = today_timestamp  - time_difference_90_days;
        var event_timestamp = new Date(this_event.targetDate).getTime();  
        if(event_timestamp > forward_90_timestamp || event_timestamp < backward_90_timestamp) return false;
        return true;
    },

    sorting_comparison_function :  function(a, b){
        // Sort list elements by target_date ASC
        var difference = a.targetDate - b.targetDate;
        if(difference == 0 && a.status == 1 && b.status == 1) difference = a.completedDate - b.completedDate; //if both on same day and both completed, sort by completed date
        if(difference == 0 && a.status == 1) difference = -1; //if both on same day and one is completed, put it first
        if(difference == 0 && b.status == 1) difference = 1; //if both on same day and one is completed, put it first
        return difference;
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
        td4.innerHTML = "<a class='btn " + button_class + " btn-sm pull-right "+ this.button_identification_class + "'  data-id = '"+escape(this_event.id)+"'>Manage</a>";
        if(this.isACareGiver != 1) row.appendChild(td4); // only append third column if user is a care giver

        this.table_body_element.appendChild(row);
    },

    initialize_buttons : function(){
        jq('.'+this.button_identification_class).on('click', function(e){
            var id_of_appointment = jq(e.target).data('id'); 
            this.modification_modal_handler.open_modal_for(id_of_appointment);
        }.bind(this));  

        // Initialize Adder button
        document.getElementById("add_new_followup_appointment_button").onclick = function(){
            this.modification_modal_handler.open_modal_for("add_new");
        }.bind(this);
    },
};