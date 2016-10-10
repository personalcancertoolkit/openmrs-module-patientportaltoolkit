<hr>
    <div class="row">

        <% if (mycancerbuddiespeople) { %>
        <% mycancerbuddiespeople.each { mycancerbuddiesperson -> %>
        <div class="col-xs-18 col-sm-6 col-md-3">
            <div class="thumbnail">
                <div class="caption">
                    <h4>${ (mycancerbuddiesperson.myCancerBuddiesName) }</h4>
                    <p><% if (mycancerbuddiesperson.person.birthdate && !mycancerbuddiesperson.person.getBirthdate().is(null) ){ %>
                    <% if (mycancerbuddiesperson.person.age > 0) { %>
                    ${ui.message("coreapps.ageYears", mycancerbuddiesperson.person.age)}
                    <% }} %> ~ ${ui.message("coreapps.gender." + mycancerbuddiesperson.person.gender)} </p>
                    <hr>
                    <p>${ (mycancerbuddiesperson.myCancerBuddiesDescription) } </p>
                     <button href="#" class="btn btn-info btn-xs" role="button">Add Connection</button>
                </div>
            </div>
        </div>
        <% } %>
        <% } %>

    </div><!--/row-->