<script>
    
    jq(document).ready(function(){
    var OpenMRSInstance=window.location.href;
    var currentYear = new Date().getFullYear();
    var currentMonth = new Date().getMonth();
    var currentDay = new Date().getDate();
    var circleDateTime = new Date(currentYear, currentMonth, currentDay).getTime();
    function editEvent(event) {
        jq('#event-modal input[name="event-index"]').val(event ? event.id : '');
        jq('#event-modal input[name="event-name"]').val(event ? event.name : '');
        jq('#event-modal input[name="event-location"]').val(event ? event.location : '');
        jq('#event-modal input[name="event-start-date"]').datepicker('update', event ? event.startDate : '');
        jq('#event-modal input[name="event-end-date"]').datepicker('update', event ? event.endDate : '');
        jq('#event-modal').modal();
    }

    function deleteEvent(event) {
        var dataSource = jq('#calendar').data('calendar').getDataSource();

        for(var i in dataSource) {
            if(dataSource[i].id == event.id) {
                dataSource.splice(i, 1);
                break;
            }
        }

        jq('#calendar').data('calendar').setDataSource(dataSource);
    }

    function saveEvent() {
        var event = {
            id: jq('#event-modal input[name="event-index"]').val(),
            name: jq('#event-modal input[name="event-name"]').val(),
            location: jq('#event-modal input[name="event-location"]').val(),
            startDate: jq('#event-modal input[name="event-start-date"]').datepicker('getDate'),
            endDate: jq('#event-modal input[name="event-end-date"]').datepicker('getDate')
        }

        var dataSource = jq('#calendar').data('calendar').getDataSource();

        if(event.id) {
            for(var i in dataSource) {
                if(dataSource[i].id == event.id) {
                    dataSource[i].name = event.name;
                    dataSource[i].location = event.location;
                    dataSource[i].startDate = event.startDate;
                    dataSource[i].endDate = event.endDate;
                }
            }
        }
        else
        {
            var newId = 0;
            for(var i in dataSource) {
                if(dataSource[i].id > newId) {
                    newId = dataSource[i].id;
                }
            }

            newId++;
            event.id = newId;

            dataSource.push(event);
        }

        jq('#calendar').data('calendar').setDataSource(dataSource);
        jq('#event-modal').modal('hide');
    }


    function foo2 () {
        //hardcoded data to be modified
        jq.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+'/ws/patientportaltoolkit/getpreventivecareforpatient/'+ jq("#preventivepersonUuid").val(), function (pcgeData) {
            jq('#preventativeCareCalendar').data('calendar').setDataSource(pcgeData);
            //console.log(pcgeData);
        });
    }
        
    function colourNameToHex(colour) {
            var colours = {"black":"#000000", "blue":"#0000ff", "blueviolet":"#8a2be2", "brown":"#a52a2a", "crimson":"#dc143c", "cyan":"#00ffff", "darkred":"#8b0000", "gold":"#ffd700", "gray":"#808080", "grey"
:"#808080", "green":"#008000", "ivory":"#fffff0", "lavender":"#e6e6fa", "magenta":"#ff00ff", "maroon":"#800000", "navy":"#000080", "orange":"#ffa500", "orangered":"#ff4500", "plum":"#dda0dd", "powderblue":"#b0e0e6", "purple":"#800080", "red":"#ff0000", "royalblue":"#4169e1", "salmon":"#fa8072", "sandybrown":"#f4a460", "seagreen":"#2e8b57", "tan":"#d2b48c", "teal":"#008080", "turquoise":"#40e0d0", "violet":"#ee82ee", "white":"#ffffff", "yellow":"#ffff00",};

            if (typeof colours[colour.toLowerCase()] != 'undefined')
                return colours[colour.toLowerCase()];
            console.log(colour + "was not found" )
            return  colours["gray"];
    }
    
    var preventivecalendar= jq('#preventativeCareCalendar').calendar({
        style : 'custom', // required for customDataSourceRenderer to be triggered
        customDayRenderer: function(element, date) {
            if(date.getTime() == circleDateTime) {
                jq(element).css('border', '1px dashed red');
                //jq(element).css('border-color', 'red');
                //jq(element).css('color', 'white');
                jq(element).css('border-radius', '15px');
            } 
        },
        customDataSourceRenderer: function(element, date, events){ 
            var colors = events.map(function(a) {return a.color;});
            //console.log(colors);  
            
            the_colors = colors;
            //the_colors = ["red", "blue"];
            var c = "#";
            for(var i = 0; i<3; i++) {
                 var total_value = 0;
                 for(var j = 0; j < the_colors.length; j++){
                     this_color = colourNameToHex(the_colors[j]);
                     var this_sub = this_color.substring(1+2*i, 3+2*i);
                     var this_value = parseInt(this_sub, 16);
                     total_value += this_value;
                 }
                 var v = Math.floor(total_value / the_colors.length);
                 var sub = v.toString(16).toUpperCase();
                 var padsub = ('0'+sub).slice(-2);
                 c += padsub;
            }
            //console.log(c);
            jq(element).css('background-color', c);
            jq(element).css('color', 'white');
            jq(element).css('border-radius', '15px');
        },
        enableContextMenu: true,
        contextMenuItems:[
            {
                text: 'Update',
                click: editEvent
            },
            {
                text: 'Delete',
                click: deleteEvent
            }
        ],
        selectRange: function(e) {
            editEvent({ startDate: e.startDate, endDate: e.endDate });
        },
        mouseOnDay: function(e) {
            if(e.events.length > 0) {
                var content = '';

                for(var i in e.events) {
                    content += '<div class="event-tooltip-content">'
                        + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                        + '<div class="event-location">' + e.events[i].location + '</div>'
                        + '</div>';
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
        mouseOutDay: function(e) {
            if(e.events.length > 0) {
                jq(e.element).popover('hide');
            }
        }

    });
        
    setTimeout(foo2, 1000);
        
    jq('.markPreventiveCompletedReminder').click(
        function () {
            var preventiveReminderID=this.id.split("markPreventiveCompletedReminder")[0];
            //console.log(preventiveReminderID);
            jq("#preventiveCareTypeUuid").val(preventiveReminderID);
            jq(".preventiveModal").hide(); // hide any shown modal sections
            switch (preventiveReminderID){
                case "162938":
                    jq("#influenza-modal").show();
                    break;
                case "162939":
                    jq("#pneumococcal-modal").show();
                    break;
                case "162940":
                    jq("#cholesterol-modal").show();
                    break;
                case "162941":
                    jq("#bp-modal").show();
                    break;
                case "162942":
                    jq("#hiv-modal").show();
                    break;
                case "162943":
                    jq("#mammography-modal").show();
                    break;
                case "162944":
                    jq("#cervical-modal").show();
                    break;
            }
        });
    });
</script>
<div class="clearfix">
    ${ ui.includeFragment("patientportaltoolkit", "preventiveCareModal") }
    <input id="preventivepersonUuid" value="${ person.uuid}" type="hidden">
    <h4>Upcoming Preventative Care Appointments</h4>
    <div>
        <table class="table table-hover" id="due-preventive-appointments">
            <thead>
            <tr>
                <th>Appointment Type</th>
                <th>Recommended Date</th>
                <% if(isACareGiver != 1) { %>
                <th>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% alertableevents.each { events -> %>
            <% def date = new Date() %>
            <% if (events.targetDate < date-90 || events.targetDate > date+90) {%>
            <% }else { %>
            <% if (events.status == 2 && events.responseDate > date) {%>
            <% }else { %>
            <tr class="datarow">
                <td>  ${(events.followProcedureName)}</td>
                <td class="clearfix">
                    <span class="pull-left"> ${pptutil.formatDate((events.targetDate))}</span>
                </td>
                <% if(isACareGiver != 1) { %>
                <td class="clearfix">
                    <a id="${(events.followProcedure.id)}markPreventiveCompletedReminder${(events.id)}" class="btn btn-primary btn-sm markPreventiveCompletedReminder" data-toggle="modal" data-target="#preventiveCare-modal">Mark Completed</a>
                </td>
                <% } %>
            </tr>
            <% } %>
            <% } %>
            <% } %>
            </tbody>
        </table>
    </div>
    <br/>
    <div id='preventativeCareCalendar' class="calendar" width="100%"></div>
</div>