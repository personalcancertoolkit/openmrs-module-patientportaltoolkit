${ ui.includeFragment("patientportaltoolkit", "patientPortalNav") }
<body>
<div>
    <div class="container bgcontent col-sm-8 col-sm-offset-2" id="userProfileForm">
        <form class="form-inline" role="form"> <label>Given Name </label>
            <input class="form-control" id="userprofileGivenName" type="text" value="${ (person.getGivenName()) }"/>
        </form>
        <form class="form-inline" role="form"> <label>Family Name </label>
            <input class="form-control" id="userprofileFamilyName" type="text" value="${ (person.getFamilyName()) }"/>
        </form>
        <form class="form-inline" role="form"> <label>Gender </label>
            <select class="form-control" id="userprofileGenderSelect">
                <option>Male</option>
                <option>Female</option>
            </select>
        </form>
        <form class="form-inline" role="form"> <label>Date of Birth </label>
            <input class="form-control" id="userprofileDOB" type="text" value="${ ui.formatDatePretty(person.birthdate) }"/>
        </form>
        <button type="button" class="btn btn-primary" id="saveuserprofile">Save</button>
    </div>
</div>
</body>