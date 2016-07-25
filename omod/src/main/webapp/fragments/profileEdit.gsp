<div class="modal fade modal-wide"  id="profileEdit-modal" role="dialog" aria-labelledby="profileEditLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editprofileEditLabel">Profile Edit</h4>
            </div>

            <div class="modal-body">
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
                    <input class="form-control" id="userprofileDOB" type="text" value="${pptutil.formatDate(person.birthdate)}"/>
                </form>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveuserprofile">Save</button>
                </div>
            </div>
        </div>
    </div>
</div>