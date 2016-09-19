<div class="jumbotron">
    <div class="container clearfix">
        <div class="pull-left">
            <a href="#">
                <div class="profileBadgeHeader">${ (person.getGivenName()) } ${ (person.getFamilyName()) }</div>
            </a>
        </div>
        <div class="pull-left" id="profile-name">
            <h2>${ (person.getGivenName()) } ${ (person.getFamilyName()) }
            </h2>
              <div>
                <span class="gender-age">
                    <span>${ui.message("coreapps.gender." + person.gender)}&nbsp;</span>
                        <% if (person.birthdate && !person.getBirthdate().is(null) ){ %>
                        <% if (person.age > 0) { %>
                        ${ui.message("coreapps.ageYears", person.age)}
                        <% } %>
                        (<% if (person.birthdateEstimated) { %>~<% } %>${pptutil.formatDate(person.birthdate)})
                        <% } else { %>
                        ${ui.message("coreapps.unknownAge")}
                        <% } %>
                    </span>
            </div>
        </div>
    </div>
</div>