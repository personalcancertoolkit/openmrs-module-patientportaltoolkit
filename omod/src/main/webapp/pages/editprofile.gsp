${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<script type="text/javascript">
    jq(document).ready(function(){
        jq.fn.bootstrapSwitch.defaults.onColor = 'success';
        jq.fn.bootstrapSwitch.defaults.offColor = 'danger';
        jq('#patientPortalUserName').addClass('active');
        jq("[name='userprofileMyCancerBuddies']").bootstrapSwitch(function(){

        });
    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">

  <div id="alertContainer" style = 'display:none;'>
      <div class='alert alert-dismissible alert-info'> 
          <button type='button' class='close' data-dismiss='alert'>&times;</button> 
          <strong>Message from Admin:</strong> Your Profile information has been saved! 
          Please <a href="/openmrs/logout" class='alert-link'>logout</a> and log back in to see your changes applied 
      </div>
  </div>

   <h3>Edit Profile</h3>
    <br>
    ${ ui.includeFragment("patientportaltoolkit", "changePassword") }
    <br>
    <form id="editProfileForm" class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-xs-2" for="changePassword"> Security </label>
            <div class="col-xs-10">
                <button type="button" class="btn btn-primary" id="changePassword" >Change Password</button>
            </div>
        </div>
        <div class="form-group">
            <input id="personIdHolder" type="hidden" value="${ (person.getId()) }">
            <label class="control-label col-xs-2" for="userprofileGivenName">Given Name </label>
            <div class="col-xs-10">
                <input class="form-control" id="userprofileGivenName" type="text" value="${ (person.getGivenName()) }"/>
            </div>
        </div>
        
        <div class="form-group">
            <label class="control-label col-xs-2" for="userprofileFamilyName">Family Name </label>
            <div class="col-xs-10">
                <input class="form-control" id="userprofileFamilyName" type="text" value="${ (person.getFamilyName()) }"/>
            </div>
        </div>
        
        <div class="form-group">
            <label class="control-label col-xs-2" for="userprofileGenderSelect">Gender </label>
            <div class="col-xs-10">

                <select class="form-control" id="userprofileGenderSelect">
                    <option value="M"<% if (person.getGender()=="M"){%> selected <% }%>>Male</option>
                    <option value="F"<% if (person.getGender()=="F"){%> selected <% }%>>Female</option>
                </select>

            </div>
        </div>
        
        <div class="form-group">
            <label class="control-label col-xs-2" for="userprofileDOB">Date of Birth </label>
            <div class="col-xs-10"> 
                <input class="form-control" id="userprofileDOB" type="text" value="${pptutil.formatDate(person.birthdate)}"/>
            </div>
        </div>
        
        
        <div class="form-group">
            <label class="control-label col-xs-2" for="userprofilePostalCode"> Zipcode </label>
            <div class="col-xs-10">
                <input class="form-control" id="userprofilePostalCode" type="text" value="${person.getPersonAddress() != null ? (person.getPersonAddress().getPostalCode()) : "" }"/>
            </div>
        </div>
        
        <% if (personPreferences!=null){%>
            <div class="form-group">
                <label class="control-label col-xs-2" for="userprofileMyCancerBuddies">My Cancer Buddies Status</label>
                <div class="col-xs-10"> 
                    <input class="form-control" id="userprofileMyCancerBuddies" type="checkbox" name="userprofileMyCancerBuddies"  <% if (personPreferences.myCancerBuddies==true){%>checked<% }%>/>
                </div>
            </div>
        <% }%>

        <div class="form-group pull-right">
            <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
            <button type="button" class="btn btn-primary" id="saveuserprofile">Save</button>
        </div>
    </form>
    <script>
        jq('#saveuserprofile').click(
            function () {
                logEvent('clicked_EditProfile_save','');
                
                jq.get("profileEdit/saveProfileEditForm.action", {
                    personId: jq("#personIdHolder").val(),
                    givenName: jq("#userprofileGivenName").val(),
                    familyName: jq("#userprofileFamilyName").val(),
                    gender: jq("#userprofileGenderSelect").val(),
                    birthDate: jq("#userprofileDOB").val(),
                    postalCode: jq("#userprofilePostalCode").val(),
                    myCancerBuddies: jq("#userprofileMyCancerBuddies").is(':checked')
                }, function () {
                    jq('#alertContainer').css('display','block');
                });
            });
        jq('#changePassword').click(
            function () {
                logEvent('clicked_EditProfile_changePassword','');
                jq('#editProfileForm').hide();
                jq('#changePasswordForm').show();
            });
        jq('#saveNewPasswordCancel').click(
            function () {
                logEvent('clicked_EditProfile_savePassword_cancel','');
                jq('#editProfileForm').show();
                jq('#changePasswordForm').hide();
            });
    </script>
</div>
</body>