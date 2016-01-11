<div class="clearfix">



    <h4>Upcoming Appointments</h4>
    <div>
        <table class="table table-hover" id="due-appointments">
            <thead>
            <tr>
                <th width="50%">Appointment Type</th>
                <th width="50%">Recommended Date</th>
            </tr>
            </thead>
            <tbody>
            <% reminders.each { reminder -> %>
            <% def date = new Date() %>
            <% if (reminder.targetDate < date-90 || reminder.targetDate > date+90) {%>
            <% }else { %>
            <tr class="datarow">
                <td width="50%">  ${(reminder.followProcedureName)}</td>
                <td width="50%" class="clearfix">
                    <span class="pull-left"> ${(reminder.targetDate)}&emsp;</span>
                    <button ref="node" class="btn btn-primary btn-sm pull-right">Mark Completed</button>

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