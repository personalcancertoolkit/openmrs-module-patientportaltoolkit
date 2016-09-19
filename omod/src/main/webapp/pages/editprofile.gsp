${ ui.includeFragment("patientportaltoolkit", "patientPortalNav") }
<script type="text/javascript">
    \$(document).ready(function(){
        \$('#patientPortalUserName').addClass('active');
    });
</script>
<body>
<div class="container bgcontent col-sm-8 col-sm-offset-2">

  <div id="alertContainer">
  </div>

   <h3>Edit Profile</h3>
    <br>
    <form class="form-horizontal">
        <div class="form-group">
    <input id="personIdHolder" type="hidden" value="${ (person.getId()) }">
   <label class="control-label col-xs-2" for="userprofileGivenName">Given Name </label>
            <div class="col-xs-10"><input class="form-control" id="userprofileGivenName" type="text" value="${ (person.getGivenName()) }"/></div>
</div><div class="form-group">
            <label class="control-label col-xs-2" for="userprofileFamilyName">Family Name </label>
            <div class="col-xs-10"> <input class="form-control" id="userprofileFamilyName" type="text" value="${ (person.getFamilyName()) }"/></div>
    </div><div class="form-group">
     <label class="control-label col-xs-2" for="userprofileGenderSelect">Gender </label>
            <div class="col-xs-10">
        <select class="form-control" id="userprofileGenderSelect">
            <option value="M">Male</option>
            <option value="F">Female</option>
        </select>
            </div>
    </div><div class="form-group">
    <label class="control-label col-xs-2" for="userprofileDOB">Date of Birth </label>
            <div class="col-xs-10">  <input class="form-control" id="userprofileDOB" type="text" value="${pptutil.formatDate(person.birthdate)}"/></div>
    </div><div class="form-group pull-right">
            <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
    <button type="button" class="btn btn-primary" id="saveuserprofile">Save</button>
        </div>
    </form>
</div>
</body>