<div class="container bgcontent col-sm-8 col-sm-offset-2" id="userProfileForm">
    <input id="personIdHolder" type="hidden" value="${ (person.getId()) }">
    <form class="form-inline" role="form"> <label>Given Name </label>
        <input class="form-control" id="userprofileGivenName" type="text" value="${ (person.getGivenName()) }"/>
    </form>
    <form class="form-inline" role="form"> <label>Family Name </label>
        <input class="form-control" id="userprofileFamilyName" type="text" value="${ (person.getFamilyName()) }"/>
    </form>
    <form class="form-inline" role="form"> <label>Gender </label>
        <select class="form-control" id="userprofileGenderSelect">
            <option value="M">Male</option>
            <option value="F">Female</option>
        </select>
    </form>
    <form class="form-inline" role="form"> <label>Date of Birth </label>
        <input class="form-control" id="userprofileDOB" type="text" value="${ person.birthdate}"/>
    </form>
    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
    <button type="button" class="btn btn-primary" id="saveuserprofile">Save</button>
</div>