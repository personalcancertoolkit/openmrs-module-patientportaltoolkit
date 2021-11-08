${ui.includeFragment("patientportaltoolkit", "headerForApp")}
<script>
    jq(document).ready(function () {
        let newPatientBirthDate = jq("#newPatientBirthDate").datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function () {
            newPatientBirthDate.hide();
        }).data('datepicker');
    });
</script>

<body>
<div class="container bgcontent col-sm-offset-2">
    <form>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputFN">First Name</label>
                <input type="text" class="form-control" id="inputFN" placeholder="First Name">
            </div>
            <div class="form-group col-md-6">
                <label for="inputLN">Last Name</label>
                <input type="text" class="form-control" id="inputLN" placeholder="Last Name">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputBD">Birth Date</label>
                <input type="text" class="form-control" id="inputBD" placeholder="mm/dd/yyyy">
            </div>
            <div class="form-group col-md-6">
                <label for="inputGender">Gender</label>
                <select class="form-control" id="inputGender">
                    <option>Male</option>
                    <option>Female</option>
                </select>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>