<div class="jumbotron">
    <div class="container clearfix">
        <div class="pull-left profile-image">
            <a href="#">
                <img src="/openmrs/images/openmrs_logo_white.gif" alt="Default Profile Picture" />
            </a>
        </div>
        <div class="pull-left" id="profile-name">
            <h2>${ (person.getGivenName()) } ${ (person.getFamilyName()) }</h2>
            <div>
                <span class="gender-age">
                    <span>${ui.message("coreapps.gender." + person.gender)}&nbsp;</span>
                    <span>
                        <% if (!person.birthdate == null) { %>
                        <% if (person.age > 0) { %>
                        ${ui.message("coreapps.ageYears", person.age)}
                        <% } else if (patient.ageInMonths > 0) { %>
                        ${ui.message("coreapps.ageMonths", person.ageInMonths)}
                        <% } else { %>
                        ${ui.message("coreapps.ageDays", person.ageInDays)}
                        <% } %>
                        (<% if (person.birthdateEstimated) { %>~<% } %>${ ui.formatDatePretty(person.birthdate) })
                        <% } else { %>
                        ${ui.message("coreapps.unknownAge")}
                        <% } %>
                    </span>
            </div>
        </div>
    </div>
</div>