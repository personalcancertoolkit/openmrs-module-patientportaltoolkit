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
                <label>Birthday</label>
                <span>
                    <small>&emsp;${ (person.birthdate) }</small>
                </span>
            </div>
        </div>
    </div>
</div>