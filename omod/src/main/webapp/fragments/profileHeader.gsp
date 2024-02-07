<div class="jumbotron">
    <div class="container" style="display: flex;align-items: center;">
        <div>
            <a href="#">
                <div class="profileBadgeHeader">${ (person.getGivenName()) } ${ (person.getFamilyName()) }</div>
            </a>
        </div>
        <div id="profile-name">
            <h2>${ (person.getGivenName()) } ${ (person.getFamilyName()) }
            </h2>
              <div>
                <span class="gender-age">
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