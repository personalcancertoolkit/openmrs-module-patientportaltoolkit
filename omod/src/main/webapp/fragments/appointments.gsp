<div class="clearfix">


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
            <tr class="datarow">
                <td width="25%">  ${(reminder.followProcedureName)}</td>
                <td width="25%" class="clearfix">
                    <span class="pull-left"> ${(reminder.targetDate)}</span>
                </td>
                <td width="50%" class="clearfix">
                    <button id="markCompletedReminder${(reminder.id)}" class="btn btn-primary btn-sm markCompletedReminder">Mark Completed</button>
                    <button class="btn btn-primary btn-sm ">Mark Scheduled</button>
                    <button class="btn btn-primary btn-sm ">Snooze</button>

                </td>
            </tr>
            <% } %>
            <% } %>
            </tbody>
            </table>
    </div>
    <div id="chart" width="100%">
    </div>
</div>