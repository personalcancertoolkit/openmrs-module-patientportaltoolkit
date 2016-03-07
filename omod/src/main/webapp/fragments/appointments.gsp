<div class="clearfix">


    ${ ui.includeFragment("patientportaltoolkit", "markCompletedModal") }
    ${ ui.includeFragment("patientportaltoolkit", "markScheduledModal") }
    <h4>Upcoming Appointments</h4>
    <div>
        <table class="table table-hover" id="due-appointments">
            <thead>
            <tr>
                <th width="25%">Appointment Type</th>
                <th width="25%">Recommended Date</th>
                <th width="50%">Actions</th>
            </tr>
            </thead>
            <tbody>
            <% alertablereminders.each { reminder -> %>
            <% def date = new Date() %>
            <% if (reminder.targetDate < date-90 || reminder.targetDate > date+90) {%>
            <% }else { %>
            <% if (reminder.status == 2 && reminder.responseDate > date) {%>
            <% }else { %>
            <tr class="datarow">
                <td width="30%">  ${(reminder.followProcedureName)}</td>
                <td width="30%" class="clearfix">
                    <span class="pull-left"> ${pptutil.formatDate((reminder.targetDate))}</span>
                </td>
                <td width="40%" class="clearfix">
                    <a id="markCompletedReminder${(reminder.id)}" class="btn btn-primary btn-sm markCompletedReminder" data-toggle="modal" data-target="#markCompleted-modal">Mark Completed</a>
                    <a id="markScheduledReminder${(reminder.id)}" class="btn btn-primary btn-sm markScheduledReminder"  data-toggle="modal" data-target="#markScheduled-modal">Mark Scheduled</a>
                </td>
            </tr>
            <% } %>
            <% } %>
            <% } %>
            </tbody>
            </table>
    </div>
    <div id="chart" width="100%">
    </div>
</div>